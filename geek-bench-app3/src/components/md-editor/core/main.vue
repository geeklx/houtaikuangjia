<template>
  <div />
</template>
<script>
import Vditor from 'vditor'
import defaultProps from './defaultProps'
import defaultMethods from './defaultMethods'
import { formatToDate } from 'fosung-sdk'
import 'vditor/dist/index.css'

export default {
  name: 'FsMdEditor',
  inheritAttrs: false,
  props: defaultProps,
  mixins: [defaultMethods],
  data () {
    return {
      editor: null
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    // 初始化
    init () {
      const _this = this
      this.editor = new Vditor(this.$el, {
        cdn: '//unpkg.com/vditor@3.8.14',
        theme: 'classic',
        lang: 'zh_CN',
        mode: 'sv',
        toolbar: [
          'headings',
          'bold',
          'italic',
          'strike',
          'link',
          '|',
          'list',
          'ordered-list',
          'check',
          'outdent',
          'indent',
          '|',
          'quote',
          'line',
          'code',
          'inline-code',
          'insert-before',
          'insert-after',
          '|',
          {
            // 自定义上传
            hotkey: '',
            name: 'upload',
            // tipPosition: "s",
            tip: '上传图片',
            className: 'right'
          },
          'table',
          '|',
          'undo',
          'redo',
          '|',
          'fullscreen',
          'edit-mode',
          {
            name: 'more',
            // 'code-theme', 'content-theme', 'devtools', 'info', 'help'
            toolbar: ['both', 'export', 'outline', 'preview']
          },
          {
            hotkey: '',
            name: 'save',
            tipPosition: 's',
            tip: '保存',
            className: 'right',
            icon: '<img style="height: 16px" src=\'https://img.58cdn.com.cn/escstatic/docs/imgUpload/idocs/save.svg\' alt=""/>',
            click () {
              _this.onSave()
            }
          },
          {
            hotkey: '',
            name: 'publish',
            tipPosition: 's',
            tip: '发布',
            className: 'right',
            icon: '<img style="height: 16px" src=\'https://img.58cdn.com.cn/escstatic/docs/imgUpload/idocs/publish.svg\' alt=""/>',
            click () {
              _this.onPublish()
            }
          }
        ],
        toolbarConfig: {
          pin: true
        },
        outline: {
          enable: true,
          position: 'left'
        },
        cache: {
          enable: false
        },
        counter: {
          enable: true,
          type: '字数统计'
        },
        preview: {
          actions: []
        },
        height: this.height,
        width: this.width,
        value: this.value,
        upload: {
          url: this.uploadUrl,
          accept: _this.accept,
          multiple: true,
          fieldName: _this.fieldName,
          // withCredentials: true,
          max: 2 * 1024 * 1024,
          success (editor, msg) {
            const resp = JSON.parse(msg)
            resp.success && _this.editor.insertValue(`![](${resp.data.url})`)
          },
          extraData: {
            name: 'uploads/images/' + formatToDate(new Date(), 'YYYYMMDD') + 'random.jpg',
            inner: false,
            randomname: true
          },
          // handler([file]) {
          //   const formData = new FormData()
          //   formData.append('file', file)
          //   axios
          //     .post(
          //       this.uploadUrl,
          //       {
          //         name: '202176',
          //         file: formData
          //       },
          //       {
          //         headers: {
          //           'Content-Type': 'multipart/form-data'
          //         }
          //       }
          //     )
          //     .then((res) => {
          //       _this.editor.insertValue(`![](${res.data.data.url})`)
          //       return '上传成功'
          //     })
          // },
          filename (name) {
            return name
              .replace(/[^(a-zA-Z0-9\u4e00-\u9fa5.)]/g, '')
              .replace(/[?\\/:|<>*[\]()$%{}@~]/g, '')
              .replace('/\\s/g', '')
          }
        },
        after () {
          _this.$emit('after', _this.editor)
        },
        focus (value) {
          _this.$emit('focus', value)
        },
        input: (v) => {
          _this.$emit('input', v)
        },
        blur: (value) => {
          _this.$emit('blur', value)
        },
        select (value) {
          _this.$emit('select', value)
        }
      })
    }
  },
  beforeDestroy () {
    if (!this.editor) return
    try {
      if (this.editor && this.editor.destroy) {
        this.editor.destroy()
      }
    } catch (e) {
      console.log(e)
    }
  }
}
</script>
