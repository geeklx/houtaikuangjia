<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :lock-scroll="false"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '新增码值' : '编辑码值'"
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
      <fs-form-item label="名称：" prop="dictLabel">
        <fs-input
          maxlength="32"
          style="width: 389px"
          v-model="formData.dictLabel"
          :disabled="readonly"
          placeholder="请输入名称"
        />
      </fs-form-item>
      <fs-form-item label="编码：" prop="dictValue">
        <fs-input
          maxlength="32"
          style="width: 389px"
          v-model="formData.dictValue"
          :disabled="readonly"
          placeholder="请输入编码"
        />
      </fs-form-item>
      <fs-form-item label="排序" prop="num">
        <fs-input-number
          style="width: 389px"
          v-model="formData.num"
          :disabled="readonly"
          placeholder="请输入排序"
        />
      </fs-form-item>
    </fs-form>
    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">{{ readonly ? '关 闭' : '取 消' }}</fs-button>
      <fs-button v-if="!readonly" type="primary" @click="submitHandler">确 定</fs-button>
    </span>
  </fs-dialog>
</template>

<script>
export default {
  $plugins: 'form',
  data () {
    return {
      apiName: 'dict',
      queryMethod: 'getConfig',
      submitMethod: 'saveConfig',
      rules: {
        dictLabel: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { max: 32, message: '最大字符长度32', trigger: 'blur' }
        ],
        dictValue: [
          { required: true, message: '请输入编码', trigger: 'blur' },
          { max: 32, message: '最大字符长度32', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    getSubmitData () {
      const { dictType } = this.parentParam
      console.log(Object.assign(this.formData, {
        dictType
      }))
      return Object.assign(this.formData, {
        dictType
      })
    }
  }
}
</script>
