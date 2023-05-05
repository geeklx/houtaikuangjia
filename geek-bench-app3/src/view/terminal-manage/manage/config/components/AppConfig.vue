<template>
  <div class="app-config">
    <div class="fs-flex fs-row-between">
      <fs-form
        inline
        class="search-form"
        :model="queryCondition"
        label-width="80px"
        @submit.native.prevent
      >
        <fs-form-item prop="categoryType" label="分类类型">
          <fs-dictionary-select
            v-model="queryCondition.categoryType"
            dictionaryKey="categoryType"
            optionLabel="dictLabel"
            optionValue="dictValue"
            clearable
            placeholder="请选择分类类型"
          />
        </fs-form-item>
        <fs-form-item prop="area" label="地域">
          <fs-dictionary-select
            v-model="queryCondition.area"
            url="/api/application/query/regional"
            optionLabel="dictLabel"
            optionValue="dictLabel"
            clearable
            placeholder="请选择地域"
          />
        </fs-form-item>
        <fs-form-item>
          <fs-button type="primary" plain @click="queryHandler">查询</fs-button>
        </fs-form-item>
      </fs-form>
      <div class="app-config__btns">
        <fs-button type="primary" @click="appSort"><i class="fs-icon-sort"></i>分类排序</fs-button>
        <fs-button type="primary" @click="showCreate({terminalId: id})"><i class="fs-icon-plus"></i>新建分类</fs-button>
      </div>
    </div>
    <div class="app-config__content">
      <fs-super-table
        class="workbench-table"
        :tableConfig="tableConfig"
        :paginationConfig="paginationConfig"
      >
        <template #switch="{row}">
          <fs-switch v-model="row.status" @change="e => statusUpdate(e, row)"></fs-switch>
        </template>
      </fs-super-table>
      <detail-form :ref="createDialogRef"></detail-form>
      <fs-dialog
        class="dialog-workbench"
        append-to-body
        destroy-on-close
        :visible.sync="sortVisible"
        :close-on-click-modal="false"
        title="分类排序"
        @closed="closeSort"
        width="702px"
      >
        <fs-super-table
          class="app-sort-table workbench-table"
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
      <!--<transfer
        ref="transfer"
        title="管理应用"
        :visible="visible"
        :leftData="leftData"
        :rightData="rightData"
        :extraData="extraData"
        :trans-config="transConfig"
        @close="transferClose"
      >
        <div slot="content-extra">
          <fs-checkbox v-model="extraData.disabledNum"></fs-checkbox>
          <span style="font-size: 12px;color: #333333;margin-left: 8px">已选应用列表排序不允许修改</span>
        </div>
      </transfer>-->
      <fs-dialog
        class="dialog-workbench"
        width="702px"
        title="管理应用"
        append-to-body
        destroy-on-close
        :visible.sync="visible"
        :lock-scroll="false"
        :close-on-click-modal="false"
        @close="transferClose"
      >
        <div class="content">
          <div class="content-trans">
            <fs-transfer
              v-model="right"
              :data="leftData"
              filterable
              target-order=""
              :titles="['待选列表', '已选列表']"
              :transConfig="transConfig"
            >
              <div class="order-title fs-flex fs-col-center fs-row-between" slot-scope="{ option }">
                <div>{{ option.label }}</div>
                <div v-if="right.includes(option.key)">
                  <svg-icon
                    icon-class="up"
                    class="up"
                    @click="e => upRight(option.key, e)"
                  />
                  <svg-icon
                    :style="{ marginLeft: '4px' }"
                    icon-class="down"
                    class="down"
                    @click="e => downRight(option.key, e)"
                  />
                </div>
              </div>
            </fs-transfer>
          </div>
          <div class="content-extra">
            <fs-checkbox v-model="extraData.disabledNum"></fs-checkbox>
            <span style="font-size: 12px;color: #333333;margin-left: 8px">已选应用列表排序不允许修改</span>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <fs-button @click="transferClose">取 消</fs-button>
          <fs-button type="primary" @click="submitHandler">
            确 定
          </fs-button>
        </span>
      </fs-dialog>
    </div>
  </div>
</template>

<script>
export default {
  $plugins: 'list',
  name: 'AppConfig',
  components: {
    DetailForm: () => import('./form/AppConfigDetail')
  },
  props: {
    id: {
      type: String
    }
  },
  data () {
    return {
      apiName: 'appconfig',
      queryMethod: 'query',
      paginationConfig: {
        show: false
      },

      sortVisible: false,
      sortList: [],

      visible: false,
      row: '',
      leftData: [],
      rightData: [],
      right: [],
      transConfig: {
        method: 'appSave',
        params: {}
      },
      extraData: {}
    }
  },
  watch: {
    row: {
      handler (val) {
        const { code, terminalId, type } = val
        this.$set(this.transConfig, 'params', {
          terminalId,
          categoryType: type,
          categoryCode: code
        })
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
            align: 'center',
            label: '序号',
            type: 'index',
            width: '60',
            index: _this.indexMethod,
            showOverflowTooltip: true
          },
          {
            label: '分类类型',
            prop: 'type',
            showOverflowTooltip: true,
            render (h, { row }) {
              return (
                <fs-dictionary-label
                  value={row.type}
                  dictionaryKey="categoryType"
                  optionLabel="dictLabel"
                  optionValue="dictValue"
                />
              )
            }
          },
          {
            label: '分类名称',
            prop: 'name',
            showOverflowTooltip: true
          },
          {
            label: '地域',
            prop: 'area',
            showOverflowTooltip: true
          },
          {
            label: '底部导航',
            prop: 'navigationBtmName',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '状态',
            prop: 'status',
            width: '80',
            showOverflowTooltip: true,
            slotName: 'switch'
          },
          {
            align: 'center',
            label: '排序',
            prop: 'num',
            width: '60',
            showOverflowTooltip: true
          },
          {
            label: '描述说明',
            prop: 'remark',
            showOverflowTooltip: true
          },
          {
            label: '操作',
            width: '240px',
            render (h, { row }) {
              const ALL = row.type === 'all'
              const ROUTINE = row.type === 'routine'
              const REGIONAL = row.type === 'regional'
              const STATUS = row.status
              return (
                <div>
                  {!ALL ? <fs-button size='mini' onClick={() => _this.managerConfig(row)}>
                    管理应用
                  </fs-button> : ''}
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id, { terminalId: _this.id })}>
                    编辑
                  </fs-button>
                  {(ROUTINE || REGIONAL) && !STATUS ? <fs-button size='mini' onclick= {() => _this.deleteHandler([{ id: row.id }])}>
                    删除
                  </fs-button> : ''}
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
            label: '分类名称',
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
    upRight (key, e) {
      e.preventDefault()
      e.stopPropagation()
      const array = JSON.parse(JSON.stringify(this.right))
      let num = 0
      array.find((item, index) => {
        item === key && (num = index)
        return item === key
      })
      if (num === 0) return false
      array[num] = array.splice(num - 1, 1, array[num])[0]
      this.right = [].concat(array)
    },
    downRight (key, e) {
      e.preventDefault()
      e.stopPropagation()
      const array = JSON.parse(JSON.stringify(this.right))
      let num = 0
      array.find((item, index) => {
        item === key && (num = index)
        return item === key
      })
      if (num === array.length - 1) return false
      array[num] = array.splice(num + 1, 1, array[num])[0]
      this.right = [].concat(array)
    },
    submitHandler () {
      if (this.right === this.rightData) {
        this.transferClose()
        return
      }
      let params_ = [].concat(this.right)
      const { method, params } = this.transConfig
      params_ = params_.map((item, index) => {
        return {
          configId: item,
          ...params,
          num: index + 1
        }
      })
      this.$api('transconfig', method, {
        ...this.extraData,
        ...params,
        list: params_
      }).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('提交成功！')
          this.transferClose()
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
    },
    beforeCreateHandler () {
      this.extraData.disabledNum = false
    },
    managerConfig (row) {
      const { code, terminalId, type } = row
      this.row = row
      this.$api('appconfig', 'manageApp', {
        terminalId,
        categoryType: type,
        categoryCode: code
      }).then(res => {
        const { success, data } = res
        if (success) {
          let { checked, uncheck } = data
          checked = checked.map(item => {
            const { id } = item
            return id
          })
          uncheck = uncheck.map((item) => {
            const { id, appName } = item
            return {
              key: id,
              label: appName
            }
          })
          this.rightData = [].concat(checked)
          this.right = [].concat(checked)
          this.leftData = [].concat(uncheck)
          this.$set(this.extraData, 'disabledNum', data.disabledNum)
          this.visible = true
        }
      })
    },
    statusUpdate (e, row) {
      const { id } = row
      this.row = row
      this.$api('appconfig', 'statusUpdate', {
        id,
        status: e
      }).then(res => {
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
    transferClose () {
      this.currId = ''
      this.visible = false
      this.extraData = {}
    },
    getQueryCondition (params = {}) {
      return Object.assign({}, params, this.queryCondition, {
        terminalId: this.id
      })
    },
    appSort () {
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
      this.$api('appconfig', 'sortsave', params).then(res => {
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
    }
  }
}
</script>

<style scoped lang="scss">
.app-config {
  width: 100%;
  height: 100%;
  padding: 12px;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  &__btns {
    padding-bottom: 12px;
    display: flex;
    justify-content: flex-end;
  }
  &__content {
    height: calc(100% - 36px);
    overflow-y: auto;
  }
}
.order-title {
  width: 200px;
  overflow-x: hidden;
  z-index: 1100;
  > div:first-child {
    width: calc(100% - 60px);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    //overflow: hidden;
    //text-overflow: ellipsis;
    //display: -webkit-box;
    //-webkit-line-clamp: 2;
    //-webkit-box-orient: vertical;
  }
  > div:nth-child(2) {
    width: 60px;
  }
}
</style>
