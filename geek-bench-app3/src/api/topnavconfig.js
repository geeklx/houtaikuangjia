export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/config/navigation/top/query', data)
    },
    get (data) {
      return http.postJson('/api/terminal/config/navigation/top/get', data)
    },
    save (data) {
      return http.postJson('/api/terminal/config/navigation/top/save', data)
    },
    delete (data) {
      return http.postJson('/api/terminal/config/navigation/top/delete', data)
    },
    getTreeList (data) {
      return http.postJson('/api/terminal/config/menu/top/query', data)
    },
    editGet (data) {
      return http.postJson('/api/terminal/config/menu/top/get', data)
    },
    editSave (data) {
      return http.postJson('/api/terminal/config/menu/top/save', data)
    },
    editSort (data) {
      return http.postJson('/api/terminal/config/menu/top/sort', data)
    },
    editDelete (data) {
      return http.postJson('/api/terminal/config/menu/top/delete', data)
    }
  }
}
