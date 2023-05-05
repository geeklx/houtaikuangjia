<template>
  <list-layout :topRightWidth="130">
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
      <fs-form-item prop="status" label="版本状态">
        <fs-dictionary-select
          v-model="queryCondition.status"
          dictionaryKey="versionStatus"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择版本状态"
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
    <fs-button slot="search-right" type="primary" @click="showCreate"><i class="fs-icon-plus"></i>添加发布包</fs-button>
    <fs-super-table
      slot="table"
      class="workbench-table"
      :tableConfig="tableConfig"
      :paginationConfig="paginationConfig"
    />
    <detail-form :ref="createDialogRef"></detail-form>
    <release-list ref="releaseList"></release-list>
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
import axios from 'axios'
import { downloadByData } from 'fosung-sdk'
export default {
  $plugins: 'page', // 此处是关键，必须包含page才可以！！！
  components: {
    DetailForm: () => import('./detail'),
    ReleaseList: () => import('./release/index')
  },
  data () {
    return {
      terminalList: [],
      apiName: 'terminalversion',
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
          // {
          //   align: 'center',
          //   label: '版本名称',
          //   prop: 'versionName',
          //   width: '180',
          //   showOverflowTooltip: true
          // },
          {
            align: 'center',
            label: '版本号',
            prop: 'terminalVersion',
            minWidth: '70',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '版本状态',
            prop: 'status',
            minWidth: '90',
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
            label: '更新说明',
            prop: 'remark',
            minWidth: '180',
            showOverflowTooltip: true
          },
          // {
          //   align: 'center',
          //   label: '是否强制更新',
          //   prop: 'forceUpgrade',
          //   width: '120',
          //   showOverflowTooltip: true,
          //   render (h, { row }) {
          //     return <span>{row.forceUpgrade ? '是' : '否'}</span>
          //   }
          // },
          // {
          //   align: 'center',
          //   label: '安装数',
          //   prop: 'installationsNumber',
          //   width: '70',
          //   showOverflowTooltip: true
          // },
          // {
          //   align: 'center',
          //   label: '下载数',
          //   prop: 'downloadNumber',
          //   width: '70',
          //   showOverflowTooltip: true
          // },
          // {
          //   align: 'center',
          //   label: '大小',
          //   width: '80',
          //   showOverflowTooltip: true,
          //   render (h, { row }) {
          //     return (
          //       <span>{_this.sizeJs(row.appSize)}</span>
          //     )
          //   }
          // },
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
            width: '240px',
            render (h, { row }) {
              const STATUS = row.status === 'release' // 已发布状态
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showDetail(row.id)}>
                    详情
                  </fs-button>
                  {STATUS ? <fs-button size='mini' onClick={() => _this.statusUpdate(row.id, 'offline')}>
                    下线
                  </fs-button> : <fs-button size='mini' onClick={() => _this.statusUpdate(row.id, 'release')}>
                    发布
                  </fs-button>}
                  <fs-dropdown
                    placement='bottom'
                    onCommand={data => _this.handleCommand(data, row)}
                  >
                    <fs-button class='fs-m-l-10'>
                      更多操作
                    </fs-button>
                    <fs-dropdown-menu slot='dropdown'>
                      {!STATUS ? <fs-dropdown-item command='edit'>
                        <div className='fs-p-l-20 fs-p-r-20'>编辑</div>
                      </fs-dropdown-item> : null}
                      <fs-dropdown-item command='releaseRecord'>
                        <div class='fs-p-l-20 fs-p-r-20'>发布记录</div>
                      </fs-dropdown-item>
                      <fs-dropdown-item command='download'>
                        <div class='fs-p-l-20 fs-p-r-20'>下载</div>
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
    statusUpdate (id, status) {
      this.$api('terminalversion', 'status', {
        id,
        status: status
      }).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('更新成功')
          this.queryHandler()
        } else {
          this.$message.error(message)
        }
      })
    },
    handleCommand (data, row) {
      switch (data) {
        case 'edit':
          this.showEdit(row.id)
          break
        case 'releaseRecord':
          this.releaseRecord(row)
          break
        case 'download':
          this.download(row)
          break
        case 'delete':
          this.deleteHandler([{ id: row.id }])
          break
        default:
          break
      }
    },
    releaseRecord (row) {
      this.showCreate({
        ref: 'releaseList',
        title: '发布记录',
        versionId: row.id,
        type: 'ReleaseRecord'
      })
    },
    download (row) {
      const { installationPackage } = row
      if (!installationPackage) {
        this.$message.warning('无文件下载！')
        return
      }
      this.$message.info('文件下载中，请稍后...')
      const arr = installationPackage.split('/')
      axios.get(installationPackage, {
        responseType: 'blob',
        headers: {
          system: 'tyxs',
          'content-Type': 'application/json'
        }
      }).then(res => {
        const { data } = res
        if (!Reflect.has(data, 'success')) {
          downloadByData(data, arr[arr.length - 1])
        } else {
          this.$message.error(data.message)
        }
      })
    }
  }
}
</script>
