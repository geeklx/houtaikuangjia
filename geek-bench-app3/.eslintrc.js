module.exports = {
  root: true,
  env: {
      node: true
  },
  extends: [
      'plugin:vue/essential',
      '@vue/standard'
  ],
  parserOptions: {
      parser: 'babel-eslint'
  },
  rules: {
      'quotes': [1, 'single'],
      "no-callback-literal": 0,
      'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
  }
}
