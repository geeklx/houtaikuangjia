<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="添加发布包"
    @closed="close"
    width="702px"
  >
    <fs-form
      ref="formRef"
      :model="formData"
      label-width="100px"
      :rules="rules"
      class="wrapper-content-detail__body__form"
    >
      <fs-form-item label="选择应用:" prop="appId">
        <fs-dictionary-select
          style="width: 372px;"
          v-model="formData.appId"
          :options="appIdList"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          filterable
          placeholder="请选择应用"
          @change="appIdChange"
        />
        <div class="error-text" v-if="h5Error">该应用包已存在，请返回列表进行版本升级</div>
      </fs-form-item>
    </fs-form>
    <template v-if="formData.appType === 'h5' && !h5Error">
      <fs-form
        ref="formRef"
        :model="formData"
        label-width="100px"
        :rules="rules"
        class="wrapper-content-detail__body__form"
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
            :data="h5UploadData"
            :action="uploadAction"
            :limit="1"
            :on-remove="handleRemove"
            :before-upload="beforeUploadH5"
            :on-success="handleSuccess"
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
            placeholder="请输入版本号"
          />-->
          <fs-input
            style="width: 372px;"
            v-model="formData.versionNum"
            placeholder="请输入版本号"
          />
        </fs-form-item>
        <fs-form-item label="版本名称:" prop="versionName">
          <fs-input
            style="width: 372px;"
            maxlength="32"
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
    </template>
    <template v-if="formData.appType === 'appNative'">
      <div class="type-title">
        Android应用配置:
        <fs-switch
          style="margin-left: 16px"
          v-model="formData.isAndroid"
          @change="isAndroidChange"
        />
        <span class="error-text" v-if="androidError">该应用包已存在，请返回列表进行版本升级</span>
      </div>
      <fs-form
        style="margin-top: 16px"
        v-if="formData.isAndroid && !androidError"
        ref="formRef"
        :model="formData"
        label-width="170px"
        :rules="rules"
        class="wrapper-content-detail__body__form"
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
            style="width: 372px;"
            v-model="formData.startName"
            placeholder="请输入启动类名"
          />
        </fs-form-item>
        <fs-form-item label="启动参数:" prop="startParams">
          <fs-input
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
      <div class="type-title" style="margin-top: 16px">
        IOS应用配置:
        <fs-switch
          style="margin-left: 16px"
          v-model="formData.isIos"
          @change="isIosChange"
        />
        <span class="error-text" v-if="iosError">该应用包已存在，请返回列表进行版本升级</span>
      </div>
      <fs-form
        style="margin-top: 16px"
        v-if="formData.isIos && !iosError"
        ref="formRef"
        :model="formData"
        label-width="170px"
        :rules="rules"
        class="wrapper-content-detail__body__form"
      >
        <fs-form-item label="AppStore ID:" prop="appStoreId">
          <fs-input
            style="width: 372px;"
            v-model="formData.appStoreId"
            placeholder="请输入AppStore ID"
          />
        </fs-form-item>
        <fs-form-item label="版本号:" prop="versionNum_">
          <fs-input
            style="width: 372px;"
            v-model="formData.versionNum_"
            placeholder="请输入版本号"
          />
        </fs-form-item>
        <fs-form-item label="版本名称:" prop="versionName_">
          <fs-input
            style="width: 372px;"
            maxlength="32"
            v-model="formData.versionName_"
            placeholder="请输入版本名称"
          />
        </fs-form-item>
        <fs-form-item label="调起应用:" prop="startApp_">
          <fs-radio-group v-model="formData.startApp_">
            <fs-radio :label="true">是</fs-radio>
            <fs-radio :label="false">否</fs-radio>
          </fs-radio-group>
        </fs-form-item>
        <fs-form-item label="包名:" prop="packageName_">
          <fs-input
            style="width: 372px;"
            v-model="formData.packageName_"
            placeholder="请输入包名"
          />
        </fs-form-item>
        <fs-form-item label="启动类名:" prop="startName_">
          <fs-input
            style="width: 372px;"
            v-model="formData.startName_"
            placeholder="请输入启动类名"
          />
        </fs-form-item>
        <fs-form-item label="启动参数:" prop="startParams_">
          <fs-input
            style="width: 372px;"
            v-model="formData.startParams_"
            placeholder="请输入启动参数"
          />
        </fs-form-item>
        <fs-form-item label="IOS URL Schemes:" prop="iosUrlScemes">
          <fs-input
            style="width: 372px;"
            v-model="formData.iosUrlScemes"
            placeholder="请输入IOS URL Schemes"
          />
        </fs-form-item>
        <fs-form-item label="版本说明:" prop="remark_">
          <fs-input
            style="width: 372px;"
            maxlength="220"
            v-model="formData.remark_"
            :autosize="{ minRows: 3 }"
            type="textarea"
            placeholder="请输入版本说明"
          />
        </fs-form-item>
      </fs-form>
    </template>
    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler">确 定</fs-button>
    </span>
  </fs-dialog>
</template>

<script>
import axios from 'axios'

export default {
  $plugins: 'form',
  data () {
    return {
      rules: {
        appId: [
          { required: true, message: '请选择应用', trigger: 'change' }
        ],
        publishType: [
          { required: true, message: '请选择发布方式', trigger: 'change' }
        ],
        frontUrl: [
          { required: true, message: '请输入前端URL', trigger: 'blur' }
        ],
        appPackagePath: [
          { required: true, message: '请上传文件', trigger: 'change' }
        ],
        startApp: [
          { required: true, message: '请选择调起应用', trigger: 'change' }
        ],
        downloadPath: [
          { required: true, message: '请输入下载地址', trigger: 'blur' }
        ],
        appStoreId: [
          { required: true, message: '请输入AppStore ID', trigger: 'blur' }
        ],
        versionName: [
          { required: true, message: '版本名称不能为空', trigger: 'blur' }
        ],
        versionNum: [
          { required: true, message: '版本号不能为空', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              // if (!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value)) {
              if (!/^[0-9]+$/.test(value)) {
                callback(new Error('版本号必须为数字'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        startName: [
          { required: true, message: '请输入启动类名', trigger: 'blur' }
        ],
        startParams: [
          { required: true, message: '请输入启动参数', trigger: 'blur' }
        ],
        startName_: [
          { required: true, message: '请输入启动类名', trigger: 'blur' }
        ],
        startParams_: [
          { required: true, message: '请输入启动参数', trigger: 'blur' }
        ],
        versionName_: [
          { required: true, message: '请输入版本名称', trigger: 'blur' }
        ],
        versionNum_: [
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
        ],
        packageName: [
          { required: true, message: '包名不能为空', trigger: 'change' }
        ],
        packageName_: [
          { required: true, message: '请输入包名', trigger: 'change' }
        ],
        startApp_: [
          { required: true, message: '请选择调起应用', trigger: 'change' }
        ],
        iosUrlScemes: [
          { required: true, message: '请输入IOS URL Schemes', trigger: 'blur' }
        ]
      },
      appIdList: [],
      androidError: false,
      iosError: false,
      h5Error: false,
      h5UploadData: null,
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
    this.queryAppIdList()
  },
  methods: {
    queryAppIdList () {
      this.$api('appcenterversion', 'queryAppIdList').then(res => {
        const { success, data } = res
        if (success) {
          this.$set(this, 'appIdList', data)
        }
      })
    },
    appIdChange (val) {
      this.h5Error = false
      this.androidError = false
      this.iosError = false
      this.$set(this.formData, 'isAndroid', false)
      this.$set(this.formData, 'isIos', false)
      const info = this.appIdList.find(item => {
        return item.dictValue === val
      })
      this.$set(this.formData, 'appType', info.appType)
      if (info.appType === 'h5') {
        this.isH5()
      }
    },
    isH5 () {
      this.$api('appcenterversion', 'isExist', {
        appId: this.formData.appId,
        appType: 'h5'
      }).then(res => {
        const { message } = res
        if (!message || message === '') {
          this.h5Error = false
        } else {
          this.h5Error = true
        }
      })
    },
    isAndroidChange (val) {
      if (val) {
        this.$api('appcenterversion', 'isExist', {
          appId: this.formData.appId,
          appType: 'android'
        }).then(res => {
          const { message } = res
          if (!message || message === '') {
            this.androidError = false
          } else {
            this.androidError = true
            this.$set(this.formData, 'isAndroid', false)
          }
        })
      }
    },
    isIosChange (val) {
      if (val) {
        this.$api('appcenterversion', 'isExist', {
          appId: this.formData.appId,
          appType: 'ios'
        }).then(res => {
          const { message } = res
          if (!message || message === '') {
            this.iosError = false
          } else {
            this.iosError = true
            this.$set(this.formData, 'isIos', false)
          }
        })
      }
    },
    handleRemove () {
      this.$delete(this.formData, 'appPackagePath')
      this.$delete(this.formData, 'versionSize')
      this.$delete(this.formData, 'fileName')
      // this.$delete(this.formData, 'versionNum')
      // this.$delete(this.formData, 'versionName')
      // this.$delete(this.formData, 'packageName')
    },
    handleSuccess (res, file) {
      const { data } = res
      this.$set(this.formData, 'appPackagePath', data.url)
      this.$set(this.formData, 'versionSize', file.size)
      this.$set(this.formData, 'fileName', file.name)
      this.$refs.formRef.clearValidate('appPackagePath')
    },
    beforeUploadH5 (file) {
      const name = file.name
      const index = name.lastIndexOf('.')
      const ext = name.substr(index + 1)
      if (ext === 'zip') {
        this.$set(this, 'h5UploadData', { name })
        return new Promise((resolve) => {
          this.$nextTick(function () {
            resolve(true)
          })
        })
      }
      this.$message.error('请上传.zip格式!')
      return false
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
    submitHandler () {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          const { appType } = this.formData
          if (appType === 'h5') {
            this.saveParams(this.formData, 'saveH5')
          } else {
            const params = Object.assign({}, this.formData)
            const { isAndroid, isIos } = params
            if (isAndroid) {
              const {
                appId,
                appPackagePath,
                versionNum,
                startApp,
                downloadPath,
                remark,
                versionSize,
                versionName,
                packageName,
                startName,
                startParams,
                fileName
              } = params
              this.saveParams({
                appId,
                appType: 'android',
                appPackagePath,
                versionNum,
                startApp,
                downloadPath,
                remark,
                versionSize,
                versionName,
                packageName,
                startName,
                startParams,
                fileName
              }, 'saveAndroid')
            }
            if (isIos) {
              const {
                appId,
                appStoreId,
                versionNum_,
                versionName_,
                startApp_,
                packageName_,
                startName_,
                startParams_,
                iosUrlScemes,
                remark_
              } = params
              this.saveParams({
                appId,
                appType: 'ios',
                appStoreId,
                versionNum: versionNum_,
                versionName: versionName_,
                startApp: startApp_,
                packageName: packageName_,
                startName: startName_,
                startParams: startParams_,
                iosUrlScemes,
                remark: remark_
              }, 'saveIos')
            }
          }
        }
      })
    },
    saveParams (params, method = '') {
      this.$api('appcenteradd', method, params).then(res => {
        const { success, message } = res
        if (success) {
          this.close()
          this.$emit('query')
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.type-title {
  font-size: 16px;
  font-weight: 700;
  color: #333333;
}
.error-text {
  margin-left: 32px;
  font-size: 12px;
  color: #f94444;
  font-weight: normal;
}
</style>
