import { isString } from 'fosung-sdk'

export default function useEmits (emit) {
  const onInput = (value) => {
    emit('input', isString(value) && value.length === 0 ? null : value)
  }

  const onChange = (data, allKeys, isInit = false) => {
    emit('change', data, allKeys, isInit)
  }

  const onLazyLoad = (data) => {
    emit('lazyLoad', data)
  }

  return { onInput, onChange, onLazyLoad }
}
