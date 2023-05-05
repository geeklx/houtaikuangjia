export default function (http) {
  return {
    /**
     * 获取配置
     * @return {*}
     */
    getConfigure () {
      return http.postJson('/api/user/configure')
    },
    /**
     * 获取当前登录用户
     * @return {*}
     */
    getCurrentUser () {
      return http.postJson('/api/user/info')
    },
    /**
     * 登录
     * @return {*}
     */
    login (data) {
      return http.post('/login', data)
    },
    /**
     * 退出登录
     * @return {*}
     */
    logout () {
      return http.postJson('/logout')
    },
    /**
     * 动态获取菜单
     * @returns
     */
    // getMenu ({ ext }) {
    //   return http.postJson('/api/common/menu/query', { roleId: ext })
    // },
    getMenu ({ ext }) {
      return http.postJson('/api/sys/resourceinfobyrole', { roleId: ext || '' })
    },
    register (data) {
      return http.postJson('/api/common/register', data)
    },
    forget (data) {
      return http.postJson('/api/common/reset/password', data)
    }
  }
}
