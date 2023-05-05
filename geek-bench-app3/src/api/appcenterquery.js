export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/application/query/query', data)
    }
  }
}
