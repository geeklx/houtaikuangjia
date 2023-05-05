<template>
  <fs-dialog
    class="dialog-workbench medium"
    append-to-body
    destroy-on-close
    :lock-scroll="false"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '创建配置' : '编辑配置'"
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
      <fs-form-item label="认证平台名称:" prop="configName">
        <fs-input
          maxlength="32"
          style="width: 372px"
          v-model="formData.configName"
          placeholder="请输入认证平台名称"
        />
      </fs-form-item>
      <fs-form-item label="配置类型:" prop="configType">
        <fs-select
          v-if="operateType === 'new'"
          style="width: 372px"
          v-model="formData.configType"
          placeholder="请选择配置类型"
          clearable
          @change="configTypeChange"
        >
          <fs-option
            v-for="item in configTypeList"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue">
          </fs-option>
        </fs-select>
        <fs-input
          disabled
          v-else
          style="width: 372px"
          v-model="formData.configTypeName"
        />
      </fs-form-item>
      <fs-form-item label="认证平台地址:" prop="configPlatform">
        <!--<fs-select
          v-if="operateType === 'new'"
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
        </fs-select>-->
        <fs-input
          style="width: 372px"
          v-model="formData.configPlatform"
          placeholder="请输入认证平台地址"
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
      apiName: 'configmanage',
      queryMethod: 'get',
      submitMethod: 'save',
      configTypeList: [],
      configPlatformList: [],
      rules: {
        configName: [
          { required: true, message: '请输入认证平台名称', trigger: 'blur' }
        ],
        configType: [
          { required: true, message: '请选择配置类型', trigger: 'change' }
        ],
        configPlatform: [
          // { required: true, message: '请选择认证平台', trigger: 'change' }
          { required: true, message: '请输入认证平台地址', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    beforeCreateHandler () {
      this.queryList(0, 'configTypeList')
    },
    configTypeChange (val) {
      // this.$delete(this.formData, 'configPlatform')
      // this.$set(this, 'configPlatformList', [])
      // if (val) {
      //   const info = this.configTypeList.find(item => {
      //     return item.dictValue === val
      //   })
      //   const { id } = info
      //   this.queryList(id, 'configPlatformList')
      // }
    },
    async queryList (val, type) {
      await this.$api('configmanage', 'queryList', {
        parentId: val,
        configType: 'config'
      }).then(res => {
        const { success, message, datalist } = res
        if (success) {
          this.$set(this, type, datalist)
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
    },
    getSubmitData () {
      const info1 = this.configTypeList.find(item => {
        return item.dictValue === this.formData.configType
      })
      // const info2 = this.configPlatformList.find(item => {
      //   return item.dictValue === this.formData.configPlatform
      // })
      info1 && (this.formData.configTypeName = info1.dictLabel)
      this.formData.configName && (this.formData.configPlatformName = this.formData.configName)
      return this.formData
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
