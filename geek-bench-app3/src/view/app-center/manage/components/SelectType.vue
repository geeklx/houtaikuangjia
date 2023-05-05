<template>
  <div class="fs-flex fs-row-center fs-col-top wrapper-content-detail">
    <div class="wrapper-content-detail__body">
      <div class="fs-flex fs-row-center active-content">
        <div
          class="active-content-outer fs-flex-col fs-col-center fs-row-center"
          :class="formData.appType === 'appNative' ? 'is-active' : ''"
          @click="selectTypeChange('appNative')"
        >
          <img class="active-content-outer__icon" src="../png/app.png" />
          <div class="active-content-outer__label">APP原生</div>
        </div>
        <div
          class="active-content-outer fs-flex-col fs-col-center fs-row-center"
          :class="formData.appType === 'h5' ? 'is-active' : ''"
          @click="selectTypeChange('h5')"
        >
          <img class="active-content-outer__icon" src="../png/H5.png" />
          <span class="active-content-outer__label">H5应用</span>
        </div>
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
  name: 'SelectType',
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
      readonly: false
    }
  },
  mounted () {
    const id = this.$route.query.id
    if (id) this.readonly = true
    const APPTYPE = sessionStorage.getItem('APPTYPE')
    this.$set(this.formData, 'appType', APPTYPE || 'appNative')
  },
  methods: {
    selectTypeChange (val) {
      if (this.readonly) {
        this.$message.warning('应用类型不可编辑！')
        return
      }
      this.$set(this.formData, 'appType', val)
    },
    handleNext () {
      if (!this.formData.appType || this.formData.appType === '') {
        this.$message({
          type: 'warning',
          message: '请选择应用类型！'
        })
        return
      }
      this.$emit('on-next', this.formData)
    },
    handlePrev () {
      removeData()
      this.$emit('on-prev')
    }
  }
}
</script>

<style scoped lang="scss">
.active-content {
  &-outer {
    background-color: #F7F7F7;
    border-radius: 12px;
    width: 188px;
    height: 188px;
    color: #333333;
    &__icon {
      width: 72px;
      height: 72px;
    }
    &__label {
      margin-top: 22px;
      font-size: 16px;
    }
  }
  &-outer:first-child {
    margin-right: 32px;
  }
  &-outer:hover {
    background-color: $themeBackground;
    color: $themeColor;
  }
  &-outer.is-active {
    background-color: $themeBackground;
    border: 1px solid $themeColor;
    color: $themeColor;
  }
}
</style>
