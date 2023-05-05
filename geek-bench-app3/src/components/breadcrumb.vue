<template>
  <fs-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <fs-breadcrumb-item v-for="(item, index) in levelList" :key="item.path">
        <span v-if="item.redirect === 'noRedirect' || index === levelList.length - 1" class="no-redirect">{{ item.meta.title }}</span>
        <a v-else class="parent-redirect" @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
      </fs-breadcrumb-item>
    </transition-group>
  </fs-breadcrumb>
</template>

<script>
import { pathToRegexp } from 'path-to-regexp'

export default {
  name: 'breadcrumb',
  data () {
    return {
      levelList: null
    }
  },
  watch: {
    $route () {
      this.getBreadcrumb()
    }
  },
  created () {
    this.getBreadcrumb()
  },
  methods: {
    getBreadcrumb () {
      // only show routes with meta.title
      let matched = this.$route.matched.filter(
        (item) => item.meta && item.meta.title
      )
      const first = matched[0]

      if (!this.isDashboard(first)) {
        matched = [{ path: '/Dashboard', meta: { title: '' } }].concat(
          matched
        )
      }

      this.levelList = matched.filter(
        (item) => item.meta && item.meta.title && item.meta.breadcrumb !== false
      )
    },
    isDashboard (route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()
    },
    pathCompile (path) {
      // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
      const { params } = this.$route
      const toPath = pathToRegexp.compile(path)
      return toPath(params)
    },
    handleLink (item) {
      const { redirect, path } = item
      this.$jump({ path: redirect || path })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.fs-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 12px;

  .no-redirect {
    color: #999999;
    cursor: text;
  }
  .parent-redirect {
    color: #333333;
  }
  ::v-deep.fs-breadcrumb__separator {
    margin: 0 4px;
  }
}
</style>
