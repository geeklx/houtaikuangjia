export default function (http) {
  return {
    query (data) {
      return http.postJson('/api/terminal/application/config/queryapp', data)
    },
    queryTerminalList (data) {
      return http.postJson('/api/terminal/basic/query/terminaloptions', data)
    },
    queryCategory (data) {
      return http.postJson('/api/terminal/application/config/query/category', data)
    },
    bind (data) {
      return http.postJson('/api/terminal/application/config/save/terminalappbind', data)
    }
  }
}
