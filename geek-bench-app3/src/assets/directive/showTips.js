const showTips = {
  // el {element} 当前元素
  inserted (el, binding, vnode) {
    const curStyle = window.getComputedStyle(el, '') // 获取当前元素的style

    const textSpan = document.createElement('span') // 创建一个容器来记录文字的width
    let textSpanWidth = 0

    // 设置新容器的字体样式，确保与当前需要隐藏的样式相同

    textSpan.style.fontSize = curStyle.fontSize

    textSpan.style.fontWeight = curStyle.fontWeight

    textSpan.style.fontFamily = curStyle.fontFamily

    // 将容器插入body，如果不插入，offsetWidth为0

    document.body.appendChild(textSpan)

    // 设置新容器的文字

    textSpan.innerHTML = el.innerText

    // 如果字体元素大于当前元素，则需要隐藏
    if (textSpan.offsetWidth > el.offsetWidth) {
      textSpanWidth = textSpan.offsetWidth
      const _this = vnode.context
      // 触发父级事件 但不触发tooltip
      _this[binding.arg](binding.value, false)
      // 鼠标经过触发
      el.onmouseenter = function (e) {
        if (textSpanWidth > el.offsetWidth) _this[binding.arg](binding.value)
      }
    }

    // 记得移除刚刚创建的记录文字的容器

    document.body.removeChild(textSpan)
  },
  // 指令与元素解绑时
  unbind () {
    // 找到浮层元素并移除

    const vcTooltipDom = document.getElementById('vc-tooltip')

    vcTooltipDom && document.body.removeChild(vcTooltipDom)
  }
}
export default showTips
