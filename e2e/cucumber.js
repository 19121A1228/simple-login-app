module.exports = {
  default: {
    require: [
      'e2e/steps/**/*.ts'
    ],
    requireModule: ['ts-node/register'],
    format: ['progress'],
    paths: ['e2e/features/**/*.feature'],
    publishQuiet: true
  }
};
