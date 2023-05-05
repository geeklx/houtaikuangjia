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
          <fs-form-item label="发布方式：" prop="publishType">
            <fs-radio-group v-model="formData.publishType">
              <fs-radio label="staticFile">静态文件</fs-radio>
              <fs-radio label="remoteLink">远程链接</fs-radio>
            </fs-radio-group>
          </fs-form-item>
          <fs-form-item label="前端URL：" prop="frontUrl" v-if="formData.publishType === 'remoteLink'">
            <fs-input
              style="width: 372px"
              v-model="formData.frontUrl"
              placeholder="请输入前端URL"
            />
          </fs-form-item>
          <fs-form-item label="H5压缩包：" prop="appPackagePath" v-if="formData.publishType === 'staticFile'">
            <fs-upload
              style="width: 372px"
              class="dialog-form-upload"
              :action="uploadAction"
              :limit="1"
              :on-remove="handleRemove"
              :before-upload="beforeUpload"
              :on-success="handleSuccess">
              <fs-button size="small" type="primary" plain><i class="fs-icon-upload" />点击上传</fs-button>
              <div slot="tip" class="dialog-form-upload__tip">支持格式：.zip</div>
            </fs-upload>
          </fs-form-item>
          <fs-form-item label="版本号：" prop="versionNum">
            <fs-input
              style="width: 372px"
              v-if="formData.publishType === 'staticFile'"
              v-model="formData.versionName"
              disabled
            />
            <fs-input
              v-else
              style="width: 372px"
              v-model="formData.versionNum"
              placeholder="请输入版本号"
            />
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
import axios from 'axios'
export default {
  name: 'SeniorInfo',
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
        publishType: [
          { required: true, message: '请选择发布方式', trigger: 'change' }
        ],
        frontUrl: [
          { required: true, message: '请输入前端URL', trigger: 'blur' }
        ],
        appPackagePath: [
          { required: true, message: '请上传H5压缩包', trigger: 'change' }
        ]
      },
      currModule: 'Android'
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
    // 更新H5信息
    const H5 = JSON.parse(sessionStorage.getItem('H5'))
    if (H5) this.formData = Object.assign({}, H5)
  },
  methods: {
    handleNext () {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.$emit('on-next', this.formData)
        }
      })
    },
    handlePrev () {
      this.$emit('on-prev')
    },
    handleRemove () {
      this.$delete(this.formData, 'appPackagePath')
      this.$delete(this.formData, 'versionSize')
      this.$delete(this.formData, 'versionNum')
      this.$delete(this.formData, 'versionName')
      this.$delete(this.formData, 'packageName')
    },
    handleSuccess (res, file) {
      const { data } = res
      this.$set(this.formData, 'appPackagePath', data.url)
      this.$set(this.formData, 'versionSize', file.size)
      this.$refs.formRef.clearValidate('appPackagePath')
    },
    beforeUpload (file) {
      const name = file.name
      const index = name.lastIndexOf('.')
      const ext = name.substr(index + 1)
      if (ext === 'zip') {
        const APPID = sessionStorage.getItem('APPID')
        const formInfo = new FormData()
        formInfo.append('file', file)
        formInfo.append('appId', APPID)
        axios.post('/api/application/version/parse/apk', formInfo, {
          headers: {
            'content-Type': 'multipart/form-data'
          },
          timeout: 300000
        }).then(res => {
          const { success, message, data } = res
          if (success) {
            const { versionCode, versionName, appPackageName } = data
            this.$set(this.formData, 'versionNum', versionCode)
            this.$set(this.formData, 'versionName', versionName)
            this.$set(this.formData, 'packageName', appPackageName)
            this.$refs.formRef.clearValidate(['versionNum', 'versionName', 'packageName'])
          } else {
            this.$delete(this.formData, 'appPackagePath')
            this.$delete(this.formData, 'versionSize')
            this.$delete(this.formData, 'versionNum')
            this.$delete(this.formData, 'versionName')
            this.$delete(this.formData, 'packageName')
            this.$message.error(message)
          }
        })
        return true
      }
      this.$message.error('请上传.zip格式!')
      return false
    }
  }
}
</script>

<style scoped lang="scss">
.active-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  &-upload {
    width: 800px;
    &__tip {
      color: #999999;
      font-size: 14px;
    }
  }
}
</style>
