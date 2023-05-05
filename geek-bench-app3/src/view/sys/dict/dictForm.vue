<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :lock-scroll="false"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '新增字典' : '编辑字典'"
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
      <fs-form-item label="字典名称：" prop="dictName">
        <fs-input
          maxlength="32"
          style="width: 389px"
          v-model="formData.dictName"
          :disabled="readonly"
          placeholder="请输入字典名称"
        />
      </fs-form-item>
      <fs-form-item label="字典编码：" prop="dictType">
        <fs-input
          maxlength="32"
          style="width: 389px"
          v-model="formData.dictType"
          :disabled="operateType !== 'new'"
          placeholder="请输入字典编码"
        />
      </fs-form-item>
      <fs-form-item label="备注" prop="remark">
        <fs-input
          :autosize="{ minRows: 6 }"
          maxlength="210"
          style="width: 389px"
          type="textarea"
          v-model="formData.remark"
          :disabled="readonly"
          placeholder="请输入备注信息(210字以内)"
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
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        dictName: [
          { required: true, message: '请输入字典名称', trigger: 'blur' },
          { max: 32, message: '最大字符长度32', trigger: 'blur' }
        ],
        dictType: [
          { required: true, message: '请输入字典编码', trigger: 'blur' },
          { max: 32, message: '最大字符长度32', trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
