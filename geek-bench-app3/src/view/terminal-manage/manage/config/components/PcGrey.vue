<template>
  <div class="pc-grey">
    <div class="pc-grey__btns">
      <fs-button type="primary" @click="save">保存</fs-button>
    </div>
    <div class="pc-grey__content">
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
            <fs-form-item prop="style" label="客户端置灰:">
              <fs-switch v-model="formData.style" @change="styleChange"></fs-switch>
            </fs-form-item>
          </fs-col>
          <fs-col :span="24" v-if="formData.style">
            <fs-form-item prop="time" label="指定日期:">
              <fs-switch v-model="formData.time"></fs-switch>
            </fs-form-item>
          </fs-col>
          <fs-col :span="24" v-if="formData.time">
            <fs-form-item prop="startEndTime" label="选择日期:">
              <fs-date-picker
                v-model="formData.startEndTime"
                unlink-panels
                @change="timeChange"
                type="datetimerange"
                :picker-options="pickerOptions"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd HH:mm:ss"
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
  name: 'PcGrey',
  props: {
    id: {
      type: String
    }
  },
  data () {
    return {
      rules: {
        startEndTime: [
          { required: true, message: '请选择指定日期', trigger: 'change' }
        ]
      },
      defaultData: {}
    }
  },
  computed: {
    pickerOptions (time) {
      return {
        disabledDate (time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        }
      }
    }
  },
  mounted () {
    const SHORTSTYLE = JSON.parse(sessionStorage.getItem('SHORTSTYLE'))
    this.formData = Object.assign({}, SHORTSTYLE)
    this.defaultData = Object.assign({}, SHORTSTYLE)
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
          if (params.style) {
            params.style = 'grey'
          } else {
            params.style = ''
            params.start_time = ''
            params.end_time = ''
          }
          await this.$api('terminalmanage', 'configSave', {
            terminalId: this.id,
            shortStyle: params
          }).then(res => {
            const { success, message } = res
            if (success) {
              this.$message.success('更新成功')
              sessionStorage.setItem('SHORTSTYLE', JSON.stringify(this.formData)) // session存储与defaultData不需处理特殊字段
              this.defaultData = Object.assign({}, this.formData)
            } else {
              this.$message.error(message)
            }
          })
        } else {
          if (val === '1') this.$message.error('必填项为空，无法进行保存!')
        }
      })
    },
    timeChange (time) {
      if (time) {
        this.formData.start_time = time[0]
        this.formData.end_time = time[1]
        // const startAt = new Date(this.formData.start_time) * 1000 / 1000
        // if (startAt < Date.now()) {
        //   this.formData.start_time = new Date() * 1 + 600 * 1000
        // }
      } else {
        this.formData.start_time && delete this.formData.start_time
        this.formData.end_time && delete this.formData.end_time
      }
    },
    styleChange (val) {
      if (!val) {
        this.formData.time && delete this.formData.time
        this.formData.startEndTime && delete this.formData.startEndTime
        this.formData.start_time && delete this.formData.start_time
        this.formData.end_time && delete this.formData.end_time
      }
    }
  }
}
</script>

<style scoped lang="scss">
.pc-grey {
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
