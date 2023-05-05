<template>
  <fs-dialog
    class="dialog-workbench"
    width="1150px"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '创建协议' : '编辑协议'"
    @closed="close"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="100px"
      label-position="right"
    >
      <fs-form-item label="协议类型:" prop="agreementType">
        <fs-dictionary-select
          v-model="formData.agreementType"
          dictionaryKey="agreement"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择协议类型"
        />
      </fs-form-item>
      <fs-form-item label="协议名称:" prop="agreementName">
        <fs-input
          maxlength="32"
          v-model="formData.agreementName"
          placeholder="请输入协议名称"
        />
      </fs-form-item>
      <fs-form-item
        label="协议内容:"
        prop="agreementContent"
        v-if="operateType !== 'new' ? formData.agreementType : true"
      >
        <div style="border: 1px solid #ccc;">
          <Toolbar
            style="border-bottom: 1px solid #ccc"
            :editor="editor"
            :defaultConfig="toolbarConfig"
            :mode="mode"
          />
          <Editor
            style="height: 300px; overflow-y: hidden;"
            v-model="formData.agreementContent"
            :defaultConfig="editorConfig"
            :mode="mode"
            @onCreated="onCreated"
          />
        </div>
      </fs-form-item>
    </fs-form>

    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler_">确 定</fs-button>
    </span>
  </fs-dialog>
</template>

<script>
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { buildUUID } from 'fosung-sdk'
import axios from 'axios'

export default {
  $plugins: 'form', // 此处是关键，必须包含form才可以！！！
  components: { Editor, Toolbar },
  name: 'ProtocolConfigDetail',
  data () {
    return {
      apiName: 'protocolconfig',
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        agreementName: [
          { required: true, message: '请输入协议名称', trigger: 'blur' }
        ],
        agreementType: [
          { required: true, message: '请选择协议类型', trigger: 'change' }
        ]
      },
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
    }
  },
  methods: {
    onCreated (editor) {
      this.editor = Object.seal(editor)
    },
    querySuccessHandler (res) {
      const { data } = res
      this.$set(this, 'formData', data)
    },
    getSubmitData () {
      let params = Object.assign({}, this.formData)
      const { terminalId } = this.parentParam
      if (terminalId) {
        params = Object.assign(params, {
          terminalId
        })
      }
      return params
    },
    submitHandler_ () {
      this.$refs[this.validRef].validate(async valid => {
        if (valid) {
          let { agreementContent, agreementUrl, agreementName } = this.formData
          if (agreementContent) {
            agreementContent = `
            <!DOCTYPE html>
            <html lang="">
              <head>
                <title>${agreementName}</title>
              </head>
              <body>
                ${agreementContent}
              </body>
            </html>
          `
            const html = new Blob([agreementContent], { type: 'text/html;charset=utf-8' })
            const formData = new FormData()
            let title = buildUUID()
            if (agreementUrl) {
              const t = agreementUrl.split('/')
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
                const { terminalId } = this.parentParam
                this.formData = Object.assign({}, this.formData, {
                  agreementUrl: data.data.url,
                  terminalId
                })
                this.submitHandler()
              } else {
                this.$delete(this.formData, 'agreementUrl')
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
<style lang="scss" scoped>
.nav-icon {
  display: flex;
  .workbench-upload:first-child {
    margin-right: 12px;
  }
}
</style>
