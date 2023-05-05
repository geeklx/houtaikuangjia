export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/project/basic/query', data)
    },
    save (data) {
      return http.postJson('/api/project/basic/save', data)
    },
    get (data) {
      return http.postJson('/api/project/basic/get', data)
    },
    delete (data) {
      return http.postJson('/api/project/basic/delete', data)
    },
    queryPerson (data) {
      return http.postJson('/api/project/manager/query/all', data)
    },
    all (data) {
      return http.postJson('/api/project/basic/query/option', data)
    }
  }
}
