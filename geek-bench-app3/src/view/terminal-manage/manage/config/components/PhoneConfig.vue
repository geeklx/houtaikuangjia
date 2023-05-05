<template>
  <div class="phone-config">
    <div class="phone-config__btns">
      <fs-button type="primary" @click="save">保存</fs-button>
    </div>
    <div class="phone-config__content">
      <fs-form
        inline
        :ref="validRef"
        slot="searchForm"
        class="search-form"
        :rules="rules"
        :model="formData"
        label-width="120px"
        @submit.native.prevent
        size="mini"
      >
        <fs-row>
          <fs-col :span="24">
            <fs-form-item prop="customer" label="客服电话:">
              <fs-input
                maxlength="32"
                style="width: 372px;"
                v-model="formData.customer"
                placeholder="请输入客服电话"
              />
            </fs-form-item>
          </fs-col>
        </fs-row>
      </fs-form>
    </div>
  </div>
</template>

<script>
export default {
  $plugins: 'form',
  name: 'PhoneConfig',
  props: {
    id: {
      type: String
    }
  },
  data () {
    return {
      rules: {
        customer: [
          { required: true, message: '请输入电话', trigger: 'blur' }
        ]
      },
      defaultData: {}
    }
  },
  mounted () {
    const PHONE = JSON.parse(sessionStorage.getItem('PHONE'))
    this.formData = Object.assign({}, PHONE)
    this.defaultData = Object.assign({}, PHONE)
  },
  methods: {
    getForm () {
      return {
        obj: this.formData,
        obj_: this.defaultData
      }
    },
    save (val) {
      this.$refs.validRef.validate(async valid => {
        if (valid) {
          const params = Object.assign({}, this.formData)
          await this.$api('terminalmanage', 'configSave', {
            terminalId: this.id,
            phone: params
          }).then(res => {
            const { success, message } = res
            if (success) {
              this.$message.success('更新成功')
              sessionStorage.setItem('PHONE', JSON.stringify(this.formData))
              this.defaultData = Object.assign({}, this.formData)
            } else {
              this.$message.error(message)
            }
          })
        } else {
          if (val === '1') this.$message.error('必填项为空，无法进行保存!')
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.phone-config {
  height: 100%;
  padding: 12px;
  &__btns {
    width: 100%;
    padding-bottom: 12px;
    display: flex;
    justify-content: flex-end;
  }
  &__content {
    height: calc(100% - 36px);
    overflow-y: auto;
  }
  ::v-deep.fs-form-item__label {
    min-width: 100px;
  }
  ::v-deep.fs-form-item {
    display: flex;
  }
}
</style>
