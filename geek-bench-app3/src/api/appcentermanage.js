export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/application/basic/query', data)
    },
    delete (data) {
      return http.postJson('/api/application/basic/delete', data)
    },
    get (data) {
      return http.postJson('/api/application/basic/get', data)
    },
    save (data) {
      return http.postJson('/api/application/basic/save', data)
    }
  }
}
