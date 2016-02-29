var path = require("path");
var webpack = require("webpack");
var entry = path.resolve(__dirname, "app", "main.js");
var output = path.resolve(__dirname, "target", "classes", "static");

module.exports = {
    entry: entry,
    output: {
        path: output,
        filename: "bundle.js"
    },
    module: {
        loaders: [
            {test: path.join(__dirname, "app"), loader: "babel", query: {presets: ["es2015"]}},
            {test: /\.css$/, loader: "style!css"},
            {test: /\.html$/, loader: "file?name=[name].[ext]"},
            // font awesome
            {test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: "url-loader?limit=10000&minetype=application/font-woff"},
            {test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: "file-loader"}
        ]
    },
    plugins: [
        new webpack.EnvironmentPlugin(["API_HOST"]),
        // ignore locales for moment
        new webpack.IgnorePlugin(/^\.\/locale$/, [/moment$/])
    ]
};
