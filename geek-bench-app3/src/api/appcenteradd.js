export default function (http) {
  return {
    query (data) {
      return http.postJson('', data)
    },
    get (data) {
      return http.postJson('/api/application/basic/get', data)
    },
    saveBasic (data) {
      return http.postJson('/api/application/basic/save', data)
    },
    saveH5 (data) {
      return http.postJson('/api/application/config/html/save', data)
    },
    saveAndroid (data) {
      return http.postJson('/api/application/config/android/save', data)
    },
    saveIos (data) {
      return http.postJson('/api/application/config/ios/save', data)
    }
  }
}
