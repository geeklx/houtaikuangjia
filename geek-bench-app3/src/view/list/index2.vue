<template>
  <list-layout>
    <div slot="top-header" class="title">普通列表</div>

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
          placeholder="请输入作者"
        />
      </fs-form-item>
      <fs-form-item prop="idCard" label="标题">
        <fs-input
          type="text"
          v-model="queryCondition.title"
          clearable
          placeholder="请输入标题"
        />
      </fs-form-item>
    </fs-form>

    <!-- 右上角 -->
    <fs-button slot="search-right" type="primary" plain @click="toCreate"
      >新建</fs-button
    >
    <fs-button
      slot="search-right"
      type="primary"
      plain
      @click="deleteHandler(multipleSelection)"
      >批量删除</fs-button
    >

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

    <!-- 表格 -->
    <tree-table
      slot="table"
      :data="list"
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
        :index="(index) => index + 1"
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
          <fs-button class="in-table-button" @click="toDetail(row.id)">
            查看
          </fs-button>
          <fs-button class="in-table-button" @click="toEdit(row.id)">
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

    <defail-form ref="editDialogRef"></defail-form>
  </list-layout>
</template>

<script>
export default {
  $plugins: 'list', // 此处是关键，必须包含list才可以！！！

  components: {
    'defail-form': require('./components/dialog').default
  },

  data () {
    return {
      apiName: 'post',
      queryMethod: 'getDictionary', // 列表查询接口名称
      deleteMethod: 'delete', // 删除操作接口名称
      list: [], // 列表数据
      createPageName: 'addForm', // 新增页面name
      editPageName: 'addForm', // 编辑页面name
      detailPageName: 'addForm', // 详情页面name

      multipleSelection: [] // 批量选择
    }
  },
  methods: {
    // 批量选择
    handleSelectionChange (val) {
      this.multipleSelection = val
    }
  }
}
</script>
