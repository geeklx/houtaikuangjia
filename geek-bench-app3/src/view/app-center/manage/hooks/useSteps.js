export const saveAppType = (value) => {
  sessionStorage.setItem('APPTYPE', value)
}
export const saveAppId = (value) => {
  sessionStorage.setItem('APPID', value)
}
export const saveBasicData = (value) => {
  sessionStorage.setItem('BASIC', JSON.stringify(value))
}
export const saveH5Data = (value) => {
  sessionStorage.setItem('H5', JSON.stringify(value))
}
export const saveAndroidData = (value) => {
  sessionStorage.setItem('ANDROID', JSON.stringify(value))
}
export const saveIosData = (value) => {
  sessionStorage.setItem('IOS', JSON.stringify(value))
}
export const removeData = () => {
  sessionStorage.removeItem('APPTYPE')
  sessionStorage.removeItem('APPID')
  sessionStorage.removeItem('BASIC')
  sessionStorage.removeItem('H5')
  sessionStorage.removeItem('ANDROID')
  sessionStorage.removeItem('IOS')
}
