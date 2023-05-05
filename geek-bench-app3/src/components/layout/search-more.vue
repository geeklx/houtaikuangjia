<template>
  <fs-form
    v-bind="$attrs"
    v-on="$listeners"
    ref="searchMoreFormRef"
    class="list-layout-search-more-wrap"
    @submit.native.prevent
    size="mini"
    inline
  >
    <slot></slot>
  </fs-form>
</template>

<script>
export default {
  name: 'SearchMore',

  registryName: 'search-more',

  methods: {
    resetFields () {
      const searchMoreFormInstance = this.$getRef('searchMoreFormRef')

      searchMoreFormInstance && searchMoreFormInstance.resetFields && searchMoreFormInstance.resetFields()
    }
  }
}
</script>

<style lang="scss">
.list-layout-search-more-wrap {
  width: calc(100% - 20px);
  height: 100%;
  min-height: 40px;
  padding: 10px 10px 0;
}
</style>
