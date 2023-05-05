<template>
  <fs-card id="dashboard">
    <div>首页</div>
  </fs-card>
</template>

<script>
export default {
  $stores: ['user', 'configInfo', 'currentRole'],
  data () {
    return {}
  },
  watch: {},
  methods: {}
}
</script>

<style lang="scss" scoped>
#dashboard {
  height: 100%;
}
</style>
