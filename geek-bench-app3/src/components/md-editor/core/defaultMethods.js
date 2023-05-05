export default {
  methods: {
    // 发布
    onPublish () {
      const mdValue = this.editor && this.editor.getValue()
      this.$emit('publish', mdValue)
    },
    // 保存
    onSave () {
      const mdValue = this.editor && this.editor.getValue()
      this.$emit('save', mdValue)
    }
  }
}
