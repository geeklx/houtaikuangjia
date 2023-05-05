<template>
  <list-layout :topRightWidth="120">
    <div slot="top-header" class="title">{{ $route.meta.title }}</div>

    <!-- 查询条件 -->
    <fs-form
      inline
      slot="searchForm"
      class="search-form"
      :model="queryCondition"
      label-width="60px"
      @submit.native.prevent
      size="mini"
    >
      <fs-form-item prop="appName" label="应用名称">
        <fs-input
          type="text"
          v-model="queryCondition.appName"
          clearable
          placeholder="请输入应用名称"
        />
      </fs-form-item>
      <fs-form-item prop="versionName" label="版本名称">
        <fs-input
          type="text"
          v-model="queryCondition.versionName"
          clearable
          placeholder="请输入版本名称"
        />
      </fs-form-item>
      <fs-form-item prop="appType" label="应用类型">
        <fs-dictionary-select
          v-model="queryCondition.appType"
          dictionaryKey="appType"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择应用类型"
        />
      </fs-form-item>
      <fs-form-item prop="time" label="创建时间">
        <fs-date-picker
          v-model="queryCondition.startAndEndTime"
          unlink-panels
          @change="timeChange"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss">
        </fs-date-picker>
      </fs-form-item>
    </fs-form>
    <template slot="searchButton">
      <fs-button type="primary" plain @click="queryHandler">查询</fs-button>
      <fs-button plain @click="resetQueryCondition">重置</fs-button>
    </template>
    <fs-button slot="search-right" type="primary" @click="addPackage"><i class="fs-icon-plus"></i>添加发布包</fs-button>
    <fs-super-table
      slot="table"
      class="workbench-table"
      :tableConfig="tableConfig"
      :paginationConfig="paginationConfig"
    />
    <dialog-list ref="dialogList" @query="queryHandler"></dialog-list>
    <history-list ref="historyList" @query="queryHandler"></history-list>
    <add-package ref="addPackage" @query="queryHandler"></add-package>
    <template slot="page">
      <!-- 分页组件 -->
      <fs-pagination
        @size-change="onPageSizeChange"
        @current-change="onPageNumChange"
        :current-page="page.current"
        :page-sizes="pageSizes"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total">
      </fs-pagination>
    </template>
  </list-layout>
</template>

<script>
export default {
  $plugins: 'page', // 此处是关键，必须包含page才可以！！！
  components: {
    DialogList: () => import('./dialog'),
    HistoryList: () => import('./history'),
    AddPackage: () => import('./package')
  },
  data () {
    return {
      apiName: 'appcenterversion',
      queryMethod: 'query', // 获取列表数据接口名称
      deleteMethod: 'delete', // 删除操作接口名称
      defaultQueryPageSize: 10, // 默认每页分页条数
      pageSizes: [10, 20, 30, 40],
      paginationConfig: {
        show: false
      }
    }
  },
  computed: {
    tableConfig () {
      const _this = this
      return {
        data: _this.page?.data,
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
            align: 'center',
            label: '应用名称',
            prop: 'appName',
            minWidth: '180',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '版本名称',
            prop: 'versionName',
            minWidth: '90',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '类型',
            prop: 'appType',
            width: '90',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '最新版本',
            prop: 'versionNum',
            minWidth: '90',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '版本说明',
            prop: 'remark',
            minWidth: '240',
            showOverflowTooltip: true
          },
          // {
          //   align: 'center',
          //   label: '下载量',
          //   prop: 'downloadNum',
          //   width: '90',
          //   showOverflowTooltip: true
          // },
          {
            align: 'center',
            label: '创建时间',
            prop: 'createDateTime',
            width: '180',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            width: '260px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showDetail(row.id)}>
                    详情
                  </fs-button>
                  <fs-button size='mini' onClick={() => _this.versionUp(row)}>
                    版本升级
                  </fs-button>
                  <fs-dropdown
                    placement='bottom'
                    onCommand={data => _this.handleCommand(data, row)}
                  >
                    <fs-button class='fs-m-l-10'>
                      更多操作
                    </fs-button>
                    <fs-dropdown-menu slot='dropdown'>
                      <fs-dropdown-item command='historyVersion'>
                        <div class='fs-p-l-20 fs-p-r-20'>所有版本</div>
                      </fs-dropdown-item>
                    </fs-dropdown-menu>
                  </fs-dropdown>
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
    timeChange (time) {
      if (time) {
        this.queryCondition.startTime = time[0]
        this.queryCondition.endTime = time[1]
      } else {
        this.queryCondition.startTime && delete this.queryCondition.startTime
        this.queryCondition.endTime && delete this.queryCondition.endTime
      }
    },
    // 动态计算序号
    indexMethod (index) {
      return (this.page.pageNum - 1) * this.page.pageSize + index + 1
    },
    // 默认请求参数处理，可以将查询条件之外的其他参数返回给框架
    // getQueryCondition (params = {}) {
    //   return { }
    // },
    handleCommand (data, row) {
      switch (data) {
        case 'historyVersion':
          this.historyVersion(row)
          break
        default:
          break
      }
    },
    addPackage (row) {
      this.showCreate({
        ref: 'addPackage',
        title: '添加发布包'
      })
    },
    historyVersion (row) {
      this.showCreate({
        ref: 'historyList',
        title: '所有版本',
        appId: row.appId,
        appType: row.appType,
        type: 'HistoryVersion'
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
      const { appConfig, appType, appId } = row
      this.showCreate({
        ref: 'dialogList',
        title: '版本升级',
        appConfig,
        appId,
        type: appType
      })
      // this.$api('appcenterversion', 'get', {
      //   id: row.id
      // }).then(res => {
      //   const { success, message, data } = res
      //   if (success) {
      //     const { appConfigs } = data
      //     const [appConfig] = appConfigs
      //     if (appConfig) {
      //       const { appType } = appConfig
      //       this.showCreate({
      //         ref: 'dialogList',
      //         title: '版本升级',
      //         appConfig,
      //         type: appType
      //       })
      //     }
      //   } else {
      //     this.$message.error(message)
      //   }
      // })
    }
  }
}
</script>
