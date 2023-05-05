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
      <fs-form-item prop="configName" label="配置名称">
        <fs-input
          type="text"
          v-model="queryCondition.configName"
          clearable
          placeholder="请输入配置名称"
        />
      </fs-form-item>
      <fs-form-item prop="configType" label="配置类型">
        <fs-dictionary-select
          v-model="queryCondition.configType"
          url="/api/config/cascade/query"
          optionLabel="dictLabel"
          optionValue="dictValue"
          :params="{parentId: '0'}"
          requestType="postJson"
          clearable
          placeholder="请选择配置类型"
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
    <fs-button slot="search-right" type="primary" @click="showCreate"><i class="fs-icon-plus"></i>创建配置</fs-button>
    <fs-super-table
      slot="table"
      class="workbench-table"
      :tableConfig="tableConfig"
      :paginationConfig="paginationConfig"
    />
    <detail-form :ref="createDialogRef" />
    <template slot="page">
      <!-- 分页组件 -->
      <fs-pagination
        @size-change="onPageSizeChange"
        @current-change="onPageNumChange"
        :current-page="page.current"
        :page-sizes="pageSizes"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total"
      />
    </template>
  </list-layout>
</template>

<script>
export default {
  $plugins: 'page',
  components: {
    DetailForm: () => import('./detail')
  },
  data () {
    return {
      apiName: 'configmanage',
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
            label: '认证平台名称',
            prop: 'configPlatformName',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '认证平台地址',
            prop: 'configPlatform',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '配置类型',
            prop: 'configTypeName',
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
            width: '140px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id)}>
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
    }
    // 默认请求参数处理，可以将查询条件之外的其他参数返回给框架
    // getQueryCondition (params = {}) {
    //   return { }
    // }
  }
}
</script>
