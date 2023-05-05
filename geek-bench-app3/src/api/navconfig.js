export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/navigation/config/query/allnavigation', data)
    },
    get (data) {
      return http.postJson('/api/terminal/navigation/config/get', data)
    },
    save (data) {
      return http.postJson('/api/terminal/navigation/config/save', data)
    },
    status (data) {
      return http.postJson('/api/terminal/navigation/config/update/status', data)
    },
    delete (data) {
      return http.postJson('/api/terminal/navigation/config/delete', data)
    },
    sortsave (data) {
      return http.postJson('/api/terminal/navigation/config/navigationsort', data)
    },
    queryNavigationUrlList (data) {
      return http.postJson('/api/terminal/navigation/turn/url/query/all', data)
    }
  }
}
