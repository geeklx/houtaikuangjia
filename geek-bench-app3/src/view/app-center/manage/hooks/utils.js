export const isReadonly = (root) => {
  return +root.$route.query.readonly === 1
}
