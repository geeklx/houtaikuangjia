export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/agreement/config/query', data)
    },
    get (data) {
      return http.postJson('/api/terminal/agreement/config/get', data)
    },
    save (data) {
      return http.postJson('/api/terminal/agreement/config/save', data)
    },
    delete (data) {
      return http.postJson('/api/terminal/agreement/config/delete', data)
    }
  }
}
