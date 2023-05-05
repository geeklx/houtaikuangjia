<template>
  <div class="sys-dict">
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
        <fs-form-item prop="dictName" label="字典名称">
          <fs-input
            type="text"
            v-model="queryCondition.dictName"
            clearable
            placeholder="请输入字典名称"
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
        <fs-button slot="search-right" type="primary" @click="showCreate"><i class="fs-icon-plus"></i>新增</fs-button>
      <div slot="table">
        <div>
          <fs-button @click="batch('stop')">批量禁用</fs-button>
          <fs-button class="fs-m-l-10" @click="batch('normal')">批量启用</fs-button>
        </div>
        <fs-super-table
          class="workbench-table fs-m-t-10"
          :tableConfig="tableConfig"
          :paginationConfig="paginationConfig"
          @selection-change="handleSelectionChange"
        />
      </div>
      <dict-form :ref="createDialogRef" />
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
      <dict-drawer ref="dictDrawerRef" @query="queryHandler" />
    </list-layout>
  </div>
</template>

<script>
export default {
  $plugins: 'page',
  components: {
    DictForm: () => import('./dictForm'),
    DictDrawer: () => import('./dictDrawer')
  },
  data () {
    return {
      apiName: 'dict',
      queryMethod: 'query', // 获取列表数据接口名称
      deleteMethod: 'delete', // 删除操作接口名称
      defaultQueryPageSize: 10, // 默认每页分页条数
      pageSizes: [10, 20, 30, 40],
      paginationConfig: {
        show: false
      },
      multipleSelection: []
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
            type: 'selection',
            width: '55',
            showOverflowTooltip: true
          },
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
            label: '字典名称',
            prop: 'dictName',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '字典编码',
            prop: 'dictType',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '备注',
            prop: 'remark',
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
            label: '状态',
            prop: 'status_dict',
            minWidth: '90',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            width: 260,
            render (h, { row }) {
              const { status } = row
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id)}>
                    编辑{row.status === 'stop'}
                  </fs-button>
                  <fs-button size='mini' onClick={() => _this.showConfig(row.dictType)}>
                    配置码值
                  </fs-button>
                  {
                    status === 'normal' ? <fs-button size='mini' onClick={() => _this.statusUpdate([row.id], 'stop', '禁用该')}>
                      禁用
                    </fs-button> : null
                  }
                  {
                    status === 'stop' ? <fs-button size='mini' onClick={() => _this.statusUpdate([row.id], 'normal', '启用该')}>
                      启用
                    </fs-button> : null
                  }
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
    // 批量选择
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 默认请求参数处理，可以将查询条件之外的其他参数返回给框架
    // getQueryCondition (params = {}) {
    //   return { }
    // },
    batch (t) {
      if (!this.multipleSelection.length) {
        this.$message.warning('请选择要操作的数据!')
        return
      }
      const ids = this.multipleSelection.map(item => {
        return item.id
      })
      this.statusUpdate(ids, t, t === 'stop' ? '禁用当前选中' : '启用当前选中')
    },
    statusUpdate (ids, type, title) {
      const params = ids.map(item => {
        return {
          id: item,
          status: type
        }
      })
      this.$confirm(`是否要${title}数据?`, '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        this.$api('dict', 'status', params).then(res => {
          const { success } = res
          if (success) {
            this.$message.success('更新成功！')
            this.queryHandler()
          }
        })
      })
    },
    showConfig (dictType) {
      this.$refs.dictDrawerRef.open(dictType)
    }
  }
}
</script>
<style lang="scss" scoped>
.sys-dict {
  width: 100%;
  height: 100%;
}
</style>
