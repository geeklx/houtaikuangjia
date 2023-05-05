<template>
  <fs-card
    :class="outScroll ? 'list-layout-card-iner-external-scrolling' : 'list-layout-card-internal-scrolling'"
    class="common-layout-card"
  >
    <div slot="header" v-if="$slots['top-header']">
      <slot name="top-header"></slot>
    </div>
    <div class="common-layout-tab-wrap clearFloat" v-bind="$attrs" v-on="$listeners">
      <div
        class="searchWarp flexBetweenTop"
        v-if="$slots['searchForm'] || $slots['searchButton'] || $slots['search-right']"
      >
        <!-- 条件查询区域 -->
        <div class="searchForm flexCell">
          <!-- 查询表单插槽 -->
          <slot name="searchForm"></slot>
          <!-- 查询按钮插槽 -->
          <div class="button-box" :class="$slots['searchMore'] ? 'button-box-more' : ''">
            <slot name="searchButton"></slot>
            <!-- 更多查询条件按钮，使用 searchMore 插槽后会自动显示-->
            <fs-button v-if="moreBtnShow" type="primary" :icon="searchMoreVisible ? 'fs-icon-arrow-down' : 'fs-icon-arrow-up'" plain @click="handleToggleSearchMoreVisible">更多</fs-button>
          </div>
        </div>
        <div v-if="$slots['search-right']" class="search-right" :style="{ minWidth: topRightWidth + 'px' }">
          <!-- 头部右侧插槽 -->
          <slot name="search-right"></slot>
        </div>
      </div>
      <!-- 下拉显示更多查询条件 -->
      <div class="searchMore" v-if="$slots['searchMore']">
        <transition name="fade">
          <div class="searchMore-box">
            <!-- 更多查询条件插槽 -->
            <slot name="searchMore"></slot>
          </div>
        </transition>
      </div>
      <div
        v-if="$slots['table'] || $slots['page']"
        class="table-wrap"
        :style="{ height: $slots['searchMore'] ? 'calc(100% - 48px - 68px)' : 'calc(100% - 68px)' }"
      >
        <!-- 表格区域 -->
        <div
          :class="{ table: true, noScroll: !isScroll }"
          :style="{ height: $slots['page'] ? 'calc(100% - 60px)' : '100%' }"
        >
          <!-- 表格插槽 -->
          <slot name="table"></slot>
        </div>
        <!-- 分页控件区域 -->
        <div class="page" v-if="$slots['page']">
          <!-- 分页控件插槽 -->
          <slot name="page"></slot>
        </div>
      </div>
      <slot></slot>
    </div>
  </fs-card>
</template>

<script>
export default {
  name: 'ListLayout',
  props: {
    title: {
      type: String
    },

    topRightWidth: {
      type: Number,
      default () {
        return 410
      }
    },
    /**
     * outScroll 外部滚动条
     * true: 滚动条出现在白色区域外，整个白色区域都可滚动，
     * false: 滚动条出现在白色区域内，白色区域固定，内部滚动
     */
    outScroll: {
      type: Boolean,
      default: false
    },
    isScroll: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      searchMoreVisible: false,
      queryCondition: {}
    }
  },
  computed: {
    // 判断是否使用了searchMore插槽，如果使用了自动添加更多按钮
    moreBtnShow () {
      // return this.$slots.searchMore
      return false
    }
  },
  methods: {
    handleToggleSearchMoreVisible () {
      this.searchMoreVisible = !this.searchMoreVisible
    }
  },
  watch: {}
}
</script>

<style lang="scss">
.fs-form--inline .fs-form-item {
  margin-right: 20px;
}
.fs-form-item__label {
  font-weight: normal;
  font-size: 14px;
}
.list-layout-card-internal-scrolling {
  height: 100%;
}
.list-layout-card-iner-external-scrolling {
  min-height: 100%;
}
.no-Scroll {
  height: 100%;
  overflow: hidden;
}
.common-layout-card {
  border: none;
  padding: 12px;
  height:   100%;
  background-color: #F0F2F5;
  .fs-card__header {
    background-color: #FFF;
    padding: 0 0 0 12px;
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #F0F2F7;
    .title {
      color: $themeColor;
      padding-left: 8px;
      font-size: 18px;
      font-weight: bold;
      display: flex;
      align-items: center;
    }
    .title::before {
      margin-right: 8px;
      content: "";
      display: inline-block;
      width: 3px;
      height: 16px;
      border-radius: 25px;
      background: $themeColor;
    }
  }
  .fs-card__body {
    background-color: #FFF;
    padding: 0;
    height: calc(100% - 50px);
    .common-layout-tab-wrap {
      width: 100%;
      height: 100%;
      & > div {
        width: 100%;
      }
      & > .searchWarp {
        padding: 20px 14px 0;
        align-items: center;
        & > .search-right {
          float: right;
          text-align: right;
          & .fs-form-item {
            margin-bottom: 0;
            &:last-child {
              margin-right: 0;
            }
            & .fs-button {
              & + .fs-button {
                margin-left: 3px;
              }
            }
          }
        }
        & > .searchForm {
          display: flex;
          align-items: center;
          & > .button-box {
            margin-left: 15px;
            min-width: 125px;
          }
          & > .button-box-more {
            margin-left: 15px;
            //min-width: 210px;
          }
          & > .fs-form {
            display: flex;
            & > .fs-form-item {
              display: flex;
              margin-bottom: 0;
              & > .fs-form-item__content {
                & > .fs-date-editor {
                  width: auto;
                }
              }
              & > .fs-form-item__label {
                min-width: 80px;
              }
            }
          }
        }
      }
      & > .searchMore {
        padding: 0 14px;
        .searchMore-box {
          margin-top: 20px;
          & > .fs-form {
            display: flex;
            & > .fs-form-item {
              display: flex;
              margin-bottom: 0;
              & > .fs-form-item__content {
                & > .fs-date-editor {
                  width: auto;
                }
              }
              & > .fs-form-item__label {
                min-width: 80px;
              }
            }
          }
        }
      }
      & > .table-wrap {
        height: 100%;
        margin-top: 20px;
        overflow: hidden;
        display: flex;
        flex-direction: column;
        justify-content: center;
        & > .table {
          height: calc(100% - 60px);
          padding: 0 20px;
          overflow-y: auto;
        }
        & > .page {
          height: 60px;
          padding: 0 20px;
          background-color: #F9F9F9;
          display: flex;
          justify-content: flex-end;
          align-items: center;
        }
        .noScroll {
          overflow-y: hidden;
        }
      }
      .fade-enter-active, .fade-leave-active {
        transition: all 0.2s ease;
      }
      .fade-enter, .fade-leave-to {
        transform: translateY(-100%);
      }
    }
  }
}
</style>
