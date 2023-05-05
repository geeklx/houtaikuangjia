import Layout from '@/layout/index'
const load = require('./import/_import_' + process.env.NODE_ENV)

export default async function routerBefore (to, from, next) {
  // 全局上下文信息
  const { storeInstance, Utils } = window.appContext

  const configInfo = storeInstance.getters.configInfo
  const user = storeInstance.getters.user
  const menus = storeInstance.getters.menuList

  // 检查配置信息
  if (Utils.isEmpty(configInfo)) {
    // 如果用户信息不存在，则调用框架内vuex封装的action方法获取配置信息
    await storeInstance.dispatch('getConfigure')
  }

  // 检查用户信息
  if (Utils.isEmpty(user)) {
    // 如果用户信息不存在，则调用框架内vuex封装的action方法获取用户信息
    await storeInstance.dispatch('getUserInfo')

    // 异步加载路由
    // 需要把load, Layout传入到框架内
    await storeInstance.dispatch('getMenu', { load, Layout })

    // 确保addRoute完整的hack方法
    // 设置replace:true，这样导航就不会留下历史记录
    next({ ...to, replace: true })
  }

  // 检查菜单权限
  // 如果不存在路由菜单则说明当前账号暂无权限
  if (Utils.isEmpty(menus)) {
    next('403')
  } else if (to.name && menus.some(n => n.name === to.name)) {
    // 目标路由存在于路由菜单里面，则放行
    next()
  } else {
    // 否则进入404页面
    next('404')
  }
}
