<template>
  <div style="padding: 0 15px" @click="toggleClick">
    <svg-icon
      :class="{ 'is-active': isActive }"
      class="hamburger"
      icon-class="main-return"
    ></svg-icon>
  </div>
</template>

<script>
export default {
  name: 'Hamburger',
  props: {
    isActive: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    toggleClick () {
      this.$emit('toggleClick')
    }
  }
}
</script>

<style scoped>
.hamburger {
  display: inline-block;
  vertical-align: middle;
  width: 20px;
  height: 20px;
}

.hamburger.is-active {
  transform: rotate(180deg);
}
</style>
