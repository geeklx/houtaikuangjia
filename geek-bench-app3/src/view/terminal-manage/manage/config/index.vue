<template>
  <list-layout :topRightWidth="0">
    <div slot="top-header" class="title">{{ $route.meta.title }}</div>
    <div class="config-contain">
      <div class="config-contain-menu">
        <fs-menu
          default-active="navConfig"
          :default-openeds="menuOpens"
          @select="menuSelect"
        >
          <fs-menu-item index="navConfig">
            <span slot="title">导航配置</span>
          </fs-menu-item>
          <fs-submenu index="BasicConfig">
            <template slot="title">
              <span>基础配置</span>
            </template>
            <fs-menu-item index="advConfig">广告页设置</fs-menu-item>
            <fs-menu-item index="guideConfig">引导页设置</fs-menu-item>
            <!--<fs-menu-item index="enjoyConfig">分享页设置</fs-menu-item>-->
            <fs-menu-item index="pcGrey">客户端置灰</fs-menu-item>
            <fs-menu-item index="protocolConfig">协议设置</fs-menu-item>
            <!--<fs-menu-item index="languageConfig">语言设置</fs-menu-item>
            <fs-menu-item index="themeConfig">主题设置</fs-menu-item>-->
            <fs-menu-item index="phoneConfig">客服电话配置</fs-menu-item>
          </fs-submenu>
          <fs-submenu index="AppConfigs">
            <template slot="title">
              <span>工作台配置</span>
            </template>
            <fs-menu-item index="appConfig">应用维护</fs-menu-item>
          </fs-submenu>
        </fs-menu>
      </div>
      <div class="config-contain-content">
        <component
          v-if="menuCurr !== '' && id !== ''"
          ref="component"
          :is="menuCurr"
          :id="id"
          :key="menuCurr"
        ></component>
      </div>
    </div>
  </list-layout>
</template>

<script>
import appConfig from './components/AppConfig'
import navConfig from './components/NavConfig'
import advConfig from './components/AdvConfig'
import guideConfig from './components/GuideConfig'
import enjoyConfig from './components/EnjoyConfig'
import pcGrey from './components/PcGrey'
import protocolConfig from './components/ProtocolConfig'
import languageConfig from './components/LanguageConfig'
import themeConfig from './components/ThemeConfig'
import phoneConfig from './components/PhoneConfig'

export default {
  $plugins: 'form',
  components: {
    appConfig,
    guideConfig,
    navConfig,
    advConfig,
    enjoyConfig,
    pcGrey,
    protocolConfig,
    languageConfig,
    themeConfig,
    phoneConfig
  },
  data () {
    return {
      id: '',
      menuCurr: 'navConfig',
      menuOpens: ['BasicConfig', 'AppConfigs']
    }
  },
  mounted () {
    this.id = this.$route.query.id
    this.configQuery()
  },
  methods: {
    configQuery () {
      this.$api('terminalmanage', 'configQuery', {
        terminalId: this.id
      }).then(res => {
        const { data } = res
        const { share, language, shortStyle, theme, phone } = data
        sessionStorage.setItem('SHARE', this.dealList(share, 'share'))
        sessionStorage.setItem('LANGUAGE', this.dealList(language))
        sessionStorage.setItem('SHORTSTYLE', this.dealList(shortStyle, 'shortStyle'))
        sessionStorage.setItem('THEME', this.dealList(theme))
        sessionStorage.setItem('PHONE', this.dealList((phone)))
      })
    },
    dealList (list, name = '') {
      let obj = {}
      if (list && list.length > 0) {
        list.forEach(item => {
          const { configCode, configValue } = item
          const obj_ = {}
          obj_[configCode] = configValue
          obj = Object.assign(obj, obj_)
        })
      }
      if (name === 'share') {
        const { channel } = obj
        if (!channel) obj.channel = []
        if (channel && channel.length > 0) obj.channel = obj.channel.split(',')
      }
      if (name === 'shortStyle') {
        const { style } = obj
        if (style === 'grey') {
          obj.style = true
        } else {
          obj.style = false
        }
      }
      return JSON.stringify(obj)
    },
    menuSelect (index) {
      if (['advConfig', 'guideConfig', 'enjoyConfig', 'pcGrey', 'languageConfig', 'themeConfig', 'phoneConfig'].includes(this.menuCurr)) {
        const { obj, obj_ } = this.$refs.component.getForm()
        let flag = true
        if (obj && obj_) {
          flag = this.isObjStringEqual(obj, obj_)
        }
        if (!flag) {
          // 是否需要保存
          this.$confirm('信息已修改，是否需要保存?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            await this.$refs.component.save('1')
            this.menuCurr = index
          }).catch(() => {
            this.menuCurr = index
          })
        } else { this.menuCurr = index }
      } else { this.menuCurr = index }
    },
    isObjStringEqual (obj1, obj2) {
      return JSON.stringify(obj1) === JSON.stringify(obj2)
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep.fs-menu-item {
  height: 42px;
  line-height: 42px;
  min-width: 0;
  border: none;
  &.is-active {
    color: $themeColor;
    background-color: $themeBackground;
    border: none;
  }
  &:active, &:hover, &:focus {
    color: $themeColor;
    background-color: $themeBackground;
    border: none;
  }
}
::v-deep.fs-submenu {
  & > .fs-submenu__title:hover, & > .fs-submenu__title:focus {
    color: $themeColor;
    background-color: $themeBackground;
  }
  &.is-active > .fs-submenu__title {
    color: $themeColor;
  }
}
.config-contain {
  width: 100%;
  height: 100%;
  padding: 0 0 0 12px;
  display: flex;
  &-menu {
    width: 170px;
    height: 100%;
    .fs-menu {
      height: 100%;
      padding-top: 12px;
    }
  }
  &-content {
    width: calc(100% - 170px);
    height: 100%;
  }
}
</style>
