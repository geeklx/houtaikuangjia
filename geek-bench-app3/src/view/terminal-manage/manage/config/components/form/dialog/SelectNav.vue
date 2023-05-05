<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="选择底部导航"
    @closed="close"
    width="742px"
  >
    <div class="select-nav fs-flex fs-col-top fs-p-r-45 fs-p-l-45">
      <div class="select-nav__left">
        <div class="select-nav__left--title">地域列表</div>
        <template v-if="type === 'regional'">
          {{ area }}
        </template>
        <template v-else>
          <fs-org-tree
            v-if="visible"
            v-model="areaName"
            :httpConfig="httpConfig"
            :props="props"
            :expandOnClickNode="false"
            lazy
            @lazyLoad="lazyLoad"
          />
        </template>
      </div>
      <div class="select-nav__right fs-m-l-45">
        <div class="select-nav__right--title">底部导航列表</div>
        <fs-radio-group v-model="navigationBtmId">
          <fs-radio v-for="item in navigationBtmList" :key="item.intId" :label="item.intId">
            {{ item.navigationName }}
          </fs-radio>
        </fs-radio-group>
      </div>
    </div>
    <span slot="footer" class="dialog-footer">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler_">
        确 定
      </fs-button>
    </span>
  </fs-dialog>
</template>

<script>
import OrgTree from '@/components/org-tree/index'
export default {
  name: 'SelectNav',
  components: {
    FsOrgTree: OrgTree
  },
  props: {
    navVisible: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: ''
    },
    terminalId: {
      type: String,
      default: ''
    },
    area: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      areaName: '',
      navigationBtmList: [],
      navigationBtmId: null,
      visible: false
    }
  },
  computed: {
    props () {
      return {
        value: 'dictLabel',
        label: 'dictLabel',
        isLeaf: (data, node) => node.level === 2
      }
    },
    httpConfig () {
      return {
        lazy: true,
        // 请求url
        url: '/api/application/query/regional',
        // 请求类型
        type: 'postJson',
        // 请求参数
        params: this.getParams,
        // 响应数据字段
        responseField: 'datalist'
      }
    }
  },
  watch: {
    areaName: {
      handler (val) {
        this.$set(this, 'navigationBtmList', [])
        this.$set(this, 'navigationBtmId', null)
        val && this.queryBtm(val)
      }
    },
    navVisible: {
      handler (val) {
        this.$set(this, 'visible', val)
      },
      deep: true,
      immediate: true
    }
  },
  mounted () {
    if (this.type === 'regional' && this.area) this.queryBtm(this.area)
  },
  methods: {
    lazyLoad (res) {
      const [info] = res
      const { dictLabel } = info
      if (this.areaName === '') this.$set(this, 'areaName', dictLabel)
    },
    getParams (data) {
      let params = {}
      if (data) {
        params = Object.assign(params, {
          cityCode: data.data.dictValue
        })
      }
      return params
    },
    queryBtm (val) {
      let text = val
      if (text.indexOf('-')) {
        text = text.split('-')
        text = text[text.length - 1]
      }
      this.$api('navconfig', 'query', {
        terminalId: this.terminalId,
        area: text
      }).then(res => {
        const { datalist } = res
        this.$set(this, 'navigationBtmList', datalist)
      })
    },
    submitHandler_ () {
      if (!this.navigationBtmId) {
        this.$message({
          type: 'warning',
          message: '请选择导航！'
        })
        return
      }
      const info = this.navigationBtmList.find(item => {
        return item.intId === this.navigationBtmId
      })
      this.$emit('submit', info)
      this.close()
    },
    close () {
      this.$set(this, 'visible', false)
      this.$emit('close')
    }
  }
}
</script>
<style lang="scss" scoped>
.nav-title {
  font-size: 16px;
  color: #333333;
  margin-bottom: 20px;
}
.select-nav {
  &__left {
    width: 120px;
    border-right: 1px solid #F0F2F7;
    &--title {
      @extend .nav-title;
    }
  }

  &__right {
    &--title {
      @extend .nav-title;
    }
  }
}
.fs-radio-group {
  display: flex;
  flex-direction: column;
  > label {
    margin-bottom: 16px;
    font-weight: normal;
  }
}
</style>
