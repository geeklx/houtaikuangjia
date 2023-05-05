module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  plugins: [
    '@babel/plugin-proposal-object-rest-spread',
    '@babel/plugin-proposal-optional-chaining',
    ['import',
      {
        libraryName: 'fs-ace-editor',
        libraryDirectory: 'lib'
      }
    ],
    ['import',
      {
        libraryName: 'fs-select-tree',
        libraryDirectory: 'lib',
        style: (name, file) => {
          const libDirIndex = name.lastIndexOf('/')
          const libDir = name.substring(0, libDirIndex)
          const fileName = name.substr(libDirIndex + 1)
          return `${libDir}/theme/${fileName}.css`
        }
      },
      'fs-select-tree'
    ],
    ['import',
      {
        libraryName: 'fs-md-editor',
        libraryDirectory: 'lib',
        style: (name, file) => {
          const libDirIndex = name.lastIndexOf('/')
          const libDir = name.substring(0, libDirIndex)
          const fileName = name.substr(libDirIndex + 1)
          return `${libDir}/theme/${fileName}.css`
        }
      },
      'fs-md-editor'
    ],
    ['import',
      {
        libraryName: 'fs-org-tree',
        libraryDirectory: 'lib',
        style: (name, file) => {
          const libDirIndex = name.lastIndexOf('/')
          const libDir = name.substring(0, libDirIndex)
          const fileName = name.substr(libDirIndex + 1)
          return `${libDir}/theme/${fileName}.css`
        }
      },
      'fs-org-tree'
    ]
  ]
}
