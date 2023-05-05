<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="parentParam.title"
    @closed="close"
    width="960px"
  >
    <component
      v-if="visible && parentParam.appId"
      :is="parentParam.type"
      :appId="parentParam.appId"
      :appType="parentParam.appType"
      :key="parentParam.type"
    ></component>
  </fs-dialog>
</template>

<script>
import HistoryVersion from './historyVersion'

export default {
  $plugins: 'form',
  components: {
    HistoryVersion
  },
  methods: {
    close () {
      this.$emit('query')
    }
  }
}
</script>

<style>
</style>
