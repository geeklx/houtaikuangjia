<template>
  <div class="fs-tinymce-container" :style="{ width: containerWidth }">
    <textarea :id="tinymceId" ref="elRef" :style="{ visibility: 'hidden' }"></textarea>
  </div>
</template>

<script>
import { tinymce } from './config'
import { buildShortUUID, convertToUnit, formatToDate } from 'fosung-sdk'
import defaultProps from './defaultProps'

export default {
  name: 'FsRichEditor',
  props: defaultProps,
  data () {
    return {
      editorRef: null,
      tinymceId: buildShortUUID('tiny-vue')
    }
  },
  computed: {
    // 容器宽度
    containerWidth () {
      return convertToUnit(this.width)
    }
  },
  methods: {
    // 初始化编辑器
    initEditor () {
      const _this = this
      const el = this.$refs.elRef
      if (el) {
        el.style.visibility = ''
      }
      tinymce.init({
        selector: `#${this.tinymceId}`,
        height: this.height,
        toolbar: this.toolbar,
        plugins: this.plugins,
        language_url: require('./lang'),
        language: 'zh_CN',
        branding: false,
        default_link_target: '_blank',
        link_title: false,
        object_resizing: false,
        skin: 'oxide',
        skin_url: require('tinymce/skins/ui/oxide/skin.css'),
        content_css: require('tinymce/skins/ui/oxide/content.min.css'),
        ...this.options,
        images_upload_handler (blobInfo, success, failure, progress) {
          _this.upload(blobInfo, success, failure, progress)
        },
        setup: (editor) => {
          this.editorRef = editor
          editor.on('init', (e) => this.init(e))
          editor.on('blur', () => {
            _this.$emit('blur')
          })
          editor.on('input change undo redo', () => {
            _this.$emit('input', editor.getContent())
          })
        }
      })
    },
    init (e) {
      this.$emit('init', e)
      this.editorRef.setContent(this.value)
    },
    // 销毁编辑器
    destroy () {
      if (tinymce !== null && tinymce.remove) {
        tinymce.remove(this.editorRef)
      }
    },
    // 渲染
    nextTickRender () {
      this.tinymceId = buildShortUUID('tiny-vue')
      this.$nextTick(() => {
        this.initEditor()
      })
    },
    upload (blobInfo, success, failure, progress) {
      if (!this.uploadUrl) {
        this.$message.error('缺少uploadUrl参数')
        return
      }
      const xhr = new XMLHttpRequest()
      xhr.withCredentials = false
      xhr.open('POST', this.uploadUrl)
      xhr.upload.onprogress = function (e) {
        progress((e.loaded / e.total) * 100)
      }
      xhr.onload = function () {
        if (xhr.status === 403) {
          failure('HTTP Error: ' + xhr.status, { remove: true })
          return
        }
        if (xhr.status < 200 || xhr.status >= 300) {
          failure('HTTP Error: ' + xhr.status)
          return
        }
        const json = JSON.parse(xhr.responseText)
        if (!json.success && json.data.url !== 'string') {
          failure('Invalid JSON: ' + xhr.responseText)
          return
        }
        success(json.data.url)
      }
      xhr.onerror = function () {
        failure('Image upload failed due to a XHR Transport error. Code: ' + xhr.status)
      }
      const formData = new FormData()
      formData.append('file', blobInfo.blob(), blobInfo.filename())
      formData.append('name', 'uploads/images/' + formatToDate(new Date(), 'YYYY-MM-DD') + '/random.jpg')
      formData.append('inner', false)
      formData.append('randomname', false)
      xhr.send(formData)
    }
  },
  mounted () {
    this.initEditor()
  },
  beforeDestroy () {
    this.destroy()
  }
}
</script>
