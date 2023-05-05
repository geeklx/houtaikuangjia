export default {
  value: {
    type: String,
    default: ''
  },
  height: {
    type: Number,
    default: 360
  },
  width: {
    type: [Number, String],
    default: '100%'
  },
  uploadUrl: {
    type: String,
    default: '/api/oss/resource-handle/upload/'
  },
  accept: {
    type: String,
    default: 'image/*'
  },
  fieldName: {
    type: String,
    default: 'file'
  }
}
