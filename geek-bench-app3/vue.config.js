const path = require('path')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')

function resolve (dir) {
  return path.join(__dirname, dir)
}

const isProduction = process.env.NODE_ENV === 'production'

// proxyTable 配置
const remoteServerPath = process.env.VUE_APP_API_URL

//  输出路径
const outputDir = process.env.VUE_APP_OUTPUT_DIR

module.exports = {
  publicPath: '/',
  outputDir: outputDir,
  assetsDir: 'static',
  productionSourceMap: false,
  // 配置 axios 代理请求
  devServer: {
    port: 9528,
    https: false,
    proxy: {
      '/oauth2/login/*': {
        target: remoteServerPath
      },
      '/login': {
        target: remoteServerPath
      },
      '/logout': {
        target: remoteServerPath
      },
      '/mock': {
        target: 'http://10.1.1.191:3000',
        changeOrigin: true
      },
      '/api': {
        target: remoteServerPath,
        changeOrigin: true
      }
    },
    overlay: {
      warnings: false,
      errors: true
    }
  },
  configureWebpack: config => {
    const newConfig = {}

    newConfig.resolve = {
      alias: {
        '@': resolve('src')
      }
    }
    if (isProduction) {
      newConfig.externals = {
        normalize: 'normalize.css'
      }
      newConfig.plugins = [
        // gzip
        new CompressionWebpackPlugin({
          filename: '[path].gz[query]',
          test: new RegExp('\\.(' + ['js', 'css'].join('|') + ')$'),
          threshold: 10240,
          minRatio: 0.8,
          deleteOriginalAssets: false
        }),
        new UglifyJsPlugin({
          uglifyOptions: {
            output: { comments: false },
            warnings: false,
            compress: {
              drop_console: true,
              drop_debugger: false,
              pure_funcs: ['console.log']// 移除console
            }
          }
        })
      ]
    }
    return newConfig
  },
  chainWebpack (config) {
    // it can improve the speed of the first screen, it is recommended to turn on preload
    config.plugin('preload').tap(() => [
      {
        rel: 'preload',
        // to ignore runtime.js
        // https://github.com/vuejs/vue-cli/blob/dev/packages/@vue/cli-service/lib/config/app.js#L171
        fileBlacklist: [/\.map$/, /hot-update\.js$/, /runtime\..*\.js$/],
        include: 'initial'
      }
    ])

    // when there are many pages, it will cause too many meaningless requests
    config.plugins.delete('prefetch')

    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()

    config.when(process.env.NODE_ENV !== 'development', config => {
      config
        .plugin('ScriptExtHtmlWebpackPlugin')
        .after('html')
        .use('script-ext-html-webpack-plugin', [
          {
            // `runtime` must same as runtimeChunk name. default is `runtime`
            inline: /runtime\..*\.js$/
          }
        ])
        .end()
      config.optimization.splitChunks({
        chunks: 'all',
        cacheGroups: {
          libs: {
            name: 'chunk-libs',
            test: /[\\/]node_modules[\\/]/,
            priority: 10,
            chunks: 'initial' // only package third parties that are initially dependent
          },
          FosungUI: {
            name: 'chunk-FosungUI', // split FosungUI into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?fosung-ui(.*)/ // in order to adapt to cnpm
          },
          FosungFront: {
            name: 'chunk-FosungFront', // split FosungUI into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?fosung-front(.*)/ // in order to adapt to cnpm
          },
          common: {
            chunks: 'all',
            test: /[\\/]src[\\/]components[\\/]/,
            name: 'common',
            minChunks: 2,
            maxInitialRequests: 5,
            minSize: 0,
            priority: 60
          }
        }
      })
      // https:// webpack.js.org/configuration/optimization/#optimizationruntimechunk
      config.optimization.runtimeChunk('single')
    })
  },
  pluginOptions: {
    'style-resources-loader': {
      preProcessor: 'scss',
      patterns: [
        path.resolve(__dirname, './src/styles/index.scss')
      ]
    }
  },

  /* 代码保存时进行eslint检测 */
  lintOnSave: true
}
