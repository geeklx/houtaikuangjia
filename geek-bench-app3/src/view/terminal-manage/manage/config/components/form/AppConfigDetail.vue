<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '创建分类' : '编辑分类'"
    @closed="close"
    width="702px"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="100px"
      label-position="right"
    >
      <fs-form-item label="分类名称:" prop="name">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.name"
          placeholder="请输入分类名称"
        />
      </fs-form-item>
      <fs-form-item label="分类类型:" prop="type">
        <fs-select
          v-if="operateType === 'new'"
          style="width: 372px"
          v-model="formData.type"
          placeholder="请选择分类类型"
          clearable
          @change="typeChange"
        >
          <fs-option
            v-for="item in typeList"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
            :disabled="item.disabled"
          />
        </fs-select>
        <fs-input
          v-else
          style="width: 372px"
          disabled
          v-model="formData.type_dict"
        />
      </fs-form-item>
      <fs-form-item label="选择地域:" prop="area" v-if="formData.type === 'regional'">
        <fs-cascader v-model="formData.area"  :show-all-levels="true" :props="getProps" @change="areaChange" />
      </fs-form-item>
      <fs-form-item label="底部导航:" prop="navigationBtmId" v-if="formData.type !== 'me' && formData.type !== 'all'">
        <fs-input
          readonly
          style="width: 332px"
          :value="btmValue"
          placeholder="请点击右侧选择"
        />
        <fs-button class="fs-m-l-10" type="text" @click="selectNav">选择</fs-button>
        <select-nav
          v-if="navVisible"
          ref="selectNavRef"
          :nav-visible="navVisible"
          :type="formData.type"
          :terminalId="parentParam.terminalId"
          :area="formData.type === 'regional' ? formData.area.join('-') : null"
          @close="navClose"
          @submit="navSubmit"
        />
      </fs-form-item>
      <fs-form-item label="分类图标:" prop="logoUrl">
        <div class="nav-icon">
          <fs-upload
            style="width: 372px"
            class="workbench-upload"
            :action="uploadAction"
            :show-file-list="false"
            :on-success="handleSuccess"
            :before-upload="beforeUpload"
          >
            <div v-if="formData.logoUrl" class="workbench-upload__operate">
              <div class="icons">
                <i class="fs-icon-edit"></i>
                <i class="fs-icon-delete" @click="handleRemove"></i>
              </div>
            </div>
            <img v-if="formData.logoUrl" :src="formData.logoUrl" class="workbench-upload__avatar" />
            <div v-else class="workbench-upload__icon">
              <img src="@/view/png/add-image.png" />
              <div>上传图标</div>
            </div>
          </fs-upload>
        </div>
        <div style="width: 372px" class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
      </fs-form-item>
      <fs-form-item label="描述说明" prop="remark">
        <fs-input
          maxlength="220"
          style="width: 372px"
          :autosize="{ minRows: 3 }"
          type="textarea"
          v-model="formData.remark"
          placeholder="请输入描述说明"
        />
      </fs-form-item>
    </fs-form>

    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler">确 定</fs-button>
    </span>
  </fs-dialog>
</template>

<script>
import { isEmpty } from 'fosung-sdk'

export default {
  $plugins: 'form', // 此处是关键,必须包含form才可以！！！
  name: 'AppConfigDetail',
  components: {
    SelectNav: () => import('./dialog/SelectNav')
  },
  data () {
    return {
      apiName: 'appconfig',
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择分类类型', trigger: 'change' }
        ],
        area: [
          { required: true, message: '请选择地域', trigger: 'change' }
        ],
        navigationBtmId: [
          { required: true, message: '底部导航不可为空', trigger: 'blur' }
        ]
      },
      typeList: [],
      level: 0,
      navVisible: false,
      navigationBtmList: []
    }
  },
  computed: {
    getProps () {
      return { lazy: true, lazyLoad: this.getLazyLoad, label: 'dictLabel', value: 'dictLabel', checkStrictly: true }
    },
    uploadAction () {
      const { storeInstance } = window.appContext
      const { ossUrl } = storeInstance.getters.configInfo
      return ossUrl || ''
    },
    btmValue () {
      if (isEmpty(this.formData)) return ''
      let val = ''
      if (this.formData.type === 'regional') {
        this.formData.area && (val = (Array.isArray(this.formData.area) ? this.formData.area.join('-') : this.formData.area) + '-')
        this.formData.navigationBtmName && (val += this.formData.navigationBtmName)
      } else {
        this.formData.navigationBtmArea && this.formData.navigationBtmName && (val = this.formData.navigationBtmArea + '-' + this.formData.navigationBtmName)
      }
      if (this.formData.navigationBtmName) this.$refs[this.validRef].clearValidate('navigationBtmId')
      return val
    }
  },
  methods: {
    getLazyLoad (node, resolve) {
      this.$api('terminalversion', 'queryUpgradeRangeValuesList', { cityCode: node.data && node.data.dictValue ? node.data.dictValue : null }).then((res) => {
        const data = res.datalist.map(item => {
          item.leaf = node.level >= 1
          return item
        })
        resolve(data)
      })
    },
    typeChange () {
      this.$set(this.formData, 'area', null)
      this.$set(this.formData, 'navigationBtmId', null)
      this.$set(this.formData, 'navigationBtmName', null)
      this.$set(this.formData, 'navigationBtmArea', null)
    },
    areaChange () {
      this.$set(this.formData, 'navigationBtmId', null)
      this.$set(this.formData, 'navigationBtmName', null)
    },
    // 创建实例之前
    beforeCreateHandler () {
      this.queryType()
      this.$set(this.formData, 'type', 'routine')
    },
    queryType () {
      this.$api('appconfig', 'queryType').then(res => {
        let { datalist } = res
        datalist = datalist.map(item => {
          item.disabled = item.dictValue === 'me' || item.dictValue === 'all'
          return item
        })
        this.$set(this, 'typeList', datalist)
      })
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'logoUrl', data.url)
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('上传图片只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      return isJPG
    },
    handleRemove (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'logoUrl')
    },
    getSubmitData () {
      const { terminalId } = this.parentParam
      return Object.assign(this.formData, {
        terminalId,
        area: Array.isArray(this.formData.area) ? this.formData.area.join('-') : ''
      })
    },
    getResponseInfo (response) {
      response.data.navigationBtmArea = response.data.routineArea
      if (response.data.area) {
        let area = []
        if (!Array.isArray(response.data.area)) {
          area = response.data.area.split('-')
        } else if (typeof response.data.area === 'string') {
          area = [response.data.area]
        }
        response.data.area = area
      }
      return response
    },
    selectNav () {
      if (this.formData.type === 'regional' && !this.formData.area) {
        this.$message.warning('请选择地域！')
        return
      }
      this.navVisible = true
    },
    navClose () {
      this.navVisible = false
    },
    navSubmit (info) {
      const { area, navigationName, intId } = info
      this.$set(this.formData, 'navigationBtmId', intId)
      this.$set(this.formData, 'navigationBtmName', navigationName)
      this.$set(this.formData, 'navigationBtmArea', area)
    }
  }
}
</script>
<style lang="scss" scoped>
.nav-icon {
  display: flex;
  .workbench-upload:first-child {
    margin-right: 12px;
  }
}
</style>
