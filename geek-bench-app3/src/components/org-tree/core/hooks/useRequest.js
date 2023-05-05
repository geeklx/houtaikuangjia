export const useRequest = async (root, type, requestUrl, requestParams, loading) => {
  if (type.includes('post')) {
    return await root.$ajax[type](requestUrl, {
      ...requestParams
    }, {
      loading
    })
  } else {
    return root.$ajax[type](requestUrl, {
      data: { ...requestParams },
      loading
    })
  }
}
