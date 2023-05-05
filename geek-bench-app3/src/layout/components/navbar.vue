<template>
  <div class="navbar">
    <!-- 菜单栏收缩开关 -->
    <fs-tooltip class="item" effect="dark" content="点击收起导航菜单" placement="bottom-start">
      <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
    </fs-tooltip>
    <!-- 面屑导航 -->
    <breadcrumb class="breadcrumb-container" />
  </div>
</template>

<script>
import hamburger from '@/components/hamburger'
import breadcrumb from '@/components/breadcrumb'
export default {
  $stores: 'sidebar',
  components: {
    hamburger,
    breadcrumb
  },
  methods: {
    toggleSideBar () {
      this.$store.dispatch('app/toggleSideBar')
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  //box-shadow: 2px 1px 4px rgba(0,21,41,.08);
  border-bottom: 1px solid #F0F2F7;
  z-index: 1001;

  .hamburger-container {
    height: 100%;
    width: 50px;
    padding: 0 14px !important;
    color: $themeColor;
    background: linear-gradient(to right, rgba(247,247,247, 1), rgba(247,247,247, 0), rgba(247,247,247, 0), rgba(247,247,247, 0));
    line-height: 50px;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    /*&:hover {
      background: rgba(0, 0, 0, .025)
    }*/
  }

  .breadcrumb-container {
    float: left;
  }
}
</style>
