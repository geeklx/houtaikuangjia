<template>
  <div class="guide-config">
    <div class="guide-config__btns">
      <fs-button type="primary" @click="addImg"><i class="fs-icon-plus"></i>添加引导页</fs-button>
      <fs-button type="primary" @click="save">保存</fs-button>
    </div>
    <div class="guide-config__content">
      <fs-table border :data="dataList" class="adv-table">
        <fs-table-column label="序号" width="80" align="center">
          <template slot-scope="scope">
            {{scope.$index + 1}}
          </template>
        </fs-table-column>
        <fs-table-column label="图片地址" align="center">
          <template slot-scope="scope">
            <div class="img">
              <fs-input class="img__input" v-model="scope.row.imgUrl"/>
              <fs-upload
                class="img__img"
                :action="uploadAction"
                :show-file-list="false"
                :on-success="handleSuccess"
                :before-upload="beforeUpload"
              >
                <img src="./png/img.png" @click="handleProgress(scope)" />
              </fs-upload>
            </div>
          </template>
        </fs-table-column>
        <fs-table-column label="排序" width="80" align="center">
          <template slot-scope="scope">
            <svg-icon
              v-if="scope.$index !== 0"
              icon-class="up"
              class="up"
              @click="up(scope.$index)"
            />
            <svg-icon
              v-if="scope.$index !== dataList.length - 1"
              :style="scope.$index === 0 ? '' : {marginLeft: '12px'}"
              icon-class="down"
              class="down"
              @click="down(scope.$index)"
            />
          </template>
        </fs-table-column>
        <fs-table-column label="操作" width="90" align="center">
          <template slot-scope="scope">
            <fs-button @click="deleteImg(scope.$index)">删除</fs-button>
          </template>
        </fs-table-column>
      </fs-table>
    </div>
  </div>
</template>

<script>
import { buildUUID } from 'fosung-sdk'
export default {
  name: 'GuideConfig',
  props: {
    id: {
      type: String
    }
  },
  data () {
    return {
      formData: {},
      dataList: [],
      currData: {},
      defaultData: {}
    }
  },
  computed: {
    uploadAction () {
      const { storeInstance } = window.appContext
      const { ossUrl } = storeInstance.getters.configInfo
      return ossUrl || ''
    }
  },
  mounted () {
    this.advConfigQuery()
  },
  methods: {
    advConfigQuery () {
      const params = {
        terminalId: this.id,
        imageType: 'guide'
      }
      this.$api('terminalmanage', 'advConfigQuery', params).then(res => {
        const { datalist } = res
        this.dataList = [].concat(datalist)
        this.defaultData = JSON.parse(JSON.stringify(datalist))
      })
    },
    getForm () {
      return {
        obj: this.dataList,
        obj_: this.defaultData
      }
    },
    numReset (arr) {
      arr = arr.map((item, index) => {
        item.num = index
        return item
      })
      return arr
    },
    up (index) {
      const array = JSON.parse(JSON.stringify(this.dataList))
      if (index === 0) return false
      array[index] = array.splice(index - 1, 1, array[index])[0]
      this.dataList = [].concat(this.numReset(array))
    },
    down (index) {
      const array = JSON.parse(JSON.stringify(this.dataList))
      if (index === array.length - 1) return false
      array[index] = array.splice(index + 1, 1, array[index])[0]
      this.dataList = [].concat(this.numReset(array))
    },
    addImg () {
      if (this.dataList.length > 5) {
        this.$message.warning('最多添加六个引导页！')
        return
      }
      this.dataList = this.dataList.concat([{
        id: buildUUID(),
        imgUrl: ''
      }])
    },
    async save () {
      if (this.dataList.length === 0) {
        this.$message.warning('请至少添加一张引导页！')
        return
      }
      try {
        this.dataList.forEach(item => {
          if (!item.imgUrl || item.imgUrl === '') {
            throw new Error()
          }
        })
      } catch (e) {
        this.$message.warning('请上传图片！')
        return
      }
      const params = this.dataList.map((item, index) => {
        const { imgUrl } = item
        return {
          imageType: 'guide',
          terminalId: this.id,
          num: index,
          imgUrl
        }
      })
      await this.$api('terminalmanage', 'advConfigSave', params).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('更新成功')
          this.advConfigQuery()
        } else {
          this.$message.error(message)
        }
      })
    },
    deleteImg (index) {
      this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.dataList.splice(index, 1)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleSuccess (res) {
      const { data } = res
      const { id } = this.currData
      this.dataList = this.dataList.map(item => {
        if (item.id === id) {
          item.imgUrl = data.url
        }
        return item
      })
    },
    handleProgress (scope) {
      const { row } = scope
      this.currData = Object.assign({}, row)
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('上传图片只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      const isLt10M = file.size / 1024 / 1024 < 10
      if (!isLt10M) {
        this.$message.error('上传图片大小不能超过10MB!')
      }
      return isJPG && isLt10M
    }
  }
}
</script>

<style scoped lang="scss">
.guide-config {
  height: 100%;
  padding: 12px;
  ::v-deep.fs-upload--picture-card {
    margin-bottom: 14px;
  }
  ::v-deep.fs-upload-list__item {
    .fs-upload-list__item-actions {
      height: 40%;
      top: 60%
    }
  }
  &__btns {
    width: 100%;
    padding-bottom: 12px;
    display: flex;
    justify-content: flex-end;
  }
  &__content {
    height: calc(100% - 36px);
    overflow-y: auto;
  }
}
::v-deep.fs-dialog__header {
  height: 40px;
  line-height: 40px;
}
.adv-table {
  ::v-deep.fs-table__header-wrapper thead tr th {
    height: 40px;
    background-color: #F9F9F9;
  }
  &::v-deep .cell, &::v-deep th > .cell {
    color: #333333;
    font-size: 14px;
  }
}
.img {
  display: flex;
  align-items: center;
  &__input {
    width: calc(100% - 30px);
  }
  &__img {
    margin-left: 12px;
    width: 18px;
    height: 18px;
  }
}
::v-deep.fs-range-editor.fs-input__inner {
  width: 100%;
}
</style>
