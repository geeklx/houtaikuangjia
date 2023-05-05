<template>
  <fs-card class="li-layout">
    <div slot="header" v-if="$slots['top-header']">
      <slot name="top-header"></slot>
    </div>
    <div class="li-layout__content">
      <div class="li-layout__content--scroll">
        <div class="scroll-conditions clearFloat" v-bind="$attrs" v-on="$listeners">
          <div class="searchWarp flexBetweenTop" v-if="$slots['searchForm'] || $slots['searchButton'] || $slots['search-right']">
            <div class="searchForm flexCell">
              <slot name="searchForm"></slot>
              <div class="button-box">
                <slot name="searchButton"></slot>
              </div>
            </div>
            <div v-if="$slots['search-right']" class="search-right" :style="{ minWidth: topRightWidth + 'px' }">
              <slot name="search-right"></slot>
            </div>
          </div>
          <div class="searchMore" v-if="$slots['searchMore']">
            <transition name="fade">
              <div class="searchMore-box">
                <slot name="searchMore"></slot>
              </div>
            </transition>
          </div>
        </div>
        <div class="scroll-list clearFloat" v-bind="$attrs" v-on="$listeners">
          <slot name="list-content"></slot>
        </div>
      </div>
      <div class="li-layout__content--footer">
        <slot name="list-footer"></slot>
      </div>
    </div>
    <slot></slot>
  </fs-card>
</template>

<script>
export default {
  name: 'ListLiLayout',
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
      queryCondition: {}
    }
  }
}
</script>

<style lang="scss">
.fs-date-editor, .fs-range-editor {
  width: auto;
}

.li-layout {
  height: 100%;
  border: none;
  .fs-card__header {
    padding: 0 0 0 12px;
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #F0F2F7;
    .title-no-border {
      color: $themeColor;
      padding-left: 8px;
      font-size: 18px;
      font-weight: bold;
      display: flex;
      align-items: center;
    }
  }
  .fs-card__body {
    padding: 0;
    height: calc(100% - 50px);

    .li-layout__content {
      height: 100%;
      display: flex;
      flex-direction: column;
      &--scroll {
        flex: 1;
        overflow-y: auto;

        display: flex;
        flex-direction: column;
        .scroll-conditions {
          padding: 15px 26px 22px 26px;
          width: 100%;
          > div {
            width: 100%;
          }
          > .top-wrap {
            z-index: 1;
            position: relative;
            display: flex;
            align-items: center;
            justify-content: space-between;
            font-size: 14px;
            font-weight: 500;
            & > .top-left {
              display: inline-block;
              .title {
                font-size: 18px;
                font-weight: bold;
                padding-left: 8px;
                line-height: 22px;
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
                background: #C50405;
              }
            }
            & > .search-more-wrap {
              width: 100%;
              position: absolute;
              top: 40px;
              left: 0;
            }
          }
          > .searchWarp {
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
          > .searchMore {
            margin-top: 14px;
            .searchMore-box {
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
          .fade-enter-active, .fade-leave-active {
            transition: all 0.2s ease;
          }
          .fade-enter, .fade-leave-to {
            transform: translateY(-100%);
          }
        }
        .scroll-list {
          flex: 1;
          background-color: #F0F2F5;
          display: flex;
          flex-direction: column;
        }
      }
      &--footer {
        height: 60px;
        padding: 0 20px;
        background-color: #F9F9F9;
        display: flex;
        justify-content: flex-end;
        align-items: center;
      }
    }
  }
}
</style>
