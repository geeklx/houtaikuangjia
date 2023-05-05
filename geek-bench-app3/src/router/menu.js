// import Layout from '@/layout'
const load = require('./import/_import_' + process.env.NODE_ENV)
// 静态路由配置示例
const menu = [
  {
    name: 'toLogin',
    path: '/toLogin',
    component: load('view/login/login'),
    meta: { title: '登录页面', icon: 'main-home-page' }
  }
  // {
  //   name: '/',
  //   path: '/',
  //   component: Layout,
  //   redirect: 'projectManage',
  //   children: [
  //     {
  //       name: 'projectManage',
  //       path: '/projectManage',
  //       component: load('view/project/index'),
  //       meta: { title: '项目管理', icon: 'system-manage' }
  //     }, {
  //       name: 'configManage',
  //       path: '/configManage',
  //       component: load('view/config/index'),
  //       meta: { title: '配置管理', icon: 'system-manage' }
  //     }, {
  //       name: 'appCenter',
  //       path: '/appCenter',
  //       component: load('view/app-center/index'),
  //       meta: { title: '应用中心', icon: 'system-manage' },
  //       redirect: 'noRedirect', // 面包屑禁止点击
  //       children: [
  //         {
  //           name: 'appCenterManage',
  //           path: '/appCenterManage',
  //           component: load('view/app-center/manage/index'),
  //           meta: { title: '应用管理' }
  //         }, {
  //           name: 'appCenterManageActive',
  //           path: '/appCenterManageActive',
  //           component: load('view/app-center/manage/active'),
  //           hidden: true,
  //           meta: { title: '应用维护' }
  //         }, {
  //           name: 'appCenterVersion',
  //           path: '/appCenterVersion',
  //           component: load('view/app-center/version/index'),
  //           meta: { title: '应用版本' }
  //         }, {
  //           name: 'appCenterManageDetail',
  //           path: '/appCenterManageDetail',
  //           component: load('view/app-center/version/detail'),
  //           hidden: true,
  //           meta: { title: '应用详情' }
  //         }, {
  //           name: 'appCenterAuthor',
  //           path: '/appCenterAuthor',
  //           component: load('view/app-center/author/index'),
  //           meta: { title: '应用查询' }
  //         }
  //       ]
  //     },
  //     {
  //       name: 'terminal',
  //       path: '/terminal',
  //       component: load('view/terminal-manage/index'),
  //       meta: { title: '多终端管理', icon: 'system-manage' },
  //       redirect: 'noRedirect', // 面包屑禁止点击
  //       children: [
  //         {
  //           name: 'runConfig',
  //           path: '/runConfig',
  //           component: load('view/terminal-manage/config/index'),
  //           meta: { title: '运行配置' }
  //         },
  //         {
  //           name: 'terminalManage',
  //           path: '/terminalManage',
  //           component: load('view/terminal-manage/manage/index'),
  //           meta: { title: '终端管理' },
  //           children: [
  //             {
  //               name: 'terminalConfig',
  //               path: '/terminalConfig',
  //               component: load('view/terminal-manage/manage/config/index'),
  //               hidden: true,
  //               meta: { title: '终端维护' }
  //             },
  //             {
  //               name: 'terminalConfigBind',
  //               path: '/terminalConfigBind',
  //               component: load('view/terminal-manage/apply/bind/index'),
  //               hidden: true,
  //               meta: { title: '终端应用绑定' }
  //             }
  //           ]
  //         }, {
  //           name: 'terminalVersion',
  //           path: '/terminalVersion',
  //           component: load('view/terminal-manage/version/index'),
  //           meta: { title: '终端版本' }
  //         }, {
  //           name: 'terminalApply',
  //           path: '/terminalApply',
  //           component: load('view/terminal-manage/apply/index'),
  //           meta: { title: '终端应用' },
  //           children: [
  //             {
  //               name: 'terminalApplyBind',
  //               path: '/terminalApplyBind',
  //               component: load('view/terminal-manage/apply/bind/index'),
  //               hidden: true,
  //               meta: { title: '终端应用绑定' }
  //             },
  //             {
  //               name: 'terminalApplyConfig',
  //               path: '/terminalApplyConfig',
  //               component: load('view/terminal-manage/apply/config/index'),
  //               hidden: true,
  //               meta: { title: '应用配置' }
  //             }
  //           ]
  //         }
  //       ]
  //     }
  //   ]
  // }
]

export default menu
