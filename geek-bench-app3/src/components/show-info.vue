<template>
<div
  class="show-info"
  :class="labelWidth ? `label__${this.labelWidth}` : ''"
>
  <div
    v-for="(item, index) in basicForm"
    :key="index"
    class="show-info__item"
  >
    <div class="show-info__item__label">{{item.name}}：</div>
    <div class="show-info__item__value">
      <div v-if="item.type === 'selectKey'">
        <fs-dictionary-label
          v-model="item.value"
          :dictionaryKey="item.key"
          :optionLabel="item.optionLabel"
          :optionValue="item.optionValue"
        >
        </fs-dictionary-label>
      </div>
      <div v-else-if="item.type === 'selectUrl'">
        <fs-dictionary-label
          v-model="item.value"
          :url="item.url"
          :optionLabel="item.optionLabel"
          :optionValue="item.optionValue"
        >
        </fs-dictionary-label>
      </div>
      <div v-else-if="item.type === 'selectOption'">
        <fs-dictionary-label
          v-model="item.value"
          :options="item.options"
          :optionLabel="item.optionLabel"
          :optionValue="item.optionValue"
        >
        </fs-dictionary-label>
      </div>
      <div v-else-if="item.type === 'editor'">
        <fs-ace-editor
          v-model="item.value"
          lang="text"
          readonly
          :height="item.height"
        />
      </div>
      <div v-else-if="item.type === 'switch'">
        {{item.value ? '是' : '否'}}
      </div>
      <div v-else-if="item.type === 'textarea'">
        <fs-input
          readonly
          autosize
          type="textarea"
          v-model="item.value"
        />
      </div>
      <div v-else-if="item.type === 'icon'">
        <img :src="item.value" />
      </div>
      <div v-else-if="item.type === 'richText'">
        <div v-html="item.value"></div>
      </div>
      <div v-else>{{item.value}}</div>
    </div>
  </div>
</div>
</template>

<script>
import { AceEditor } from 'fs-ace-editor'
export default {
  name: 'ShowInfo',
  components: {
    FsAceEditor: AceEditor
  },
  props: {
    basicForm: {
      type: Array
    },
    labelWidth: {
      type: Number
    }
  },
  filters: {
    unescape: function (html) {
      return html
        .replace(html ? /&(?!#?\w+;)/g : /&/g, '&amp;')
        .replace(/&lt;/g, '<')
        .replace(/&gt;/g, '>')
        .replace(/&quot;/g, '"')
        .replace(/&#39;/g, '\'')
    }
  }
}
</script>

<style scoped lang="scss">
@import "styles/info";
</style>
