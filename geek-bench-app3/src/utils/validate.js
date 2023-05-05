/**
 * @param {string} path
 * @returns {Boolean}
 * 判断是否是http或https开头的地址
 */
export function isHttps (path) {
  return /^https?:/.test(path)
}

/**
 * @param {string} path
 * @returns {Boolean}
 * 判断是否是外部地址
 */
export function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername (str) {
  const validMap = ['admin', 'editor']
  return validMap.indexOf(str.trim()) >= 0
}
