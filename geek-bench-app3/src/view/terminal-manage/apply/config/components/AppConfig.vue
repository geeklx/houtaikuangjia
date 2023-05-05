<template>
  <div class="app-config">
    <div class="app-config__content">
      <fs-super-table
        class="workbench-table"
        :tableConfig="tableConfig"
        :paginationConfig="paginationConfig"
      />
    </div>
  </div>
</template>

<script>
export default {
  $plugins: 'list',
  name: 'AppConfig',
  props: {
    id: {
      type: String
    },
    appId: {
      type: String
    },
    terminalId: {
      type: String
    },
    projectId: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'terminalapply',
      queryMethod: 'appConfigGet',
      deleteMethod: 'appConfigDelete',
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
            label: '版本名称',
            prop: 'versionName',
            showOverflowTooltip: true
          },
          {
            label: '版本号',
            prop: 'versionNum',
            showOverflowTooltip: true
          },
          {
            label: '大小',
            prop: 'versionSize',
            showOverflowTooltip: true,
            render (h, { row }) {
              return (
                <span>{_this.sizeJs(row.versionSize)}</span>
              )
            }
          },
          {
            label: '发布状态',
            prop: 'status',
            showOverflowTooltip: true,
            render (h, { row }) {
              return (
                <fs-dictionary-label
                  value={row.status}
                  dictionaryKey='versionStatus'
                  optionLabel="dictLabel"
                  optionValue="dictValue"
                />
              )
            }
          },
          {
            label: '版本说明',
            prop: 'remark',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            width: '140px',
            render (h, { row }) {
              const STATUS = row.status === 'release'
              return (
                <div>
                  {STATUS ? <fs-button size='mini' onClick={() => _this.statusUpdate(row, 'offline')}>
                    下线
                  </fs-button> : <fs-button size='mini' onClick={() => _this.statusUpdate(row, 'release')}>
                    发布
                  </fs-button>}
                  {!STATUS ? <fs-button size='mini' onclick= {() => _this.deleteHandler([{ id: row.id }])}>
                    删除
                  </fs-button> : ''}
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
    sizeJs (limit) {
      if (!limit) {
        return
      }
      let size = ''
      if (limit < 0.1 * 1024) {
        size = limit.toFixed(2) + 'B'
      } else if (limit < 0.1 * 1024 * 1024) {
        size = (limit / 1024).toFixed(2) + 'KB'
      } else if (limit < 0.1 * 1024 * 1024 * 1024) {
        size = (limit / (1024 * 1024)).toFixed(2) + 'MB'
      } else {
        size = (limit / (1024 * 1024 * 1024)).toFixed(2) + 'GB'
      }
      const sizeStr = size + ''
      const index = sizeStr.indexOf('.')
      const dou = sizeStr.substr(index + 1, 2)
      if (dou === '00') {
        return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
      }
      return size || ''
    },
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        appId: this.appId,
        terminalId: this.terminalId,
        projectId: this.projectId,
        appConfigId: this.id
      })
    },
    statusUpdate (row, status) {
      const { id, appConfigId } = row
      const params = {
        appConfigId,
        id,
        status
      }
      this.$api('terminalapply', 'appConfigSave', params).then(res => {
        const { success, message } = res
        if (success) {
          this.queryHandler()
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

<style scoped lang="scss">
.app-config {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  &__content {
    height: 100%;
    overflow-y: auto;
  }
}
</style>
