<template>
  <div class="pubnum">
    <span>共{{ totalelements }}条，{{ currentPage }}/{{ totalpages }}页</span>
    <span>首页</span>
    <span>
      <div class="page_icon" @click="firstChange">
        <svg-icon icon-class="first_page" class="first_page"></svg-icon>
      </div>
    </span>
    <span>
      <fs-pagination
        style="float: right; margin-top: 10px"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pagesize"
        layout="prev, pager, next"
        :total="totalelements"
      >
      </fs-pagination>
    </span>
    <span>
      <div class="page_icon" @click="lastChange">
        <svg-icon icon-class="last_page" class="first_page"></svg-icon>
      </div>
    </span>
    <span>尾页</span>
  </div>
</template>
<script>
export default {
  name: 'pagination',
  props: {
    totalelements: Number,
    currentPage: Number,
    totalpages: Number,
    pagesize: Number,
    querySourceList: {
      type: Function,
      default: null
    }
  },
  data () {
    return {
      current: this.currentPage,
      topage: this.pagesize
    }
  },
  methods: {
    handleCurrentChange (currentPage) {
      this.$emit('currentPage', currentPage)
      this.current = currentPage
      this.querySourceList()
    },
    firstChange () {
      this.$emit('fristPage', 1)
      this.current = 1
      this.querySourceList()
    },
    lastChange () {
      this.$emit('lastPage', this.totalpages)
      this.current = this.totalpages
      this.querySourceList()
    },
    handleSizeChange (size) {
      this.pagesize = size
      this.querySourceList()
    }
  }
}
</script>
<style lang="scss" scoped>
.pubnum {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 15px;
  color: #606266;
  span:nth-child(1),
  span:nth-child(2),
  span:nth-child(3),
  span:nth-child(5),
  span:nth-child(6) {
    height: 42px;
    padding-top: 12px;
    line-height: 30px;
    margin-right: 10px;
  }
  span:nth-child(4) {
    margin-right: 10px;
  }
  .page_icon {
    width: 30px;
    height: 28px;
    border: 1px solid #f4f4f5;
    text-align: center;
    line-height: 24px;
    cursor: pointer;
    &:hover {
      border: 1px solid #df2a2a;
    }
    .first_page {
      font-size: 12px;
    }
  }
}
</style>
