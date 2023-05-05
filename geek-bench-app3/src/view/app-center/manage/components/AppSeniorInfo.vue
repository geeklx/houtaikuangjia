<template>
  <div class="fs-flex fs-row-center fs-col-top wrapper-content-detail">
    <div class="wrapper-content-detail__body">
      <div class="fs-flex fs-row-center active-content">
        <div v-if="currModule === 'Android'">
          <div class="module-sub-title" style="margin-bottom: 16px">
            Android应用配置
            <fs-switch
              style="margin-left: 32px"
              v-model="formData.isAndroid"
            >
            </fs-switch>
          </div>
          <fs-form
            v-if="formData.isAndroid"
            ref="formRef"
            :model="formData"
            label-width="120px"
            :rules="rules"
            class="wrapper-content-detail__body__form"
          >
            <fs-form-item label="应用包：" prop="appPackagePath">
              <fs-upload
                style="width: 372px"
                class="active-content-upload"
                :action="uploadAction"
                :limit="1"
                :on-remove="handleRemove"
                :before-upload="beforeUpload"
                :on-success="handleSuccess">
                <fs-button size="small" type="primary" plain><i class="fs-icon-upload" />点击上传</fs-button>
                <div slot="tip" class="active-content-upload__tip">支持格式：.apk .hap .app .aab</div>
              </fs-upload>
            </fs-form-item>
            <fs-form-item label="版本号：" prop="versionName" required>
              <fs-input
                disabled
                style="width: 372px"
                v-model="formData.versionName"
              />
            </fs-form-item>
            <fs-form-item label="调起应用：" prop="startApp">
              <fs-radio-group v-model="formData.startApp">
                <fs-radio :label="true">是</fs-radio>
                <fs-radio :label="false">否</fs-radio>
              </fs-radio-group>
            </fs-form-item>
            <fs-form-item label="Bundle ID：" prop="bundleId">
              <fs-input
                style="width: 372px"
                v-model="formData.bundleId"
                placeholder="请输入Bundle ID"
              />
            </fs-form-item>
            <fs-form-item label="下载地址：" prop="downloadPath">
              <fs-input
                style="width: 372px"
                v-model="formData.downloadPath"
                placeholder="请输入下载地址"
              />
            </fs-form-item>
          </fs-form>
        </div>
        <div v-else>
          <div class="module-sub-title" style="margin-bottom: 16px">
            IOS应用配置
            <fs-switch
              style="margin-left: 32px"
              v-model="formData2.isIos"
            >
            </fs-switch>
          </div>
          <fs-form
            v-if="formData2.isIos"
            ref="formRef2"
            :model="formData2"
            label-width="180px"
            :rules="rules2"
            class="wrapper-content-detail__body__form"
          >
            <fs-form-item label="AppStore ID：" prop="appStoreId">
              <fs-input
                style="width: 372px"
                v-model="formData2.appStoreId"
                placeholder="请输入AppStore ID"
              />
            </fs-form-item>
            <fs-form-item label="版本号：" prop="versionNum">
              <fs-input
                style="width: 372px"
                v-model="formData2.versionNum"
                placeholder="请输入版本号"
              />
            </fs-form-item>
            <fs-form-item label="调起应用：" prop="startApp">
              <fs-radio-group v-model="formData2.startApp">
                <fs-radio :label="true">是</fs-radio>
                <fs-radio :label="false">否</fs-radio>
              </fs-radio-group>
            </fs-form-item>
            <fs-form-item label="Bundle ID：" prop="bundleId">
              <fs-input
                style="width: 372px"
                v-model="formData2.bundleId"
                placeholder="请输入Bundle ID"
              />
            </fs-form-item>
            <fs-form-item label="IOS URL Schemes：" prop="iosUrlScemes">
              <fs-input
                style="width: 372px"
                v-model="formData2.iosUrlScemes"
                placeholder="请输入IOS URL Schemes"
              />
            </fs-form-item>
          </fs-form>
        </div>
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
import axios from 'axios'
import Footer from './Footer.vue'
import { saveAndroidData } from '../hooks/useSteps'
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
        appPackagePath: [
          { required: true, message: '请上传应用包', trigger: 'change' }
        ],
        startApp: [
          { required: true, message: '请选择调起应用', trigger: 'change' }
        ],
        bundleId: [
          { required: true, message: '请输入Bundle ID', trigger: 'blur' }
        ],
        downloadPath: [
          { required: true, message: '请输入下载地址', trigger: 'blur' }
        ]
      },
      formData2: {},
      rules2: {
        appStoreId: [
          { required: true, message: '请输入AppStore ID', trigger: 'blur' }
        ],
        versionNum: [
          { required: true, message: '请输入版本号', trigger: 'blur' }
        ],
        startApp: [
          { required: true, message: '请选择调起应用', trigger: 'change' }
        ],
        bundleId: [
          { required: true, message: '请输入Bundle ID', trigger: 'blur' }
        ],
        iosUrlScemes: [
          { required: true, message: '请输入IOS URL Schemes', trigger: 'blur' }
        ]
      },
      currModule: ''
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
    // 默认先保存Android
    this.currModule = 'Android'
    this.$set(this.formData, 'isAndroid', true)
    // 更新Android信息
    const ANDROID = JSON.parse(sessionStorage.getItem('ANDROID'))
    if (ANDROID) this.formData = Object.assign({}, ANDROID)
  },
  methods: {
    handleNext () {
      if (this.currModule === 'Android') {
        // Android是否需要保存
        const { isAndroid } = this.formData
        if (isAndroid) {
          this.$refs.formRef.validate(valid => {
            if (valid) {
              const params = Object.assign({}, this.formData)
              const APPID = sessionStorage.getItem('APPID')
              params.appId = APPID
              params.appType = 'Android'
              // 调用Android保存接口
              this.$api('appcenteradd', 'saveAndroid', params).then(res => {
                const { success, message, data } = res
                if (success) {
                  this.$message.success('提交成功')
                  saveAndroidData(data)
                } else {
                  this.$message.error(message)
                }
              })
              this.currModule = 'IOS'
            }
          })
          return
        }
        this.currModule = 'IOS'
        this.$set(this.formData2, 'isIos', true)
      } else {
        const { isIos } = this.formData2
        if (isIos) {
          this.$refs.formRef2.validate(valid => {
            if (valid) {
              this.$emit('on-next', this.formData2)
            }
          })
          return
        }
        this.$emit('on-next', this.formData2)
      }
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
      if (ext === 'apk' || ext === 'hap' || ext === 'app' || ext === 'aab') {
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
      this.$message.error('请上传.apk .hap .app .aab格式!')
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
