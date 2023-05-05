import { isFunction, isObject, isString } from 'fosung-sdk'

const validateField = {
  url: (result) => {
    if (!isString(result)) {
      console.error('url字段返回数据类型错误，应为string类型')
      return false
    }
    return true
  },
  params: (result) => {
    if (!isObject(result)) {
      console.error('params字段返回数据类型错误，应为object类型')
      return false
    }
    return true
  }
}
const useFunctionField = (node, field, fieldName) => {
  const result = isFunction(field) ? field(node) : field

  const callBack = validateField[fieldName]

  const flag = callBack(result)

  return flag ? result : false
}

export {
  useFunctionField
}
