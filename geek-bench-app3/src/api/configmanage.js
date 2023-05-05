export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/config/manage/query', data)
    },
    save (data) {
      return http.postJson('/api/config/manage/save', data)
    },
    get (data) {
      return http.postJson('/api/config/manage/get', data)
    },
    delete (data) {
      return http.postJson('/api/config/manage/delete', data)
    },
    queryList (data) {
      return http.postJson('/api/config/cascade/query', data)
    }
  }
}
