<template>
  <div class="anno-detail">
    <div class="anno-detail__form">
      <fs-form
        :ref="validRef"
        :rules="rules"
        :model="formData"
        label-width="100px"
        label-position="right"
      >
        <fs-form-item label="选择项目:" prop="projectId">
          <fs-select
            v-model="formData.projectId"
            clearable
            filterable
            placeholder="请选择项目"
            :disabled="readonly"
          >
            <fs-option
              v-for="item in projectList"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </fs-select>
        </fs-form-item>
        <fs-form-item label="公告标题:" prop="noticeTitle">
          <fs-input
            maxlength="32"
            v-model="formData.noticeTitle"
            placeholder="请输入公告标题"
            :disabled="readonly"
          />
        </fs-form-item>
        <fs-form-item label="公告摘要:" prop="noticeSummary">
          <fs-input
            maxlength="220"
            v-model="formData.noticeSummary"
            placeholder="请输入公告摘要"
            :disabled="readonly"
          />
        </fs-form-item>
        <fs-form-item label="正文内容:" prop="noticeContent">
          <div v-if="readonly" v-html="formData.noticeContent"></div>
          <div v-else style="border: 1px solid #ccc;">
            <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editor"
              :defaultConfig="toolbarConfig"
              :mode="mode"
            />
            <Editor
              style="height: 350px; overflow-y: hidden;"
              v-model="formData.noticeContent"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="onCreated"
            />
          </div>
        </fs-form-item>
        <fs-form-item label="发送终端:" prop="sendTerminal">
          <fs-select
            v-model="formData.sendTerminal"
            clearable
            multiple
            filterable
            placeholder="请选择发送终端"
            :disabled="readonly"
          >
            <fs-option
              v-for="item in terminalList"
              :key="item.id"
              :label="item.terminalName"
              :value="item.id"
            />
          </fs-select>
        </fs-form-item>
        <fs-form-item label="是否跳转:" prop="isJump">
          <fs-radio-group v-model="formData.isJump" :disabled="readonly">
            <fs-radio label="0">否</fs-radio>
            <fs-radio label="1">是</fs-radio>
          </fs-radio-group>
        </fs-form-item>
        <fs-form-item v-if="formData.isJump === '1'" label="" prop="jumpLink">
          <fs-input
            v-model="formData.jumpLink"
            placeholder="请输入跳转链接"
            :disabled="readonly"
          />
        </fs-form-item>
        <fs-form-item label="设置图标:" prop="noticeIconType">
          <fs-radio-group v-model="formData.noticeIconType" :disabled="readonly">
            <fs-radio label="0">默认</fs-radio>
            <fs-radio label="1">自定义</fs-radio>
          </fs-radio-group>
        </fs-form-item>
        <fs-form-item v-if="formData.noticeIconType === '1'" label="" prop="noticeIcon">
          <div v-if="readonly">
            <img style="height: 104px" :src="formData.noticeIcon" alt="" />
          </div>
          <template v-else>
            <fs-upload
              class="workbench-upload"
              :action="uploadAction"
              :show-file-list="false"
              :on-success="handleSuccess"
              :before-upload="beforeUpload">
              <div v-if="formData.noticeIcon" class="workbench-upload__operate">
                <div class="icons">
                  <i class="fs-icon-edit"></i>
                  <i class="fs-icon-delete" @click="handleRemove"></i>
                </div>
              </div>
              <img v-if="formData.noticeIcon" :src="formData.noticeIcon" class="workbench-upload__avatar" />
              <div v-else class="workbench-upload__icon">
                <img src="@/view/png/add-image.png" />
                <div>上传图标</div>
              </div>
            </fs-upload>
            <div class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
          </template>
        </fs-form-item>
        <fs-form-item label="提醒样式:" prop="noticeStyle">
          <fs-radio-group v-model="formData.noticeStyle" :disabled="readonly">
            <fs-radio label="1">标题+正文</fs-radio>
            <fs-radio label="2">标题+摘要</fs-radio>
          </fs-radio-group>
        </fs-form-item>
        <fs-form-item label="发送时间:" prop="sendTimeType">
          <fs-radio-group v-model="formData.sendTimeType" :disabled="readonly">
            <fs-radio label="1">立即发送</fs-radio>
            <fs-radio label="2">定时发送</fs-radio>
          </fs-radio-group>
        </fs-form-item>
        <fs-form-item v-if="formData.sendTimeType === '2'" label="" prop="sendTime">
          <fs-date-picker
            v-model="formData.sendTime"
            type="datetime"
            placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            :disabled="readonly"
          />
        </fs-form-item>
      </fs-form>
    </div>
    <div class="anno-detail__footer">
      <fs-button type="primary" size="small" plain @click="cancel">返回</fs-button>
      <fs-button v-show="!readonly" type="primary" size="small" plain style="margin-left: 32px" @click="submitHandler_('tempStore')">暂存</fs-button>
      <fs-button v-show="!readonly" type="primary" size="small" style="margin-left: 32px" @click="submitHandler_('send')">发送</fs-button>
    </div>
  </div>
</template>

<script>
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import axios from 'axios'

export default {
  $plugins: 'form',
  name: 'formDetail',
  components: { Editor, Toolbar },
  data () {
    return {
      rules: {
        projectId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        sendTerminal: [
          { required: true, message: '请选择发送终端', trigger: 'change' }
        ],
        noticeTitle: [
          { required: true, message: '请填写公告标题', trigger: 'blur' }
        ],
        noticeSummary: [
          { required: true, message: '请填写公告摘要', trigger: 'blur' }
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
      mode: 'default',

      terminalList: [],
      projectList: [],
      apiName: 'anno',
      queryMethod: 'get',
      submitMethod: 'save'
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
    this.queryTerminalList()
    this.queryProject()
  },
  methods: {
    onCreated (editor) {
      this.editor = Object.seal(editor)
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'noticeIcon', data.url)
      this.$refs[this.validRef].clearValidate('noticeIcon')
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('上传图片只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      return isJPG
    },
    handleRemove (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'noticeIcon')
    },
    queryTerminalList () {
      this.$api('bindApply', 'queryTerminalList').then(res => {
        const { datalist } = res
        this.$set(this, 'terminalList', datalist)
      })
    },
    queryProject () {
      this.$api('projectmanage', 'all').then(res => {
        const { datalist } = res
        this.$set(this, 'projectList', datalist)
      })
    },
    getSubmitData () {
      const { projectId, sendTerminal } = this.formData
      const pro = this.projectList.find(i => {
        return i.dictValue === projectId
      })
      return Object.assign({}, this.formData, {
        projectName: pro.dictLabel,
        sendTerminal: sendTerminal.join()
      })
    },
    querySuccessHandler (res) {
      const { data: { sendTerminal } } = res
      sendTerminal && (this.$set(this.formData, 'sendTerminal', sendTerminal.split(',')))
    },
    beforeCreateHandler () {
      this.$set(this.formData, 'isJump', '0')
      this.$set(this.formData, 'noticeIconType', '0')
      this.$set(this.formData, 'noticeStyle', '1')
      this.$set(this.formData, 'sendTimeType', '1')
    },
    submitHandler_ (val) {
      this.$refs[this.validRef].validate(async valid => {
        if (valid) {
          this.$set(this.formData, 'noticeStatus', val)
          this.submitHandler()
        }
      })
    },
    submitSuccessHandler () {
      this.$router.push({ path: '/anno' })
    },
    cancel () {
      this.$router.back()
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
.anno-detail {
  width: 100%;
  height: 100%;
  padding: 12px;
  display: flex;
  flex-direction: column;
  &__form {
    padding: 24px;
    background: #fff;
    flex: 1;
    overflow: auto;
  }
  &__footer {
    background: #fff;
    height: 72px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-top: 1px solid #F0F2F7;
  }
}
</style>
<style lang="scss">
.w-e-full-screen-container {
  z-index: 1002;
}
</style>
