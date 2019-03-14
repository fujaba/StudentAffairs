module.exports = {
  name: 'segroup-app',
  preset: '../../jest.config.js',
  coverageDirectory: '../../coverage/apps/segroup-app/',
  snapshotSerializers: [
    'jest-preset-angular/AngularSnapshotSerializer.js',
    'jest-preset-angular/HTMLCommentSerializer.js'
  ]
};
