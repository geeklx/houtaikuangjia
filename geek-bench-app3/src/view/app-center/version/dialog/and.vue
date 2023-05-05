<template>
  <div class="dialog-content">
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="100px"
      label-position="right"
    >
      <fs-form-item label="应用包:" prop="appPackagePath">
        <fs-upload
          style="width: 372px"
          class="active-content-upload"
          :data="uploadData"
          :action="uploadAction"
          :limit="1"
          :on-remove="handleRemove"
          :before-upload="beforeUpload"
          :on-success="handleSuccess"
          :file-list="formData.fileList"
        >
          <fs-button size="small" type="primary" plain><i class="fs-icon-upload" />点击上传</fs-button>
          <div slot="tip" class="active-content-upload__tip">支持格式:.apk .hap .app .aab</div>
        </fs-upload>
      </fs-form-item>
      <fs-form-item label="版本号:" prop="versionNum" required>
        <fs-input
          style="width: 372px;"
          disabled
          v-model="formData.versionNum"
        />
      </fs-form-item>
      <fs-form-item label="版本名称:" prop="versionName" required>
        <fs-input
          style="width: 372px;"
          disabled
          v-model="formData.versionName"
        />
      </fs-form-item>
      <fs-form-item label="包名:" prop="packageName" required>
        <fs-input
          style="width: 372px;"
          disabled
          v-model="formData.packageName"
        />
      </fs-form-item>
      <fs-form-item label="调起应用:" prop="startApp">
        <fs-radio-group v-model="formData.startApp">
          <fs-radio :label="true">是</fs-radio>
          <fs-radio :label="false">否</fs-radio>
        </fs-radio-group>
      </fs-form-item>
      <fs-form-item label="启动类名:" prop="startName">
        <fs-input
          maxlength="220"
          style="width: 372px;"
          v-model="formData.startName"
          placeholder="请输入启动类名"
        />
      </fs-form-item>
      <fs-form-item label="启动参数:" prop="startParams">
        <fs-input
          maxlength="220"
          style="width: 372px;"
          v-model="formData.startParams"
          placeholder="请输入启动参数"
        />
      </fs-form-item>
      <fs-form-item label="下载地址:" prop="downloadPath">
        <fs-input
          style="width: 372px;"
          v-model="formData.downloadPath"
          placeholder="请输入下载地址"
        />
      </fs-form-item>
      <fs-form-item label="版本说明:" prop="remark">
        <fs-input
          style="width: 372px;"
          maxlength="220"
          v-model="formData.remark"
          :autosize="{ minRows: 3 }"
          type="textarea"
          placeholder="请输入版本说明"
        />
      </fs-form-item>
    </fs-form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  $plugins: 'form', // 此处是关键，必须包含page才可以！！！
  props: {
    appConfig: {
      type: Object
    },
    appId: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'appcenteradd',
      queryMethod: 'saveAndroid',
      rules: {
        versionNum: [
          { required: true, message: '版本号不能为空', trigger: 'blur' }
        ],
        versionName: [
          { required: true, message: '版本名称不能为空', trigger: 'blur' }
        ],
        packageName: [
          { required: true, message: '包名不能为空', trigger: 'blur' }
        ],
        appPackagePath: [
          { required: true, message: '请上传应用包', trigger: 'change' }
        ],
        startApp: [
          { required: true, message: '请选择调起应用', trigger: 'change' }
        ],
        startName: [
          { required: true, message: '请输入启动类名', trigger: 'blur' }
        ],
        startParams: [
          { required: true, message: '请输入启动参数', trigger: 'blur' }
        ],
        downloadPath: [
          { required: true, message: '请输入下载地址', trigger: 'blur' }
        ]
      },

      uploadData: null
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
    this.formData = Object.assign({}, this.appConfig, {
      appId: this.appId
    })
  },
  methods: {
    // 默认请求参数处理，可以将查询条件之外的其他参数返回给框架
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        id: this.id
      })
    },
    handleRemove () {
      this.$delete(this.formData, 'appPackagePath')
      this.$delete(this.formData, 'versionSize')
      this.$delete(this.formData, 'versionNum')
      this.$delete(this.formData, 'versionName')
      this.$delete(this.formData, 'packageName')
      this.$delete(this.formData, 'fileName')
    },
    handleSuccess (res, file) {
      const { data } = res
      this.$set(this.formData, 'appPackagePath', data.url)
      this.$set(this.formData, 'versionSize', file.size)
      this.$set(this.formData, 'fileName', file.name)
      this.$refs[this.validRef].clearValidate('appPackagePath')
    },
    beforeUpload (file) {
      const name = file.name
      const index = name.lastIndexOf('.')
      const ext = name.substr(index + 1)
      if (ext === 'apk' || ext === 'hap' || ext === 'app' || ext === 'aab') {
        const formInfo = new FormData()
        formInfo.append('file', file)
        formInfo.append('appId', this.formData.appId)
        axios.post('/api/application/version/parse/apk', formInfo, {
          headers: {
            'content-Type': 'multipart/form-data'
          },
          timeout: 300000
        }).then(res => {
          const { success, message, data } = res.data
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
            this.$delete(this.formData, 'fileName')
            this.$message.error(message)
          }
        })

        this.$set(this, 'uploadData', { name })
        return new Promise((resolve) => {
          this.$nextTick(function () {
            resolve(true)
          })
        })
      }
      this.$message.error('请上传.apk .hap .app .aab格式!')
      return false
    },
    saveInfo () {
      this.$refs.validRef.validate(valid => {
        if (valid) {
          this.$api('appcenteradd', 'saveAndroid', this.formData).then(res => {
            const { success, message } = res
            if (!success) {
              this.$message({
                type: 'error',
                message
              })
              return
            }
            this.$emit('submitSuccess')
          })
        }
      })
    }
  }
}
</script>
