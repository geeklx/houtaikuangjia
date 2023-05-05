<template>
  <div class="list">
    <div class="left-form" style="margin: 10px; float: left">
      <div style="">
        <i
          class="iconfont icon-yemian-copy"
          style="display: inline-block; float: left"
        ></i>
        <div style="display: inline-block; float: left">组织机构</div>
      </div>

      <slot name="left-tree"> </slot>
    </div>

    <div class="right-list">
      <div
        class="search"
        style="
          padding: 18px 10px 0 10px;
          margin-bottom: 10px;
          background-color: #fff;
        "
      >
        <slot
          name="search"
          :toggleSearchMore="toggleSearchMore"
          :showSearchMore="showSearchMore"
        ></slot>
      </div>
      <div style="background-color: #409eff; height: calc(100% - 40px)">
        <slot name="table"> </slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Accountlayout',

  data () {
    return {
      showSearchMore: false,
      listContainerRef: 'listContainerRef'
    }
  },

  computed: {
    showTableOpinion () {
      return this.$slots['table-opinion']
    },

    showTableFooter () {
      return this.$slots['table-footer']
    },

    showRightForm () {
      return this.$slots['right-form']
    },
    showLeftForm () {
      // alert(this.$slots['left-form']);
      return this.$slots['left-form']
    },

    minusHeightNumber () {
      let num = this.showTableOpinion ? 42 : 0
      num += this.showTableFooter ? 38 : 0
      return num
    },
    leftRightWith () {
      let num = 0
      num += this.showRightForm ? 375 : 0
      num += this.showLeftForm ? 375 : 0
      return num
    }
  },

  methods: {
    toggleSearchMore () {
      this.showSearchMore = !this.showSearchMore
    }
  },

  mounted () {
  }
}
</script>

<style lang="scss">
.list {
  position: relative;

  & > .list {
    margin-top: 5px;
    height: calc(100% - 55px);
    position: relative;

    & > .table-opinion {
      padding: 7px 10px;

      & > .fs-button {
        padding: 7px 15px !important;
      }
    }

    & > .table-content {
      font-size: 14px !important;

      & .fs-table__body-wrapper {
        overflow-y: auto !important;
      }

      & > .fs-table--small td,
      .fs-table--small th {
        padding: 6px 0 !important;

        & .fs-button--text {
          padding: 0 !important;
          position: relative;
          /*font-size: 14px !important;*/
          & + .fs-button--text {
            padding-left: 10px !important;
            margin-left: 5px !important;

            &:before {
              content: "";
              width: 1px;
              /*height: 14px;*/
              height: 12px;
              position: absolute;
              left: 0;
              top: 0;
              background-color: #e9e9e9;
            }
          }
        }
      }

      & .table-button {
        padding: 5px 15px !important;
      }
    }

    & > .table-footer {
      position: absolute;
      width: calc(100% - 20px);
      padding: 0 10px;
      bottom: 10px;
      text-align: right;

      & > .fs-button {
        float: left;
        padding: 7px 15px !important;
      }

      & > .fs-pagination {
        & > .fs-pagination__jump {
          margin-left: 10px;
        }
      }
    }
  }

  & > .right-list {
    width: calc(100% - 390px);
    // display: inline-block;
    float: left;
    margin: 10px;
    /*background-color: #67c23a;*/
    height: 100%;
  }

  & > .left-form {
    width: 350px;
    overflow: scroll;
    display: inline-block;
    background-color: #fff;
    height: calc(100% - 20px);
  }

  & > .right-list {
    & > .search {
      display: block;
      /*height: 40px;*/
      /*background-color: red;*/
    }
  }
}
</style>
