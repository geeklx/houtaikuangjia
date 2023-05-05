export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/application/version/query', data)
    },
    queryById (data) {
      return http.postJson('/api/application/version/query/all', data)
    },
    queryAppIdList (data) {
      return http.postJson('/api/application/version/query/application', data)
    },
    isExist (data) {
      return http.postJson('/api/application/version/query/exist', data)
    },
    get (data) {
      return http.postJson('/api/application/version/get', data)
    }
  }
}
