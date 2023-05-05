<template>
  <list-li-layout class="project" :topRightWidth="120">
    <div slot="top-header" class="title-no-border">{{ $route.meta.title }}</div>
    <!-- 查询条件 -->
    <fs-form slot="searchForm" inline class="search-form" :model="queryCondition" label-width="80px" @submit.native.prevent size="mini">
      <fs-form-item prop="projectName" label="项目名称">
        <fs-input
          type="text"
          v-model="queryCondition.projectName"
          clearable
          placeholder="请输入项目名称"
        />
      </fs-form-item>
      <fs-form-item prop="projectCode" label="项目编码">
        <fs-input
          type="text"
          v-model="queryCondition.projectCode"
          clearable
          placeholder="请输入项目编码"
        />
      </fs-form-item>
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
    <!-- 按钮 -->
    <template slot="searchButton">
      <fs-button type="primary" plain @click="queryHandler">查询</fs-button>
      <fs-button plain @click="resetQueryCondition">重置</fs-button>
    </template>
    <fs-button slot="search-right" type="primary" @click="showCreate"><i class="fs-icon-plus"></i>创建项目</fs-button>
    <template slot="list-content">
      <div class="list-content" v-if="page.data.length > 0">
        <fs-row :gutter="14">
          <fs-col :span="8" v-for="(item, index) in page.data" :key="index">
            <div class="list-content-item">
              <div class="list-content-item-info">
                <div class="list-content-item-info__icon">
                  <img v-if="item.icon" :src="item.icon">
                  <img v-else src="./png/project-list.png">
                </div>
                <div class="list-content-item-info__detail">
                  <div class="list-content-item-info__detail__title">{{item.name}}</div>
                  <pro-item :basic-form="item.formList" :label-width="60" style="margin-top: 18px"></pro-item>
                </div>
              </div>
              <div class="list-content-item-btn">
                <fs-button type="text" @click="managerConfig(item)">管理员配置</fs-button>
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
    <detail-form :ref="createDialogRef"></detail-form>
    <transfer
      ref="transfer"
      title="管理员配置"
      :visible="visible"
      :leftData="leftData"
      :rightData="rightData"
      :trans-config="transConfig"
      @close="transferClose"
    ></transfer>
  </list-li-layout>
</template>

<script>
export default {
  $plugins: 'page',
  components: {
    DetailForm: () => import('./detail')
  },
  data () {
    return {
      apiName: 'projectmanage',
      queryMethod: 'query', // 获取列表数据接口名称
      deleteMethod: 'delete',
      defaultQueryPageSize: 9,
      pageSizes: [9, 18, 27, 50],

      visible: false,
      currId: '',
      leftData: [],
      rightData: [],
      transConfig: {
        method: 'personSave',
        params: {}
      }
    }
  },
  watch: {
    currId: {
      handler (val) {
        this.$set(this.transConfig, 'params', {
          projectId: val
        })
      },
      deep: true
    }
  },
  methods: {
    managerConfig (row) {
      const { id } = row
      this.currId = id
      this.$api('projectmanage', 'queryPerson', { projectId: id }).then(res => {
        const { success, data } = res
        if (success) {
          let { checked, uncheck } = data
          checked = checked.map(item => {
            const { userId } = item
            return userId
          })
          uncheck = uncheck.map(item => {
            const { userId, userName } = item
            return {
              key: userId,
              label: userName
            }
          })
          this.rightData = [].concat(checked)
          this.leftData = [].concat(uncheck)
          this.visible = true
        }
      })
    },
    transferClose () {
      this.currId = ''
      this.visible = false
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
          name: item.projectName,
          formList: [
            {
              name: '项目编码',
              value: item.projectCode
            }, {
              name: '负责人',
              value: item.person
            }, {
              name: '项目说明',
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
.project {
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
    height: 200px;
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
