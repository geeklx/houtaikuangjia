<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="编辑搜索知识分类"
    @closed="close"
    width="702px"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="120px"
      label-position="right"
    >
      <fs-form-item label="知识分类名称:" prop="knowledgeCategoryName">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.knowledgeCategoryName"
          placeholder="请输入知识分类名称"
        />
      </fs-form-item>
      <fs-form-item label="知识分类编码:" prop="knowledgeCategoryCode">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.knowledgeCategoryCode"
          placeholder="请输入知识分类编码"
        />
      </fs-form-item>
      <fs-form-item label="描述：" prop="knowledgeCategoryRemark">
        <fs-input
          maxlength="220"
          style="width: 372px"
          v-model="formData.knowledgeCategoryRemark"
          :autosize="{ minRows: 3 }"
          type="textarea"
          placeholder="请输入描述"
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
  $plugins: 'form', // 此处是关键,必须包含form才可以！！！
  data () {
    return {
      apiName: 'runConfig',
      queryMethod: 'get2',
      submitMethod: 'save2',
      rules: {
        knowledgeCategoryName: [
          { required: true, message: '请输入知识分类名称', trigger: 'blur' }
        ],
        knowledgeCategoryCode: [
          { required: true, message: '请输入知识分类编码', trigger: 'blur' }
        ]
      },
      configPlatformList: []
    }
  },
  methods: {
    // 编辑查询接口返回数据
    getResponseInfo (response) {
      return response
    },
    getSubmitData () {
      return this.formData
    },
    closeAfterHandler () {
      this.$emit('query')
    }
  }
}
</script>
