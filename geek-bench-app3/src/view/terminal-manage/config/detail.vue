<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="编辑运行配置"
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
      <fs-form-item label="配置类型:">
        <fs-input
          style="width: 372px"
          v-model="formData.configType_dict"
          disabled
        />
      </fs-form-item>
      <fs-form-item label="认证平台:" prop="configPlatform">
        <fs-select
          style="width: 372px"
          v-model="formData.configPlatform"
          placeholder="请选择认证平台"
          clearable
        >
          <fs-option
            v-for="item in configPlatformList"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue">
          </fs-option>
        </fs-select>
      </fs-form-item>
      <fs-form-item label="描述：" prop="remark">
        <fs-input
          maxlength="220"
          style="width: 372px"
          v-model="formData.remark"
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
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        configPlatform: [
          { required: true, message: '请选择认证平台', trigger: 'change' }
        ]
      },
      configPlatformList: []
    }
  },
  methods: {
    // 编辑查询接口返回数据
    getResponseInfo (response) {
      const { data } = response
      const { configType } = data
      if (configType) this.queryConfigPlatformList(configType)
      return response
    },
    getSubmitData () {
      const { configPlatform } = this.formData
      const info = this.configPlatformList.find(item => {
        return item.dictValue === configPlatform
      })
      this.formData.configPlatformName = info.dictLabel
      return this.formData
    },
    queryConfigPlatformList (configType) {
      this.$api('runConfig', 'queryConfigPlatformList', { configType }).then(res => {
        const { datalist } = res
        this.configPlatformList = [].concat(datalist)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
