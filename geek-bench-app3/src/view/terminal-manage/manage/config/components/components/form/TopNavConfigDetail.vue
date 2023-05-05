<template>
  <fs-dialog
    v-if="visible"
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '创建顶部导航' : '编辑顶部导航'"
    @closed="close"
    width="702px"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="130px"
      label-position="right"
    >
      <fs-form-item label="导航名称:" prop="navigationName">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.navigationName"
          placeholder="请输入导航名称"
        />
      </fs-form-item>
      <fs-form-item label="关联底部菜单:" prop="navigationBtmId">
        <fs-dictionary-select
          style="width: 372px"
          v-model="formData.navigationBtmId"
          url="/api/terminal/navigation/config/option"
          optionLabel="navigationName"
          optionValue="id"
          clearable
          :isCache="false"
          :params="getParams"
          requestType="postJson"
          placeholder="请选择关联底部菜单"
        />
      </fs-form-item>
    </fs-form>

    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler">确 定</fs-button>
    </span>
  </fs-dialog>
</template>

<script>
export default {
  $plugins: 'form', // 此处是关键,必须包含form才可以！！！
  data () {
    return {
      apiName: 'topnavconfig',
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        navigationName: [
          { required: true, message: '请输入顶部导航名称', trigger: 'blur' }
        ],
        navigationBtmId: [
          { required: true, message: '请选择关联底部导航', trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    getParams () {
      const { terminalId, area } = this.parentParam
      return {
        terminalId,
        area
      }
    }
  },
  methods: {
    getSubmitData () {
      const { terminalId, area } = this.parentParam
      if (terminalId && area) {
        this.formData = Object.assign(this.formData, {
          terminalId,
          area
        })
      }
      return this.formData
    }
  }
}
</script>
<style lang="scss" scoped>
.nav-icon {
  display: flex;
  .workbench-upload:first-child {
    margin-right: 12px;
  }
}
</style>
