<template>
  <fs-drawer
    :title="title"
    :visible.sync="visible"
    :with-header="false"
    :wrapper-closable="false"
    size="970px"
  >
    <div class="fs-detail fs-flex-col">
      <div class="fs-detail__header fs-flex fs-col-center fs-row-between">
        <div class="fs-detail__header--right fs-module-title">{{title}}</div>
        <div class="fs-detail__header--left">
          <i class="fs-icon-close" @click="close" />
        </div>
      </div>
      <div class="fs-detail__content">
        <fs-form
          :ref="validRef"
          :rules="rules"
          :model="formData"
          label-width="110px"
          label-position="right"
        >
          <fs-form-item label="应用类型:" prop="appType">
            <fs-radio-group v-model="formData.appType" v-if="operateType === 'new'">
              <fs-radio label="appNative">APP原生</fs-radio>
              <fs-radio label="h5">H5应用</fs-radio>
            </fs-radio-group>
            <div v-else>{{formData.appType === 'appNative' ? 'APP原生' : formData.appType === 'h5' ? 'H5应用' : ''}}</div>
          </fs-form-item>
          <fs-form-item label="应用编码:" prop="appCode">
            <fs-input
              maxlength="32"
              v-model="formData.appCode"
              placeholder="请输入应用编码"
            />
          </fs-form-item>
          <fs-form-item label="应用名称:" prop="appName">
            <fs-input
              maxlength="32"
              v-model="formData.appName"
              placeholder="请输入应用名称"
            />
          </fs-form-item>
          <fs-form-item label="应用图标:" prop="iconUrl">
            <fs-upload
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
            <div class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
          </fs-form-item>
          <fs-form-item label="应用提供方:" prop="appSupport">
            <fs-input
              maxlength="32"
              v-model="formData.appSupport"
              placeholder="请输入应用提供方"
            />
          </fs-form-item>
          <fs-form-item label="应用来源：" prop="inOrOut">
            <fs-radio-group v-model="formData.inOrOut">
              <fs-radio :label="true">内部{{ formData.appType === 'h5' ? 'H5' : '原生' }}</fs-radio>
              <fs-radio :label="false">{{ formData.appType === 'h5' ? '外部H5' : '第三方原生' }}</fs-radio>
            </fs-radio-group>
          </fs-form-item>
          <fs-form-item label="应用类别:" prop="categoryCode">
            <fs-dictionary-select
              v-model="formData.categoryCode"
              dictionaryKey="appCategory"
              optionLabel="dictLabel"
              optionValue="dictValue"
              clearable
              placeholder="请选择应用类别"
            />
          </fs-form-item>
          <fs-form-item label="包名:" prop="packageName">
            <fs-input
              v-model="formData.packageName"
              placeholder="请输入包名"
              :disabled="operateType !== 'new'"
            />
          </fs-form-item>
          <fs-form-item label="应用简介:" prop="remark">
            <fs-input
              maxlength="220"
              v-model="formData.remark"
              :autosize="{ minRows: 3 }"
              type="textarea"
              placeholder="请输入应用简介"
            />
          </fs-form-item>
          <fs-form-item label="是否维护:" prop="maintain">
            <fs-radio-group v-model="formData.maintain" @change="maintainChange">
              <fs-radio :label="true">是</fs-radio>
              <fs-radio :label="false">否</fs-radio>
            </fs-radio-group>
          </fs-form-item>
          <fs-form-item label="维护提示:" prop="maintainMessage" v-if="formData.maintain">
            <div style="border: 1px solid #ccc;">
              <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editor"
                :defaultConfig="toolbarConfig"
                :mode="mode"
              />
              <Editor
                style="height: 300px; overflow-y: hidden;"
                v-model="formData.maintainMessage"
                :defaultConfig="editorConfig"
                :mode="mode"
                @onCreated="onCreated"
              />
            </div>
          </fs-form-item>
          <fs-form-item label="维护标题:" prop="maintainTitle" v-if="formData.maintain">
            <fs-input
              maxlength="32"
              v-model="formData.maintainTitle"
              type="text"
              placeholder="请输入维护标题"
            />
          </fs-form-item>
          <fs-form-item label="维护背景:" prop="maintainBackgroundUrl" v-if="formData.maintain">
            <fs-upload
              class="workbench-upload"
              :action="uploadAction"
              :show-file-list="false"
              :on-success="handleSuccess2"
              :before-upload="beforeUpload"
            >
              <div v-if="formData.maintainBackgroundUrl" class="workbench-upload__operate">
                <div class="icons">
                  <i class="fs-icon-edit"></i>
                  <i class="fs-icon-delete" @click="handleRemove2"></i>
                </div>
              </div>
              <img v-if="formData.maintainBackgroundUrl" :src="formData.maintainBackgroundUrl" class="workbench-upload__avatar">
              <div v-else class="workbench-upload__icon">
                <img src="@/view/png/add-image.png" />
                <div>上传背景</div>
              </div>
            </fs-upload>
          </fs-form-item>
        </fs-form>
      </div>
      <div class="fs-detail__footer fs-flex fs-col-center fs-row-right fs-p-r-10">
        <fs-button size="medium" plain @click="close">
          取 消
        </fs-button>
        <fs-button size="medium" type="primary" @click="submitHandler_">
          保 存
        </fs-button>
      </div>
    </div>
  </fs-drawer>
</template>

<script>
import { buildUUID } from 'fosung-sdk'
import axios from 'axios'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

export default {
  $plugins: 'form',
  props: {},
  components: { Editor, Toolbar },
  data () {
    return {
      title: '创建应用',
      visible: false,

      apiName: 'appcentermanage',
      queryMethod: 'get',
      submitMethod: 'save',
      editor: null,
      toolbarConfig: {
        excludeKeys: ['insertTable', 'group-video', 'insertImage']
      },
      editorConfig: {
        placeholder: '请输入内容...',
        MENU_CONF: {}
      },
      mode: 'default'
    }
  },
  computed: {
    uploadAction () {
      const { storeInstance } = window.appContext
      const { ossUrl } = storeInstance.getters.configInfo
      return ossUrl || ''
    },
    rules () {
      return {
        packageName: [
          { required: true, message: '请输入包名', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^[a-zA-Z][a-zA-Z0-9_.]+$/.test(value)) {
                callback(new Error('包名必须为字母、数字、下划线、点组成,且必须字母开头'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        appType: [
          { required: true, message: '请选择应用类型', trigger: 'change' }
        ],
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
        inOrOut: [
          { required: true, message: '请选择应用来源', trigger: 'change' }
        ],
        categoryCode: [
          { required: true, message: '请选择应用类别', trigger: 'change' }
        ],
        maintain: [
          { required: true, message: '请选择是否维护', trigger: 'change' }
        ],
        maintainMessage: [
          { required: true, message: '请填写维护提示', trigger: 'change' },
          {
            validator: function (rule, value, callback) {
              if (!value || !value.replace(/[\r\n]/g, '')) {
                callback(new Error('请填写维护提示'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    open (title, id) {
      this.title = title
      if (title === '编辑应用') {
        this.config = 'edit'
        this.$api('appcentermanage', 'get', { id }).then(res => {
          const { data } = res
          this.$set(this, 'formData', data)
          this.visible = true
        })
        return
      }
      this.visible = true
    },
    closeAfterHandler () {
      this.resetFormValid()// 重置表单
      this.visible = false
    },
    submitSuccessHandler () {
      this.close()
      this.$emit('query')
    },
    beforeCreateHandler () {
      this.$set(this.formData, 'maintain', false)
    },
    querySuccessHandler (res) {
      const { data } = res
      this.$set(this, 'formData', data)
    },
    onCreated (editor) {
      this.editor = Object.seal(editor)
    },
    handleRemove2 (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'maintainBackgroundUrl')
    },
    handleSuccess2 (res) {
      const { data } = res
      this.$set(this.formData, 'maintainBackgroundUrl', data.url)
      this.$refs[this.validRef].clearValidate('maintainBackgroundUrl')
    },
    handleRemove (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'iconUrl')
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'iconUrl', data.url)
      this.$refs[this.validRef].clearValidate('iconUrl')
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      return isJPG
    },
    maintainChange (val) {
      if (val) this.$set(this.formData, 'maintainMessage', '')
    },
    submitHandler_ () {
      this.$refs[this.validRef].validate(async valid => {
        if (valid) {
          const { maintainMessage, maintainUrl, maintain } = this.formData
          if (maintain) {
            const maintainMessage_ = `
            <!DOCTYPE html>
            <html lang="">
              <head>
                <title>维护提示</title>
              </head>
              <body>
                ${maintainMessage}
              </body>
            </html>
          `
            const html = new Blob([maintainMessage_], { type: 'text/html;charset=utf-8' })
            const formData = new FormData()
            let title = buildUUID()
            if (maintainUrl) {
              const t = maintainUrl.split('/')
              title = t[t.length - 1]
              title = title.substring(0, title.length - 5)
            }
            formData.append('file', html)
            formData.append('name', `${title}.html`)
            await axios({
              method: 'post',
              url: this.uploadAction,
              data: formData
            }).then(res => {
              const { success, message, data } = res
              if (!success) {
                this.$set(this.formData, 'maintainUrl', data.data.url)
                this.submitHandler()
              } else {
                this.$delete(this.formData, 'maintainUrl')
                this.$message.error(message)
              }
            })
            return
          }
          this.submitHandler()
        }
      })
    }
  },
  created () {
    const that = this
    this.editorConfig.MENU_CONF.uploadImage = {
      maxFileSize: 10 * 1024 * 1024,
      customUpload (file, insertFn) {
        const data = new FormData()
        data.append('file', file)
        axios({
          method: 'post',
          url: that.uploadAction,
          headers: {
            'Content-Type': 'multipart/form-data'
          },
          data
        }).then(({ data }) => {
          const { data: res, success } = data
          if (!success) return
          const { url } = res
          insertFn(url)
        })
      }
    }
  },
  beforeDestroy () {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  }
}
</script>

<style scoped lang="scss">
.height-auto {
  height: 100%;
}
.width-auto {
  width: 100%;
}
.fs-detail {
  @extend .height-auto;
  &__header {
    @extend .width-auto;
    height: 44px;
    padding: 14px 20px;
    border-bottom: 1px solid #E4E7ED;
    &--right {
      color: #333333;
      font-size: 16px;
    }
  }
  &__content {
    @extend .height-auto;
    flex: 1;
    overflow-y: auto;
    padding: 30px;
  }
  &__footer {
    @extend .width-auto;
    height: 78px;
    border-top: 1px solid #E4E7ED;
  }
}
</style>
