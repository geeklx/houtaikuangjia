<template>
  <div>
    <fs-scrollbar wrap-class="scrollbar-wrapper">
      <fs-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item v-for="route in menuTree" :key="route.path" :item="route" :base-path="route.path" />
      </fs-menu>
    </fs-scrollbar>
  </div>
</template>

<script>
import SidebarItem from './sidebar-item'
import variables from '@/styles/variables.scss'

export default {
  $stores: ['menuTree', 'sidebar'],
  components: { SidebarItem },
  computed: {
    activeMenu () {
      const route = this.$route
      const { meta, path } = route
      // 如果设置路径，侧边栏将突出显示设置的路径
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    variables () {
      return variables
    },
    isCollapse () {
      return !this.sidebar.opened
    }
  }
}
</script>
