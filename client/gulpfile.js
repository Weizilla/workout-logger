var gulp = require("gulp"),
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

gulp.task("connect", function() {
    connect.server( {
        port: 9000,
        root: ".",
        livereload: true
    });
});

// start the tasks
gulp.task("default", ["connect", "watch"]);