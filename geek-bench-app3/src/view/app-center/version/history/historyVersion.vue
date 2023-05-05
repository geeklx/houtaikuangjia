<template>
  <div>
    <fs-super-table
      class="workbench-table"
      :tableConfig="tableConfig"
      :paginationConfig="paginationConfig"
    />
    <dialog-list ref="dialogList" @query="queryHandler"></dialog-list>
  </div>
</template>

<script>
export default {
  $plugins: 'list',
  components: {
    DialogList: () => import('../dialog')
  },
  props: {
    appId: {
      type: String
    },
    appType: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'historyversion',
      queryMethod: 'query',
      deleteMethod: 'delete',
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
            width: '60'
          },
          {
            align: 'center',
            label: '版本名称',
            prop: 'versionName',
            minWidth: '120',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '版本号',
            prop: 'versionNum',
            width: '80',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '版本说明',
            prop: 'remark',
            minWidth: '80',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '下载量',
            prop: 'downloadNum',
            width: '70',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '创建人',
            prop: 'createUserName',
            width: '100',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '创建时间',
            width: '150',
            prop: 'createDatetime',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            width: '210px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showDetail(row.id)}>
                    详情
                  </fs-button>
                  <fs-button size='mini' onClick={() => _this.versionUp(row)}>
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
        attrs: [{
          border: true
        }]
      }
    }
  },
  methods: {
    // 默认请求参数处理，可以将查询条件之外的其他参数返回给框架
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        appId: this.appId,
        appType: this.appType
      })
    },
    showDetail (id) {
      this.$router.push({
        name: '/appCenterManageDetail',
        query: {
          versionId: id
        }
      })
    },
    versionUp (row) {
      this.$api('appcenterversion', 'get', {
        id: row.id
      }).then(res => {
        const { success, message, data } = res
        if (success) {
          const { appConfigs } = data
          let [appConfig] = appConfigs
          if (appConfig) {
            const { appType, appId, appPackagePath, fileName } = appConfig
            appConfig = Object.assign(appConfig, {
              fileList: [{
                name: fileName,
                url: appPackagePath
              }]
            })
            this.showCreate({
              ref: 'dialogList',
              title: '版本编辑',
              appConfig,
              appId,
              type: appType
            })
          }
        } else {
          this.$message.error(message)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
