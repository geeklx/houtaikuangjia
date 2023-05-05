<template>
  <div class="sidebar-logo-container">
    <transition name="sidebarLogoFade">
      <div key="expand" class="sidebar-logo-link" @click="$jump('/')">
<!--        <img src="//119.188.115.252:8090/resource-test/logo.png" class="sidebar-logo">-->
        <div class="system_name">
          <div></div>
          统一工作台
          <div></div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'SidebarLogo',
  props: {
  },
  data () {
    return {
    }
  }
}
</script>

<style lang="scss" scoped>
.system_name {
  display: flex !important;
  height: 70%;
  align-items: center;
  & > div {
    display: inline-block;
    width: 10px;
    height: 10px;
  }
  & > div:first-child {
    align-self: flex-start;
    border-top: 2px solid #FFFFFF;
    border-left: 2px solid #FFFFFF;
  }
  & > div:last-child {
    align-self: flex-end;
    border-bottom: 2px solid #FFFFFF;
    border-right: 2px solid #FFFFFF;
  }
}

.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  height: 50px;
  line-height: 50px;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;
    cursor: pointer;

    & .sidebar-logo {
      // width: 324px;
      vertical-align: middle;
      margin-right: 56px;
    }
    & .system_name{
      color: #ffffff;
      vertical-align: text-bottom;
      font-weight: 800;
      display: inline-block;
      font-size: 22px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 14px;
      vertical-align: middle;
    }
  }
}
</style>
