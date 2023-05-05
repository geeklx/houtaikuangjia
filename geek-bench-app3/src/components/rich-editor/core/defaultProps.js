import { plugins, toolbar } from './config'

export default {
  uploadUrl: {
    type: String,
    default: '/api/oss/resource-handle/upload/'
  },
  options: {
    type: Object,
    default: () => ({})
  },
  value: {
    type: String
  },
  toolbar: {
    type: Array,
    default: () => toolbar
  },
  plugins: {
    type: Array,
    default: () => plugins
  },
  height: {
    type: [Number, String],
    required: false,
    default: 400
  },
  width: {
    type: [Number, String],
    required: false,
    default: 'auto'
  },
  showImageUpload: {
    type: Boolean,
    default: true
  }
}
