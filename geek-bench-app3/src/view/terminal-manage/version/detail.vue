<template>
  <fs-dialog
    class="dialog-workbench"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
    :visible.sync="visible"
    :title="operateType === 'new' ? '添加发布包' : operateType === 'detail' ? '发布包详情' : '编辑发布包'"
    @closed="close"
    width="702px"
  >
    <fs-form
      :ref="validRef"
      :rules="rules"
      :model="formData"
      label-width="120px"
      label-position="right"
    >
      <fs-form-item label="项目名称:" prop="projectId">
        <fs-dictionary-select
          style="width: 372px"
          v-model="formData.projectId"
          url="/api/project/basic/query/option"
          optionLabel="dictLabel"
          optionValue="dictValue"
          clearable
          filterable
          :isCache="false"
          placeholder="请选择项目"
          :disabled="readonly"
          @change="projectChange"
        />
      </fs-form-item>
      <fs-form-item label="终端名称:" prop="terminalId">
        <fs-select
          style="width: 372px"
          v-model="formData.terminalId"
          placeholder="请选择终端"
          clearable
          filterable
          :disabled="readonly"
        >
          <fs-option
            v-for="item in terminalList"
            :key="item.id"
            :label="item.terminalName"
            :value="item.id">
          </fs-option>
        </fs-select>
      </fs-form-item>
      <fs-form-item label="终端图标:" prop="terminalLogoUrl">
        <fs-upload
          style="width: 372px"
          class="workbench-upload"
          :action="uploadAction"
          :show-file-list="false"
          :on-success="handleSuccess"
          :before-upload="beforeUpload"
          :disabled="readonly"
        >
          <div v-if="formData.terminalLogoUrl" class="workbench-upload__operate">
            <div class="icons">
              <i class="fs-icon-edit"></i>
              <i class="fs-icon-delete" @click="handleRemove"></i>
            </div>
          </div>
          <img v-if="formData.terminalLogoUrl" :src="formData.terminalLogoUrl" class="workbench-upload__avatar" />
          <div v-else class="workbench-upload__icon">
            <img src="@/view/png/add-image.png" />
            <div>上传图标</div>
          </div>
        </fs-upload>
        <div style="width: 372px" class="workbench-upload-text" v-show="!readonly">PNG、JPEG、JPG、SVG格式,图标建议尺寸：56*56px,如图标较小,需周围留白</div>
      </fs-form-item>
      <fs-form-item label="安装包:" prop="installationPackage">
        <fs-upload
          style="width: 372px"
          class="dialog-form-upload"
          :disabled="readonly"
          :action="uploadAction"
          :limit="1"
          :on-remove="handleRemove1"
          :before-upload="beforeUpload1"
          :on-success="handleSuccess1"
          :file-list="formData.fileList"
        >
          <fs-button v-if="!readonly" size="small" type="primary" plain><i class="fs-icon-upload" />点击上传</fs-button>
          <fs-button v-else size="small" type="primary" plain @click="e => downloadFile(e, formData.fileList)"><i class="fs-icon-upload" />点击下载</fs-button>
          <div v-show="!readonly" slot="tip" class="dialog-form-upload__tip">支持格式：.apk .hap .app .aab .ipa,最多上传一个</div>
        </fs-upload>
      </fs-form-item>
      <fs-form-item label="版本名称:" prop="versionName">
        <fs-input
          style="width: 372px"
          v-model="formData.versionName"
          type="text"
          disabled
        />
      </fs-form-item>
      <fs-form-item label="版本号:" prop="terminalVersion" required>
        <fs-input
          style="width: 372px"
          disabled
          type="text"
          v-model="formData.terminalVersion"
        />
      </fs-form-item>
      <fs-form-item label="安装包MD5:" prop="installationPackageMd5">
        <fs-input
          style="width: 372px"
          v-model="formData.installationPackageMd5"
          type="text"
          placeholder="请输入安装包MD5"
          :disabled="readonly"
        />
      </fs-form-item>
      <fs-form-item label="应用范围:" prop="upgradeRangeType">
        <fs-radio-group
          v-model="formData.upgradeRangeType"
          :disabled="readonly"
          @change="upgradeRangeTypeChange"
        >
          <fs-radio label="all">全部</fs-radio>
          <fs-radio label="assigned">指定范围</fs-radio>
        </fs-radio-group>
      </fs-form-item>
      <fs-form-item label="选择范围:" prop="upgradeRangeValues" v-if="formData.upgradeRangeType === 'assigned'">
        <fs-select
          style="width: 372px"
          clearable
          multiple
          v-model="formData.upgradeRangeValues"
          placeholder="请选择范围"
          :disabled="readonly"
        >
          <fs-option
            v-for="(item, index) in upgradeRangeValuesList"
            :key="index"
            :label="item.dictLabel"
            :value="item.dictLabel">
          </fs-option>
        </fs-select>
      </fs-form-item>
      <fs-form-item label="更新标题:" prop="upgradeTitle">
        <fs-input
          style="width: 372px"
          maxlength="32"
          v-model="formData.upgradeTitle"
          type="text"
          placeholder="请输入更新标题"
          :disabled="readonly"
        />
      </fs-form-item>
      <fs-form-item label="更新背景:" prop="upgradeBackImg">
        <fs-upload
          style="width: 372px"
          class="workbench-upload"
          :action="uploadAction"
          :show-file-list="false"
          :on-success="handleSuccess2"
          :before-upload="beforeUpload"
          :disabled="readonly"
        >
          <div v-if="formData.upgradeBackImg" class="workbench-upload__operate">
            <div class="icons">
              <i class="fs-icon-edit"></i>
              <i class="fs-icon-delete" @click="handleRemove2"></i>
            </div>
          </div>
          <img v-if="formData.upgradeBackImg" :src="formData.upgradeBackImg" class="workbench-upload__avatar" />
          <div v-else class="workbench-upload__icon">
            <img src="@/view/png/add-image.png" />
            <div>上传背景</div>
          </div>
        </fs-upload>
        <div style="width: 372px" class="workbench-upload-text" v-show="!readonly">PNG、JPEG、JPG、SVG格式,图标建议尺寸：56*56px,如图标较小,需周围留白</div>
      </fs-form-item>
      <fs-form-item label="更新提示:" prop="upgradePrompt">
        <fs-radio-group v-model="formData.upgradePrompt" :disabled="readonly">
          <fs-radio :label="true">是</fs-radio>
          <fs-radio :label="false">否</fs-radio>
        </fs-radio-group>
      </fs-form-item>
      <fs-form-item label="更新说明:" prop="remark" v-if="formData.upgradePrompt">
        <fs-input
          style="width: 372px"
          v-model="formData.remark"
          :autosize="{ minRows: 3 }"
          type="textarea"
          placeholder="请输入更新说明"
          :disabled="readonly"
        />
      </fs-form-item>
      <fs-form-item label="强制升级:" prop="forceUpgrade">
        <fs-radio-group v-model="formData.forceUpgrade" :disabled="readonly">
          <fs-radio :label="true">是</fs-radio>
          <fs-radio :label="false">否</fs-radio>
        </fs-radio-group>
      </fs-form-item>
    </fs-form>

    <span slot="footer" class="dialog-footer" v-if="!readonly">
      <fs-button @click="close">取 消</fs-button>
      <fs-button type="primary" @click="submitHandler">
        确 定
      </fs-button>
    </span>
  </fs-dialog>
</template>

<script>
import axios from 'axios'
import { isEmpty, downloadByUrl } from 'fosung-sdk'

export default {
  $plugins: 'form', // 此处是关键,必须包含form才可以！！！
  data () {
    return {
      apiName: 'terminalversion',
      queryMethod: 'get',
      submitMethod: 'save',
      rules: {
        projectId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        terminalId: [
          { required: true, message: '请选择终端', trigger: 'change' }
        ],
        terminalLogoUrl: [
          { required: true, message: '请上传图标', trigger: 'change' }
        ],
        versionName: [
          { required: true, message: '版本名称为空', trigger: 'blur' }
        ],
        terminalVersion: [
          { required: true, message: '版本号为空', trigger: 'blur' }
        ],
        installationPackage: [
          { required: true, message: '请上传安装包', trigger: 'change' }
        ],
        installationPackageMd5: [
          { required: true, message: '请输入安装包MD5', trigger: 'blur' }
        ],
        upgradeRangeType: [
          { required: true, message: '请选择应用范围', trigger: 'change' }
        ],
        upgradeRangeValues: [
          { required: true, message: '请选择范围', trigger: 'change' }
        ],
        upgradePrompt: [
          { required: true, message: '请选择更新提示', trigger: 'change' }
        ],
        upgradeTitle: [
          { required: true, message: '请输入更新标题', trigger: 'blur' }
        ],
        // upgradeBackImg: [
        //   { required: true, message: '请上传更新背景', trigger: 'change' }
        // ],
        remark: [
          { required: true, message: '请输入更新说明', trigger: 'blur' }
        ],
        forceUpgrade: [
          { required: true, message: '请选择是否强制更新', trigger: 'change' }
        ]
      },
      upgradeRangeValuesList: [],
      terminalList: []
    }
  },
  computed: {
    uploadAction () {
      const { storeInstance } = window.appContext
      const { ossUrl } = storeInstance.getters.configInfo
      return ossUrl || ''
    }
  },
  methods: {
    downloadFile (e, v) {
      e.stopPropagation()
      e.preventDefault()
      const url = v[0].url
      downloadByUrl({ url })
    },
    upgradeRangeTypeChange (val) {
      this.$delete(this.formData, 'upgradeRangeValues')
      this.$set(this, 'upgradeRangeValuesList', [])
      if (val && val === 'assigned') this.queryUpgradeRangeValuesList()
    },
    getResponseInfo (response) {
      let { data } = response
      const { upgradeRangeType, upgradeRangeValues, projectId, installationPackage, fileName } = data
      data = Object.assign(data, {
        fileList: [{
          name: fileName,
          url: installationPackage
        }]
      })
      this.queryTerminalList(projectId)
      if (upgradeRangeType === 'assigned') {
        this.queryUpgradeRangeValuesList()
        data.upgradeRangeValues = upgradeRangeValues.map(item => {
          return item.upgradeRangeName
        })
      }
      return response
    },
    projectChange (val) {
      this.$delete(this.formData, 'terminalId')
      this.$set(this, 'terminalList', [])
      if (val) {
        this.queryTerminalList(val)
      }
    },
    queryTerminalList (val) {
      this.$api('bindApply', 'queryTerminalList', { projectId: val }).then(res => {
        const { datalist } = res
        this.terminalList = [].concat(datalist)
      })
    },
    queryUpgradeRangeValuesList () {
      this.$api('terminalversion', 'queryUpgradeRangeValuesList').then(res => {
        const { success, datalist } = res
        if (success) this.$set(this, 'upgradeRangeValuesList', datalist)
      })
    },
    handleRemove1 () {
      this.$delete(this.formData, 'installationPackage')
      this.$delete(this.formData, 'terminalVersion')
      this.$delete(this.formData, 'versionName')
      this.$delete(this.formData, 'appSize')
      this.$delete(this.formData, 'fileName')
    },
    handleSuccess1 (res, file) {
      const { data } = res
      this.$set(this.formData, 'installationPackage', data.url)
      this.$set(this.formData, 'appSize', file.size)
      this.$set(this.formData, 'fileName', file.name)
      this.$refs[this.validRef].clearValidate('installationPackage')
    },
    beforeUpload1 (file) {
      let valid = null
      this.$refs.validRef.validateField('terminalId', (valid_) => {
        valid = valid_
      })
      if (valid === '') {
        const name = file.name
        const index = name.lastIndexOf('.')
        const ext = name.substr(index + 1)
        if (ext === 'apk' || ext === 'hap' || ext === 'app' || ext === 'aab' || ext === 'ipa') {
          const formInfo = new FormData()
          formInfo.append('file', file)
          formInfo.append('terminalId', this.formData.terminalId)
          axios.post('/api/application/version/parse/apk', formInfo, {
            headers: {
              'content-Type': 'multipart/form-data'
            },
            timeout: 300000
          }).then(res => {
            const { success, message, data } = res.data
            if (success) {
              const { versionCode, versionName } = data
              this.$set(this.formData, 'terminalVersion', versionCode)
              this.$set(this.formData, 'versionName', versionName)
              this.$refs[this.validRef].clearValidate(['terminalVersion', 'versionName'])
            } else {
              this.$delete(this.formData, 'installationPackage')
              this.$delete(this.formData, 'terminalVersion')
              this.$delete(this.formData, 'versionName')
              this.$delete(this.formData, 'appSize')
              this.$delete(this.formData, 'fileName')
              this.$message.error(message)
            }
          })
          return true
        }
        this.$message.error('请上传.apk、.hap、.app、.aab、.ipa格式!')
        return false
      } else {
        return false
      }
    },
    handleSuccess2 (res) {
      const { data } = res
      this.$set(this.formData, 'upgradeBackImg', data.url)
      // this.$refs[this.validRef].clearValidate('upgradeBackImg')
    },
    handleRemove2 (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'upgradeBackImg')
    },
    handleSuccess (res) {
      const { data } = res
      this.$set(this.formData, 'terminalLogoUrl', data.url)
      this.$refs[this.validRef].clearValidate('terminalLogoUrl')
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/svg+xml'
      if (!isJPG) {
        this.$message.error('上传图片只能是 PNG、JPEG、JPG、SVG 格式!')
      }
      return isJPG
    },
    handleRemove (e) {
      e.stopPropagation()
      e.preventDefault()
      this.$delete(this.formData, 'terminalLogoUrl')
    },
    getSubmitData () {
      const obj = Object.assign({}, this.formData)
      if (!isEmpty(obj)) {
        const { upgradeRangeType, upgradeRangeValues } = obj
        if (upgradeRangeType === 'all') {
          obj.upgradeRangeValues = []
        } else {
          let arr = []
          const _this = this
          arr = upgradeRangeValues.map(item => {
            const info = _this.upgradeRangeValuesList.find(ite => {
              return ite.dictLabel === item
            })
            return {
              upgradeRangeName: info.dictLabel,
              upgradeRangeValue: info.dictValue
            }
          })
          obj.upgradeRangeValues = arr
        }
      }
      return obj
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
