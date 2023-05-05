const state = {
  // 用于控制菜单栏伸缩状态
  sidebar: {
    opened: localStorage.getItem('sidebarStatus') ? !!+localStorage.getItem('sidebarStatus') : true,
    withoutAnimation: false
  },
  themeType: '--theme-ma-color: #157EFC',
  themeBackground: '--theme-bg-color: rgba(19,118,246,0.05)',
  themeDisBackground: '--theme-dis-bg-color: rgba(19,118,246,0.5)'
}

const mutations = {
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    if (state.sidebar.opened) {
      localStorage.setItem('sidebarStatus', 1)
    } else {
      localStorage.setItem('sidebarStatus', 0)
    }
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    localStorage.setItem('sidebarStatus', 0)
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  UPDATE_THEMETYPE: (state, val) => {
    state.themeType = '--theme-ma-color:' + val
    localStorage.setItem('themeType', '--theme-ma-color:' + val)
  },
  UPDATE_THEMEBACKGROUND: (state, val) => {
    state.themeBackground = '--theme-bg-color:' + val
    localStorage.setItem('themeBackground', '--theme-bg-color:' + val)
  },
  UPDATE_THEMEDISBACKGROUND: (state, val) => {
    state.themeDisBackground = '--theme-dis-bg-color:' + val
    localStorage.setItem('themeDisBackground', '--theme-dis-bg-color:' + val)
  }
}

const actions = {
  toggleSideBar ({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar ({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  updateThemeType ({ commit }, { color }) {
    commit('UPDATE_THEMETYPE', color)
  },
  updateThemeBackground ({ commit }, { background }) {
    commit('UPDATE_THEMEBACKGROUND', background)
  },
  updateThemeDisBackground ({ commit }, { background }) {
    commit('UPDATE_THEMEDISBACKGROUND', background)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
