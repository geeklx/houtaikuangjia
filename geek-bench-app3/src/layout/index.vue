<template>
  <div
    v-if="isShow"
    :class="classObj"
    :key="key"
    class="app-wrapper"
  >
    <app-header />
    <!-- 左侧菜单栏部分 -->
    <sidebar class="sidebar-container" />
    <div class="main-container">
      <div>
        <!-- 头部部分 -->
        <navbar />
      </div>
      <!-- 内容部分 -->
      <app-main />
    </div>
  </div>
</template>

<script>
import { AppHeader, Navbar, Sidebar, AppMain } from './components'

export default {
  $stores: ['sidebar', 'currentRole', 'buttons', 'themeType', 'themeBackground', 'themeDisBackground'],
  name: 'Layout',
  components: {
    AppHeader,
    Navbar,
    Sidebar,
    AppMain
  },
  computed: {
    classObj () {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: false
      }
    }
  },
  watch: {
    currentRole: {
      immediate: true,
      deep: true,
      handler (val) {
        // 测试按钮权限
        // 暂时使用角色code，后期改为接口形式
        if (val && val.roleCode && val.orgCode) {
          this.$store.dispatch('saveButtons', [val.roleCode])
        }
      }
    },
    buttons: {
      deep: true,
      handler () {
        // 确保buttons改变的时候重新初始化指令
        this.key++
      }
    },
    themeType: {
      immediate: true,
      deep: true,
      handler () {
        const styleHtml = ':root {' + this.themeBackground + ';' + this.themeType + ';' + this.themeDisBackground + '}'
        // 通过动态添加style的方式配置表格样式
        const styleDom = document.getElementById('theme')
        if (styleDom) {
          styleDom.innerHTML = styleHtml
        } else {
          const d = document.createElement('style')
          d.setAttribute('id', 'theme')
          d.innerHTML = styleHtml
          document.getElementsByTagName('head')[0].appendChild(d)
        }
      }
    }
  },
  provide () {
    return {
      reloads: this.reloads
    }
  },
  data () {
    return {
      isShow: true,
      key: 1
    }
  },
  methods: {
    reloads () {
      this.isShow = false
      this.$nextTick(() => {
        this.isShow = true
      })
    },
    handleClickOutside () {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/mixin.scss';
@import '~@/styles/variables.scss';

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}
.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}
</style>
