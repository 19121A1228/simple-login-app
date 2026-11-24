module.exports = {
  default: {
    paths: ["features/**/*.feature"],
    require: ["e2e/steps/**/*.js"],
    publishQuiet: true,
    format: ["progress"],
    worldParameters: {
      baseUrl: "http://localhost:8080"
    }
  }
};
