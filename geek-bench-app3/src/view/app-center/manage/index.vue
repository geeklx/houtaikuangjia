<template>
  <list-li-layout class="app-center" :topRightWidth="120">
    <div slot="top-header" class="title-no-border">{{ $route.meta.title }}</div>
    <!-- 查询条件 -->
    <fs-form slot="searchForm" inline class="search-form" :model="queryCondition" label-width="80px" @submit.native.prevent size="mini">
      <fs-form-item prop="appName" label="应用名称">
        <fs-input
          type="text"
          v-model="queryCondition.appName"
          clearable
          placeholder="请输入应用名称"
        />
      </fs-form-item>
      <fs-form-item prop="appCode" label="应用编码">
        <fs-input
          type="text"
          v-model="queryCondition.appCode"
          clearable
          placeholder="请输入应用编码"
        />
      </fs-form-item>
      <fs-form-item prop="appType" label="应用类型">
        <fs-dictionary-select
          v-model="queryCondition.appType"
          dictionaryKey="appTypeSource"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择应用类型"
        />
      </fs-form-item>
      <fs-form-item prop="categoryCode" label="应用类别">
        <fs-dictionary-select
          v-model="queryCondition.categoryCode"
          dictionaryKey="appCategory"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          placeholder="请选择应用类别"
        />
      </fs-form-item>
    </fs-form>
    <!-- 按钮 -->
    <template slot="searchButton">
      <fs-button type="primary" plain @click="queryHandler">查询</fs-button>
      <fs-button plain @click="resetQueryCondition">重置</fs-button>
    </template>
    <fs-button slot="search-right" type="primary" @click="createForm"><i class="fs-icon-plus"></i>创建应用</fs-button>
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
          <fs-col :span="6" v-for="(item, index) in page.data" :key="index">
            <div class="list-content-item">
              <div class="list-content-item-info">
                <div class="list-content-item-info__icon">
                  <img v-if="item.iconUrl" :src="item.iconUrl">
                  <img v-else src="../png/appcenter-list.png">
                </div>
                <div class="list-content-item-info__detail">
                  <div class="list-content-item-info__detail__title">{{item.name}}</div>
                  <pro-item :basic-form="item.formList" :label-width="60" style="margin-top: 18px"></pro-item>
                </div>
              </div>
              <div class="list-content-item-btn">
                <fs-button type="text" @click="editForm(item.id)">编辑</fs-button>
                <fs-button type="text" @click="deleteHandler([{ id: item.id }])">删除</fs-button>
              </div>
            </div>
          </fs-col>
        </fs-row>
      </div>
      <empty v-else></empty>
    </template>
    <template slot="list-footer">
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
    <detail-form ref="detailFormRef" @query="queryHandler" />
  </list-li-layout>
</template>

<script>
export default {
  $plugins: 'page',
  components: {
    DetailForm: () => import('./detailDrawer')
  },
  data () {
    return {
      apiName: 'appcentermanage',
      queryMethod: 'query', // 获取列表数据接口名称
      deleteMethod: 'delete',
      defaultQueryPageSize: 8,
      pageSizes: [8, 16, 24, 32]
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
    getResponseInfo (response) {
      let { datalist } = response
      datalist = datalist.map(item => {
        return {
          id: item.id,
          name: item.appName,
          iconUrl: item.iconUrl,
          formList: [
            {
              name: '应用编码',
              value: item.appCode
            }, {
              name: '应用类型',
              value: item.appType,
              type: 'selectKey',
              key: 'appTypeSource',
              optionLabel: 'dictLabel',
              optionValue: 'dictValue'
            }, {
              name: '应用类别',
              value: item.categoryCode,
              type: 'selectKey',
              key: 'appCategory',
              optionLabel: 'dictLabel',
              optionValue: 'dictValue'
            }, {
              name: '应用简介',
              value: item.remark
            }, {
              name: '应用来源',
              value: item.dataSource === 'workbench' ? (item.inOrOut ? (item.appType === 'h5' ? '内部H5' : '内部原生') : (item.appType === 'h5' ? '外部H5' : '第三方原生')) : item.dataSource_dict
            }, {
              name: '创建时间',
              value: item.createDatetime
            }
          ]
        }
      })
      response = Object.assign(response, { datalist })
      return response
    },
    createForm () {
      this.$refs.detailFormRef.open('创建应用')
    },
    editForm (id) {
      this.$refs.detailFormRef.open('编辑应用', id)
    }
  }
}
</script>

<style lang="scss" scoped>
.app-center {
 ::v-deep.fs-card__header {
   border: none;
 }
}
.list-content {
  padding: 12px;
  .fs-row > .fs-col:not(:nth-child(-n+4)) {
    margin-top: 16px;
  }
  &-item {
    background-color: #FFF;
    height: 250px;
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
