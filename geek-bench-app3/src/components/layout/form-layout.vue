<template>
  <fs-card :class="outScroll? 'form-layout-card-iner-external-scrolling' : 'form-layout-card-internal-scrolling' ">
    <div class="form">
      <div class="form-header">
        <slot name="form-header-left"></slot>
        <slot name="form-header-right"></slot>
      </div>
      <slot name="form"></slot>
    </div>
  </fs-card>
</template>

<script>
export default {
  name: 'FormLayout',
  props: {
    /**
     * outScroll 外部滚动条
     * true: 滚动条出现在白色区域外，整个白色区域都可滚动，
     * false: 滚动条出现在白色区域内，白色区域固定，内部滚动
     */
    outScroll: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="scss">
.form-layout-card-internal-scrolling{
  height: 100%;
}
.form-layout-card-iner-external-scrolling{
  min-height: 100%;
}
.form {
  height: 100%;
  background-color: #ffffff;
  & > .form-header {
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .title {
      font-size: 16px;
      font-weight: bold;
      border-left: 5px #e60000 solid;
      padding-left: 8px;
      line-height: 22px;
    }
  }

  & > .fs-tabs {
    height: calc(100% - 2px);
    box-shadow: none !important;
    & > .fs-tabs__content {
      padding: 0 !important;
      height: calc(100% - 39px);
      & > .fs-tab-pane {
        height: 100%;
        overflow-y: auto;
      }
    }
  }
  & .fs-form {
    height: calc(100% - 50px);
    overflow-y: auto;
    & .is-disabled {
      & .fs-input__inner,
      & .fs-textarea__inner {
        background-color: #fbfbfb;
        border-color: #e6e6e6;
        color: #636363;
      }
      & .fs-cascader__label {
        color: #636363 !important;
        & > span {
          color: #7d7d7d;
        }
      }
    }
  }
  & .content-block {
    width: 100%;
    & > .block-title {
      display: block;
      height: 40px;
      line-height: 40px;
      padding-left: 20px;
      background-color: #d6e7f4;
      color: #303133;
      /*font-weight: 600;*/
      font-size: 14px;
    }
    & > .block-content {
      padding: 20px;
      height: calc(100% - 90px);
      overflow-y: auto;
    }
  }
}
.right-form {
  & .block-title {
    height: 50px !important;
    line-height: 50px !important;
    color: #707379 !important;
    /*font-weight: 300 !important;*/
    font-size: 16px !important;
  }
}
</style>
