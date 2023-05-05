export default function (http) {
  return {
    personSave (data) {
      return http.postJson('/api/project/manager/save', data)
    },
    appSave (data) {
      return http.postJson('/api/terminal/category/app/save', data)
    }
  }
}
