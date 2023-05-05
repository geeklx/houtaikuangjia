<template>
  <div class="top-nav-config">
    <div class="top-nav-config__btns">
      <fs-button type="primary" @click="showCreate({terminalId: id, area: areaName})"><i class="fs-icon-plus"></i>新建导航</fs-button>
    </div>
    <div class="top-nav-config__content">
      <fs-super-table
        class="workbench-table"
        :tableConfig="tableConfig"
        :paginationConfig="paginationConfig"
      />
      <detail-form :ref="createDialogRef"></detail-form>
      <fs-dialog
        class="dialog-workbench"
        append-to-body
        destroy-on-close
        :visible.sync="sortVisible"
        :close-on-click-modal="false"
        title="导航排序"
        @closed="closeSort"
        width="702px"
      >
        <fs-super-table
          class="nav-sort-table workbench-table"
          :tableConfig="sortConfig"
          :paginationConfig="paginationConfig"
        >
          <template #sort="{row}">
            <div>
              <svg-icon
                icon-class="up"
                class="up"
                @click="up(row.num)"
              />
              <svg-icon
                style="margin-left: 12px"
                icon-class="down"
                class="down"
                @click="down(row.num)"
              />
            </div>
          </template>
        </fs-super-table>
        <span slot="footer" class="dialog-footer">
        <fs-button @click="closeSort">取 消</fs-button>
        <fs-button type="primary" @click="submitSort">确 定</fs-button>
      </span>
      </fs-dialog>
    </div>
    <div class="top-nav-config__edit" v-if="currRow">
      <nav-edit-config :navigationId="currRow.id"></nav-edit-config>
    </div>
    <div v-else></div>
  </div>
</template>

<script>
export default {
  $plugins: 'list',
  name: 'TopNavConfig',
  components: {
    DetailForm: () => import('./form/TopNavConfigDetail'),
    NavEditConfig: () => import('./components/NavEditConfig')
  },
  props: {
    id: {
      type: String
    },
    areaName: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'topnavconfig',
      queryMethod: 'query',
      paginationConfig: {
        show: false
      },
      initQuery: false,

      sortVisible: false,
      sortList: [],
      formData: {},
      currRow: null
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
            width: '60',
            index: _this.indexMethod,
            showOverflowTooltip: true
          },
          {
            label: '导航名称',
            prop: 'topNavigationName',
            showOverflowTooltip: true
          },
          {
            label: '关联底部菜单',
            prop: 'btmNavigationName',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            width: '210px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.editDetail(row)}>
                    维护
                  </fs-button>
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id, { terminalId: _this.id, area: _this.areaName })}>
                    编辑
                  </fs-button>
                  <fs-button size='mini' onclick= {() => _this.deleteHandler([{ id: row.id }])}>
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
    },
    sortConfig () {
      const _this = this
      return {
        data: _this.sortList,
        column: [
          {
            align: 'center',
            label: '序号',
            type: 'index',
            width: '60',
            index: _this.indexMethod,
            showOverflowTooltip: true
          }, {
            label: '导航名称',
            prop: 'navigationName',
            showOverflowTooltip: true
          }, {
            align: 'center',
            label: '排序',
            width: '120px',
            slotName: 'sort'
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
  watch: {
    areaName: {
      handler (val) {
        if (val) {
          this.queryHandler()
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    editDetail (row) {
      this.$set(this, 'currRow', row)
    },
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        terminalId: this.id,
        area: this.areaName
      })
    },
    navSort () {
      const arr = JSON.parse(JSON.stringify(this.list))
      this.sortList = [].concat(this.numReset(arr))
      this.sortVisible = true
    },
    closeSort () {
      this.sortList = []
      this.sortVisible = false
    },
    submitSort () {
      const params = this.sortList.map(item => {
        let { id, num } = item
        return {
          id,
          num: ++num
        }
      })
      this.$api('navconfig', 'sortsave', params).then(res => {
        const { success, message } = res
        if (success) {
          this.queryHandler()
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
      this.closeSort()
    },
    numReset (arr) {
      arr = arr.map((item, index) => {
        item.num = index
        return item
      })
      return arr
    },
    up (index) {
      const array = JSON.parse(JSON.stringify(this.sortList))
      if (index === 0) return false
      // 将上一个数组元素值替换为当前元素值，并将被替换的元素值赋值给当前元素
      array[index] = array.splice(index - 1, 1, array[index])[0]
      this.sortList = [].concat(this.numReset(array))
    },
    down (index) {
      const array = JSON.parse(JSON.stringify(this.sortList))
      if (index === array.length - 1) return false
      // 将下一个数组元素值替换为当前元素值，并将被替换的元素值赋值给当前元素
      array[index] = array.splice(index + 1, 1, array[index])[0]
      this.sortList = [].concat(this.numReset(array))
    },
    statusUpdate (id, status) {
      const params = {
        id,
        status
      }
      this.$api('navconfig', 'save', params).then(res => {
        const { success, message } = res
        if (success) {
          this.queryHandler()
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
    },
    deleteAfter (item) {
      const [id_] = item
      const { id } = id_
      if (this.currRow.id === id) this.$set(this, 'currRow', null)
    }
  }
}
</script>

<style scoped lang="scss">
.top-nav-config {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  &__btns {
    width: 100%;
    padding-bottom: 12px;
    display: flex;
    justify-content: flex-end;
  }
  &__content {
    //height: calc(100% - 36px);
    //overflow-y: auto;
  }
  &__edit {
    margin-top: 20px;
    width: 100%;
  }
}
</style>
