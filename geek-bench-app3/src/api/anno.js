export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/notice/query', data)
    },
    save (data) {
      return http.postJson('/api/notice/save', data)
    },
    delete (data) {
      return http.postJson('/api/notice/delete', data)
    },
    status (data) {
      return http.postJson('/api/notice/updateStatus', data)
    },
    get (data) {
      return http.postJson('/api/notice/get', data)
    }
  }
}
