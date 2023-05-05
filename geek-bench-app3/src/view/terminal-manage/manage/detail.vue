<template>
  <fs-dialog
    class="dialog-workbench"
    width="1150px"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '创建终端' : '编辑终端'"
    @closed="close"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="100px"
      label-position="right"
    >
      <fs-form-item label="选择项目:" prop="projectId">
        <!-- readonly 编辑与新建弹窗默认为false,查看弹窗默认为true -->
        <fs-dictionary-select
          v-model="formData.projectId"
          url="/api/project/basic/query/option"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          filterable
          :isCache="false"
          :disabled="operateType !== 'new'"
          placeholder="请选择项目"
        />
      </fs-form-item>
      <fs-form-item label="选择专区:" prop="zoneId">
        <fs-dictionary-select
          v-model="formData.zoneId"
          dictionaryKey="zone"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择专区"
        />
      </fs-form-item>
      <fs-form-item label="终端名称:" prop="terminalName">
        <fs-input
          maxlength="32"
          v-model="formData.terminalName"
          placeholder="请输入终端名称"
        />
      </fs-form-item>
      <fs-form-item label="终端编码:" prop="terminalCode">
        <fs-input
          maxlength="32"
          :disabled="operateType !== 'new'"
          v-model="formData.terminalCode"
          placeholder="请输入终端编码"
        />
      </fs-form-item>
      <fs-form-item label="包名:" prop="packageName">
        <fs-input
          v-model="formData.packageName"
          placeholder="请输入包名"
          :disabled="operateType !== 'new'"
        />
      </fs-form-item>
      <fs-form-item label="终端图标:" prop="terminalLogo">
        <fs-upload
          class="workbench-upload"
          :action="uploadAction"
          :show-file-list="false"
          :on-success="handleSuccess"
          :before-upload="beforeUpload">
          <div v-if="formData.terminalLogo" class="workbench-upload__operate">
            <div class="icons">
              <i class="fs-icon-edit"></i>
              <i class="fs-icon-delete" @click="handleRemove"></i>
            </div>
          </div>
          <img v-if="formData.terminalLogo" :src="formData.terminalLogo" class="workbench-upload__avatar" />
          <div v-else class="workbench-upload__icon">
            <img src="@/view/png/add-image.png" />
            <div>上传图标</div>
          </div>
        </fs-upload>
        <div class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
      </fs-form-item>
      <fs-form-item label="终端类型:" prop="terminalType">
        <fs-dictionary-select
          v-model="formData.terminalType"
          :disabled="operateType !== 'new'"
          dictionaryKey="terminalType"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择终端类型"
        />
      </fs-form-item>
      <fs-form-item label="终端简介:" prop="remark">
        <div style="border: 1px solid #ccc;">
          <Toolbar
            style="border-bottom: 1px solid #ccc"
            :editor="editor"
            :defaultConfig="toolbarConfig"
            :mode="mode"
          />
          <Editor
            style="height: 300px; overflow-y: hidden;"
            v-model="formData.remark"
            :defaultConfig="editorConfig"
            :mode="mode"
            @onCreated="onCreated"
          />
        </div>
      </fs-form-item>
    </fs-form>

    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler_">
        确 定
      </fs-button>
    </span>
  </fs-dialog>
</template>

<script>
import { buildUUID } from 'fosung-sdk'
import axios from 'axios'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

export default {
  $plugins: 'form', // 此处是关键,必须包含form才可以！！！
  components: { Editor, Toolbar },
  data () {
    return {
      apiName: 'terminalmanage',
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        projectId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        zoneId: [
          { required: true, message: '请选择专区', trigger: 'change' }
        ],
        terminalName: [
          { required: true, message: '请输入终端名称', trigger: 'blur' }
        ],
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
        terminalCode: [
          { required: true, message: '请输入终端编码', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^[a-zA-Z0-9_-]+$/.test(value)) {
                callback(new Error('终端编码必须为字母、数字、下划线、中划线组成'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        terminalLogo: [
          { required: true, message: '请上传图标', trigger: 'change' }
        ],
        terminalType: [
          { required: true, message: '请选择终端类型', trigger: 'change' }
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
    submitHandler_ () {
      this.$refs[this.validRef].validate(async valid => {
        if (valid) {
          const { remark, remarkUrl } = this.formData
          if (remark) {
            const remark_ = `
            <!DOCTYPE html>
            <html lang="">
              <head>
                <title>终端简介</title>
              </head>
              <body>
                ${remark}
              </body>
            </html>
          `
            const html = new Blob([remark_], { type: 'text/html;charset=utf-8' })
            const formData = new FormData()
            let title = buildUUID()
            if (remarkUrl) {
              const t = remarkUrl.split('/')
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
                this.$set(this.formData, 'remarkUrl', data.data.url)
                this.submitHandler()
              } else {
                this.$delete(this.formData, 'remarkUrl')
                this.$message.error(message)
              }
            })
            return
          }
          this.submitHandler()
        }
      })
    },
    onCreated (editor) {
      this.editor = Object.seal(editor)
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'terminalLogo', data.url)
      this.$refs[this.validRef].clearValidate('terminalLogo')
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('上传图片只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      // const isLt2M = file.size / 1024 / 1024 < 2
      // if (!isLt2M) {
      //   this.$message.error('上传头像图片大小不能超过 2MB!')
      // }
      // return isJPG && isLt2M
      return isJPG
    },
    handleRemove (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'terminalLogo')
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
</style>
