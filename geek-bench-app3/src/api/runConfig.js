export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/config/manage/query', data)
    },
    save (data) {
      return http.postJson('/api/terminal/config/manage/save', data)
    },
    get (data) {
      return http.postJson('/api/terminal/config/manage/get', data)
    },
    save2 (data) {
      return http.postJson('/api/terminal/third/party/config/save', data)
    },
    get2 (data) {
      return http.postJson('/api/terminal/third/party/config/get', data)
    },
    queryConfigPlatformList (data) {
      return http.postJson('/api/config/manage/query/platform', data)
    },
    queryTree (data) {
      return http.postJson('/api/terminal/basic/query/terminal/tree', data)
    },
    queryThirdConfig (data) {
      return http.postJson('/api/terminal/third/party/config/query', data)
    },
    thirdConfigStatus (data) {
      return http.postJson('/api/terminal/third/party/config/update/status', data)
    }
  }
}
