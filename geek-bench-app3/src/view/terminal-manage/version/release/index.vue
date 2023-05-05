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
      v-if="visible && parentParam.versionId"
      :is="parentParam.type"
      :versionId="parentParam.versionId"
      :key="parentParam.type"
    ></component>
  </fs-dialog>
</template>

<script>
import ReleaseRecord from './releaseRecord'

export default {
  $plugins: 'form',
  components: {
    ReleaseRecord
  }
}
</script>
