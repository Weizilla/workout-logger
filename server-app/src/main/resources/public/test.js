$(document).ready(function() {

    function update() {
        $.getJSON("/api/workouts").done(function(data) {
            console.log("Getting all workouts...");
            var workouts = data.map(function (data) {return JSON.stringify(data)}).join("<br>");
            $("#workoutDisplay").html(workouts);
        });
    }

    $("#newWorkout").submit(function(event) {
        $.post("/api/workouts/random").done(function(data) {
            update();
        });
        return false;
    });

    update();
});