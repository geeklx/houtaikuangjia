<template>
  <div class="enjoy-config">
    <div class="enjoy-config__btns">
      <fs-button type="primary" @click="save">保存</fs-button>
    </div>
    <div class="enjoy-config__content">
      <fs-form
        inline
        slot="searchForm"
        class="search-form"
        :rules="rules"
        :model="formData"
        label-width="100px"
        @submit.native.prevent
        size="mini"
      >
        <fs-row>
          <fs-col :span="24">
            <fs-form-item prop="userShelvesRange" label="用户范围:">
              <fs-select-tree
                style="width: 642px"
                v-if="showSelectTree"
                v-model="formData.userShelvesRange"
                :treeConfig="treeConfigUser"
                clearable
                multiple
                placeholder="请选择用户范围"
                @change="userShelvesRangeChange"
              ></fs-select-tree>
              <fs-select
                style="width: 642px"
                v-else
                v-model="formData.userShelvesRange"
                placeholder="请选择用户范围"
                clearable
              ></fs-select>
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="identityShelvesRange" label="身份范围:">
              <fs-dictionary-select
                style="width: 642px"
                v-model="formData.identityShelvesRange"
                url="/api/application/query/identity"
                optionLabel="dictLabel"
                optionValue="dictValue"
                clearable
                multiple
                :isCache="false"
                placeholder="请选择身份范围"
              />
            </fs-form-item>
          </fs-col>
          <fs-col :span="24">
            <fs-form-item prop="orgShelvesRange" label="组织范围:">
              <fs-select-tree
                style="width: 642px"
                v-if="showSelectTree"
                v-model="formData.orgShelvesRange"
                :treeConfig="treeConfig"
                clearable
                multiple
                placeholder="请选择组织范围"
                @change="orgShelvesRangeChange"
              ></fs-select-tree>
              <fs-select
                style="width: 642px"
                v-else
                v-model="formData.orgShelvesRange"
                placeholder="请选择组织范围"
                clearable
              ></fs-select>
            </fs-form-item>
          </fs-col>
          <!--<fs-col :span="24">
            <fs-form-item prop="areaShelvesRange" label="地域范围:">
              <fs-dictionary-select
                style="width: 372px"
                v-model="formData.areaShelvesRange"
                url="/api/application/query/regional"
                optionLabel="dictLabel"
                optionValue="dictLabel"
                clearable
                placeholder="请选择地域范围"
              />
            </fs-form-item>
          </fs-col>-->
        </fs-row>
      </fs-form>
    </div>
  </div>
</template>

<script>
import { SelectTree } from 'fs-select-tree'
export default {
  $plugins: 'form',
  components: {
    FsSelectTree: SelectTree
  },
  props: {
    terminalId: {
      type: String
    },
    id: {
      type: String
    }
  },
  data () {
    return {
      rules: {},
      defaultData: {},
      defaultUserExpandedKeys: [],
      defaultOrgExpandedKeys: [],
      showSelectTree: false
    }
  },
  computed: {
    treeConfig () {
      return {
        props: {
          value: 'id',
          label: 'name',
          isLeaf: (data) => data.hasChildren === '0'
        },
        lazy: true,
        leafOnly: false,
        expandOnClickNode: false,
        defaultExpandedKeys: this.defaultOrgExpandedKeys,
        httpConfig: {
          // 请求url
          url: '/api/application/query/organ',
          // 请求类型
          type: 'postJson',
          // 请求参数
          params: this.getParentId,
          // 响应数据字段
          responseField: 'datalist'
        }
      }
    },
    treeConfigUser () {
      return {
        props: {
          value: 'code',
          label: 'name',
          disabled: (data) => data.type === 0,
          isLeaf: (data) => data.type === 1
        },
        lazy: true,
        // leafOnly: false,
        expandOnClickNode: false,
        defaultExpandedKeys: this.defaultUserExpandedKeys,
        httpConfig: {
        // 请求url
          url: '/api/application/query/user',
          // 请求类型
          type: 'postJson',
          // 请求参数
          params: this.getParams,
          // 响应数据字段
          responseField: 'datalist'
        }
      }
    }
  },
  mounted () {
    this.configQuery()
  },
  methods: {
    // beforeCreateHandler () {
    //   this.formData.orgShelvesRange = []
    // },
    getParams (data) {
      const { id, hasChildren } = data.data
      return {
        terminalId: this.terminalId,
        parentId: id || '0',
        requestType: hasChildren === '0' ? '1' : '0'
      }
    },
    getParentId (data) {
      const { id } = data.data
      return {
        terminalId: this.terminalId,
        parentId: id || '0'
      }
    },
    userShelvesRangeChange (val, allKeys) {
      this.$set(this.formData, 'userShelvesRangesParents', allKeys.join(','))
    },
    orgShelvesRangeChange (val, allKeys) {
      this.$set(this.formData, 'orgShelvesRangeParents', allKeys.join(','))
    },
    configQuery () {
      this.$api('terminalapply', 'authorConfigGet', {
        id: this.id
      }).then(res => {
        const { data } = res
        const { identity, user, org } = data
        const org_ = this.getConfig(org)
        const orgShelvesRangeParents = this.getExpanded(org)

        const user_ = this.getConfig(user)
        const userShelvesRangesParents = this.getExpanded(user)

        const identity_ = this.getConfig(identity)

        this.formData = Object.assign({}, {
          identityShelvesRange: identity_,
          userShelvesRange: user_,
          orgShelvesRange: org_
        })
        this.defaultData = {
          identityShelvesRange: identity_,
          userShelvesRange: user_,
          orgShelvesRange: org_
        }
        if (userShelvesRangesParents) this.defaultUserExpandedKeys = userShelvesRangesParents
        if (orgShelvesRangeParents) this.defaultOrgExpandedKeys = orgShelvesRangeParents
        this.showSelectTree = true
      })
    },
    getConfig (list) {
      const list_ = list ? list.map(item => {
        return item.shelvesRange
      }) : []
      return list_
    },
    getExpanded (list) {
      if (list) {
        let list_ = []
        const [parents] = list
        const { shelvesRangeParents } = parents
        if (shelvesRangeParents) (list_ = [].concat(shelvesRangeParents.split(',')))
        return list_
      }
      return []
    },
    getForm () {
      return {
        obj: this.formData,
        obj_: this.defaultData
      }
    },
    async save () {
      const params = Object.assign(this.formData, {
        terminalId: this.terminalId,
        appConfigId: this.id
      })
      await this.$api('terminalapply', 'authorConfigSave', params).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('更新成功')
          this.defaultData = Object.assign({}, this.formData)
        } else {
          this.$message.error(message)
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.enjoy-config {
  height: 100%;
  &__btns {
    width: 100%;
    padding-bottom: 12px;
    display: flex;
    justify-content: flex-end;
  }
  &__content {
    height: calc(100% - 36px);
    overflow-y: auto;
  }
  ::v-deep.fs-form-item__label {
    min-width: 100px;
  }
  ::v-deep.fs-form-item {
    display: flex;
  }
}
::v-deep.fs-tree-select {
  & > .fs-select > .fs-select__tags {
    overflow-x: auto;
  }
}
</style>
