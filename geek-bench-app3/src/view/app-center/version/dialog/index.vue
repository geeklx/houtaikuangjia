<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="parentParam.title"
    @closed="close"
    width="702px"
  >
    <component
      ref="component"
      v-if="visible && parentParam.type"
      :is="parentParam.type === 'android' ? 'Android' : parentParam.type === 'h5' ? 'H5' : parentParam.type === 'ios' ? 'Ios' : ''"
      :appConfig="parentParam.appConfig"
      :appId="parentParam.appId"
      :key="parentParam.type"
      @submitSuccess="submitSuccess"
    ></component>
    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitInfo">确 定</fs-button>
    </span>
  </fs-dialog>
</template>

<script>
import Android from './and'
import H5 from './h5'
import Ios from './ios'

export default {
  $plugins: 'form',
  components: {
    Android,
    H5,
    Ios
  },
  methods: {
    submitInfo () {
      this.$refs.component.saveInfo()
    },
    submitSuccess () {
      this.close()
      this.$emit('query')
    }
  }
}
</script>

<style>
</style>
