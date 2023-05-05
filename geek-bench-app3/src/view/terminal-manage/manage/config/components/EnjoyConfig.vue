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
        label-width="220px"
        @submit.native.prevent
        size="mini"
      >
        <fs-row>
          <fs-col :span="24">
            <fs-form-item prop="client_url" label="客户端logo:">
              <fs-upload
                style="width: 372px"
                class="workbench-upload"
                :action="uploadAction"
                :show-file-list="false"
                :on-success="handleSuccess"
                :before-upload="beforeUpload"
              >
                <div v-if="formData.client_url" class="workbench-upload__operate">
                  <div class="icons">
                    <i class="fs-icon-edit"></i>
                    <i class="fs-icon-delete" @click="handleRemove"></i>
                  </div>
                </div>
                <img v-if="formData.client_url" :src="formData.client_url" class="workbench-upload__avatar">
                <div v-else class="workbench-upload__icon">
                  <img src="@/view/png/add-image.png" />
                  <div>上传图标</div>
                </div>
              </fs-upload>
              <div style="width: 372px" class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="bottom_url" label="分享页底部logo:">
              <fs-upload
                style="width: 372px"
                class="workbench-upload"
                :action="uploadAction"
                :show-file-list="false"
                :on-success="handleSuccess2"
                :before-upload="beforeUpload"
              >
                <div v-if="formData.bottom_url" class="workbench-upload__operate">
                  <div class="icons">
                    <i class="fs-icon-edit"></i>
                    <i class="fs-icon-delete" @click="handleRemove2"></i>
                  </div>
                </div>
                <img v-if="formData.bottom_url" :src="formData.bottom_url" class="workbench-upload__avatar">
                <div v-else class="workbench-upload__icon">
                  <img src="@/view/png/add-image.png" />
                  <div>上传图标</div>
                </div>
              </fs-upload>
              <div style="width: 372px" class="workbench-upload-text">PNG、JPEG、JPG、SVG格式,图标建议尺寸:56*56px,如图标较小,需周围留白</div>
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="android_url" label="Android客户端下载地址:">
              <fs-input
                style="width: 372px"
                type="text"
                v-model="formData.android_url"
                clearable
                placeholder="请输入Android客户端下载地址"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="ios_url" label="IOS客户端下载地址:">
              <fs-input
                style="width: 372px"
                type="text"
                v-model="formData.ios_url"
                clearable
                placeholder="请输入IOS客户端下载地址"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="android_url_schemes" label="Android URL Schemes:">
              <fs-input
                style="width: 372px"
                type="text"
                v-model="formData.android_url_schemes"
                clearable
                placeholder="请输入Android URL Schemes"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="android_hosts" label="Android hosts:">
              <fs-input
                style="width: 372px"
                type="text"
                v-model="formData.android_hosts"
                clearable
                placeholder="请输入Android hosts"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="app_store_appid" label="AppStore appid:">
              <fs-input
                style="width: 372px"
                type="text"
                v-model="formData.app_store_appid"
                clearable
                placeholder="请输入AppStore appid"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="ios_url_schemes" label="IOS URL Schemes:">
              <fs-input
                style="width: 372px"
                type="text"
                v-model="formData.ios_url_schemes"
                clearable
                placeholder="请输入IOS URL Schemes"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="domain" label="分享链接域名:">
              <fs-input
                style="width: 372px"
                type="text"
                v-model="formData.domain"
                clearable
                placeholder="请输入分享链接域名"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="channel" label="分享渠道:">
              <fs-checkbox-group
                v-model="formData.channel"
              >
                <fs-checkbox
                  v-for="item in channelList"
                  :key="item.id"
                  :label="item.dictValue"
                >
                  {{item.dictLabel}}
                </fs-checkbox>
              </fs-checkbox-group>
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
      channelList: [],
      rules: {
        android_url: [
          // {
          //   validator: (rule, value, callback) => {
          //     if (value && !isHttps(value)) {
          //       callback(new Error('必须是http或https开头的地址'))
          //     } else {
          //       callback()
          //     }
          //   },
          //   trigger: 'blur'
          // }
        ],
        ios_url: [
          // {
          //   validator: (rule, value, callback) => {
          //     if (value && !isHttps(value)) {
          //       callback(new Error('必须是http或https开头的地址'))
          //     } else {
          //       callback()
          //     }
          //   },
          //   trigger: 'blur'
          // }
        ]
      },
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
    const SHARE = JSON.parse(sessionStorage.getItem('SHARE'))
    this.formData = Object.assign({}, SHARE)
    this.defaultData = Object.assign({}, SHARE)
    this.queryChannelList()
  },
  methods: {
    getForm () {
      return {
        obj: this.formData,
        obj_: this.defaultData
      }
    },
    async save () {
      const params = Object.assign({}, this.formData)
      if (params.channel.length > 0) {
        params.channel = params.channel.join(',')
      } else if (params.channel.length === 0) {
        params.channel = ''
      }
      await this.$api('terminalmanage', 'configSave', {
        terminalId: this.id,
        share: params
      }).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('更新成功')
          sessionStorage.setItem('SHARE', JSON.stringify(this.formData))
          this.defaultData = Object.assign({}, this.formData)
        } else {
          this.$message.error(message)
        }
      })
    },
    queryChannelList () {
      this.$api('common', 'getDictionary', {
        dictionaryKey: 'channel'
      }).then(res => {
        const { datalist } = res
        this.channelList = [].concat(datalist)
      })
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'client_url', data.url)
    },
    handleSuccess2 (res) {
      const { data } = res
      this.$set(this.formData, 'bottom_url', data.url)
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
      this.$delete(this.formData, 'client_url')
    },
    handleRemove2 (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'bottom_url')
    }
  }
}
</script>

<style scoped lang="scss">
.enjoy-config {
  height: 100%;
  padding: 12px;
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
