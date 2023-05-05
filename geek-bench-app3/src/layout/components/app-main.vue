<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    key () {
      return this.$route.path
    }
  }
}
</script>

<style scoped lang="scss">
.app-main {
  /*50 = navbar  */
  height: calc(100% - 50px);
  //padding: 12px;
  width: 100%;
  position: relative;
  overflow: auto;
  background-color: #F4F4F4;
  .fs-card {
    box-shadow: none;
  }
}
</style>
