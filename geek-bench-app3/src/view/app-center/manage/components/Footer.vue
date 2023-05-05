<template>
  <div class="fs-flex fs-row-center fs-p-60">
    <div>
      <fs-button size="small" @click="callback" v-if="active === 0">取消</fs-button>
      <fs-button size="small" @click="$emit('on-prev')" v-else>上一步</fs-button>
      <fs-button size="small" type="primary" @click="$emit('on-next')">
        {{active === 3 ? '返回' : '下一步'}}
      </fs-button>
    </div>
  </div>
</template>

<script>
import { removeData } from '../hooks/useSteps'
export default {
  name: 'StepFooter',
  props: {
    active: {
      type: Number,
      required: true
    }
  },
  setup (props, { root, emit }) {
    const callback = () => {
      removeData()
      root.$router.go(-1)
    }
    return { callback }
  }
}
</script>

<style scoped lang="scss">
@import "../wrapper";
</style>
