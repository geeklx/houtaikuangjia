export default function (http) {
  return {
    // 获取字典项
    getDictionary ({ dictionaryKey }) {
      return http.postJson(`/api/common/dict/query/option/${dictionaryKey}`)
    },
    // 错误码查询
    errorCodeQuery (data) {
      return http.postJson('/api/gatewayadmin/errorcode/query', data)
    }
  }
}
