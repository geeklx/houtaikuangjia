<template>
  <list-layout :topRightWidth="0">
    <div slot="top-header" class="title">{{ $route.meta.title }}</div>
    <div class="config-contain">
      <div class="config-contain-menu">
        <div class="config-contain-menu__icon">
          <img :src="appIcon">
          <div>{{appType}}</div>
        </div>
        <fs-menu :default-active="menuCurr" @select="menuSelect">
          <fs-menu-item index="basicConfig">
            <span slot="title">基础配置</span>
          </fs-menu-item>
          <fs-menu-item index="appConfig">
            <span slot="title">应用版本</span>
          </fs-menu-item>
          <fs-menu-item index="authorConfig">
            <span slot="title">应用授权</span>
          </fs-menu-item>
        </fs-menu>
      </div>
      <div class="config-contain-content">
        <component
          v-if="menuCurr !== '' && id !== ''"
          ref="component"
          :is="menuCurr"
          :id="id"
          :appId="appId"
          :terminalId="terminalId"
          :projectId="projectId"
          :key="menuCurr"
        ></component>
      </div>
    </div>
  </list-layout>
</template>

<script>
import BasicConfig from './components/BasicConfig'
import AppConfig from './components/AppConfig'
import AuthorConfig from './components/AuthorConfig'
export default {
  $plugins: 'form',
  components: {
    basicConfig: BasicConfig,
    appConfig: AppConfig,
    authorConfig: AuthorConfig
  },
  data () {
    return {
      queryMethod: 'query',
      id: '',
      appId: '',
      terminalId: '',
      projectId: '',
      appIcon: '',
      appType: '',
      menuCurr: ''
    }
  },
  mounted () {
    this.id = this.$route.query.id
    this.appId = this.$route.query.appId
    this.terminalId = this.$route.query.terminalId
    this.projectId = this.$route.query.projectId
    this.appIcon = this.$route.query.appIcon
    this.appType = this.$route.query.appType
    this.menuCurr = this.$route.query.menuCurr || 'basicConfig'
  },
  methods: {
    menuSelect (index) {
      if (['basicConfig', 'authorConfig'].includes(this.menuCurr)) {
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
            await this.$refs.component.save()
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
}
.config-contain {
  width: 100%;
  height: 100%;
  padding: 0 0 0 12px;
  display: flex;
  &-menu {
    width: 170px;
    height: 100%;
    &__icon {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding-top: 12px;
      border-right: solid 1px #e6e6e6;
      img {
        width: 66px;
        height: 66px;
      }
      div {
        margin-top: 10px;
        width: 61px;
        height: 22px;
        line-height: 22px;
        text-align: center;
        border-radius: 25px;
        font-size: 12px;
        color: #FFFFFF;
        background: linear-gradient(#FF9730, #FD472F);
      }
    }
    .fs-menu {
      height: calc(100% - 110px);
      padding-top: 12px;
    }
  }
  &-content {
    width: calc(100% - 170px);
    height: 100%;
    padding: 12px;
  }
}
</style>
