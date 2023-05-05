<template>
    <form-layout>
      <!-- 右上角 -->
      <div slot="form-header-left" class="title">表单</div>
      <div slot="form-header-right">
        <fs-button slot="top-right" type="primary" plain @click="$go(-1)"
          >取消</fs-button
        >
        <fs-button
          slot="top-right"
          type="primary"
          plain
          @click="submitHandler"
          v-if="this.operateType != 'detail'"
          >保存</fs-button
        >
      </div>

      <fs-form
        slot="form"
        :ref="validRef"
        :model="formData"
        :rules="rules"
        label-width="90px"
        label-position="right"
      >
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
    </form-layout>
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
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
        ],
        pageviews: [
          { max: 50, message: '最大字符长度50', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '请输入标题', trigger: 'change' },
          { max: 50, message: '最大字符长度50', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' }
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
    // 提交成功处理
    submitSuccessHandler (res) {
      this.resetFormValid()// 重置表单
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
