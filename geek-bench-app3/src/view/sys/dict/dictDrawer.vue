<template>
  <fs-drawer
    :title="title"
    :visible.sync="visible"
    :with-header="false"
    :wrapper-closable="false"
    size="820px"
  >
    <div class="fs-detail fs-flex-col">
      <div class="fs-detail__header fs-flex fs-col-center fs-row-between">
        <div class="fs-detail__header--right fs-module-title">{{title}}</div>
        <div class="fs-detail__header--left">
          <i class="fs-icon-close" @click="close" />
        </div>
      </div>
      <div class="fs-detail__content">
        <div style="text-align: right">
          <fs-button type="primary" @click="showCreate({dictType})">
            新 增
          </fs-button>
        </div>
        <fs-super-table
          class="workbench-table"
          :tableConfig="tableConfig"
          :paginationConfig="paginationConfig"
        />
      </div>
    </div>
    <dict-drawer-form :ref="createDialogRef" />
  </fs-drawer>
</template>

<script>
export default {
  $plugins: 'list',
  components: {
    DictDrawerForm: () => import('./dictDrawerForm')
  },
  props: {},
  data () {
    return {
      title: '创建应用',
      visible: false,
      initQuery: false,

      apiName: 'dict',
      queryMethod: 'queryConfig',
      deleteMethod: 'deleteConfig',
      paginationConfig: {
        show: false
      },
      dictType: ''
    }
  },
  computed: {
    tableConfig () {
      const _this = this
      return {
        data: _this.list,
        column: [
          {
            align: 'center',
            label: '序号',
            type: 'index',
            width: '60'
          },
          {
            align: 'center',
            label: '名称',
            prop: 'dictLabel',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '排序',
            prop: 'num',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id, { dictType: _this.dictType })}>
                    编辑
                  </fs-button>
                  <fs-button size='mini' onClick={() => _this.deleteHandler([{ id: row.id }])}>
                    删除
                  </fs-button>
                </div>
              )
            }
          }
        ],
        attrs: [
          {
            border: true
          }
        ]
      }
    }
  },
  methods: {
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        dictType: this.dictType
      })
    },
    open (dictType) {
      this.visible = true
      this.dictType = dictType
      this.queryHandler()
    },
    close () {
      this.visible = false
    }
  }
}
</script>

<style scoped lang="scss">
.height-auto {
  height: 100%;
}
.width-auto {
  width: 100%;
}
.fs-detail {
  @extend .height-auto;
  &__header {
    @extend .width-auto;
    height: 44px;
    padding: 14px 20px;
    border-bottom: 1px solid #E4E7ED;
    &--right {
      color: #333333;
      font-size: 16px;
    }
  }
  &__content {
    @extend .height-auto;
    flex: 1;
    overflow-y: auto;
    padding: 30px;
  }
}
.workbench-table{
  margin-top: 10px;
}
</style>
