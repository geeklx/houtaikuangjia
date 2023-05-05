import { onMounted, onUnmounted, ref, watch, nextTick } from '@vue/composition-api'
import { findNodeAll, isArray, isFunction } from 'fosung-sdk'
import { useData } from './useData'
import { useTreeEvent } from './useTreeEvent'
import { useEqualsArray } from './useEquals'
import defaultEmits from '../defaultEmits'

export const useTree = (props, root, attrs, emit) => {
  if (!Reflect.has(attrs, 'defaultExpandedKeys')) Reflect.set(attrs, 'defaultExpandedKeys', [])
  const cloneAttrs = ref({})
  const cloneProps = Object.assign({}, props)
  const { multiple, filterNodeMethod, leafOnly, includeHalfChecked } = cloneProps
  const filterText = ref('')
  const allTreeData = ref([])

  // const { onInput, onChange } = defaultEmits(emit)
  const { onChange } = defaultEmits(emit)

  const { fsOrgTree, selectedLabel, selectedValue, selectNodes, mergeProps, treeShow, reloadTree, getNodeField, setCurrentKey, setCheckedKeys, filterNode, onNodeClick, onCheckChange } = useTreeEvent(props, attrs, emit)

  const { getData, loadNode, treeData } = useData(props, root, emit, attrs, setCurrentKey, setCheckedKeys, initTreeData, getNodeField)
  cloneAttrs.value = { ...attrs }
  // 通过数据查找label
  function initTreeData () {
    // 1. 根据值设置默认展开项
    if ((!attrs.defaultExpandedKeys || !attrs.defaultExpandedKeys.length) && attrs.value) {
      attrs.defaultExpandedKeys = multiple ? [...attrs.value] : [attrs.value]
    }

    // 2. 赋值
    selectedValue.value = multiple ? [...attrs.value] : attrs.value
    multiple ? setCheckedKeys(selectedValue.value) : setCurrentKey(selectedValue.value)

    cloneAttrs.value = { ...attrs }

    nextTick(async () => {
      // 3. 判断是否有值
      const hasValue = (multiple && Array.isArray(attrs.value) && attrs.value.length) || (!multiple && !!attrs.value)

      // 4. 获取选中节点
      if (props.lazy) { // 针对懒加载
        selectNodes.value = !hasValue ? [] : multiple ? fsOrgTree.value.getCheckedNodes(leafOnly, includeHalfChecked) : [fsOrgTree.value.getCurrentNode()]
      } else {
        // 针对非懒加载
        if (!treeData.value || !treeData.value.length) {
          await getData()
        }
        selectNodes.value = !hasValue
          ? []
          : findNodeAll(treeData.value, (node) => {
            const treeValues = getNodeField('value', node)
            if ((multiple && attrs.value.includes(treeValues)) || treeValues === attrs.value) return true
          }, {
            children: [mergeProps.value.children]
          }).filter(i => !getNodeField('disabled', i))
      }

      // 5. 获选选中的节点的label
      if (multiple) {
        selectedLabel.value = selectNodes.value.map(i => getNodeField('label', i))
        onChange(selectNodes.value, [], true)
      } else {
        selectedLabel.value = hasValue ? getNodeField('label', selectNodes.value[0]) : undefined
        onChange(hasValue ? selectNodes.value[0] : undefined, [], true)
      }
    })
  }

  const stopWatchValue = watch(
    () => attrs.value,
    (val) => {
      if (!(multiple ? !useEqualsArray(val, selectedValue.value) : selectedValue.value !== val)) return
      setTimeout(() => {
        initTreeData()
      })
    },
    {
      deep: true
    }
  )

  const stopWatchFilterText = watch(
    filterText,
    (val, oldVal) => {
      if (!oldVal && val) {
        allTreeData.value = treeData.value
      } else if (!val && oldVal) {
        treeData.value = allTreeData.value
      }
      if (!val) return

      if (filterNodeMethod && isFunction(filterNodeMethod)) {
        const filterList = filterNodeMethod(val)
        treeData.value = isArray(filterList) ? filterList : []
      } else {
        fsOrgTree.value.filter(val)
      }
    },
    {
      deep: true
    }
  )

  onMounted(() => {
    initTreeData()
  })

  onUnmounted(() => {
    stopWatchValue()
    stopWatchFilterText()
  })

  return {
    treeData,
    cloneAttrs,
    filterText,
    selectedLabel,
    selectedValue,
    selectNodes,
    fsOrgTree,
    mergeProps,
    treeShow,
    getData,
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
