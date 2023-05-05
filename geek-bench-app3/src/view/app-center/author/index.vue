<template>
  <list-layout :topRightWidth="0">
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
          @change="terminalChange"
        >
          <fs-option
            v-for="item in terminalList"
            :key="item.id"
            :label="item.terminalName"
            :value="item.id">
          </fs-option>
        </fs-select>
      </fs-form-item>
      <fs-form-item prop="identity" label="选择身份">
        <fs-dictionary-select
          v-model="queryCondition.identity"
          url="/api/application/query/identity"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择身份"
        />
      </fs-form-item>
      <fs-form-item prop="org" label="选择组织" v-show="useWidth >= 1066">
        <fs-select-tree
          style="min-width: 272px"
          v-if="showSelectTree"
          v-model="queryCondition.org"
          :treeConfig="treeConfig"
          clearable
          placeholder="请选择组织"
        ></fs-select-tree>
        <fs-select
          style="min-width: 272px"
          v-else
          v-model="queryCondition.org"
          placeholder="请选择组织"
          clearable
        ></fs-select>
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
        <fs-form-item prop="org" label="选择组织" v-show="useWidth < 1066">
          <fs-select-tree
            style="min-width: 272px"
            v-if="showSelectTree"
            v-model="queryCondition.org"
            :treeConfig="treeConfig"
            clearable
            placeholder="请选择组织"
          ></fs-select-tree>
          <fs-select
            style="min-width: 272px"
            v-else
            v-model="queryCondition.org"
            placeholder="请选择组织"
            clearable
          ></fs-select>
        </fs-form-item>
        <fs-form-item prop="user" label="选择人员">
          <fs-select-tree
            style="min-width: 272px"
            v-if="showSelectTree"
            v-model="queryCondition.user"
            :treeConfig="treeConfigUser"
            clearable
            placeholder="请选择人员"
          ></fs-select-tree>
          <fs-select
            style="min-width: 272px"
            v-else
            v-model="queryCondition.user"
            placeholder="请选择人员"
            clearable
          ></fs-select>
        </fs-form-item>
      </fs-form>
    </template>
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
</template>

<script>
import { SelectTree } from 'fs-select-tree'
export default {
  $plugins: 'page', // 此处是关键，必须包含page才可以！！！
  components: {
    FsSelectTree: SelectTree
  },
  data () {
    return {
      apiName: 'appcenterquery',
      queryMethod: 'query', // 获取列表数据接口名称
      defaultQueryPageSize: 10, // 默认每页分页条数
      pageSizes: [10, 20, 30, 40],
      terminalList: [],
      paginationConfig: {
        show: false
      },
      showSelectTree: true,
      screenWidth: document.body.clientWidth,
      useWidth: 0
    }
  },
  watch: {
    screenWidth: {
      handler (val) {
        this.screenWidth = val
        this.useWidth = this.screenWidth - document.getElementsByClassName('sidebar-container')[0].offsetWidth
      },
      deep: true,
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
            minWidth: '120',
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
            minWidth: '120',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '应用版本',
            prop: 'versionName',
            minWidth: '90',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '应用简介',
            prop: 'remark',
            minWidth: '240',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '授权来源',
            prop: 'dataSource_dict',
            minWidth: '120',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '授权时间',
            prop: 'createDateTime',
            width: '180',
            showOverflowTooltip: true
          }
        ],
        attrs: [
          {
            border: true
          }
        ]
      }
    },
    treeConfig () {
      return {
        props: {
          value: 'id',
          label: 'name',
          isLeaf: (data) => data.hasChildren === '0'
        },
        lazy: true,
        leafOnly: false,
        expandOnClickNode: false,
        httpConfig: {
          // 请求url
          url: '/api/application/query/organ',
          // 请求类型
          type: 'postJson',
          // 请求参数
          params: this.getParentId,
          // 响应数据字段
          responseField: 'datalist'
        }
      }
    },
    treeConfigUser () {
      return {
        props: {
          value: 'id',
          label: 'name',
          disabled: (data) => data.type === 0,
          isLeaf: (data) => data.type === 1
        },
        lazy: true,
        leafOnly: false,
        expandOnClickNode: false,
        httpConfig: {
          // 请求url
          url: '/api/application/query/user',
          // 请求类型
          type: 'postJson',
          // 请求参数
          params: this.getParams,
          // 响应数据字段
          responseField: 'datalist'
        }
      }
    }
  },
  mounted () {
    const _ = this
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth
        _.screenWidth = window.screenWidth
      })()
    }
    this.queryTerminalList(null)
  },
  methods: {
    terminalChange (val) {
      this.$delete(this.queryCondition, 'org')
      this.$delete(this.queryCondition, 'user')
      if (val) {
        // 查组织和人员
        this.showSelectTree = false
        const _ = this
        setTimeout(() => {
          _.showSelectTree = true
        })
      }
    },
    getParentId (data) {
      const { id } = data.data
      return {
        terminalId: this.queryCondition.terminalId || '',
        parentId: id || '0'
      }
    },
    getParams (data) {
      const { id, hasChildren } = data.data
      return {
        terminalId: this.queryCondition.terminalId || '',
        parentId: id || '0',
        requestType: hasChildren === '0' ? '1' : '0'
      }
    },
    projectChange (val) {
      this.$delete(this.queryCondition, 'terminalId')
      if (val) {
        this.queryTerminalList(val)
      }
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
    getQueryCondition (params_ = {}) {
      const params = Object.assign({}, params_, this.queryCondition, {
        shelvesList: []
      })
      const { identity, org, user } = this.queryCondition
      params.shelvesList = params.shelvesList.concat(
        this.getQueryParams(identity, 'identity') || [],
        this.getQueryParams(org, 'org') || [],
        this.getQueryParams(user, 'user') || []
      )
      return params
    },
    getQueryParams (val, type) {
      return !val ? null : [{
        shelvesType: type,
        shelvesRange: val
      }]
    },
    handleCommand (data, row) {
      switch (data) {
        case 'publish':
          break
        case 'preview':
          break
        default:
          break
      }
    }
  }
}
</script>
