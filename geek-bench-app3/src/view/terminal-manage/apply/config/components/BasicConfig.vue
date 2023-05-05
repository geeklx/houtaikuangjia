<template>
  <div class="enjoy-config">
    <div class="enjoy-config__btns">
      <fs-button type="primary" @click="save">保存</fs-button>
    </div>
    <div class="enjoy-config__content">
      <fs-form
        inline
        slot="searchForm"
        class="search-form"
        :rules="rules"
        :model="formData"
        label-width="100px"
        @submit.native.prevent
        size="mini"
      >
        <fs-row>
          <fs-col :span="24">
            <fs-form-item prop="appName" label="应用名称:">
              <fs-input
                maxlength="32"
                style="width: 372px"
                type="text"
                v-model="formData.appName"
                clearable
                placeholder="请输入应用名称"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="appType_dict" label="应用类别:">
              <fs-input
                style="width: 372px"
                type="text"
                disabled
                v-model="formData.appType_dict"
                clearable
                placeholder="请输入应用类别"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="appIcon" label="应用图标:">
              <fs-upload
                style="width: 372px"
                class="workbench-upload"
                :action="uploadAction"
                :show-file-list="false"
                :on-success="handleSuccess"
                :before-upload="beforeUpload"
              >
                <div v-if="formData.appIcon" class="workbench-upload__operate">
                  <div class="icons">
                    <i class="fs-icon-edit"></i>
                    <i class="fs-icon-delete" @click="handleRemove"></i>
                  </div>
                </div>
                <img v-if="formData.appIcon" :src="formData.appIcon" class="workbench-upload__avatar">
                <div v-else class="workbench-upload__icon">
                  <img src="@/view/png/add-image.png" />
                  <div>上传图标</div>
                </div>
              </fs-upload>
              <div style="width: 372px" class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="startName" label="启动类名:">
              <fs-input
                maxlength="220"
                style="width: 372px"
                type="text"
                v-model="formData.startName"
                clearable
                placeholder="请输入启动类名"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="startParam" label="启动参数:">
              <fs-input
                maxlength="220"
                style="width: 372px"
                type="text"
                v-model="formData.startParam"
                clearable
                placeholder="请输入启动参数"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="remark" label="应用简介:">
              <fs-input
                style="width: 372px"
                type="textarea"
                maxlength="220"
                :autosize="{ minRows: 3 }"
                v-model="formData.remark"
                clearable
                placeholder="请输入应用简介"
              />
            </fs-form-item>
          </fs-col>
        </fs-row>
      </fs-form>
    </div>
  </div>
</template>

<script>
// import { isHttps } from '@/utils/validate'
export default {
  $plugins: 'form',
  props: {
    id: {
      type: String
    }
  },
  data () {
    return {
      rules: {},
      defaultData: {}
    }
  },
  computed: {
    uploadAction () {
      const { storeInstance } = window.appContext
      const { ossUrl } = storeInstance.getters.configInfo
      return ossUrl || ''
    }
  },
  mounted () {
    this.configQuery()
  },
  methods: {
    configQuery () {
      this.$api('terminalapply', 'basicConfigGet', {
        id: this.id
      }).then(res => {
        const { data } = res
        this.formData = Object.assign({}, data)
        this.defaultData = Object.assign({}, data)
      })
    },
    getForm () {
      return {
        obj: this.formData,
        obj_: this.defaultData
      }
    },
    async save () {
      await this.$api('terminalapply', 'basicConfigSave', this.formData).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('更新成功')
          this.defaultData = Object.assign({}, this.formData)
        } else {
          this.$message.error(message)
        }
      })
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'appIcon', data.url)
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
      this.$delete(this.formData, 'appIcon')
    }
  }
}
</script>

<style scoped lang="scss">
.enjoy-config {
  height: 100%;
  &__btns {
    width: 100%;
    padding-bottom: 12px;
    display: flex;
    justify-content: flex-end;
  }
  &__content {
    height: calc(100% - 36px);
    overflow-y: auto;
  }
  ::v-deep.fs-form-item__label {
    min-width: 100px;
  }
  ::v-deep.fs-form-item {
    display: flex;
  }
}
</style>
