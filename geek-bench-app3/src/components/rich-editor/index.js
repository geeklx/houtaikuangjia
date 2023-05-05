import RichEditor from './core/main.vue'

RichEditor.install = function (Vue) {
  Vue.component(RichEditor.name, RichEditor)
}

export default RichEditor
