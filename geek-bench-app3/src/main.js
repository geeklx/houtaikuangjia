// 引入 FosungUI
// 此处根据自身项目情况引入ui框架可引入iview等
import FosungUI from 'fosung-ui'
import 'fosung-ui/lib/theme-chalk/index.css'

// css样式初始化
import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import '@/icons' // icon

import FosungFront from 'fosung-front'
import 'fosung-front/lib/fosung-front.css'

import 'fosung-css'

import CompositionApi from '@vue/composition-api'

import FsSuperTable from 'fs-super-table'
import 'fs-super-table/lib/theme/index.css'

import FsSuperLayout from 'fs-super-layout'
import 'fs-super-layout/lib/theme/index.css'

import '@wangeditor/editor/dist/css/style.css'

import FsDictionary from 'fs-dictionary'

import routerBefore from '@/router/routerBefore'
import menu from '@/router/menu'

const fosungFront = new FosungFront()

// vuex 配置
fosungFront
  .storeBuilder()
  .addModules(require.context('@/store/', true, /\.js$/)) // 添加自定义store

// http 配置
fosungFront
  .httpBuilder()
  // .timeout(100000)
  // 扫描api文件夹内的请求封装js
  .api(require.context('@/api/', true, /\.js$/))
  // 设置请求根地址
  // .baseUrl(process.env.NODE_ENV === 'production' ? process.env.VUE_APP_API_URL : '')
  // 错误拦截
  .error((error) => new Promise((resolve, reject) => {
    const Router = window.appContext.routerInstance
    if (error.response && [401, 403].includes(Number(error.response.status))) {
      Router.push({ path: '/toLogin' }).catch(err => { console.log(err) })
    } else {
      reject(error)
    }
  }))

// router 配置
fosungFront
  .routerBuilder()
  .whiteList(['404', '403', 'error', 'toLogin']) // 设置白名单
  .menu(menu) // 静态路由传入
  .before(routerBefore) // routerBefore 配置

// vue配置
fosungFront
  .vueBuilder()
  .install(CompositionApi)
  .install(FosungUI, { size: 'mini' }) // 安装第三方插件
  .install(FsSuperTable) // 安装第三方插件
  .install(FsDictionary) // 安装第三方插件
  .install(FsSuperLayout)
  .components(require.context('@/components/', true, /\.vue$/)) // 注册全局组件
  // .directive(require.context('@/assets/directive/', true, /\.js$/))

// 全局构建
fosungFront.build()
