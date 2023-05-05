<template>
  <div class="edit-config">
    <div class="edit-config__title">导航维护</div>
    <fs-form
      style="margin-top: 16px"
      ref="validRef"
      :model="formData"
      label-width="75px"
      label-position="right"
    >
      <fs-form-item label="数据定义:" prop="dataDefine">
        <fs-dictionary-select
          style="width: 372px"
          v-model="formData.dataDefine"
          dictionaryKey="menuType"
          optionLabel="dictLabel"
          optionValue="dictValue"
          placeholder="请选择数据定义"
        />
      </fs-form-item>
    </fs-form>
    <div class="edit-config__btns">
      <fs-button type="primary" @click="editSort('')"><i class="fs-icon-sort"></i>排序</fs-button>
      <fs-button type="primary" @click="showCreate({navigationId: navigationId, parentId: navigationId})"><i class="fs-icon-plus"></i>新建父级菜单</fs-button>
    </div>
    <fs-super-table
      class="edit-config__table workbench-table"
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
      title="排序"
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
</template>

<script>
export default {
  $plugins: 'list',
  name: 'NavEditConfig',
  components: {
    DetailForm: () => import('./form/NavEditConfigDetail')
  },
  props: {
    navigationId: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'topnavconfig',
      queryMethod: 'getTreeList',
      deleteMethod: 'editDelete',
      paginationConfig: {
        show: false
      },
      formData: {
        dataDefine: 'custom'
      },
      sortVisible: false,
      sortList: []
    }
  },
  watch: {
    navigationId: {
      handler (val) {
        if (val) this.queryHandler()
      },
      deep: true
    }
  },
  computed: {
    tableConfig () {
      const _this = this
      return {
        data: _this.list,
        column: [
          {
            label: '序号',
            align: 'center',
            width: '70',
            prop: 'indexNum',
            // type: 'index',
            // index: _this.indexMethod,
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '菜单名称',
            prop: 'name',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '链接地址',
            prop: 'url',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '排序',
            width: '60',
            prop: 'num',
            showOverflowTooltip: true
          },
          {
            label: '操作',
            width: '320px',
            render (h, { row }) {
              const ISCHILDREN = row.isChildren
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id, { navigationId: _this.navigationId })}>
                    编辑
                  </fs-button>
                  {!ISCHILDREN ? <fs-button size='mini' onClick={() => _this.showCreate({
                    navigationId: _this.navigationId,
                    parentId: row.id
                  })}>
                    新建下级
                  </fs-button> : null}
                  {!ISCHILDREN ? <fs-button size='mini' onClick={() => _this.editSort(row.id)}>
                    下级排序
                  </fs-button> : null}
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
            border: true,
            rowKey: 'id',
            expandRowKeys: _this.expandRowKeys
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
            label: '菜单名称',
            prop: 'name',
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
  methods: {
    editSort (val) {
      let chil = JSON.parse(JSON.stringify(this.list))
      if (val !== '') {
        const row = this.list.find(item => {
          return item.id === val
        })
        chil = JSON.parse(JSON.stringify(row.children))
      }
      this.sortList = [].concat(this.numReset(chil))
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
      this.$api('topnavconfig', 'editSort', params).then(res => {
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

    getResponseInfo (response) {
      if (response.datalist.length > 0) {
        let num = 1
        response.datalist = response.datalist.map(item => {
          item.indexNum = num++
          item.isChildren = false
          if (item.children && item.children.length > 0) {
            item.children = item.children.map(ite => {
              ite.isChildren = true
              return ite
            })
          }
          return item
        })
      }
      return response
    },
    beforeCreateHandler () {
    },
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        navigationId: this.navigationId
      })
    }
  }
}
</script>

<style scoped lang="scss">
.edit-config {
  width: 100%;
  display: flex;
  flex-direction: column;
  &__btns {
    width: 100%;
    display: flex;
    justify-content: flex-end;
  }
  &__title {
    font-size: 14px;
    padding-bottom: 8px;
    border-bottom: 1px solid #F0F2F7;
  }
  &__table {
    margin-top: 16px;
  }
}
</style>
