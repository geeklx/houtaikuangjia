module.exports = function (loadPath) {
  return function () {
    return import(`@/${loadPath}.vue`).catch(() => {
      alert('版本已更新，请清除浏览器缓存！')
    })
  }
}
