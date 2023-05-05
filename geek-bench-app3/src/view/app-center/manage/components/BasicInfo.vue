<template>
  <div class="fs-flex fs-row-center fs-col-top wrapper-content-detail">
    <div class="wrapper-content-detail__body">
      <div class="fs-flex fs-row-center active-content">
        <fs-form
          ref="formRef"
          :model="formData"
          label-width="120px"
          :rules="rules"
          class="wrapper-content-detail__body__form"
        >
          <fs-form-item label="应用编码：" prop="appCode">
            <fs-input
              style="width: 372px"
              maxlength="32"
              v-model="formData.appCode"
              placeholder="请输入应用编码"
            />
          </fs-form-item>
          <fs-form-item label="应用名称：" prop="appName">
            <fs-input
              style="width: 372px"
              maxlength="32"
              v-model="formData.appName"
              placeholder="请输入应用名称"
            />
          </fs-form-item>
          <fs-form-item label="应用图标：" prop="iconUrl">
            <fs-upload
              style="width: 372px"
              class="workbench-upload"
              :action="uploadAction"
              :show-file-list="false"
              :on-success="handleSuccess"
              :before-upload="beforeUpload"
            >
              <div v-if="formData.iconUrl" class="workbench-upload__operate">
                <div class="icons">
                  <i class="fs-icon-edit"></i>
                  <i class="fs-icon-delete" @click="handleRemove"></i>
                </div>
              </div>
              <img v-if="formData.iconUrl" :src="formData.iconUrl" class="workbench-upload__avatar">
              <div v-else class="workbench-upload__icon">
                <img src="@/view/png/add-image.png" />
                <div>上传图标</div>
              </div>
            </fs-upload>
            <div style="width: 372px" class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
          </fs-form-item>
          <fs-form-item label="应用提供方：" prop="appSupport">
            <fs-input
              style="width: 372px"
              maxlength="32"
              v-model="formData.appSupport"
              placeholder="请输入应用提供方"
            />
          </fs-form-item>
          <fs-form-item label="应用类别：" prop="categoryCode">
            <fs-dictionary-select
              style="width: 372px"
              v-model="formData.categoryCode"
              dictionaryKey="appCategory"
              optionLabel="dictLabel"
              optionValue="dictValue"
              clearable
              placeholder="请选择应用类别"
            />
          </fs-form-item>
          <fs-form-item label="应用简介：" prop="remark">
            <fs-input
              style="width: 800px"
              maxlength="220"
              v-model="formData.remark"
              :autosize="{ minRows: 3 }"
              type="textarea"
              placeholder="请输入应用简介"
            />
          </fs-form-item>
          <fs-form-item label="是否维护：" prop="maintain">
            <fs-radio-group v-model="formData.maintain" @change="maintainChange">
              <fs-radio :label="true">是</fs-radio>
              <fs-radio :label="false">否</fs-radio>
            </fs-radio-group>
          </fs-form-item>
          <fs-form-item label="维护提示：" prop="maintainMessage" v-if="formData.maintain">
            <fs-rich-editor
              style="width: 800px"
              v-model="formData.maintainMessage"
              :height="300"
            ></fs-rich-editor>
          </fs-form-item>
        </fs-form>
      </div>
    </div>

    <step-footer
      class="wrapper-content-detail__bottom fs-flex fs-row-right"
      :active="active"
      @on-next="handleNext"
      @on-prev="handlePrev"
    ></step-footer>
  </div>
</template>

<script>
import Footer from './Footer.vue'
export default {
  name: 'BasicInfo',
  props: {
    active: {
      type: Number,
      required: true
    }
  },
  components: {
    StepFooter: Footer
  },
  data () {
    return {
      formData: {},
      rules: {
        appCode: [
          { required: true, message: '请输入应用编码', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^[a-zA-Z0-9_-]+$/.test(value)) {
                callback(new Error('应用编码必须为字母、数字、下划线、中划线组成'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        appName: [
          { required: true, message: '请输入应用名称', trigger: 'blur' }
        ],
        iconUrl: [
          { required: true, message: '请上传图标', trigger: 'blur' }
        ],
        appSupport: [
          { required: true, message: '请输入应用提供方', trigger: 'blur' }
        ],
        categoryCode: [
          { required: true, message: '请选择应用类别', trigger: 'change' }
        ],
        maintain: [
          { required: true, message: '请选择是否维护', trigger: 'change' }
        ]
      }
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
    const BASIC = JSON.parse(sessionStorage.getItem('BASIC'))
    const APPTYPE = sessionStorage.getItem('APPTYPE')
    if (BASIC && BASIC.appType === APPTYPE) this.formData = Object.assign({}, BASIC)
  },
  methods: {
    handleNext () {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.$emit('on-next', this.formData)
        }
      })
    },
    handleRemove (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'iconUrl')
    },
    handlePrev () {
      this.$emit('on-prev')
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'iconUrl', data.url)
      this.$refs.formRef.clearValidate('iconUrl')
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('上传图片只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      return isJPG
    },
    maintainChange (val) {
      if (val) this.$set(this.formData, 'maintainMessage', '')
    }
  }
}
</script>

<style scoped lang="scss">
</style>
