<template>
  <fs-dialog
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="标题"
    @closed="close"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="90px"
      label-position="right"
    >
      <fs-form-item label="orgName" prop="orgName">
        <fs-input
          v-model="formData.orgName"
          :disabled="readonly"
          placeholder="请输入orgName"
        />
      </fs-form-item>
      <fs-form-item label="作者" prop="author">
        <!-- readonly 编辑与新建弹窗默认为false,查看弹窗默认为true -->
        <fs-input
          v-model="formData.author"
          :disabled="readonly"
          placeholder="请输入作者姓名"
        />
      </fs-form-item>
      <fs-form-item label="标题" prop="title">
        <fs-input
          v-model="formData.title"
          :disabled="readonly"
          placeholder="请输入标题"
        />
      </fs-form-item>
      <fs-form-item label="综合浏览量" prop="pageviews">
        <fs-input
          v-model="formData.pageviews"
          :disabled="readonly"
          placeholder="请输入综合浏览量"
        />
      </fs-form-item>
      <fs-form-item label="状态" prop="status">
        <fs-select
          v-model="formData.status"
          :disabled="readonly"
          placeholder="请选择状态"
        >
          <fs-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </fs-option>
        </fs-select>
      </fs-form-item>
      <fs-form-item label="创建时间" prop="display_time">
        <fs-date-picker
          v-model="formData.display_time"
          type="datetime"
          :disabled="readonly"
          placeholder="请选择创建时间"
        >
        </fs-date-picker>
      </fs-form-item>
    </fs-form>

    <span slot="footer" class="dialog-footer" v-show="!readonly">
      <fs-button @click="close">取 消</fs-button>
      <fs-button v-if="!readonly" type="primary" @click="submitHandler"
        >确 定</fs-button
      >
    </span>
  </fs-dialog>
</template>

<script>
export default {
  $plugins: 'form', // 此处是关键，必须包含form才可以！！！
  data () {
    return {
      apiName: 'get',
      queryMethod: 'villagePartyDetail',
      submitMethod: 'save',
      rules: {
        author: [
          { required: true, message: '请输入活动名称' },
          { min: 2, max: 8, message: '长度在 2 到 8 个字符' }
        ],
        orgName: [
          { max: 50, message: '最大字符长度50', trigger: 'blur' }
        ],
        pageviews: [
          { max: 50, message: '最大字符长度50', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '请输入标题' },
          { max: 50, message: '最大字符长度50', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择状态' }
        ]
      },
      options: [{
        value: 'published',
        label: '已发布'
      }, {
        value: 'draft',
        label: '未发布'
      }]
    }
  },
  methods: {
    // 表单创建之前回调，可以给创建的表单赋默认值
    beforeCreateHandler () {
      this.formData.orgName = '123'
    },
    // 表单提交之前回调
    beforeSubmitHandler () {
    }
    /**
     * 常用内置函数
     */
    // // 查询之前的处理
    // queryBeforeHandler () { },
    // // 查询成功时处理
    // querySuccessHandler (_responseInfo) { },
    // // 查询异常时处理
    // queryErrorHandler (_errorResponse) { },
    // // 查询之后的处理，不管查询是否执行成功了
    // queryAfterHandler () { },
    // // 提交之前的回调
    // beforeSubmitHandler () { },
    // // 提交之后的处理，无论提交是否成功
    // afterSubmitHandler () { },
    // // 提交成功时处理
    // submitSuccessHandler (_responseInfo) { },
    // // 提交失败时的处理
    // submitErrorHandler (_errorResponse) { },
  }
}
</script>

<style lang="scss" scoped>
</style>
