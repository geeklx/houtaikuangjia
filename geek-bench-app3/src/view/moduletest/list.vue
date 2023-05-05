<template>
  <fs-card id="page-list">
    <list-layout>
      <div slot="top-header" class="title">列表</div>

      <!-- 查询条件 -->
      <fs-form
        slot="searchForm"
        inline
        class="search-form"
        :model="queryCondition"
        label-width="60px"
        @submit.native.prevent
        size="mini"
      >
        <fs-form-item prop="name" label="作者">
          <fs-input
            type="text"
            v-model="queryCondition.author"
            clearable
            style="width: 180px"
            placeholder="请输入作者"
          />
        </fs-form-item>
        <fs-form-item prop="idCard" label="标题">
          <fs-input
            type="text"
            v-model="queryCondition.title"
            clearable
            style="width: 180px"
            placeholder="请输入标题"
          />
        </fs-form-item>
      </fs-form>
      <!-- 按钮 -->
      <template slot="searchButton">
        <!--
            page 内置查询函数queryHandler
            查询条件会读取this.queryCondition中的值
          -->
        <fs-button type="primary" @click="queryHandler">查询</fs-button>
        <!--
            page 内置重置查询条件函数 resetQueryCondition
            可传参, 默认 true（重置查询条件并更新列表）；false(重置查询条件不更新列表)
          -->
        <fs-button type="primary" plain @click="resetQueryCondition"
          >重置</fs-button
        >
      </template>
      <fs-button slot="search-right" type="primary" plain @click="showCreate()"
        >新建</fs-button
      >
      <fs-button
        slot="search-right"
        type="primary"
        plain
        @click="deleteHandler(multipleSelection)"
        >批量删除</fs-button
      >

      <template slot="searchMore">
        <fs-form
          slot="searchForm"
          inline
          class="search-form"
          :model="queryCondition"
          label-width="60px"
          @submit.native.prevent
          size="mini"
        >
          <fs-form-item prop="name" label="作者2">
            <fs-input
              type="text"
              v-model="queryCondition.author"
              clearable
              style="width: 180px"
              placeholder="请输入作者"
            />
          </fs-form-item>
          <fs-form-item prop="idCard" label="标题2">
            <fs-input
              type="text"
              v-model="queryCondition.title"
              clearable
              style="width: 180px"
              placeholder="请输入标题"
            />
          </fs-form-item>
        </fs-form>
      </template>

      <!-- 表格 -->
      <tree-table
        slot="table"
        :data="page.data"
        lazy
        @selection-change="handleSelectionChange"
      >
        <fs-table-column
          align="center"
          type="selection"
          width="55"
        ></fs-table-column>
        <fs-table-column
          align="center"
          type="index"
          :index="indexMethod"
          label="序号"
          width="80"
        ></fs-table-column>
        <fs-table-column
          align="center"
          show-overflow-tooltip
          label="作者"
          prop="author"
        ></fs-table-column>
        <fs-table-column
          show-overflow-tooltip
          label="标题"
          prop="title"
        ></fs-table-column>
        <fs-table-column
          align="center"
          show-overflow-tooltip
          label="综合浏览量"
          prop="pageviews"
        ></fs-table-column>
        <fs-table-column
          align="center"
          show-overflow-tooltip
          label="状态"
          prop="status"
        >
          <template #default="{ row }">
            {{ row.status === "published" ? "已发布" : "未发布" }}
          </template>
        </fs-table-column>
        <fs-table-column align="center" label="创建时间">
          <template #default="{ row }">
            {{ row.display_time | formatDate("yyyy-MM-dd hh:mm:ss") }}
          </template>
        </fs-table-column>
        <fs-table-column align="center" label="操作">
          <template #default="{ row }">
            <!--
              弹窗形式打开详情页
              params：id外的其他参数
              新建：showCreate(params)、编辑、showEdit(id, params)、查看（只读）：showDetail(id, params)
            -->
            <fs-button class="in-table-button" @click="showDetail(row.id)">
              查看
            </fs-button>
            <fs-button class="in-table-button" @click="showEdit(row.id)">
              编辑
            </fs-button>
            <!--
              跳转页面形式打开详情页
              params：id外的其他参数
              新建：toCreate(params)、编辑、toEdit(id, params)、查看（只读）：toDetail(id, params)
            -->
            <!-- <fs-button class="in-table-button" @click="toEdit(row.id)">
              编辑
            </fs-button> -->
            <fs-button class="in-table-button" @click="deleteHandler(row)">
              删除
            </fs-button>
          </template>
        </fs-table-column>
      </tree-table>

      <!-- 分页 -->
      <fs-pagination
        slot="page"
        background
        layout="total, prev, pager, next"
        :total="page.total"
        :page-size="page.pageSize"
        :current-page.sync="page.pageNum"
        @current-change="onPageNumChange"
        @size-change="onPageSizeChange"
      ></fs-pagination>
      <defail-form :ref="createDialogRef"></defail-form>
    </list-layout>
  </fs-card>
</template>

<script>
export default {
  $plugins: 'page', // 此处是关键，必须包含page才可以！！！

  components: {
    'defail-form': require('./edit').default
  },

  data () {
    return {
      apiName: 'post',
      queryMethod: 'getDictionary', // 获取列表数据接口名称
      deleteMethod: 'delete', // 删除操作接口名称
      /**
       * 设置默认条数需要修改
       * 设置分页起始索引需要在main.js内全局配置, 示例：
       * fosungFront.httpBuilder().httpParams()
          .requestParam({
            // 当前分页号
            pageNum: 'pageNum',
            // 每页记录最大数
            pageSize: 'pageSize',
            // 分页开始索引
            pageNumStartIndex: 0
          })
       */
      defaultQueryPageSize: 10, // 默认每页分页条数
      multipleSelection: [] // 批量选择
    }
  },
  methods: {
    // 动态计算序号
    indexMethod (index) {
      return (this.page.pageNum - 1) * this.page.pageSize + index + 1
    },
    // 批量选择
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 默认请求参数处理，可以将查询条件之外的其他参数返回给框架
    getDefQueryCondition () {
      return {
        orgId: '12333'
      }
    }
    /**
     * 常用内置函数
     */
    // //删除之前的回调，检测是否可以继续执行删除操作
    // deleteBefore (_item, ..._params) {
    //   return true;
    // },
    // //删除成功之后的回调
    // deleteAfter (_item, ..._params) { },
    // // 查询之前的处理
    // queryBeforeHandler () { },
    // // 查询成功时处理
    // querySuccessHandler (_responseInfo) { },
    // // 查询异常时处理
    // queryErrorHandler (_errorResponse) { },
    // // 查询之后的处理，不管查询是否执行成功了
    // queryAfterHandler () { }
    // //页码改变时的处理
    // onPageNumChange (pageNum) { },
    // //每页条目数改变时的处理
    // onPageSizeChange (pageSize) { },

    /**
     * 不建议覆盖的默认方法
     */
    // //获取分页查询条件
    // getQueryPageCondition() {}
  }
}
</script>

<style lang="scss" scoped>
#page-list {
  height: 100%;
}
</style>
