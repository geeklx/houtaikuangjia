<template>
  <fs-drawer
    append-to-body
    :wrapperClosable="false"
    destroy-on-close
    v-bind="$attrs"
    v-on="$listeners"
    :custom-class="'drawer-form-layout-wrap ' + $attrs.class"
  >
    <div class="content-wrap">
      <slot></slot>
    </div>

    <div class="footer-button-wrap block-box-shadow">
      <slot name="footer"></slot>
    </div>
  </fs-drawer>
</template>

<script>
export default {
  name: 'DrawerFormLayout'
}
</script>

<style lang="scss">
.drawer-form-layout-wrap {
  & > .fs-drawer__body {
    height: 100%;
    width: 100%;
    overflow: hidden;
    background-color: #fafafa !important;
    overflow-y: auto;
    & > .content-wrap {
      width: 100%;
      height: calc(100% - 49px);
      overflow-y: auto;
      padding: 10px;
      & .content-block {
        padding: 15px;
      }
    }
    & > .footer-button-wrap {
      padding: 10px;
      width: 100%;
      text-align: center;
      border-top: 1px solid #ececec;
    }
  }
}
</style>
