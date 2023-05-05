export const useSessionData = ({ isCache }) => {
  // 将字点数据存入sessionStorage
  const setSessionData = (datalist, sessionKey) => {
    if (datalist && datalist.length && sessionKey && isCache) {
      sessionStorage.setItem(sessionKey, JSON.stringify(datalist))
    }
  }

  const getSessionData = (sessionKey) => {
    const sessionData = sessionStorage.getItem(sessionKey)
    return JSON.parse(sessionData) || []
  }

  return { setSessionData, getSessionData }
}
