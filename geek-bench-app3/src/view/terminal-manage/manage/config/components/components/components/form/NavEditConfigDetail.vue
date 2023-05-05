<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '创建菜单' : '编辑菜单'"
    @closed="close"
    width="702px"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="90px"
      label-position="right"
    >
      <fs-form-item label="菜单名称:" prop="name">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.name"
          placeholder="请输入菜单名称"
        />
      </fs-form-item>
      <fs-form-item label="链接地址:" prop="url">
        <fs-input
          style="width: 372px"
          v-model="formData.url"
          placeholder="请输入链接地址"
        />
      </fs-form-item>
      <fs-form-item label="排序:" prop="num">
        <fs-input
          type="text"
          oninput="value=value.replace(/[^\d]/g,'')"
          style="width: 372px"
          v-model="formData.num"
          placeholder="请输入数字"
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
      queryMethod: 'editGet',
      submitMethod: 'editSave',
      rules: {
        name: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '请输入链接地址', trigger: 'blur' }
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
      const { navigationId, parentId } = this.parentParam
      if (navigationId) {
        this.formData = Object.assign(this.formData, {
          navigationId,
          parentId
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
