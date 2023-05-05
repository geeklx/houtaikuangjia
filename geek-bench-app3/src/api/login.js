export default function (http) {
  return {
    getCode () {
      return http.get('/api/common/photo/code')
    }
  }
}
