<template>
  <div class="fs-flex fs-row-center fs-col-top wrapper-content-detail">
    <div class="wrapper-content-detail__body">
      <div class="fs-flex-col fs-col-center active-content">
        <img src="../png/H5.png" />
        <div>应用创建完毕</div>
        <div><span style="color: #157EFC">{{ num }}秒</span>自动跳转回应用列表</div>
      </div>
    </div>
    <step-footer
      class="wrapper-content-detail__bottom fs-flex fs-row-right"
      :active="active"
      @on-next="handleNext"
      @on-prev="handlePrev"
    ></step-footer>
  </div>
</template>

<script>
import Footer from './Footer.vue'
import { removeData } from '../hooks/useSteps'
export default {
  name: 'Finish',
  props: {
    active: {
      type: Number,
      required: true
    }
  },
  components: {
    StepFooter: Footer
  },
  data () {
    return {
      formData: {},
      num: 5,
      timer: null
    }
  },
  created () {
  },
  watch: {
    num (val) {
      if (val === 0) {
        removeData()
        this.$router.push({ name: '/appCenterManage' })
      }
    }
  },
  methods: {
    showTime () {
      this.num--
      const _ = this
      setTimeout(function () {
        _.showTime()
      }, 1000)
    },
    handleNext () {
      this.$emit('on-next')
    },
    handlePrev () {
      this.$emit('on-prev')
    }
  }
}
</script>

<style scoped lang="scss">
.active-content {
  width: 100%;
  div {
    font-size: 14px;
    color: #999999;
  }
  img {
   width: 133px;
   height: 133px;
 }
}
</style>
