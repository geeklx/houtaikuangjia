<template>
  <div class="terminal-manage">
    <list-li-layout v-if="manageFlag" class="app-center" :topRightWidth="120">
      <div slot="top-header" class="title-no-border">{{ $route.meta.title }}</div>
      <fs-form slot="searchForm" inline class="search-form" :model="queryCondition" label-width="80px" @submit.native.prevent size="mini">
        <fs-form-item prop="projectId" label="项目名称">
          <fs-dictionary-select
            v-model="queryCondition.projectId"
            clearable
            filterable
            url="/api/project/basic/query/option"
            optionLabel="dictLabel"
            optionValue="dictValue"
            placeholder="请选择项目"
            :isCache="false"
            @change="projectChange"
          />
        </fs-form-item>
        <fs-form-item prop="id" label="终端名称">
          <fs-select
            v-model="queryCondition.id"
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
        <fs-form-item prop="terminalCode" label="终端编码">
          <fs-input
            type="text"
            v-model="queryCondition.terminalCode"
            clearable
            placeholder="请输入终端编码"
          />
        </fs-form-item>
        <fs-form-item prop="terminalType" label="终端类型">
          <fs-dictionary-select
            v-model="queryCondition.terminalType"
            dictionaryKey="terminalType"
            optionLabel="dictLabel"
            optionValue="dictValue"
            clearable
            placeholder="请选择终端类型"
          />
        </fs-form-item>
      </fs-form>
      <template slot="searchButton">
        <fs-button type="primary" plain @click="queryHandler">查询</fs-button>
        <fs-button plain @click="resetQueryCondition">重置</fs-button>
      </template>
      <fs-button slot="search-right" type="primary" @click="showCreate"><i class="fs-icon-plus"></i>创建终端</fs-button>
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
          <fs-form-item prop="name" label="创建时间">
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
      <template slot="list-content">
        <div class="list-content" v-if="page.data.length > 0">
          <fs-row :gutter="14">
            <fs-col :span="8" v-for="(item, index) in page.data" :key="index">
              <div class="list-content-item">
                <div class="list-content-item-info">
                  <div class="list-content-item-info__icon">
                    <img v-if="item.terminalLogo" :src="item.terminalLogo">
                    <img v-else src="../png/terminal-list.png">
                  </div>
                  <div class="list-content-item-info__detail">
                    <div class="list-content-item-info__detail__title">{{item.name}}</div>
                    <pro-item :basic-form="item.formList" :label-width="60" style="margin-top: 18px"></pro-item>
                  </div>
                </div>
                <div class="list-content-item-btn">
                  <fs-button type="text" @click="showConfig(item)">终端维护</fs-button>
                  <fs-button type="text" @click="applyBind(item)">应用绑定</fs-button>
                  <fs-button type="text" @click="showEdit(item.id)">编辑</fs-button>
                  <fs-button type="text" @click="deleteHandler([{ id: item.id }])">删除</fs-button>
                </div>
              </div>
            </fs-col>
          </fs-row>
        </div>
        <empty v-else></empty>
      </template>
      <template slot="list-footer">
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
      <detail-form :ref="createDialogRef"></detail-form>
    </list-li-layout>
    <router-view v-else/>
  </div>
</template>

<script>
export default {
  $plugins: 'page',
  components: {
    DetailForm: () => import('./detail')
  },
  data () {
    return {
      terminalList: [],
      apiName: 'terminalmanage',
      queryMethod: 'query', // 获取列表数据接口名称
      deleteMethod: 'delete',
      defaultQueryPageSize: 9,
      pageSizes: [9, 18, 27, 50],
      manageFlag: true
    }
  },
  mounted () {
    this.queryTerminalList(null)
  },
  watch: {
    $route: {
      handler: function (route) {
        this.$nextTick(function () {
          if (route.path === '/terminalManage') {
            this.manageFlag = true
          } else {
            this.manageFlag = false
          }
        })
      },
      immediate: true
    }
  },
  methods: {
    projectChange (val) {
      this.$delete(this.queryCondition, 'id')
      this.queryTerminalList(val)
    },
    queryTerminalList (val) {
      this.$api('bindApply', 'queryTerminalList', { projectId: val }).then(res => {
        const { datalist } = res
        this.terminalList = [].concat(datalist)
      })
    },
    showConfig (row) {
      this.manageFlag = false
      this.$router.push({
        name: '/terminalConfig',
        query: {
          id: row.id
        }
      })
    },
    applyBind (row) {
      const { projectId, id } = row
      this.manageFlag = false
      this.$router.push({
        name: '/terminalConfigBind',
        query: { projectId, terminalId: id }
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
    getResponseInfo (response) {
      let { datalist } = response
      datalist = datalist.map(item => {
        return {
          id: item.id,
          projectId: item.projectId,
          name: item.terminalName,
          terminalLogo: item.terminalLogo,
          formList: [
            {
              name: '项目名称',
              value: item.projectName
            }, {
              name: '终端编码',
              value: item.terminalCode
            }, {
              name: '终端类型',
              value: item.terminalType,
              type: 'selectKey',
              key: 'terminalType',
              optionLabel: 'dictLabel',
              optionValue: 'dictValue'
            }, {
              name: '终端简介',
              value: item.remark
            }, {
              name: '创建时间',
              value: item.createDatetime
            }
          ]
        }
      })
      response = Object.assign(response, { datalist })
      return response
    }
  }
}
</script>

<style lang="scss" scoped>
.terminal-manage {
  height: 100%;
}
.app-center {
  height: 100%;
 ::v-deep.fs-card__header {
   border: none;
 }
}
.list-content {
  padding: 12px;
  .fs-row > .fs-col:not(:nth-child(-n+3)) {
    margin-top: 16px;
  }
  &-item {
    background-color: #FFF;
    height: 222px;
    display: flex;
    flex-direction: column;
    &-info {
      height: calc(100% - 41px);
      overflow-y: auto;
      overflow-x: hidden;
      padding: 20px;
      display: flex;
      &__icon > img {
        width: 44px;
        height: 44px;
      }
      &__detail {
        width: calc(100% - 44px - 14px);
        margin-left: 14px;
        &__title {
          font-size: 18px;
          font-weight: 600;
          color: #333333;
          min-width: 80px;
        }
      }
    }
    &-btn {
      height: 41px;
      border-top: 1px dashed #E5E9EF;
      padding: 13px 0;
      display: flex;
      align-items: center;
      justify-content: space-around;
      font-size: 12px;
      .fs-button {
        width: 50%;
        color: #666666;
      }
      .fs-button:not(:first-child) {
        border-left: 1px solid #E5E9EF;
        margin-left: 0;
      }
    }
  }
}
</style>
