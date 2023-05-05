import { computed, nextTick, ref } from '@vue/composition-api'
import defaultEmits from '../defaultEmits'
import { isFunction } from 'fosung-sdk'

const defaultProps = {
  value: 'id',
  label: 'label',
  children: 'children',
  disabled: 'disabled',
  isLeaf: 'isLeaf'
}

export const useTreeEvent = (props, attrs, emit) => {
  const { multiple, leafOnly, includeHalfChecked } = props
  const treeShow = ref(true)
  const fsOrgTree = ref()
  const selectedLabel = multiple ? ref([]) : ref('')
  const selectedValue = multiple ? ref([]) : ref('')
  const selectNodes = ref([])

  const mergeProps = computed(() => {
    return Object.assign({}, defaultProps, props.props)
  })

  const { onInput, onChange } = defaultEmits(emit)

  const getNodeField = (field, node) => {
    const fieldKey = mergeProps.value[field]
    return isFunction(fieldKey) ? fieldKey(node?.data || node) : node && node[fieldKey]
  }

  const setCurrentKey = (value) => {
    setTimeout(() => {
      fsOrgTree.value.setCurrentKey(value)
    }, 0)
  }

  const setCheckedKeys = (value) => {
    setTimeout(() => {
      fsOrgTree.value.setCheckedKeys(value)
    }, 0)
  }

  const reloadTree = () => {
    treeShow.value = false
    nextTick(() => {
      treeShow.value = true
    })
  }

  const filterNode = (value, data) => {
    if (!value) return true
    return data.label.indexOf(value) !== -1
  }

  const getParentNode = (node, res = []) => {
    if (node.parent && node.parent.level > 0) res.push(node.parent)
    if (node.parent.parent && node.parent.parent.level > 0) getParentNode(node.parent, res)
    return res
  }

  const getAllKeys = (node) => {
    return [...[...getParentNode(node)].reverse(), node].map(i => i.key)
  }

  // 单选
  const onNodeClick = (data, node) => {
    if (node.isCurrent && node.disabled) {
      setCurrentKey()
      return
    }

    if (node.disabled || multiple) return

    const allKeys = getAllKeys(node)

    selectedValue.value = node.key
    selectedLabel.value = node.label
    if (JSON.stringify(attrs.value) !== JSON.stringify(selectedValue.value)) {
      onInput(selectedValue.value)
    }
    onChange(data, allKeys)
  }

  // 多选
  const onCheckChange = (data) => {
    if (getNodeField('disabled', data) || !multiple) return

    selectNodes.value = fsOrgTree.value.getCheckedNodes(leafOnly, includeHalfChecked) // 这里两个true，1. 是否只是叶子节点 2. 是否包含半选节点（就是使得选择的时候不包含父节点）
    selectedValue.value = selectNodes.value.map(i => getNodeField('value', i))
    selectedLabel.value = selectNodes.value.map(i => getNodeField('label', i))

    const allKeys = selectNodes.value.map(i => {
      const nodeItem = fsOrgTree.value.getNode(i)
      return getAllKeys(nodeItem)
    })
    const allParentKeys = [...new Set(allKeys.flat(2))]

    if (JSON.stringify(attrs.value) !== JSON.stringify(selectedValue.value)) {
      onInput(selectedValue.value)
    }
    onChange(selectNodes.value, allParentKeys)
  }

  return {
    fsOrgTree,
    selectedLabel,
    selectedValue,
    selectNodes,
    mergeProps,
    treeShow,
    reloadTree,
    getNodeField,
    setCurrentKey,
    setCheckedKeys,
    filterNode,
    onNodeClick,
    onCheckChange
  }
}
