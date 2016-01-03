var gulp = require("gulp"),
    jspm = require("gulp-jspm"),
    uglify = require("gulp-uglify"),
    replace = require("gulp-html-replace"),
    connect = require("gulp-connect");

// watch for changes
gulp.task("html", function() {
    gulp.src("*.html")
        .pipe(connect.reload());
});

gulp.task("css", function() {
    gulp.src("*.css")
        .pipe(connect.reload());
});

gulp.task("js", function() {
    gulp.src("./app/**/*.js")
        .pipe(connect.reload());
});

gulp.task("watch", function() {
    gulp.watch(["*.html"], ["html"]);
    gulp.watch(["*.css"], ["css"]);
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
	gulp.src("*.css")
		.pipe(gulp.dest("target/classes/static"));

	gulp.src("*.html")
		.pipe(gulp.dest("target/classes/static"));

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
