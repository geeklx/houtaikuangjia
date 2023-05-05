<template>
  <div class="protocol-config">
    <div class="protocol-config-btns">
      <fs-button type="primary" @click="showCreate({terminalId: id})"><i class="fs-icon-plus"></i>新建协议</fs-button>
    </div>
    <fs-super-table
      class="protocol-config-table workbench-table"
      :tableConfig="tableConfig"
      :paginationConfig="paginationConfig"
    />
    <detail-form :ref="createDialogRef" />
  </div>
</template>

<script>
export default {
  $plugins: 'list',
  name: 'ProtocolConfig',
  components: {
    DetailForm: () => import('./form/ProtocolConfigDetail')
  },
  props: {
    id: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'protocolconfig',
      queryMethod: 'query',
      paginationConfig: {
        show: false
      }
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
            width: '60',
            index: _this.indexMethod,
            showOverflowTooltip: true
          },
          {
            label: '协议名称',
            prop: 'agreementName',
            showOverflowTooltip: true
          },
          {
            label: '协议类型',
            prop: 'agreementType',
            showOverflowTooltip: true,
            render (h, { row }) {
              return (
                <fs-dictionary-label
                  value={row.agreementType}
                  dictionaryKey="agreement"
                  optionLabel="dictLabel"
                  optionValue="dictValue"
                />
              )
            }
          },
          {
            label: '创建时间',
            prop: 'createDatetime',
            showOverflowTooltip: true
          }, {
            align: 'center',
            label: '操作',
            width: '140px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id, { terminalId: _this.id })}>
                    编辑
                  </fs-button>
                  <fs-button size='mini' onclick= {() => _this.deleteHandler([{ id: row.id }])}>
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
        terminalId: this.id
      })
    }
  }
}
</script>

<style scoped lang="scss">
.protocol-config {
  width: 100%;
  height: 100%;
  padding: 12px;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  &-btns {
    display: flex;
    justify-content: flex-end;
  }
  &-table {
    margin-top: 12px;
  }
}
</style>
