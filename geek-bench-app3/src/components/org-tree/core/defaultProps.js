export default {
  // 自定义数据
  data: {
    type: Array,
    default () {
      return []
    }
  },
  // 接口请求数据配置
  httpConfig: {
    type: Object,
    default () {
      return {
        // 请求url
        url: '',
        // 请求类型
        type: 'post',
        // 请求参数
        params: {},
        // 响应数据字段
        responseField: 'datalist'
      }
    }
  },
  // 子节点接口请求数据配置，该配置url为空时，则取httpConfig数据
  subHttpConfig: {
    type: Object,
    default () {
      return {
        // 请求url
        url: '',
        // 请求类型
        type: 'post',
        // 请求参数
        params: {},
        // 响应数据字段
        responseField: 'datalist'
      }
    }
  },
  // 请求加载状态
  loading: {
    type: Boolean,
    default: true
  },
  // 树配置项
  props: {
    type: Object,
    default () {
      return {
        value: 'id',
        label: 'label',
        children: 'children',
        disabled: 'disabled',
        isLeaf: 'isLeaf'
      }
    }
  },
  // 是否多选
  multiple: {
    type: Boolean,
    default: false
  },
  // 是否懒加载
  lazy: {
    type: Boolean,
    default: false
  },
  // 懒加载函数
  load: {
    type: Function
  },
  // 是否需要隐藏树
  hidden: {
    type: Boolean,
    default: false
  },
  // 是否需要搜索框
  searchable: {
    type: Boolean,
    default: false
  },
  placeholder: {
    type: String,
    default: '输入关键字进行过滤'
  },
  // 自定义筛选事件
  filterNodeMethod: {
    type: Function
  },
  // 多选时是否只是叶子节点
  leafOnly: {
    type: Boolean,
    default: true
  },
  // 多选时是否包含半选节点
  includeHalfChecked: {
    type: Boolean,
    default: false
  }
}
