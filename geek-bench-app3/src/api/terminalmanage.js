export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/basic/query', data)
    },
    save (data) {
      return http.postJson('/api/terminal/basic/save', data)
    },
    get (data) {
      return http.postJson('/api/terminal/basic/get', data)
    },
    delete (data) {
      return http.postJson('/api/terminal/basic/delete', data)
    },
    configSave (data) {
      return http.postJson('/api/terminal/config/save/common', data)
    },
    configQuery (data) {
      return http.postJson('/api/terminal/config/query/common', data)
    },
    advConfigSave (data) {
      return http.postJson('/api/terminal/image/config/save', data)
    },
    advConfigQuery (data) {
      return http.postJson('/api/terminal/image/config/query', data)
    }
  }
}
