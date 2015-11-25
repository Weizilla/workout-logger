$(document).ready(function() {

    function update() {
        $.getJSON("/api/workouts", function(data) {
            console.log("Getting all workouts...");
            $("#workoutDisplay").html(JSON.stringify(data));
        });
    }

    $("#newWorkout").submit(function(event) {
        console.log("Adding new workout");
        var type = $("#newWorkoutType").val();
        var duration = "PT1H";
        var date = "2015-11-02";
        var workoutJson = {
            type: type,
            duration: duration,
            date: date,
        };

        $.ajax({
            url: "/api/workouts",
            type: "POST",
            data: JSON.stringify(workoutJson),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });

        update();
        return false;
    });

    update();
});