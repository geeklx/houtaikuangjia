<template>
  <component :is="type" v-bind="linkProps(to)" @click="goToPage(to)">
    <slot />
  </component>
</template>

<script>
import { isExternal } from '@/utils/validate'

export default {
  props: {
    to: {
      type: String,
      required: true
    }
  },
  computed: {
    isExternal () {
      return isExternal(this.to)
    },
    type () {
      if (this.isExternal) {
        return 'a'
      }
      return 'div'
    }
  },
  methods: {
    goToPage (to) {
      if (!this.isExternal) {
        this.$jump({ path: to })
      }
    },
    linkProps (to) {
      if (this.isExternal) {
        return {
          href: to,
          target: '_blank',
          rel: 'noopener'
        }
      }
      return {
        to: to
      }
    }
  }
}
</script>
