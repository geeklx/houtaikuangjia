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
      label-width="130px"
      label-position="right"
    >
      <fs-form-item label="菜单名称:" prop="navigationName">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.navigationName"
          placeholder="请输入菜单名称"
        />
      </fs-form-item>
      <fs-form-item label="关联对象:" prop="associatedObject">
        <fs-dictionary-select
          style="width: 372px"
          v-model="formData.associatedObject"
          dictionaryKey="associationType"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择关联对象"
          @change="associatedObjectChange"
        />
      </fs-form-item>
      <fs-form-item label="跳转页面:" prop="navigationUrl">
        <template v-if="formData.associatedObject === 'remote'">
          <fs-input
            style="width: 372px"
            v-model="formData.navigationUrl"
            placeholder="请输入跳转页面"
          />
        </template>
        <template v-else>
          <fs-select
            style="width: 372px"
            v-model="formData.navigationUrl"
            placeholder="请选择跳转页面"
            clearable
          >
            <fs-option
              v-for="item in navigationUrlList"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue">
            </fs-option>
          </fs-select>
        </template>
      </fs-form-item>
      <fs-form-item label="显示样式:" prop="showStyle">
        <fs-dictionary-select
          style="width: 372px"
          v-model="formData.showStyle"
          dictionaryKey="showStyleType"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择显示样式"
        />
      </fs-form-item>
      <fs-form-item label="默认选中:" prop="defaultShow" required>
        <fs-switch v-model="formData.defaultShow"></fs-switch>
      </fs-form-item>
      <fs-form-item label="菜单图标:" prop="navigationLogoChecked" class="fs-nav-icons">
        <div
          class="fs-nav-icons__container"
        >
          <fs-upload
            class="workbench-upload"
            :action="uploadAction"
            :show-file-list="false"
            :on-success="handleSuccess"
            :before-upload="beforeUpload"
          >
            <div v-if="formData.navigationLogoChecked" class="workbench-upload__operate">
              <div class="icons">
                <i class="fs-icon-edit"></i>
                <i class="fs-icon-delete" @click="handleRemove"></i>
              </div>
            </div>
            <img v-if="formData.navigationLogoChecked" :src="formData.navigationLogoChecked" class="workbench-upload__avatar" />
            <div v-else class="workbench-upload__icon">
              <img src="@/view/png/add-image.png" />
              <div>选中状态</div>
            </div>
          </fs-upload>
          <fs-form-item label="" prop="navigationLogoNoChecked">
            <fs-upload
              class="workbench-upload"
              :action="uploadAction"
              :show-file-list="false"
              :on-success="handleSuccess2"
              :before-upload="beforeUpload">
              <div v-if="formData.navigationLogoNoChecked" class="workbench-upload__operate">
                <div class="icons">
                  <i class="fs-icon-edit"></i>
                  <i class="fs-icon-delete" @click="handleRemove2"></i>
                </div>
              </div>
              <img v-if="formData.navigationLogoNoChecked" :src="formData.navigationLogoNoChecked" class="workbench-upload__avatar" />
              <div v-else class="workbench-upload__icon">
                <img src="@/view/png/add-image.png" />
                <div>未选中状态</div>
              </div>
            </fs-upload>
          </fs-form-item>
        </div>
        <div style="width: 372px" class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸：56*56px,如图标较小,需周围留白</div>
      </fs-form-item>
      <fs-form-item label="是否强制登录:" prop="forceLogin">
        <fs-select v-model="formData.forceLogin" style="width: 372px">
          <fs-option value="true" label="是"></fs-option>
          <fs-option value="false" label="否"></fs-option>
        </fs-select>
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
      apiName: 'navconfig',
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        navigationName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' }
        ],
        associatedObject: [
          { required: true, message: '请选择关联对象', trigger: 'change' }
        ],
        navigationLogoNoChecked: [
          { required: true, message: '请上传图标', trigger: 'change' }
        ],
        navigationLogoChecked: [
          { required: true, message: '请上传图标', trigger: 'change' }
        ],
        forceLogin: [
          { required: true, message: '请选择是否强制登录', trigger: 'change' }
        ],
        showStyle: [
          { required: true, message: '请选择显示样式', trigger: 'change' }
        ],
        navigationUrl: [
          { required: true, message: '请选择跳转页面', trigger: 'change' }
        ]
      },
      navigationUrlList: []
    }
  },
  computed: {
    uploadAction () {
      const { storeInstance } = window.appContext
      const { ossUrl } = storeInstance.getters.configInfo
      return ossUrl || ''
    }
  },
  methods: {
    queryNavigationUrlList (val) {
      const { terminalId } = this.parentParam
      const params = {
        terminalId,
        associationType: val
      }
      this.$api('navconfig', 'queryNavigationUrlList', params).then(res => {
        const { datalist } = res
        this.$set(this, 'navigationUrlList', datalist)
      })
    },
    associatedObjectChange (val) {
      this.$delete(this.formData, 'navigationUrl')
      this.$set(this, 'navigationUrlList', [])
      if (val !== 'remote') this.queryNavigationUrlList(val)
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'navigationLogoChecked', data.url)
      this.$refs[this.validRef].clearValidate('navigationLogoChecked')
    },
    beforeCreateHandler () {
      this.$set(this.formData, 'forceLogin', 'false')
      this.$set(this.formData, 'defaultShow', false)
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('上传图片只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      return isJPG
    },
    handleRemove (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'navigationLogoChecked')
    },
    handleSuccess2 (res) {
      const { data } = res
      this.$set(this.formData, 'navigationLogoNoChecked', data.url)
      this.$refs[this.validRef].clearValidate('navigationLogoNoChecked')
    },
    handleRemove2 (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'navigationLogoNoChecked')
    },
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
.fs-nav-icons {
  &__container {
    display: flex;
    .fs-form-item {
      margin-left: 18px;
    }
  }
  ::v-deep > .fs-form-item__content {
    .workbench-upload-text + .fs-form-item__error {
      margin-top: -73px;
    }
  }
}
</style>
