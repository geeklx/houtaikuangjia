export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/sysdicttype/query', data)
    },
    get (data) {
      return http.postJson('/api/sysdicttype/get', data)
    },
    save (data) {
      return http.postJson('/api/sysdicttype/save', data)
    },
    status (data) {
      return http.postJson('/api/sysdicttype/update/status', data)
    },
    queryConfig (data) {
      return http.postJson('/api/sysdictdata/queryall', data)
    },
    getConfig (data) {
      return http.postJson('/api/sysdictdata/get', data)
    },
    saveConfig (data) {
      return http.postJson('/api/sysdictdata/save', data)
    },
    deleteConfig (data) {
      return http.postJson('/api/sysdictdata/delete', data)
    }
  }
}
