<template>
 <div class="detail-form">
   <div class="detail-form-header">
     <div class="module-title">应用详情</div>
     <fs-button type="primary" @click="$go(-1)" plain>
       返回
     </fs-button>
   </div>
   <div class="detail-form-body">
     <div class="detail-form-body-item">
       <div class="module-sub-title">基本配置</div>
       <div class="item__list">
         <show-info :basic-form="basicForm" :label-width="90" />
       </div>
     </div>
     <div class="detail-form-body-item">
       <div class="module-sub-title">高级配置信息</div>
       <div class="item__list">
         <show-info :basic-form="seniorForm" :label-width="appType === 'ios' ? 140 : 90" />
       </div>
     </div>
   </div>
 </div>
</template>

<script>
export default {
  $plugins: 'form',
  name: 'detail',
  data () {
    return {
      basicForm: [],
      seniorForm: [],
      appType: ''
    }
  },
  mounted () {
    this.$api('appcenterversion', 'get', {
      id: this.$route.query.versionId
    }).then(res => {
      const { success, message, data } = res
      if (success) {
        const { appInfo, appConfigs } = data
        if (appInfo) {
          const { maintain } = appInfo
          // 基础信息
          this.basicForm = [].concat([
            {
              name: '应用名称',
              value: appInfo.appName
            }, {
              name: '应用图标',
              type: 'icon',
              value: appInfo.iconUrl
            }, {
              name: '应用提供方',
              value: appInfo.appSupport
            }, {
              name: '应用类别',
              type: 'selectKey',
              key: 'appCategory',
              optionLabel: 'dictLabel',
              optionValue: 'dictValue',
              value: appInfo.categoryCode
            }, {
              name: '应用简介',
              value: appInfo.remark
            }, {
              name: '是否维护',
              value: maintain ? '是' : '否'
            }, {
              name: '包名',
              value: appInfo.packageName
            }
          ])
          if (maintain) {
            this.basicForm = this.basicForm.concat([{
              name: '维护提示',
              type: 'richText',
              value: appInfo.maintainMessage
            }])
          }
        }
        const [appConfig] = appConfigs
        if (appConfig) {
          const { appType } = appConfig
          this.appType = appType
          if (this.appType === 'h5') {
            const { publishType } = appConfig
            this.seniorForm = [].concat([
              {
                name: '发布方式',
                value: appConfig.publishType === 'staticFile' ? '静态文件' : appConfig.publishType === 'remoteLink' ? '远程链接' : ''
              }, {
                name: '版本号',
                value: appConfig.versionNum
              }, {
                name: '版本说明',
                value: appConfig.remark
              }
            ])
            if (publishType === 'remoteLink') {
              this.seniorForm = this.seniorForm.concat([
                {
                  name: '前端URL',
                  value: appConfig.frontUrl
                }
              ])
            } else {
              this.seniorForm = this.seniorForm.concat([
                {
                  name: 'H5压缩包',
                  value: appConfig.appPackagePath
                }
              ])
            }
          } else if (this.appType === 'android') {
            this.seniorForm = [].concat([
              {
                name: '应用包',
                value: appConfig.appPackagePath
              }, {
                name: '版本号',
                value: appConfig.versionNum
              }, {
                name: '调起应用',
                value: appConfig.startApp ? '是' : '否'
              }, {
                name: '下载地址',
                value: appConfig.downloadPath
              }, {
                name: '版本说明',
                value: appConfig.remark
              }
            ])
          } else if (this.appType === 'ios') {
            this.seniorForm = [].concat([
              {
                name: 'AppStore ID',
                value: appConfig.appStoreId
              }, {
                name: '版本号',
                value: appConfig.versionNum
              }, {
                name: '调起应用',
                value: appConfig.startApp ? '是' : '否'
              }, {
                name: '包名',
                value: appConfig.packageName
              }, {
                name: 'IOS URL Schemes',
                value: appConfig.iosUrlScemes
              }, {
                name: '版本说明',
                value: appConfig.remark
              }
            ])
          }
        }
      } else {
        this.$message.error(message)
      }
    })
  }
}
</script>

<style scoped lang="scss">
.detail-form {
  padding: 14px;
  height: 100%;
  &-header {
    padding: 0 12px;
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #F0F2F7;
    background-color: #FFF;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  &-body {
    background-color: #FFF;
    padding: 19px 12px;
    height: calc(100% - 50px);
    overflow-y: auto;
    &-item {
      border: 1px solid #F0F2F7;
      padding: 14px 14px 20px;
      .module-sub-title {
        margin-bottom: 20px;
      }
      ::v-deep.fs-input.is-disabled .fs-input__inner,
      ::v-deep.fs-select .fs-input.is-disabled .fs-input__inner,
      ::v-deep.fs-textarea.is-disabled .fs-textarea__inner {
        border-color: #CFD4DB;
        background: #fff;
        color: #666;
      }
    }
    &-item:not(:last-child) {
      margin-bottom: 20px;
    }
  }
}
</style>
