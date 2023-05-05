export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/config/category/query/category', data)
    },
    get (data) {
      return http.postJson('/api/terminal/config/category/get', data)
    },
    save (data) {
      return http.postJson('/api/terminal/config/category/save', data)
    },
    delete (data) {
      return http.postJson('/api/terminal/config/category/delete', data)
    },
    sortsave (data) {
      return http.postJson('/api/terminal/config/category/sort', data)
    },
    manageApp (data) {
      return http.postJson('/api/terminal/config/category/query/app', data)
    },
    statusUpdate (data) {
      return http.postJson('/api/terminal/config/category/update/status', data)
    },
    queryType (data) {
      return http.postJson('/api/common/dict/query/option/categoryType', data)
    }
  }
}
