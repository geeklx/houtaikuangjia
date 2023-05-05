<template>
  <div class="sys-anno">
    <list-layout v-if="manageFlag" :topRightWidth="120">
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
        <fs-form-item prop="projectId" label="项目名称">
          <fs-dictionary-select
            v-model="queryCondition.projectId"
            clearable
            filterable
            placeholder="请选择项目"
            optionLabel="dictLabel"
            optionValue="dictValue"
            url="/api/project/basic/query/option"
            :isCache="false"
          />
        </fs-form-item>
        <fs-form-item prop="noticeTitle" label="公告标题">
          <fs-input
            type="text"
            v-model="queryCondition.noticeTitle"
            clearable
            placeholder="请输入公告标题"
          />
        </fs-form-item>
        <fs-form-item prop="noticeStatus" label="状态">
          <fs-dictionary-select
            v-model="queryCondition.noticeStatus"
            dictionaryKey="noticeStatus"
            optionLabel="dictLabel"
            optionValue="dictValue"
            clearable
            placeholder="请选择状态"
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
      <fs-button slot="search-right" type="primary" @click="toCreate"><i class="fs-icon-plus"></i>新公告</fs-button>
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
  $plugins: 'page',
  data () {
    return {
      apiName: 'anno',
      queryMethod: 'query', // 获取列表数据接口名称
      deleteMethod: 'delete', // 删除操作接口名称
      defaultQueryPageSize: 10, // 默认每页分页条数
      pageSizes: [10, 20, 30, 40],
      createPageName: '/annoDetail',
      detailPageName: '/annoDetail',
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
          if (route.path === '/anno') {
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
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '标题',
            prop: 'noticeTitle',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '状态',
            prop: 'noticeStatus',
            minWidth: '90',
            showOverflowTooltip: true,
            render (h, { row }) {
              return (
                <fs-dictionary-label
                  value={row.noticeStatus}
                  dictionaryKey='noticeStatus'
                  optionLabel="dictLabel"
                  optionValue="dictValue"
                />
              )
            }
          },
          {
            align: 'center',
            label: '创建人',
            prop: '',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '创建时间',
            prop: 'createDatetime',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            render (h, { row }) {
              const { noticeStatus: STATUS } = row
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.toDetail(row.id)}>
                    详情
                  </fs-button>
                  {
                    STATUS === 'send' ? <fs-button size='mini' onClick={() => _this.status(row.id, 'cancel', '取消')}>
                      取消
                    </fs-button> : null
                  }
                  {
                    STATUS === 'timingSend' ? <fs-button size='mini' onClick={() => _this.status(row.id, 'revoke', '撤销')}>
                      撤销
                    </fs-button> : null
                  }
                  {
                    STATUS === 'revoke' ? <fs-button size='mini' onClick={() => _this.status(row.id, row.sendTimeType === '1' ? 'send' : 'timingSend', '恢复')}>
                      恢复
                    </fs-button> : null
                  }
                  <fs-button size='mini' onClick={() => _this.deleteHandler({ id: row.id })}>
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
    status (id, type, title) {
      this.$confirm(`是否要${title}该数据?`, '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        this.$api('anno', 'status', {
          id,
          noticeStatus: type
        }).then(res => {
          const { success } = res
          if (success) this.$message.success('提交成功！')
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.sys-anno {
  width: 100%;
  height: 100%;
}
</style>
