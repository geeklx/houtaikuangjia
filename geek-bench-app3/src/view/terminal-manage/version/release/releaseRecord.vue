<template>
  <fs-super-table
    class="workbench-table"
    :tableConfig="tableConfig"
    :paginationConfig="paginationConfig"
  />
</template>

<script>
export default {
  $plugins: 'list',
  props: {
    versionId: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'terminalversion',
      queryMethod: 'updatequery', // 获取列表数据接口名称
      paginationConfig: {
        show: false
      }
    }
  },
  computed: {
    tableConfig () {
      const _this = this
      return {
        data: _this.list,
        column: [
          {
            align: 'center',
            label: '序号',
            type: 'index',
            width: '60'
          },
          {
            align: 'center',
            label: '发布详情',
            prop: 'updateContent',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '发布时间',
            width: '180',
            prop: 'createDatetime',
            showOverflowTooltip: true
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
    // 默认请求参数处理，可以将查询条件之外的其他参数返回给框架
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        terminalVersionId: this.versionId
      })
    }
  }
}
</script>

<style scoped>

</style>
