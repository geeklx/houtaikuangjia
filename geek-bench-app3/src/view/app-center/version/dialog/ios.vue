<template>
  <div class="dialog-content">
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="150px"
      label-position="right"
    >
      <fs-form-item label="AppStore ID:" prop="appStoreId">
        <fs-input
          style="width: 372px;"
          v-model="formData.appStoreId"
          placeholder="请输入AppStore ID"
        />
      </fs-form-item>
      <fs-form-item label="版本号:" prop="versionNum">
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
      <fs-form-item label="包名:" prop="packageName">
        <fs-input
          style="width: 372px;"
          v-model="formData.packageName"
          placeholder="请输入包名"
        />
      </fs-form-item>
      <fs-form-item label="IOS URL Schemes:" prop="iosUrlScemes">
        <fs-input
          style="width: 372px;"
          v-model="formData.iosUrlScemes"
          placeholder="请输入IOS URL Schemes"
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
      queryMethod: 'saveIos',
      rules: {
        startApp: [
          { required: true, message: '请选择调起应用', trigger: 'change' }
        ],
        appStoreId: [
          { required: true, message: '请输入AppStore ID', trigger: 'blur' }
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
        ],
        startName: [
          { required: true, message: '请输入启动类名', trigger: 'blur' }
        ],
        startParams: [
          { required: true, message: '请输入启动参数', trigger: 'blur' }
        ],
        packageName: [
          { required: true, message: '请输入包名', trigger: 'blur' }
        ],
        iosUrlScemes: [
          { required: true, message: '请输入IOS URL Schemes', trigger: 'blur' }
        ]
      }
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
    saveInfo () {
      this.$refs.validRef.validate(valid => {
        if (valid) {
          this.$api('appcenteradd', 'saveIos', this.formData).then(res => {
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
