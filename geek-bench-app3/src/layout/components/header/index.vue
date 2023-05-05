<template>
  <div
    class="app-header flexLeftCenter"
    :style="{background: getBgUrl, backgroundSize: '100% 100%'}"
  >
    <logo class="flexCell" />
    <div class="flexCell">
      <div class="right-menu">
        <!-- 用户头像 -->
        <div class="avatar">
          <img v-if="user.avatar" :src="user.avatar" class="user-avatar">
          <img v-else :src="getAvatarUrl" class="user-avatar">
        </div>
        <div class="user-info">
          <!-- 用户名称 -->
          <div class="user-name">
            <span>{{user.name || "用户名称"}}</span>
            <span class="user-name_date" v-if="user.date">最后登录日期：{{user.date || "-"}}</span>
          </div>
          <div v-if="user.roleList && user.roleList.length" class="user-role">
            <!-- 权限选择 -->
            <fs-dropdown class="avatar-container" trigger="click">
              <div class="avatar-wrapper">
                <span>{{(currentRole.orgName ? currentRole.orgName + '-' : '') + currentRole.roleName}}</span>
                <i class="fs-icon-caret-bottom" />
              </div>
              <fs-dropdown-menu slot="dropdown" class="user-dropdown">
                <fs-dropdown-item @click.native="switchRoles(item)" v-for="(item, index) in user.roleList" :key="item.orgId + item.roleCode + index">
                 <span :class="{'org-name-list':true,'currentRole':(currentRole.orgName + '-' + currentRole.roleName) === (item.orgName + '-' + item.roleName)}"
                 >{{ (item.orgName ?  item.orgName + '-' : '') + item.roleName }}</span>
               </fs-dropdown-item>
              </fs-dropdown-menu>
            </fs-dropdown>

          </div>
        </div>
        <!-- 退出登录 -->
        <!--<div class="log-out">
          <img src="./logo/nav_signout.png" class="close" @click="handleLogout">
          <div class="log-out_font">退出</div>
        </div>-->
        <fs-dropdown
          style="margin-left: 16px;"
          @command="data => themeChange(data)"
        >
          <span class="fs-dropdown-link">
            <div class="log-out">
              <svg-icon icon-class="skin" class="skin close"></svg-icon>
              <div class="log-out_font">换肤</div>
            </div>
          </span>
          <fs-dropdown-menu slot="dropdown">
            <fs-dropdown-item class="color-theme" :command="{color: '#C50405', background: 'rgba(197,7,18,0.1)', disBackground: 'rgba(197,7,18,0.5)'}"><div class="color-block" style="background: #C50405"></div>党建红</fs-dropdown-item>
            <fs-dropdown-item class="color-theme" :command="{color: '#157EFC', background: 'rgba(19,118,246,0.05)', disBackground: 'rgba(19,118,246,0.5)'}"><div class="color-block" style="background: #157EFC"></div>办公蓝</fs-dropdown-item>
          </fs-dropdown-menu>
        </fs-dropdown>
        <!--<div class="log-out" style="margin-right: 16px;">
          <svg-icon icon-class="news" class="news close"></svg-icon>
          <div class="log-out_font">消息</div>
        </div>-->
        <fs-dropdown
          style="margin-left: 16px;"
          @command="data => exit(data)"
        >
          <span class="fs-dropdown-link">
            <div class="log-out">
              <svg-icon icon-class="set" class="set close"></svg-icon>
              <div class="log-out_font">设置</div>
            </div>
          </span>
          <fs-dropdown-menu slot="dropdown">
            <fs-dropdown-item command="exit">
              <div class='fs-p-l-10 fs-p-r-10'>退出</div>
            </fs-dropdown-item>
          </fs-dropdown-menu>
        </fs-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import Logo from './logo'
export default {
  inject: ['reloads'],
  $stores: ['user', 'configInfo', 'currentRole', 'themeType', 'themeBackground'],
  components: {
    Logo
  },
  computed: {
    getBgUrl () {
      const color = this.themeType.substring(this.themeType.length - 6, this.themeType.length)
      return `url(${require('./logo/' + color + '_nav_bg.png')})`
    },
    getAvatarUrl () {
      const color = this.themeType.substring(this.themeType.length - 6, this.themeType.length)
      return require('./logo/' + color + '_nav_head.png')
    }
  },
  methods: {
    themeChange ({ color, background, disBackground }) {
      this.setThemeType(color, background, disBackground)
    },
    exit (data) {
      switch (data) {
        case 'exit':
          this.handleLogout()
          break
        default:
          break
      }
    },
    setThemeType (color, background, disBackground) {
      this.$store.dispatch('app/updateThemeType', { color })
      this.$store.dispatch('app/updateThemeBackground', { background })
      this.$store.dispatch('app/updateThemeDisBackground', { disBackground })
    },
    handleLogout () {
      this.$confirm('您确定要退出登录吗？', '退出登录', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$api('user', 'logout').then(({ success }) => {
          if (success) {
            this.$store.commit('CLEAR_USER')
            this.$jump('/toLogin')
          }
        })
      })
    },
    // 切换用户信息
    switchRoles (role) {
      if (role.orgId === this.currentRole.orgId && role.roleCode === this.currentRole.roleCode) return
      this.$store.commit('SAVE_ROLE', role)
      this.$store.dispatch('checkMenu', role).then(() => {
        // 切换角色后动态修改按钮权限数据
        // this.$store.dispatch('saveButtons', [`${role.roleCode}_${role.orgCode}`])
        this.$jump('/')

        this.$message({
          type: 'success',
          message: '切换用户成功'
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.color-theme {
  display: flex;
  align-items: center;
  .color-block {
    margin-right: 12px;
    width: 12px;
    height: 12px;
  }
}
.currentRole{
  color: $themeColor;
}
.app-header {
  height: 80px;
  padding: 0 30px 0 20px;
  position: relative;
  .right-menu {
    float: right;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    &:focus {
      outline: none;
    }
    .user-avatar{
      width: 48px;
      height: 48px;
      border-radius: 24px;
      margin-right: 10px;
    }
    .user-info{
      display: flex;
      flex-direction: column;
      justify-content: center;
      margin-right: 40px;
      .user-name{
        color: #fff ;
        font-size: 14px;
        &_date {
          margin-left: 8px;
          color: #fff ;
          font-size: 12px;
        }
      }
    }
    .log-out{
      cursor: pointer;
      text-align: center;
      .close{
        font-size: 20px;
        color: #ffffff;
        font-weight: bold;
        &:hover{
          cursor: pointer;
          transform: scale(1.2, 1.2);
        }
      }
      &_font {
        color: #FFFFFF;
        font-size: 12px;
      }
    }

    .avatar-container {
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        color: #fff ;
        font-size: 12px;
         .fs-icon-caret-bottom {
           font-size: 12px;
           margin-left: 4px;
         }
      }
      .right-menu-item {
          display: inline-block;
          padding: 0 8px;
          height: 100%;
          font-size: 18px;
          color: #5a5e66;
          vertical-align: text-bottom;
          &.hover-effect {
            cursor: pointer;
            transition: background 0.3s;
            &:hover {
              background: rgba(0, 0, 0, 0.025);
            }
          }
          .org-name-list{
            font-size: 14px;
          }
      }
    }
  }
}
::v-deep.fs-dropdown-menu__item{
  &:hover{
    background-color: #FCE7E7;
    color: #DF2A2A;
  }
}
</style>
