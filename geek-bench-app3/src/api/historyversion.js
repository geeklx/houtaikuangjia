export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/application/version/query/all', data)
    },
    delete (data) {
      return http.postJson('/api/application/version/delete', data)
    }
  }
}
