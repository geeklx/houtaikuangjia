<template>
  <div
    class="login-container"
    :style="themeType + ';' +  themeBackground"
  >
    <div
      class="login-content fs-flex fs-row-center fs-col-center"
      :style="{backgroundImage: getBgUrl, backgroundSize: '100% 100%'}"
    >
      <div class="login-content-form" :class="isLogin ? 'fs-login-width' : isForget ? 'fs-forget-width' : isRegister ? 'fs-register-width' : ''">
        <div class="login-content-form__header">
          <fs-row>
            <fs-col :span="11" class="fs-flex fs-row-right fs-col-center">
<!--              <img src="./png/fosung.png" />-->
            </fs-col>
            <fs-col :span="2" class="fs-flex fs-row-center fs-col-center">
              <div class="svg-top"></div>
            </fs-col>
            <fs-col :span="11" class="fs-flex fs-row-left fs-col-center">
              <img src="./png/workfront.png" />
            </fs-col>
          </fs-row>
        </div>
        <div class="login-content-form__body">
          <template v-if="isLogin">
            <fs-row>
              <fs-col :span="11" class="fs-flex fs-row-center fs-col-center">
                <img src="./png/loginimg.png" />
              </fs-col>
              <fs-col :span="2" class="fs-flex fs-row-center fs-col-center">
                <div class="svg-bottom"></div>
              </fs-col>
              <fs-col :span="11" class="fs-flex-col fs-col-center fs-row-center">
                <div style="font-size: 24px;color: #3A3D40;">用户登录</div>
                <fs-form
                  inline
                  ref="validRef"
                  :model="formData"
                  :rules="formRule"
                  label-width="60px"
                  label-position="center"
                  class="fs-btmborder-form fs-flex-col fs-row-center fs-col-center"
                  size="mini"
                  @submit.native.prevent
                >
                  <fs-form-item prop="u">
                    <div class="input-container fs-flex">
                      <fs-input
                        size="small"
                        class="input-container__item"
                        v-model="formData.u"
                        placeholder="请输入账号"
                      >
                        <img slot="prefix" src="./png/person.png" />
                      </fs-input>
                    </div>
                  </fs-form-item>
                  <fs-form-item prop="p">
                    <div class="input-container fs-flex">
                      <fs-input
                        size="small"
                        class="input-container__item"
                        v-model="formData.p"
                        type="password"
                        placeholder="请输入密码"
                      >
                        <img slot="prefix" src="./png/lock.png" />
                      </fs-input>
                    </div>
                  </fs-form-item>
                  <fs-form-item prop="imgCode">
                    <div class="input-container fs-flex">
                      <fs-input
                        maxlength="4"
                        placeholder="请输入验证码"
                        v-model="formData.imgCode"
                        class="input-container__item"
                      >
                        <img slot="prefix" src="./png/code.png" />
                        <div slot="append" class="fs-flex fs-col-center">
                          <div class="append-img fs-flex fs-row-center fs-col-center" @click="getCode">
                            <img slot="append" :src="codeImg" />
                          </div>
                          <div class="append-text fs-flex-col fs-row-center fs-col-center" @click="getCode">
                            <span>看不清</span>
                            <span>换一个</span>
                          </div>
                        </div>
                      </fs-input>
                    </div>
                  </fs-form-item>
                  <fs-form-item>
                    <div class="fs-btns fs-flex fs-row-center">
                      <fs-button size="medium" type="primary" @click="infoValid('login')">
                        登 录
                      </fs-button>
                    </div>
                    <div class="fs-forget-register fs-flex fs-row-between">
                      <span style="color: #999999;font-size: 14px" @click="forgetChange">忘记密码?</span>
                      <span class="fs-theme-color" style="font-size: 14px;" @click="registerChange">注册<i class="fs-icon-arrow-right"/></span>
                    </div>
                  </fs-form-item>
                </fs-form>
              </fs-col>
            </fs-row>
          </template>
          <template v-if="isForget || isRegister">
            <fs-row class="fs-width-auto fs-flex-col fs-row-center fs-col-center">
              <fs-col :span="24">
                <div class="fs-width-auto fs-flex fs-row-between fs-col-center">
                  <span style="font-size: 20px;font-weight: 600">{{ isForget? '修改密码' : isRegister ? '用户注册' : '' }}</span>
                  <span style="font-size: 14px;color: #666666">
                    ！已有账号，
                    <span class="fs-theme-color" @click="loginChange">
                      登录<i class="fs-icon-arrow-right"/>
                    </span>
                  </span>
                </div>
              </fs-col>
              <fs-col :span="24">
                <fs-form
                  inline
                  ref="validRef"
                  :model="formData"
                  :rules="formRule"
                  label-width="96px"
                  label-position="center"
                  class="fs-dashed-form fs-width-auto fs-flex-col fs-row-center fs-col-center"
                  size="mini"
                  @submit.native.prevent
                >
                  <fs-form-item label="手机号:" prop="telephone">
                    <div class="input-container fs-flex">
                      <fs-input
                        style="width: 374px"
                        placeholder="请输入手机号"
                        v-model="formData.telephone"
                        class="input-container__item"
                      />
                    </div>
                  </fs-form-item>
                  <fs-form-item label="短信验证码:" prop="code">
                    <div class="input-container fs-forget-sms fs-flex fs-row-left">
                      <fs-input
                        style="width: 229px"
                        placeholder="请输入短信验证码"
                        v-model="formData.code"
                        class="input-container__item"
                      />
                    </div>
                    <div class="fs-forget-sms__btn fs-flex fs-row-center">
                      <fs-button plain>获取短信验证码</fs-button>
                    </div>
                  </fs-form-item>
                  <fs-form-item :label="isForget ? '新密码:' : isRegister ? '登录密码:' : ''" prop="password">
                    <div class="input-container fs-flex">
                      <fs-input
                        style="width: 374px"
                        show-password
                        placeholder="请输入新密码"
                        v-model="formData.password"
                        class="input-container__item"
                      />
                    </div>
                  </fs-form-item>
                  <fs-form-item label="确认密码:" prop="affirmPassword">
                    <div class="input-container fs-flex" style="float: left">
                      <fs-input
                        :style="{ width: isError ? '350px' : '374px'}"
                        placeholder="请输入确认密码"
                        show-password
                        v-model="formData.affirmPassword"
                        class="input-container__item"
                        @input="affirmPasswordInput"
                      />
                    </div>
                    <div v-if="isError" class="fs-error-icon fs-flex fs-row-center fs-col-center">
                      <i class="fs-icon-close" />
                    </div>
                  </fs-form-item>
                  <fs-form-item label="姓名:" prop="realName" v-if="isRegister">
                    <div class="input-container fs-flex">
                      <fs-input
                        style="width: 374px"
                        placeholder="请输入真实姓名"
                        v-model="formData.realName"
                        class="input-container__item"
                      />
                    </div>
                  </fs-form-item>
                  <fs-form-item label="身份证号:" prop="idCard" v-if="isRegister">
                    <div class="input-container fs-flex">
                      <fs-input
                        style="width: 374px"
                        placeholder="请输入身份证号"
                        v-model="formData.idCard"
                        class="input-container__item"
                      />
                    </div>
                  </fs-form-item>
                  <fs-form-item>
                    <div class="fs-btns fs-flex fs-row-center">
                      <fs-button size="medium" type="primary" @click="infoValid(isRegister ? 'register' : 'forget')">
                        确 认
                      </fs-button>
                    </div>
                  </fs-form-item>
                </fs-form>
              </fs-col>
            </fs-row>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  $stores: ['themeType', 'themeBackground'],
  data () {
    return {
      formData: {},
      formRule: {
        u: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        p: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        imgCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入短信验证码', trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\d{8}$$/.test(value)) {
                callback(new Error('手机号输入有误'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘'，。、]).{8,30}$/.test(value)) {
                callback(new Error('密码必须8位以上及大小写字母、数字、下划线等特殊字符的组合'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        affirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[`~!@#$%^&*()_\-+=<>?:"{}|,./;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘'，。、]).{8,30}$/.test(value)) {
                callback(new Error('密码必须8位以上及大小写字母、数字、下划线等特殊字符的组合'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        realName: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        idCard: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          {
            validator: function (rule, value, callback) {
              if (!/(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/.test(value)) {
                callback(new Error('身份证号输入有误'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        affirmp: [
          { required: true, message: '请输入确认密码', trigger: 'blur' }
        ]
      },
      codeImg: '',
      isLogin: true,
      isForget: false,
      isRegister: false,
      isError: false
    }
  },
  computed: {
    getBgUrl  () {
      const color = this.themeType.substring(this.themeType.length - 6, this.themeType.length)
      return `url(${require('./png/' + color + '_bg.png')})`
    }
  },
  mounted () {
    this.getCode()
  },
  watch: {
    isLogin: {
      handler (val) {
        if (val) {
          this.$set(this, 'formData', {})
          this.isForget = false
          this.isRegister = false
        }
      }
    },
    isForget: {
      handler (val) {
        if (val) {
          this.$set(this, 'formData', {})
          this.isLogin = false
          this.isRegister = false
        }
      }
    },
    isRegister: {
      handler (val) {
        if (val) {
          this.$set(this, 'formData', {})
          this.isLogin = false
          this.isForget = false
        }
      }
    }
  },
  methods: {
    getCode () {
      axios({
        method: 'get',
        url: '/api/common/photo/code',
        responseType: 'blob'
      }).then(res => {
        const { data } = res
        this.codeImg = window.URL.createObjectURL(data)
      })
    },
    toLogin () {
      this.$api('user', 'login', {
        ...this.formData
      }).then(res => {
        const { success } = res
        if (success) location.href = location.origin
      })
    },
    toRegister () {
      this.$api('user', 'register', {
        ...this.formData
      }).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('注册成功，即将跳转登录页...')
          location.href = location.origin
        } else {
          this.$message.error(message)
        }
      })
    },
    toForget () {
      this.$api('user', 'forget', {
        ...this.formData
      }).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('修改成功，即将跳转登录页...')
          location.href = location.origin
        } else {
          this.$message.error(message)
        }
      })
    },
    infoValid (val) {
      this.$refs.validRef.validate(valid => {
        if (valid) {
          if (val === 'login') this.toLogin()
          if (val === 'register') this.toRegister()
          if (val === 'forget') this.toForget()
        }
      })
    },
    registerChange () {
      this.isRegister = true
    },
    forgetChange () {
      this.isForget = true
    },
    loginChange () {
      this.isLogin = true
    },
    affirmPasswordInput () {
      const { affirmPassword, password } = this.formData
      if (affirmPassword !== password) {
        this.isError = true
      } else {
        this.isError = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.fs-error-icon {
  width: 14px;
  height: 38px;
  float: right;
  margin-left: 10px;
  & > i {
    border: 1px solid #FA4444;
    color: #FA4444;
    border-radius: 50%;
  }
}
.svg-top {
  width: 1px;
  height: 48px;
  background: #E5E5E6;
}
.svg-bottom {
  width: 1px;
  height: 208px;
  background: #E5E5E6;
}
::v-deep.fs-input .fs-input__inner {
  border: none;
}
.fs-width-auto {
  width: 100%;
}
.fs-height-auto {
  height: 100%;
}
.fs-form--inline .fs-form-item {
  margin-right: 0;
}
.login-container {
  @extend .fs-width-auto;
  @extend .fs-height-auto;
  min-width: 1200px;
  .login-content {
    @extend .fs-width-auto;
    @extend .fs-height-auto;
    .fs-register-width {
      width: 600px;
      height: 588px;
    }
    .fs-forget-width {
      width: 600px;
      height: 498px;
    }
    .fs-login-width {
      width: 53.5%;
      min-width: 800px;
      height: 50.8%;
      min-height: 448px;
    }
    &-form {
      background: #FFFFFF;
      padding: 0 44px;
      border-radius: 12px;
      &__header {
        height: 83px;
        border-bottom: 1px dashed $themeColor;
        .fs-row {
          @extend .fs-height-auto;
          ::v-deep.fs-col-11, ::v-deep.fs-col-2 {
          @extend .fs-height-auto;
          }
        }
      }
      &__body {
        height: calc(100% - 83px);
        .fs-row {
          @extend .fs-height-auto;
          ::v-deep.fs-col-11, ::v-deep.fs-col-2 {
          @extend .fs-height-auto;
          }
        }
        .fs-btmborder-form {
          margin-top: 30px;
          ::v-deep.input-container {
            border-bottom: 1px solid #E5E5E6;
            &__item {
              width: 333px;
              height: 36px;
              .fs-input__inner {
                height: 36px;
                line-height: 36px;
              }
              .fs-input__prefix {
                display: flex;
                align-items: center;
                justify-content: center;
              }
              .fs-input-group__append {
                width: 121px;
                border: none;
                background: #fff;
                padding: 0;
                & > div {
                  @extend .fs-width-auto;
                  @extend .fs-height-auto;
                  .append-img {
                    width: 76px;
                    height: 35px;
                    cursor: pointer;
                    & > img {
                      @extend .fs-width-auto;
                    }
                  }
                  .append-text {
                    color: #999999;
                    font-size: 12px;
                    margin-left: 4px;
                    cursor: pointer;
                  }
                }
              }
            }
          }
          .fs-btns {
            .fs-button {
              margin-top: 10px;
              width: 333px;
              border-radius: 8px;
            }
          }
          .fs-forget-register {
            margin-top: 6px;
            & > span {
              cursor: pointer;
            }
          }
        }
        .fs-dashed-form {
          margin-top: 30px;
          ::v-deep.fs-form-item__label {
            height: 36px;
            line-height: 36px;
          }
          ::v-deep.input-container {
            border: 1px dashed #E5E5E6;
            &__item {
              width: 333px;
              height: 36px;
              .fs-input__inner {
                height: 36px;
                line-height: 36px;
              }
            }
          }
          .fs-forget-sms {
            float: left;
          }
          .fs-forget-sms__btn {
            float: right;
            width: 145px;
            background: #fff;
            padding-left: 15px;
            .fs-button {
              @extend .fs-width-auto;
              height: 38px;
              border: 1px dashed #E5E5E6;
              &:active, &:hover, &:focus {
                border: 1px dashed $themeColor;
              }
            }
          }
          .fs-btns {
            .fs-button {
              margin-top: 10px;
              width: 472px;
              border-radius: 8px;
            }
          }
        }
      }
    }
  }
}
.fs-theme-color {
  color: $themeColor;
  cursor: pointer;
}
</style>
