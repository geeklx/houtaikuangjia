<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :lock-scroll="false"
    :close-on-click-modal="false"
    :visible.sync="visible_"
    :title="title"
    @close="close"
    width="702px"
  >
    <div class="content">
      <div class="content-trans">
        <fs-transfer
          v-model="right"
          :data="left"
          filterable
          :titles="titles"
          :transConfig="transConfig"
        >
        </fs-transfer>
      </div>
      <div class="content-extra" v-if="$slots['content-extra']">
        <slot name="content-extra"></slot>
      </div>
    </div>
    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler">
        确 定
      </fs-button>
    </span>
  </fs-dialog>
</template>

<script>
export default {
  name: 'Transfer',
  props: {
    visible: {
      type: Boolean
    },
    titles: {
      type: Array,
      default: () => {
        return ['待选列表', '已选列表']
      }
    },
    title: {
      type: String
    },
    leftData: {
      type: Array,
      default: () => {
        return []
      }
    },
    rightData: {
      type: Array,
      default: () => {
        return []
      }
    },
    transConfig: {
      type: Object,
      default: () => {
        return {}
      }
    },
    extraData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    return {
      visible_: this.visible,
      left: this.leftData,
      right: this.rightData
    }
  },
  watch: {
    visible: {
      handler (val) {
        if (val) {
          this.visible_ = val
          this.left = [].concat(this.leftData)
          this.right = [].concat(this.rightData)
        }
      },
      deep: true
    }
  },
  methods: {
    close () {
      this.visible_ = false
      this.left = []
      this.right = []
      this.$emit('close')
    },
    submitHandler () {
      if (this.right === this.rightData) {
        this.close()
        return
      }
      let params_ = [].concat(this.right)
      const { method, params } = this.transConfig
      if (params) {
        params_ = params_.map(item => {
          return {
            configId: item,
            ...params
          }
        })
      }
      this.$api('transconfig', method, {
        ...this.extraData,
        ...params,
        list: params_
      }).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('提交成功！')
          this.close()
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
    }
  }
}
</script>
