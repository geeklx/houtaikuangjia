<template>
  <div v-if="treeShow" v-show="!hidden" class="fs-org-tree">
    <fs-input v-if="searchable" v-model="filterText" :placeholder="placeholder"/>
    <fs-tree
      v-show="!filterNodeMethod || (filterNodeMethod && !filterText)"
      ref="fsOrgTree"
      :data="treeData"
      :props="mergeProps"
      :node-key="mergeProps.value"
      :show-checkbox="multiple"
      :check-on-click-node="multiple"
      :lazy="lazy"
      :load="load || loadNode"
      :filter-node-method="filterNode"
      v-bind="cloneAttrs"
      v-on="$listeners"
      @node-click="onNodeClick"
      @check="onCheckChange"
    >
      <template v-if="hasSlot" #default="{ node, data }">
        <div :class="['fs-tree-node__label', node.disabled ? 'is-disabled' : '']">
          <slot :node="node" :data="data"></slot>
        </div>
      </template>
    </fs-tree>
    <fs-tree
      v-if="searchable"
      v-show="filterNodeMethod && filterText"
      ref="searchTree"
      :data="treeData"
      :props="mergeProps"
      :node-key="mergeProps.value"
      :show-checkbox="multiple"
      :check-on-click-node="multiple"
      v-bind="cloneAttrs"
      v-on="$listeners"
      @node-click="onNodeClick"
      @check="onCheckChange"
    >
      <template v-if="hasSlot" #default="{ node, data }">
        <div :class="['fs-tree-node__label', node.disabled ? 'is-disabled' : '']">
          <slot :node="node" :data="data"></slot>
        </div>
      </template>
    </fs-tree>
  </div>
</template>

<script>
import defaultProps from './defaultProps'
import { useTree } from './hooks/useTree'

import { computed } from '@vue/composition-api'

export default {
  name: 'FsOrgTree',
  props: defaultProps,
  setup (props, { root, attrs, slots, emit }) {
    const {
      treeData,
      cloneAttrs,
      filterText,
      selectedLabel,
      selectedValue,
      selectNodes,
      fsOrgTree,
      mergeProps,
      treeShow,
      reloadTree,
      getNodeField,
      onNodeClick,
      onCheckChange,
      initTreeData,
      setCurrentKey,
      setCheckedKeys,
      loadNode,
      filterNode
    } = useTree(props, root, attrs, emit)

    const hasSlot = computed(() => {
      return !!slots.default
    })

    return {
      hasSlot,
      treeData,
      cloneAttrs,
      filterText,
      selectedLabel,
      selectedValue,
      selectNodes,
      fsOrgTree,
      mergeProps,
      treeShow,
      reloadTree,
      getNodeField,
      onNodeClick,
      onCheckChange,
      initTreeData,
      setCurrentKey,
      setCheckedKeys,
      loadNode,
      filterNode
    }
  }
}
</script>

<style lang="scss">
.fs-org-tree {
  .fs-tree {
    &-node {
      &.is-current, &:focus {
        & > .fs-tree-node__content {
          background-color: #F5F7FA;
        }
      }

      &__content {
        .fs-tree-node__label {
          &.is-disabled {
            cursor: no-drop;
          }
        }
      }

      &__children {
        overflow: unset;
      }
    }
  }
}
</style>
