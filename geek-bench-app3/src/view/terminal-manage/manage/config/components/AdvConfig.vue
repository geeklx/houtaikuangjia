<template>
  <div class="adv-config">
    <div class="adv-config__btns">
      <fs-button type="primary" @click="addImg"><i class="fs-icon-plus"></i>添加广告页</fs-button>
      <fs-button type="primary" @click="save">保存</fs-button>
    </div>
    <div class="adv-config__content">
      <!--<fs-upload
        :action="uploadAction"
        list-type="picture-card"
        :file-list="fileList"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
      >
        <i slot="default" class="fs-icon-plus"></i>
        <div slot="file" slot-scope="{file}">
          <img class="fs-upload-list__item-thumbnail" :src="file.url">
          <span class="fs-upload-list__item-actions">
          <span class="fs-upload-list__item-preview" @click="handlePreview(file)">
            <i class="fs-icon-zoom-in"></i>
          </span>
          <span class="fs-upload-list__item-delete" @click="handleRemove(file)">
            <i class="fs-icon-delete"></i>
          </span>
        </span>
        </div>
      </fs-upload>
      <div class="workbench-upload-text">Png格式,图标建议尺寸：56*56px,如图标较小,需周围留白</div>
      <fs-dialog class="dialog-workbench" title="图片查看" :visible.sync="imgVisible">
        <img width="100%" :src="imgUrl" alt="">
      </fs-dialog>-->
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
        <fs-table-column label="链接地址" align="center">
          <template slot-scope="scope">
            <fs-input v-model="scope.row.linkUrl" placeholder="链接地址"/>
          </template>
        </fs-table-column>
        <fs-table-column label="指定日期" align="center">
          <template slot-scope="scope">
            <fs-date-picker
              v-model="scope.row.time"
              unlink-panels
              @change="e => timeChange(e, scope)"
              type="daterange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd">
            </fs-date-picker>
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
  name: 'AdvConfig',
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
      defaultData: []
      // imgVisible: false,
      // imgUrl: '',
      // fileList: []
    }
  },
  computed: {
    uploadAction () {
      const { storeInstance } = window.appContext
      const { ossUrl } = storeInstance.getters.configInfo
      return ossUrl || ''
    },
    pickerOptions () {
      return {
        disabledDate (time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        }
      }
    }
  },
  mounted () {
    this.advConfigQuery()
  },
  methods: {
    advConfigQuery () {
      const params = {
        terminalId: this.id,
        imageType: 'advert'
      }
      this.$api('terminalmanage', 'advConfigQuery', params).then(res => {
        let { datalist } = res
        datalist = datalist.map(item => {
          let { startTime, endTime } = item
          let obj = {
            ...item
          }
          if (startTime && endTime) {
            startTime = startTime.substring(0, 10)
            endTime = endTime.substring(0, 10)
            obj = Object.assign(obj, {
              time: [startTime, endTime]
            })
          }
          return obj
        })
        this.defaultData = JSON.parse(JSON.stringify(datalist))
        this.dataList = [].concat(datalist)
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
      this.dataList = this.dataList.concat([{
        id: buildUUID(),
        imgUrl: '',
        linkUrl: '',
        time: []
      }])
    },
    async save () {
      if (this.dataList.length === 0) {
        this.$message.warning('请至少添加一张广告页！')
        return
      }
      try {
        this.dataList.forEach(item => {
          // if (!item.time || item.time.length === 0 || !item.imgUrl || item.imgUrl === '') {
          if (!item.imgUrl || item.imgUrl === '') {
            throw new Error()
          }
        })
      } catch (e) {
        this.$message.warning('图片地址不可为空！')
        return
      }
      const params = this.dataList.map((item, index) => {
        const { imgUrl, linkUrl } = item
        let obj = {
          imageType: 'advert',
          terminalId: this.id,
          num: index,
          imgUrl,
          linkUrl
        }
        if (item.time && item.time.length > 0) {
          const [startTime, endTime] = item.time
          if (startTime && endTime) {
            obj = Object.assign(obj, {
              startTime: startTime + ' 00:00:00',
              endTime: endTime + ' 23:59:59'
            })
          }
        }
        return obj
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
    },
    timeChange (e, row) {
      if (!e) return
      const [startTime, endTime] = e
      const { $index } = row
      try {
        this.dataList.forEach((item, index) => {
          if (index !== $index && item.time && item.time.length > 0) {
            const [startTime_, endTime_] = item.time
            const flag = this.isDateBetween(new Date(startTime), new Date(endTime), new Date(startTime_), new Date(endTime_), true)
            if (flag) {
              throw new Error()
            }
          }
        })
      } catch (e) {
        this.dataList = this.dataList.map((item, index) => {
          if (index === $index) {
            item.time = []
          }
          return item
        })
        this.$message.warning('所选择时间段内已存在广告投放！')
      }
    },
    isDateBetween (startTime, endTime, startTime_, endTime_, isStrict) {
      if (isStrict) {
        if (!(startTime < startTime_ || startTime > endTime_)) {
          return true
        }
      } else {
        if (!(startTime <= startTime_ || startTime >= endTime_)) {
          return true
        }
      }
      return false
    }
    // handleSuccess (res, file, fileList) {
    //   const { success } = res
    //   if (success) {
    //     this.fileList = this.fileList.concat([file])
    //   }
    // },
    // beforeUpload (file) {
    //   const isJPG = file.type === 'image/png'
    //   if (!isJPG) {
    //     this.$message.error('上传头像图片只能是 PNG 格式!')
    //   }
    //   return isJPG
    // },
    // handleRemove (file) {
    //   const { uid } = file
    //   let inx = ''
    //   this.fileList.find((item, index) => {
    //     if (item.uid === uid) inx = index
    //     return item.uid === uid
    //   })
    //   this.fileList.splice(inx, 1)
    // },
    // handlePreview (file) {
    //   this.imgUrl = file.url
    //   this.imgVisible = true
    // }
  }
}
</script>

<style scoped lang="scss">
.adv-config {
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
