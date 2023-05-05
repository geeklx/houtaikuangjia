export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/version/query', data)
    },
    get (data) {
      return http.postJson('/api/terminal/version/get', data)
    },
    save (data) {
      return http.postJson('/api/terminal/version/save', data)
    },
    delete (data) {
      return http.postJson('/api/terminal/version/delete', data)
    },
    status (data) {
      return http.postJson('/api/terminal/version/update/status', data)
    },
    updatequery (data) {
      return http.postJson('/api/terminal/update/query', data)
    },
    queryUpgradeRangeValuesList (data) {
      return http.postJson('/api/application/query/regional', data)
    }
  }
}
