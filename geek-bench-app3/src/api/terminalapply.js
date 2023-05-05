export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/application/config/query', data)
    },
    copy (data) {
      return http.postJson('/api/terminal/application/config/save/copy', data)
    },
    delete (data) {
      return http.postJson('/api/terminal/application/config/delete', data)
    },
    basicConfigGet (data) {
      return http.postJson('/api/terminal/application/config/get', data)
    },
    basicConfigSave (data) {
      return http.postJson('/api/terminal/application/config/save', data)
    },
    appConfigGet (data) {
      return http.postJson('/api/terminal/application/bind/query/terminalappversion', data)
    },
    appConfigDelete (data) {
      return http.postJson('/api/terminal/application/bind/delete', data)
    },
    appConfigSave (data) {
      return http.postJson('/api/terminal/application/bind/update/status', data)
    },
    authorConfigGet (data) {
      return http.postJson('/api/terminal/application/config/query/shelves', data)
    },
    authorConfigSave (data) {
      return http.postJson('/api/terminal/application/shelves/save', data)
    }
  }
}
