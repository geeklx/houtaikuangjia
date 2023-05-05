import OrgTree from './core/main.vue'

OrgTree.install = function (Vue) {
  Vue.component(OrgTree.name, OrgTree)
}

export default OrgTree
