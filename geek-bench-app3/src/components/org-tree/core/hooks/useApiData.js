import { isArray } from 'fosung-sdk'
import defaultEmits from '../defaultEmits'
import { useRequest } from './useRequest'
import { useFunctionField } from './useFunctionField'

const defaultHttpConfig = {
  // 请求url
  url: '',
  // 请求类型
  type: 'post',
  // 请求参数
  params: {},
  // 响应数据字段
  responseField: 'datalist'
}

export const useApiData = async ({ httpConfig, loading, node, lazy, multiple }, root, emit, attrs, setCurrentKey, setCheckedKeys, initTreeData, getNodeField) => {
  if (!root.$ajax) {
    console.error('fosung-select-tree组件未检测到fosung-front，请先安装该依赖')
    return {}
  }

  const { url, type, params, responseField } = Object.assign({}, defaultHttpConfig, httpConfig)

  const { onLazyLoad } = defaultEmits(emit)

  const requestUrl = useFunctionField(node, url, 'url')
  const requestParams = useFunctionField(node, params, 'params')

  if (!requestUrl || !requestParams) return {}

  const responseInfo = await useRequest(root, type, requestUrl, requestParams, loading)

  let datalist = []
  if (responseInfo && responseInfo.success) {
    datalist = responseInfo[responseField]
    datalist = isArray(datalist) ? datalist : [datalist]
    onLazyLoad(datalist)

    if (lazy && attrs.value && datalist.length) {
      if (datalist.some(i => multiple ? attrs.value.includes(getNodeField('value', i)) : getNodeField('value', i) === attrs.value)) {
        if (multiple) {
          setCheckedKeys(attrs.value)
        } else {
          setCurrentKey(attrs.value)
        }

        setTimeout(() => {
          initTreeData()
        })
      }
    }
  }

  return { datalist }
}
