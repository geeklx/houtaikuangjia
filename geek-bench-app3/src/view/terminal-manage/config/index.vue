<template>
  <list-layout :topRightWidth="0">
    <div slot="top-header" class="title">{{ $route.meta.title }}</div>
    <div class="config-contain">
      <div class="config-contain-menu">
        <fs-menu
          v-if="treeData.length > 0"
          :default-active="terminalId"
          @select="menuSelect"
        >
          <fs-submenu v-for="sub in treeData" :key="sub.key" :index="sub.key">
            <template slot="title">
              <span>{{ sub.label }}</span>
            </template>
            <fs-menu-item
              v-for="item in sub.children"
              :key="item.key"
              :index="item.key"
            >
              <template slot="title">
                <span>{{ item.label }}</span>
              </template>
            </fs-menu-item>
          </fs-submenu>
        </fs-menu>
        <div v-else style="font-size: 14px;color: #999999;text-align: center;height: 100%;padding-top: 20px;width: 180px;">暂无数据</div>
      </div>
      <div class="config-contain-content">
        <div style="display: flex;justify-content: flex-end">
          <fs-button slot="search-right" type="primary" @click="save">保存</fs-button>
        </div>
        <div class="content-text">访问控制</div>
        <div class="content-form">
          <fs-form
            ref="validRef"
            size="mini"
            :rules="rules"
            :model="formData"
            label-width="100px"
            label-position="right"
          >
            <fs-form-item prop="ak" label="访问Key">
              <fs-input
                style="width: 372px"
                v-model="formData.ak"
                type="text"
                placeholder="请输入访问Key"
              />
            </fs-form-item>
            <fs-form-item prop="sk" label="访问Secret">
              <fs-input
                style="width: 372px"
                v-model="formData.sk"
                type="text"
                placeholder="请输入访问Secret"
              />
            </fs-form-item>
            <fs-form-item prop="version" label="认证版本">
              <fs-dictionary-select
                style="width: 372px"
                v-model="formData.version"
                dictionaryKey="authorizationVersion"
                optionLabel="dictLabel"
                optionValue="dictValue"
                clearable
                placeholder="请选择认证版本"
              />
            </fs-form-item>
          </fs-form>
        </div>
        <div class="content-text">运行配置</div>
        <div class="content-table">
          <fs-super-table
            class="workbench-table"
            :tableConfig="tableConfig"
            :paginationConfig="paginationConfig"
          />
          <detail-form :ref="createDialogRef"></detail-form>
        </div>
        <div class="content-text" style="margin-top: 16px">搜索知识分类( * 选填，配置统一搜索后填写)</div>
        <fs-super-table
          class="workbench-table"
          :tableConfig="tableConfig2"
          :paginationConfig="paginationConfig"
        >
          <template #switch="{row}">
            <fs-switch :value="row.statusType === 'ENABLE'" @change="e => statusUpdate(e, row)" />
          </template>
        </fs-super-table>
        <detail-form2 ref="detailForm2" @query="queryList2" />
        <!--<div class="content-pagination">
          <fs-pagination
            @size-change="onPageSizeChange"
            @current-change="onPageNumChange"
            :current-page="page.current"
            :page-sizes="pageSizes"
            :page-size="page.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.total">
          </fs-pagination>
        </div>-->
      </div>
    </div>
  </list-layout>
</template>

<script>
export default {
  $plugins: 'page',
  components: {
    DetailForm: () => import('./detail'),
    DetailForm2: () => import('./detail2')
  },
  data () {
    return {
      apiName: 'runConfig',
      queryMethod: 'query',
      initQuery: false,
      terminalId: '',
      pageSizes: [10, 20, 30, 40],
      paginationConfig: {
        show: false
      },
      formData: {},
      defaultData: {},
      rules: {
        ak: [
          { required: true, message: '请输入访问Key', trigger: 'blur' }
        ],
        sk: [
          { required: true, message: '请输入访问Secret', trigger: 'blur' }
        ],
        version: [
          { required: true, message: '请输入认证版本', trigger: 'blur' }
        ]
      },
      treeData: [],
      list2: []
    }
  },
  computed: {
    tableConfig () {
      const _this = this
      return {
        data: _this.page?.data,
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
            align: 'center',
            label: '配置类型',
            prop: 'configTypeName',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '认证平台名称',
            prop: 'configPlatformName',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '描述',
            prop: 'remark',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '创建时间',
            prop: 'createDatetime',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '操作',
            width: '80px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showEdit(row.id)}>
                    编辑
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
    tableConfig2 () {
      const _this = this
      return {
        data: _this.list2,
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
            align: 'center',
            label: '知识分类名称',
            prop: 'knowledgeCategoryName',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '知识分类编码',
            prop: 'knowledgeCategoryCode',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '描述',
            prop: 'knowledgeCategoryRemark',
            showOverflowTooltip: true
          },
          {
            align: 'center',
            label: '状态',
            width: '80px',
            slotName: 'switch'
          },
          {
            align: 'center',
            label: '操作',
            width: '80px',
            render (h, { row }) {
              return (
                <div>
                  <fs-button size='mini' onClick={() => _this.showEdit2(row.id)}>
                    编辑
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
    }
  },
  mounted () {
    // 查询项目-终端树
    this.configQuery()
  },
  watch: {
    terminalId: {
      handler () {
        this.queryHandler()
        this.queryList2()
        this.queryForm()
      },
      deep: true
    }
  },
  methods: {
    // 统一搜索配置编辑
    showEdit2 (id) {
      this.showCreate({
        ref: 'detailForm2',
        id
      })
    },
    // 查询统一搜索
    queryList2 () {
      this.$api('runConfig', 'queryThirdConfig', {
        terminalId: this.terminalId,
        configType: 'unisearch'
      }).then(res => {
        const { success, message, datalist } = res
        if (!success) {
          this.$message.error(message)
          return
        }
        this.$set(this, 'list2', datalist)
      })
    },

    // 状态更新
    statusUpdate (e, row) {
      const { id } = row
      this.$api('runConfig', 'thirdConfigStatus', {
        id,
        statusType: e ? 'ENABLE' : 'DISABLE'
      }).then(res => {
        const { success, message } = res
        if (success) {
          this.queryList2()
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
    },
    dealList (list) {
      let obj = {}
      if (list && list.length > 0) {
        list.forEach(item => {
          const { configCode, configValue } = item
          const obj_ = {}
          obj_[configCode] = configValue
          obj = Object.assign(obj, obj_)
        })
      }
      return obj
    },
    async save (val) {
      await this.$refs.validRef.validate(async valid => {
        if (valid) {
          const params = Object.assign({}, this.formData)
          this.$api('terminalmanage', 'configSave', {
            terminalId: this.terminalId,
            authorization: params
          }).then(res => {
            const { success, message } = res
            if (success) {
              this.$message.success('更新成功')
              this.defaultData = Object.assign({}, this.formData)
            } else {
              this.$message.error(message)
            }
          })
        } else {
          if (val === '1') {
            this.$message.error('必填项为空，无法进行保存!')
            this.$refs.validRef.clearValidate()
          }
        }
      })
    },
    getQueryCondition (params = {}) {
      return Object.assign({}, params, {
        terminalId: this.terminalId
      })
    },
    configQuery () {
      this.$api('runConfig', 'queryTree', {}).then(res => {
        const { datalist } = res
        let tree = []
        datalist.forEach(item => {
          const obj = {
            key: item.projectId,
            label: item.projectName,
            children: []
          }
          const { terminalTrees } = item
          if (terminalTrees && terminalTrees.length > 0) {
            terminalTrees.forEach(it => {
              obj.children = obj.children.concat([{
                key: it.terminalId,
                label: it.terminalName
              }])
            })
          }
          tree = tree.concat([obj])
        })
        const [info] = tree
        const { children: [children] } = info
        this.$set(this, 'treeData', tree)
        this.terminalId = children.key
        this.queryHandler()
        this.queryForm()
      })
    },
    queryForm () {
      this.$api('terminalmanage', 'configQuery', {
        terminalId: this.terminalId
      }).then(res => {
        const { data } = res
        const { authorization } = data
        if (authorization) {
          const info = this.dealList(authorization)
          this.$set(this, 'formData', info)
          this.defaultData = Object.assign({}, info)
        } else {
          this.$set(this, 'formData', {})
          this.defaultData = {}
        }
      })
    },
    menuSelect (index) {
      const flag = this.isObjStringEqual(this.defaultData, this.formData)
      if (!flag) {
        // 是否需要保存
        this.$confirm('信息已修改，是否需要保存?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          await this.save('1')
          this.terminalId = index
        }).catch(() => {
          this.terminalId = index
        })
      } else {
        this.terminalId = index
      }
    },
    isObjStringEqual (obj1, obj2) {
      return JSON.stringify(obj1) === JSON.stringify(obj2)
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep.fs-menu-item {
  height: 42px;
  line-height: 42px;
  border: none;
  padding-right: 16px;
  display: inline-block;
  white-space: nowrap;
  &.is-active {
    color: $themeColor;
    background-color: $themeBackground;
    border: none;
  }
  &:active, &:hover, &:focus {
    color: $themeColor;
    background-color: $themeBackground;
    border: none;
  }
}
::v-deep.fs-submenu {
  & > .fs-submenu__title:hover, & > .fs-submenu__title:focus {
    color: $themeColor;
    background-color: $themeBackground;
  }
}
.config-contain {
  width: 100%;
  height: 100%;
  padding: 0 0 0 12px;
  display: flex;
  &-menu {
    height: 100%;
    width: 230px;
    .fs-menu {
      height: 100%;
      width: 100%;
      overflow-x: hidden;
      padding-top: 12px;
    }
  }
  ::v-deep.fs-submenu {
    &.is-active {
      .fs-submenu__title {
        color: $themeColor !important;
        .svg-icon {
          color: $themeColor !important;
        }
      }
    }
    &__title {
     display: flex;
     align-items: center;
     > span {
       white-space: normal;
       word-break: break-all;
       line-height: 20px;
       flex: 1;
       padding-right: 20px;
     }
    }
  }
  ::v-deep.fs-menu-item {
    display: flex;
    align-items: center;
    padding-right: 20px;
    > span {
      white-space: normal;
      word-break: break-all;
      line-height: 20px;
      flex: 1;
    }
  }
  &-content {
    width: calc(100% - 230px);
    height: 100%;
    padding: 12px;
    overflow-x: hidden;
    overflow-y: auto;
    & > .content-text {
      font-size: 14px;
      color: #333333;
      font-weight: 600;
      margin-bottom: 16px;
    }
    & > .content-pagination {
      height: 60px;
      padding: 0 20px;
      background-color: #F9F9F9;
      display: flex;
      justify-content: flex-end;
      align-items: center;
    }
  }
}
</style>
