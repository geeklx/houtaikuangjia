<template>
  <div class="dialog-content">
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="100px"
      label-position="right"
    >
      <fs-form-item label="发布方式:" prop="publishType">
        <fs-radio-group v-model="formData.publishType">
          <fs-radio label="staticFile">静态文件</fs-radio>
          <fs-radio label="remoteLink">远程链接</fs-radio>
        </fs-radio-group>
      </fs-form-item>
      <fs-form-item label="前端URL:" prop="frontUrl" v-if="formData.publishType === 'remoteLink'">
        <fs-input
          style="width: 372px;"
          v-model="formData.frontUrl"
          placeholder="请输入前端URL"
        />
      </fs-form-item>
      <fs-form-item label="H5压缩包:" prop="appPackagePath" v-if="formData.publishType === 'staticFile'">
        <fs-upload
          style="width: 372px"
          class="dialog-form-upload"
          :data="uploadData"
          :action="uploadAction"
          :limit="1"
          :on-remove="handleRemove"
          :before-upload="beforeUpload"
          :on-success="handleSuccess"
          :file-list="formData.fileList"
        >
          <fs-button size="small" type="primary" plain><i class="fs-icon-upload" />点击上传</fs-button>
          <div slot="tip" class="dialog-form-upload__tip">支持格式:.zip</div>
        </fs-upload>
      </fs-form-item>
      <fs-form-item label="版本号:" prop="versionNum">
        <!--<fs-input
          style="width: 372px;"
          v-if="formData.publishType === 'remoteLink'"
          v-model="formData.versionNum"
          disabled
        />-->
        <fs-input
          style="width: 372px;"
          v-model="formData.versionNum"
          placeholder="请输入版本号"
        />
      </fs-form-item>
      <fs-form-item label="版本名称:" prop="versionName">
        <!--<fs-input
          style="width: 372px;"
          v-if="formData.publishType === 'remoteLink'"
          v-model="formData.versionName"
          disabled
        />-->
        <fs-input
          maxlength="32"
          style="width: 372px;"
          v-model="formData.versionName"
          placeholder="请输入版本名称"
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
// import axios from 'axios'

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
      queryMethod: 'saveH5',
      rules: {
        publishType: [
          { required: true, message: '请选择发布方式', trigger: 'change' }
        ],
        frontUrl: [
          { required: true, message: '请输入前端URL', trigger: 'blur' }
        ],
        appPackagePath: [
          { required: true, message: '请上传文件', trigger: 'change' }
        ],
        versionName: [
          { required: true, message: '请输入版本名称', trigger: 'blur' }
        ],
        versionNum: [
          { required: true, message: '请输入版本号', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^[0-9]+$/.test(value)) {
                callback(new Error('版本号必须为数字'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
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
      if (ext === 'zip') {
        this.$set(this, 'uploadData', { name })
        return new Promise((resolve) => {
          this.$nextTick(function () {
            resolve(true)
          })
        })
      }
      this.$message.error('请上传.zip格式!')
      return false
    },
    saveInfo () {
      this.$refs.validRef.validate(valid => {
        if (valid) {
          this.$api('appcenteradd', 'saveH5', this.formData).then(res => {
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
