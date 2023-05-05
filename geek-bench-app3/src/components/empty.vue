<template>
  <div class="list-empty">
    <div class="icons">
      <svg-icon icon-class="empty" class="iconEmpty"></svg-icon>
      <svg-icon icon-class="arrow" class="iconArrow"></svg-icon>
    </div>
    <div class="iconfont">暂无数据，赶快新建吧</div>
  </div>
</template>

<script>
export default {
  name: 'Empty',
  computed: {}
}
</script>

<style scoped lang="scss">

.list-empty {
  height: 100%;
  display: flex;
  width: 100%;
  flex-direction: column;
  justify-content: center;
  padding-left: 40%;
  .icons {
    display: flex;
    .iconEmpty {
      width: 120px;
      height: 80px;
    }
    .iconArrow {
      width: 300px;
      height: 180px;
      margin-top: -100px;
    }
  }
  .iconfont {
    font-size: 14px;
    color: #999999;
  }
}
</style>
