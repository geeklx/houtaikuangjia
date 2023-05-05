import MdEditor from './core/main.vue'

MdEditor.install = function (Vue) {
  Vue.component(MdEditor.name, MdEditor)
}

export default MdEditor
