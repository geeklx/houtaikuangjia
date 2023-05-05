<template>
  <list-layout :topRightWidth="0">
    <div slot="top-header" class="title">{{ $route.meta.title }}</div>

    <fs-form
      inline
      slot="searchForm"
      class="search-form"
      :model="formData"
      label-width="80px"
      @submit.native.prevent
      size="mini"
    >
      <fs-form-item prop="projectId" label="项目名称">
        <fs-dictionary-select
          v-model="formData.projectId"
          url="/api/project/basic/query/option"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          filterable
          :disabled="$route.query.projectId && $route.query.projectId !== ''"
          :isCache="false"
          placeholder="请选择项目"
          @change="projectChange"
        />
      </fs-form-item>
      <fs-form-item prop="terminalId" label="选择终端">
        <fs-select
          v-model="formData.terminalId"
          placeholder="请选择终端"
          clearable
          filterable
          :disabled="$route.query.terminalId && $route.query.terminalId !== ''"
          @change="terminalChange"
        >
          <fs-option
            v-for="item in terminalList"
            :key="item.id"
            :label="item.terminalName"
            :value="item.id">
          </fs-option>
        </fs-select>
      </fs-form-item>
      <fs-form-item prop="appName" label="搜索应用">
        <fs-input
          type="text"
          v-model="formData.appName"
          clearable
          placeholder="请输入应用名称"
        />
      </fs-form-item>
    </fs-form>
    <template slot="searchButton">
      <fs-button type="primary" plain @click="queryHandler">查询</fs-button>
    </template>
    <div class="bin">
      <div class="bin-content">
        <fs-tabs v-model="formData.categoryCode" @tab-click="appChange">
          <fs-tab-pane
            v-for="item in appList"
            :label="item.categoryName"
            :name="item.categoryCode"
            :key="item.categoryCode"
          >
          </fs-tab-pane>
        </fs-tabs>
        <div class="bin-list">
          <div class="bin-list-item" v-for="item in list" :key="item.id">
            <div class="bin-list-item-checkbox">
              <fs-checkbox v-model="item.checked" :disabled="item.disabled"></fs-checkbox>
            </div>
            <div class="bin-list-item-icon">
              <img :src="item.iconUrl" />
            </div>
            <div class="bin-list-item-content">
              <div class="content-title">
                <span class="content-title__app_name">{{ item.appName }}</span>
                <div class="content-title__app_type">{{ item.appTypes_dict === 'app原生' ? 'APP原生' : item.appTypes_dict === 'h5' ? 'H5' : item.appTypes_dict }}</div>
                <span class="content-title__app_version">版本号{{ item.versionNum }}</span>
              </div>
              <div class="content-remark">
                <div>{{ item.remark }}</div>
                <div>更新时间： {{ item.lastUpdateDatetime }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="bin-bottom">
        <span>已选({{checkoutList.length}})</span>
        <fs-button type="primary" @click="bindSubmit">绑定</fs-button>
      </div>
    </div>
  </list-layout>
</template>

<script>
export default {
  components: {},
  data () {
    return {
      formData: {},
      terminalList: [],
      appList: [],
      list: [],
      checkoutList: []
    }
  },
  watch: {
    list: {
      handler (val) {
        const i = []
        val.forEach(item => {
          const { checked, disabled } = item
          checked && !disabled && (i.push(item))
        })
        this.$set(this, 'checkoutList', i)
      },
      deep: true
    }
  },
  mounted () {
    const { projectId, terminalId } = this.$route.query
    if (projectId && terminalId) {
      this.$set(this.formData, 'projectId', projectId)
      this.queryTerminalList(projectId)
      this.$set(this.formData, 'terminalId', terminalId)
      this.queryAppList()
    }
  },
  methods: {
    bindSubmit () {
      if (this.checkoutList.length === 0) {
        this.$message.warning('未选择应用！')
        return
      }
      const { projectId, terminalId } = this.formData
      let arr = []
      arr = this.checkoutList.map(item => {
        const { id, appId } = item
        return {
          appId,
          projectId,
          terminalId,
          appVersionId: id
        }
      })
      this.$api('bindApply', 'bind', arr).then(res => {
        const { success, message } = res
        if (success) {
          this.$message.success('绑定成功！')
          this.queryAppList()
        } else {
          this.$message({
            type: 'error',
            message
          })
        }
      })
    },
    appChange () {
      this.queryList()
    },
    projectChange (val) {
      this.$delete(this.formData, 'terminalId')
      this.$set(this, 'terminalList', [])
      if (val) {
        this.queryTerminalList(val)
      }
    },
    terminalChange (val) {
      if (val) {
        this.queryAppList()
      }
    },
    queryAppList () {
      const { projectId, terminalId } = this.formData
      this.$api('bindApply', 'queryCategory', { projectId, terminalId }).then(res => {
        const { datalist } = res
        const [data_] = datalist
        this.appList = [].concat(datalist)
        this.$set(this.formData, 'categoryCode', data_.categoryCode)
        this.queryList()
      })
    },
    queryTerminalList (val) {
      this.$api('bindApply', 'queryTerminalList', { projectId: val }).then(res => {
        const { datalist } = res
        this.terminalList = [].concat(datalist)
      })
    },
    queryHandler () {
      const _formData = Object.assign({}, this.formData)
      if (!_formData.projectId || _formData.projectId === '' || !_formData.terminalId || _formData.terminalId === '') {
        this.$message.warning('选择项目和终端后点击查询')
        return
      }
      this.queryAppList()
    },
    queryList () {
      const _formData = Object.assign({}, this.formData)
      if (_formData.categoryCode === 'all') _formData.categoryCode = null
      this.$api('bindApply', 'query', _formData).then(res => {
        let { datalist } = res
        datalist = datalist.map(item => {
          const { checked } = item
          item.disabled = checked || false
          item.checked = checked || false
          return item
        })
        this.list = [].concat(datalist)
      })
    },
    resetQueryCondition () {
      this.$set(this, 'formData', {})
    }
  }
}
</script>

<style scoped lang="scss">
.bin {
  height: 100%;
  width: 100%;
  .bin-content {
    padding: 14px 14px 0;
    height: calc(100% - 48px - 50px);
    & > .bin-list {
      height: calc(100% - 54px);
      overflow-y: auto;
      & > .bin-list-item {
        width: 100%;
        height: 90px;
        border-bottom: 1px solid #F0F2F7;
        padding: 18px;
        display: flex;
        align-items: center;
        & > .bin-list-item-icon {
          margin-left: 16px;
          & > img {
            width: 54px;
            height: 54px;
          }
        }
        & > .bin-list-item-content {
          width: calc(100% - 100px);
          margin-left: 10px;
          & > .content-title {
            display: flex;
            align-items: center;
            & > .content-title__app_name {
              font-size: 16px;
              font-weight: 600;
            }
            & > .content-title__app_type {
              margin-left: 10px;
              border: 1px solid $themeColor;
              border-radius: 9px;
              padding: 2px 8px;
              font-size: 12px;
              color: $themeColor;
            }
            & > .content-title__app_version {
              margin-left: 10px;
              font-size: 16px;
              color: #999999;
            }
          }
          & > .content-remark {
            margin-top: 10px;
            width: 100%;
            font-size: 12px;
            color: #999999;
            display: flex;
            justify-content: space-between;
          }
        }
      }
    }
  }
  & > .bin-bottom {
    height: 50px;
    background: #F9F9F9;
    display: flex;
    justify-content: flex-end;
    padding: 0 16px;
    align-items: center;
    & > span {
      font-size: 14px;
      color: #999999;
      margin-right: 10px;
    }
  }
}
</style>
