var gulp = require("gulp"),
    jspm = require("gulp-jspm"),
    uglify = require("gulp-uglify"),
    replace = require("gulp-html-replace"),
    connect = require("gulp-connect");

// html task
gulp.task("html", function() {
    gulp.src("*.html")
        .pipe(connect.reload());
});

// js task
gulp.task("js", function() {
    gulp.src("./app/**/*.js")
        .pipe(connect.reload());
});

// watch for changes
gulp.task("watch", function() {
    gulp.watch(["*.html"], ["html"]);
    gulp.watch(["./app/**/*.js"], ["js"]);
});

// refresh
gulp.task("connect", function() {
    connect.server( {
        port: 9000,
        root: ".",
        livereload: true
    });
});

// build for production
gulp.task("package", function() {
    var js = gulp.src("app/app.js")
        .pipe(jspm({selfExecutingBundle: true}))
        .pipe(uglify())
        .pipe(gulp.dest("target/classes/static/"));

    gulp.src("./index.html")
        .pipe(replace({"js": "app.bundle.js"}))
        .pipe(gulp.dest("target/classes/static"));
});

// start the tasks
gulp.task("default", ["connect", "watch"]);
