import { ref } from '@vue/composition-api'
import { useApiData } from './useApiData'
import { isArray } from 'fosung-sdk'

export const useData = ({ data, httpConfig, subHttpConfig, optionValue, optionLabel, lazy, loading, multiple }, root, emit, attrs, setCurrentKey, setCheckedKeys, initTreeData, getNodeField) => {
  const treeData = ref([])

  const setData = (datalist = []) => {
    treeData.value = datalist
  }

  // 获取option数据
  const getData = async () => {
    if (lazy) return

    if (isArray(data) && data.length) {
      setData(data)
    } else {
      const { datalist = [] } = await useApiData({ httpConfig, loading }, root, emit)
      setData(datalist)
    }
  }

  const loadNode = async (node, resolve) => {
    if (!lazy) return

    if (node.isLeaf || node.isLeafByUser) {
      resolve([])
      return
    }

    const { datalist } = await useApiData({
      httpConfig: (node.level === 0 || !subHttpConfig.url) ? httpConfig : subHttpConfig,
      loading,
      node,
      lazy,
      multiple
    }, root, emit, attrs, setCurrentKey, setCheckedKeys, initTreeData, getNodeField)
    resolve(datalist)
  }

  return { getData, loadNode, treeData }
}
