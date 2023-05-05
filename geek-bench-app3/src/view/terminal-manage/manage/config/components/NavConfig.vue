<template>
  <div class="navs">
    <div class="navs-menu">
      <fs-org-tree
        v-model="areaName"
        :httpConfig="httpConfig"
        :props="props"
        :expandOnClickNode="false"
        lazy
        @lazyLoad="lazyLoad"
      />
    </div>
    <div class="nav-config">
      <div class="nav-config__tab">
        <fs-tabs type="card" v-model="tabValue">
          <fs-tab-pane name="底部导航" label="底部导航"></fs-tab-pane>
          <fs-tab-pane name="顶部导航" label="顶部导航"></fs-tab-pane>
        </fs-tabs>
      </div>
      <div v-if="tabValue === '底部导航'" class="nav-config__bottom">
        <bottom-nav ref="bottomNav" :id="id" :areaName="areaName"></bottom-nav>
      </div>
      <div v-if="tabValue === '顶部导航'" class="nav-config__top">
        <top-nav ref="topNav" :id="id" :areaName="areaName"></top-nav>
      </div>
    </div>
  </div>
</template>

<script>
import OrgTree from '@/components/org-tree/index'
export default {
  name: 'NavConfig',
  components: {
    BottomNav: () => import('./components/BottomNavConfig'),
    TopNav: () => import('./components/TopNavConfig'),
    FsOrgTree: OrgTree
  },
  props: {
    id: {
      type: String
    }
  },
  data () {
    return {
      areaName: '',
      tabValue: '底部导航'
    }
  },
  computed: {
    props () {
      return {
        value: 'dictLabel',
        label: 'dictLabel',
        isLeaf: (data, node) => node.level === 2
      }
    },
    httpConfig () {
      return {
        lazy: true,
        // 请求url
        url: '/api/application/query/regional',
        // 请求类型
        type: 'postJson',
        // 请求参数
        params: this.getParams,
        // 响应数据字段
        responseField: 'datalist'
      }
    }
  },
  methods: {
    lazyLoad (res) {
      const [info] = res
      const { dictLabel } = info
      if (this.areaName === '') this.$set(this, 'areaName', dictLabel)
    },
    getParams (data) {
      let params = {}
      if (data) {
        params = Object.assign(params, {
          cityCode: data.data.dictValue
        })
      }
      return params
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep.fs-menu-item {
  height: 42px;
  line-height: 42px;
  border: none;
  padding-right: 16px;
  min-width: 179px;
  display: inline-block;
  white-space: nowrap;
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
.navs {
  width: 100%;
  height: 100%;
  display: flex;
  &-menu {
    height: 100%;
    border-right: 1px solid #F0F2F7;
    .fs-menu, .fs-org-tree {
      height: 100%;
      width: 140px;
      overflow-x: auto;
      padding: 12px 12px;
    }
  }
  .nav-config {
    width: 100%;
    height: 100%;
    padding: 12px;
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    &__bottom, &__top {
      height: calc(100% - 56px);
      overflow-y: auto;
      padding: 0 4px;
    }
  }
}
</style>
