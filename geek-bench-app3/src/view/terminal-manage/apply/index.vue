<template>
  <div class="terminal-apply">
  <list-layout v-if="manageFlag" class="app-center" :topRightWidth="90">
    <div slot="top-header" class="title">{{ $route.meta.title }}</div>

    <!-- 查询条件 -->
    <fs-form
      inline
      slot="searchForm"
      class="search-form"
      :model="queryCondition"
      label-width="80px"
      @submit.native.prevent
      size="mini"
    >
      <fs-form-item prop="projectId" label="项目名称">
        <fs-dictionary-select
          v-model="queryCondition.projectId"
          url="/api/project/basic/query/option"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          filterable
          :isCache="false"
          placeholder="请选择项目"
          @change="projectChange"
        />
      </fs-form-item>
      <fs-form-item prop="terminalId" label="终端名称">
        <fs-select
          v-model="queryCondition.terminalId"
          placeholder="请选择终端"
          clearable
          filterable
        >
          <fs-option
            v-for="item in terminalList"
            :key="item.id"
            :label="item.terminalName"
            :value="item.id">
          </fs-option>
        </fs-select>
      </fs-form-item>
<!--      <fs-form-item prop="terminalCode" label="终端编码">
        <fs-input
          type="text"
          v-model="queryCondition.terminalCode"
          clearable
          placeholder="请输入终端编码"
        />
      </fs-form-item>-->
      <fs-form-item prop="appName" label="应用名称">
        <fs-input
          type="text"
          v-model="queryCondition.appName"
          clearable
          placeholder="请输入应用名称"
        />
      </fs-form-item>
        <fs-form-item prop="status" label="状态">
          <fs-dictionary-select
            v-model="queryCondition.status"
            dictionaryKey="versionStatus"
            optionLabel="dictLabel"
            optionValue="dictValue"
            clearable
            placeholder="请选择状态"
          />
        </fs-form-item>
    </fs-form>
    <template slot="searchButton">
      <fs-button type="primary" plain @click="queryHandler">查询</fs-button>
      <fs-button plain @click="resetQueryCondition">重置</fs-button>
    </template>
    <template slot="searchMore">
      <fs-form
        slot="searchForm"
        inline
        class="search-form"
        :model="queryCondition"
        label-width="80px"
        @submit.native.prevent
        size="mini"
      >
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
    </template>
    <fs-button slot="search-right" type="primary" @click="applyBind">应用绑定</fs-button>
    <fs-super-table
      slot="table"
      class="workbench-table"
      :tableConfig="tableConfig"
      :paginationConfig="paginationConfig"
    />
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
    <router-view v-else/>
  </div>
</template>

<script>
export default {
  $plugins: 'page', // 此处是关键，必须包含page才可以！！！
  components: {
  },
  data () {
    return {
      terminalList: [],
      apiName: 'terminalapply',
      queryMethod: 'query', // 获取列表数据接口名称
      deleteMethod: 'delete', // 删除操作接口名称
      defaultQueryPageSize: 10, // 默认每页分页条数
      pageSizes: [10, 20, 30, 40],
      paginationConfig: {
        show: false
      },
      manageFlag: true
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.$nextTick(function () {
          if (route.path === '/terminalApply') {
            this.manageFlag = true
          } else {
            this.manageFlag = false
          }
        })
      },
      immediate: true
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
            label: '项目名称',
            prop: 'projectName',
            minWidth: '180',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '终端名称',
            prop: 'terminalName',
            minWidth: '180',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '应用名称',
            prop: 'appName',
            width: '180',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '应用版本',
            prop: 'versionNum',
            minWidth: '90',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '应用简介',
            prop: 'remark',
            minWidth: '120',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '状态',
            prop: 'status',
            width: '90',
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
            align: 'center',
            label: '创建时间',
            prop: 'createDatetime',
            width: '180',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            width: '280px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.basicConfig(row)}>
                    基础配置
                  </fs-button>
                  <fs-button size='mini' onClick={() => _this.basicConfig(row, 'authorConfig')}>
                    应用授权
                  </fs-button>
                  <fs-dropdown
                    placement='bottom'
                    onCommand={data => _this.handleCommand(data, row)}
                  >
                    <fs-button class='fs-m-l-10'>
                      更多操作
                    </fs-button>
                    <fs-dropdown-menu slot='dropdown'>
                      <fs-dropdown-item command='appConfig'>
                        <div class='fs-p-l-20 fs-p-r-20'>应用版本</div>
                      </fs-dropdown-item>
                      <fs-dropdown-item command='copy'>
                        <div class='fs-p-l-20 fs-p-r-20'>复制</div>
                      </fs-dropdown-item>
                      <fs-dropdown-item command='delete'>
                        <div class='fs-p-l-20 fs-p-r-20'>删除</div>
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
  mounted () {
    this.queryTerminalList(null)
  },
  methods: {
    projectChange (val) {
      this.$delete(this.queryCondition, 'terminalId')
      this.queryTerminalList(val)
    },
    queryTerminalList (val) {
      this.$api('bindApply', 'queryTerminalList', { projectId: val }).then(res => {
        const { datalist } = res
        this.terminalList = [].concat(datalist)
      })
    },
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
        case 'appConfig':
          this.basicConfig(row, 'appConfig')
          break
        case 'copy':
          this.copy(row.id)
          break
        case 'delete':
          this.deleteHandler([{ id: row.id }])
          break
        default:
          break
      }
    },
    copy (id) {
      this.$api('terminalapply', 'copy', { id }).then(res => {
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
    },
    applyBind () {
      this.manageFlag = false
      this.$router.push({
        name: '/terminalApplyBind'
      })
    },
    basicConfig (row, menuCurr = 'basicConfig') {
      const { id, appId, appIcon, terminalId, projectId } = row
      this.manageFlag = false
      this.$router.push({
        name: '/terminalApplyConfig',
        query: { id, appId, terminalId, projectId, appIcon, appType: row.appType_dict, menuCurr }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.terminal-apply {
  height: 100%;
}
.app-center {
  height: 100%;
  ::v-deep.fs-card__header {
    border: none;
  }
}
</style>
