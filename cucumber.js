module.exports = {
  default: {
    paths: ["e2e/features/**/*.feature"],
    require: ["e2e/steps/**/*.js"],
    publishQuiet: true,
    format: ["progress"],
    worldParameters: {
      baseUrl: "http://localhost:8080"
    }
  }
};
