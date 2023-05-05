<template>
<div
  class="show-info"
  :class="labelWidth ? `label__${this.labelWidth}` : ''"
>
  <div
    v-for="(item, index) in basicForm"
    :key="index"
    class="show-info-item"
  >
    <div class="show-info-item__label">{{item.name}}：</div>
    <div class="show-info-item__value">
      <template v-if="item.type === 'selectKey'">
        <fs-dictionary-label
          v-model="item.value"
          :dictionaryKey="item.key"
          :optionLabel="item.optionLabel"
          :optionValue="item.optionValue"
        >
        </fs-dictionary-label>
      </template>
      <template v-else-if="item.type === 'selectUrl'">
        <fs-dictionary-label
          v-model="item.value"
          :url="item.url"
          :optionLabel="item.optionLabel"
          :optionValue="item.optionValue"
        >
        </fs-dictionary-label>
      </template>
      <template v-else-if="item.type === 'selectOption'">
        <fs-dictionary-label
          v-model="item.value"
          :options="item.options"
          :optionLabel="item.optionLabel"
          :optionValue="item.optionValue"
        >
        </fs-dictionary-label>
      </template>
      <template v-else-if="item.type === 'editor'">
        <fs-ace-editor
          v-model="item.value"
          lang="text"
          readonly
          :height="item.height"
        />
      </template>
      <template v-else-if="item.type === 'switch'">
        {{item.value ? '是' : '否'}}
      </template>
      <template v-else>
        <fs-tooltip
          effect="dark"
          :disabled="!tooltipsShow.includes(item.name)"
          placement="right"
        >
          <div slot="content" v-html="setBr(item.value)"></div>
          <div v-showTips:showTips="item" class="show-info-item__value__tip">
            {{ item.value }}
          </div>
        </fs-tooltip>
      </template>
    </div>
  </div>
</div>
</template>

<script>
import { AceEditor } from 'fs-ace-editor'
import showTips from '../assets/directive/showTips'

export default {
  name: 'ProItem',
  components: {
    FsAceEditor: AceEditor
  },
  directives: {
    showTips: showTips
  },
  data () {
    return {
      tooltipsShow: []
    }
  },
  props: {
    basicForm: {
      type: Array
    },
    labelWidth: {
      type: Number
    }
  },
  methods: {
    setBr (val) {
      if (val) {
        for (let i = val.length; i > -1; i--) {
          if (i !== 0 && i % 40 === 0) {
            val = val.slice(0, i) + '<br/>' + val.slice(i)
          }
        }
      }
      return val
    },
    showTips (data, immediatelyShowTips = true) {
      if (immediatelyShowTips && !this.tooltipsShow.includes(data.name)) {
        this.tooltipsShow.push(data.name)
      }
    }
  }
}
</script>

<style scoped lang="scss">
@import "styles/index";
</style>
