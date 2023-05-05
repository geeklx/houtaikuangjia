<template>
  <fs-card shadow="never" align-center class="wrapper">
    <div slot="header">{{titleName}}</div>
    <div class="fs-flex fs-row-center wrapper-steps">
      <fs-steps :active="active" class="wrapper-steps__step">
        <fs-step title="选择应用类型" description=""></fs-step>
        <fs-step title="完善基础信息" description=""></fs-step>
        <fs-step title="完善高级配置" description=""></fs-step>
        <fs-step title="完成应用创建" description=""></fs-step>
      </fs-steps>
    </div>
    <div class="wrapper-bottom">
      <component :is="comp" :active="active" @on-next="handleNext" @on-prev="handlePrev"></component>
    </div>
  </fs-card>
</template>

<script>
import { ref, onMounted } from '@vue/composition-api'
import {
  saveAppType,
  saveAppId,
  saveBasicData,
  saveH5Data,
  removeData,
  saveIosData, saveAndroidData
} from './hooks/useSteps'

export default {
  name: 'RecourseActive',
  components: {
    SelectType: () => import('./components/SelectType'),
    BasicInfo: () => import('./components/BasicInfo'),
    AppSeniorInfo: () => import('./components/AppSeniorInfo'),
    H5SeniorInfo: () => import('./components/H5SeniorInfo'),
    Finish: () => import('./components/Finish')
  },
  setup (props, { root }) {
    const titleName = ref('创建应用')
    root.$route.query.id && (titleName.value = '编辑应用')
    const active = ref(0)
    const comp = ref('SelectType')
    const msgNotify = componentName => {
      active.value += 1
      comp.value = componentName
    }
    const selectOptionsService = [
      {
        key: 0,
        method: async params => {
          const { appType } = params
          saveAppType(appType)
          msgNotify('BasicInfo')
        },
        comp: 'SelectType'
      },
      {
        key: 1,
        method: async params => {
          const APPTYPE = sessionStorage.getItem('APPTYPE')
          params.appType = APPTYPE
          root.$api('appcenteradd', 'saveBasic', params).then(res => {
            const { success, message, data } = res
            if (success) {
              root.$message.success('提交成功')
              const { id } = data
              saveAppId(id)
              saveBasicData(data)
            } else {
              root.$message.error(message)
            }
          })
          APPTYPE === 'appNative' ? msgNotify('AppSeniorInfo') : msgNotify('H5SeniorInfo')
        },
        comp: 'BasicInfo'
      },
      {
        key: 2,
        method: async params => {
          const APPTYPE = sessionStorage.getItem('APPTYPE')
          const APPID = sessionStorage.getItem('APPID')
          params.appId = APPID
          if (APPTYPE === 'h5') {
            // 调用h5保存接口
            params.appType = 'h5'
            root.$api('appcenteradd', 'saveH5', params).then(res => {
              const { success, message, data } = res
              if (success) {
                root.$message.success('提交成功')
                saveH5Data(data)
              } else {
                root.$message.error(message)
              }
            })
          } else {
            const { isIos } = this.formData
            if (isIos) {
              // 调用保存IOS保存接口
              params.appType = 'ios'
              root.$api('appcenteradd', 'saveIos', params).then(res => {
                const { success, message, data } = res
                if (success) {
                  root.$message.success('提交成功')
                  saveIosData(data)
                } else {
                  root.$message.error(message)
                }
              })
            }
          }
          msgNotify('Finish')
        },
        comp: 'SeniorInfo'
      },
      {
        key: 3,
        method: () => {
          removeData()
          root.$router.push({ name: 'appCenterManage' })
        },
        comp: 'Finish'
      }
    ]
    const handleNext = params => {
      const step = selectOptionsService.find(item => item.key === active.value)
      if (Reflect.has(step, 'comp')) {
        step.method(params)
      }
    }
    const handlePrev = () => {
      active.value -= 1
      comp.value = selectOptionsService.find(
        item => item.key === active.value
      ).comp
    }

    onMounted(async () => {
      if (root.$route.query.id) {
        const id = root.$route.query.id
        saveAppId(id)
        // 编辑是否需要保存id
        root.$api('appcenteradd', 'get', { id }).then(res => {
          const { success, message, data } = res
          if (success) {
            const { config } = data
            saveBasicData(data)
            if (config) {
              config.forEach(item => {
                const { appType } = item
                if (appType === 'android') {
                  saveAndroidData(item)
                } else if (appType === 'ios') {
                  saveIosData(item)
                } else if (appType === 'h5') {
                  saveH5Data(item)
                }
              })
            }
          } else {
            root.$message.error(message)
          }
        })
      }
    })
    return { active, comp, handleNext, handlePrev, titleName }
  }
}
</script>

<style scoped lang="scss">
@import "wrapper";
</style>
