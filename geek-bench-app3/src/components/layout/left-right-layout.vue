<template>
  <div
    class="common-layout-left-right-wrap clearFloat"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <div class="left-wrap" :style="wrapStyle.left">
      <slot name="left"></slot>
    </div>

    <div class="right-wrap" :style="wrapStyle.right">
      <slot name="right"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LeftRightLayout',

  props: {
    offset: {
      type: String,
      default: '10px'
    },
    leftWidth: {
      type: String,
      default: '260px'
    },
    rightWidth: String
  },

  data () {
    return {}
  },

  computed: {
    wrapStyle () {
      let left, right

      if (this.rightWidth) {
        left = {
          width: `calc(100% - ${this.rightWidth} - ${this.offset})`,
          marginRight: this.offset
        }
        right = {
          width: this.rightWidth
        }
      } else {
        left = {
          width: this.leftWidth
        }
        right = {
          width: `calc(100% - ${this.leftWidth} - ${this.offset})`,
          marginLeft: this.offset
        }
      }

      return { left, right }
    }
  },

  methods: {},

  watch: {}
}
</script>

<style lang="scss">
.common-layout-left-right-wrap {
  width: 100%;
  height: 100%;
  & > .left-wrap,
  & > .right-wrap {
    float: left;
    height: 100%;
  }
}
</style>
