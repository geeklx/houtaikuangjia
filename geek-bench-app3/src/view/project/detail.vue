<template>
  <fs-dialog
    class="dialog-workbench medium"
    append-to-body
    destroy-on-close
    :lock-scroll="false"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '创建项目' : '编辑项目'"
    @closed="close"
    width="702px"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="100px"
      label-position="right"
    >
      <fs-form-item label="项目名称:" prop="projectName">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.projectName"
          placeholder="请输入项目名称"
        />
      </fs-form-item>
      <fs-form-item label="项目编码:" prop="projectCode">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.projectCode"
          placeholder="请输入项目编码"
        />
      </fs-form-item>
      <fs-form-item label="负责人:" prop="person">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.person"
          placeholder="请输入负责人"
        />
      </fs-form-item>
      <fs-form-item label="项目说明:" prop="remark">
        <fs-input
          style="width: 372px"
          maxlength="220"
          v-model="formData.remark"
          :autosize="{ minRows: 3 }"
          type="textarea"
          placeholder="请输入项目说明"
        />
      </fs-form-item>
    </fs-form>

    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler">
        确 定
      </fs-button>
    </span>
  </fs-dialog>
</template>

<script>
export default {
  $plugins: 'form', // 此处是关键，必须包含form才可以！！！
  data () {
    return {
      apiName: 'projectmanage',
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        projectName: [
          { required: true, message: '请输入项目名称', trigger: 'blur' }
        ],
        projectCode: [
          { required: true, message: '请输入项目编码', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^[a-zA-Z0-9_-]+$/.test(value)) {
                callback(new Error('项目编码必须为字母、数字、下划线、中划线组成'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        person: [
          { required: true, message: '请输入负责人', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
  }
}
</script>
<style lang="scss" scoped>
</style>
