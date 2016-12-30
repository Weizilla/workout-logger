package com.weizilla.workout.logger.match;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutAssert;
import com.weizilla.workout.logger.entity.WorkoutStub;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherTest
{
    private Matcher matcher;

    @Before
    public void setUp() throws Exception
    {
        matcher = new Matcher();
    }

    @Test
    public void returnsEmptyCollectionForNullAndEmpytInputs() throws Exception
    {
        Collection<Workout> workouts = matcher.match(null, null, null);
        assertThat(workouts).isEmpty();
    }

    @Test
    public void createWorkoutForNewManualEntries() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        Collection<Workout> actual = matcher.match(null, manualEntries, null);
        assertThat(actual).hasSize(1);

        Workout workout = Iterables.getOnlyElement(actual);
        assertManualWorkout(manualEntry, workout);
    }

    @Test
    public void doNotCreateWorkoutForOldManualEntries() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        Workout workout = WorkoutStub.createWithManualId(manualEntry.getId());
        Collection<Workout> workouts = Collections.singletonList(workout);

        Collection<Workout> actual = matcher.match(workouts, manualEntries, null);
        assertThat(actual).isEmpty();
    }

    @Test
    public void doNotCreateWorkoutFromNewGarminEntry() throws Exception
    {
        Collection<GarminEntry> garminEntries = GarminEntryStub.createList();

        Collection<Workout> actual = matcher.match(null, null, garminEntries);
        assertThat(actual).isEmpty();
    }

    @Test
    public void createNewWorkoutWithNewManualAndGarminEntries() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        GarminEntry garminEntry = GarminEntryStub.create();
        Collection<GarminEntry> garminEntries = Collections.singletonList(garminEntry);

        Collection<Workout> actual = matcher.match(null, manualEntries, garminEntries);
        assertThat(actual).hasSize(1);

        Workout workout = Iterables.getOnlyElement(actual);
        assertThat(workout.getId()).isNotNull();
        WorkoutAssert.assertThat(workout).hasType(manualEntry.getType());
        WorkoutAssert.assertThat(workout).isMatched();
        WorkoutAssert.assertThat(workout).hasDuration(garminEntry.getActivity().getDuration());
        WorkoutAssert.assertThat(workout).hasDate(garminEntry.getDate());
        WorkoutAssert.assertThat(workout).hasEntryTime(manualEntry.getEntryTime());
        WorkoutAssert.assertThat(workout).hasComment(manualEntry.getComment());
        WorkoutAssert.assertThat(workout).hasManualId(manualEntry.getId());
        WorkoutAssert.assertThat(workout).hasGarminId(Optional.of(garminEntry.getId()));
    }

    @Test
    public void doNotMatchExistingGarminEntries() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        GarminEntry garminEntry = GarminEntryStub.create();
        Collection<GarminEntry> garminEntries = Collections.singletonList(garminEntry);

        Workout workout = WorkoutStub.create();
        Collection<Workout> workouts = Collections.singletonList(workout);
        workout.setGarminId(garminEntry.getId());

        Collection<Workout> actual = matcher.match(workouts, manualEntries, garminEntries);
        assertThat(actual).hasSize(1);

        Workout actualWorkout = Iterables.getOnlyElement(actual);
        assertManualWorkout(manualEntry, actualWorkout);
        assertThat(actualWorkout.getGarminId()).isEmpty();
    }

    @Test
    public void addGarminIdAndUpdateForExistingWorkout() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        Collection<Workout> workouts = matcher.match(null, manualEntries, null);
        assertThat(workouts).hasSize(1);

        Workout actualWorkout = Iterables.getOnlyElement(workouts);
        assertManualWorkout(manualEntry, actualWorkout);

        GarminEntry garminEntry = GarminEntryStub.create();
        Collection<GarminEntry> garminEntries = Collections.singletonList(garminEntry);

        Collection<Workout> actual2 = matcher.match(workouts, manualEntries, garminEntries);
        assertThat(actual2).hasSize(1);

        Workout matchedWorkout = Iterables.getOnlyElement(actual2);

        assertThat(matchedWorkout.getId()).isNotNull();
        WorkoutAssert.assertThat(matchedWorkout).hasType(manualEntry.getType());
        WorkoutAssert.assertThat(matchedWorkout).isMatched();
        WorkoutAssert.assertThat(matchedWorkout).hasDuration(garminEntry.getActivity().getDuration());
        WorkoutAssert.assertThat(matchedWorkout).hasDate(garminEntry.getDate());
        WorkoutAssert.assertThat(matchedWorkout).hasRating(manualEntry.getRating());
        WorkoutAssert.assertThat(matchedWorkout).hasEntryTime(manualEntry.getEntryTime());
        WorkoutAssert.assertThat(matchedWorkout).hasComment(manualEntry.getComment());
        WorkoutAssert.assertThat(matchedWorkout).hasManualId(manualEntry.getId());
        WorkoutAssert.assertThat(matchedWorkout).hasGarminId(Optional.of(garminEntry.getId()));
    }

    @Test
    public void matchNewGarminEntryByType() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        GarminEntry differentTypeGarmin = GarminEntryStub.create("DIFFERENT TYPE");
        GarminEntry sameTypeGarmin = GarminEntryStub.create(manualEntry.getType());
        Collection<GarminEntry> garminEntries = Lists.newArrayList(differentTypeGarmin, sameTypeGarmin);

        Collection<Workout> actual = matcher.match(null, manualEntries, garminEntries);
        assertThat(actual).hasSize(1);

        Workout workout = Iterables.getOnlyElement(actual);
        assertThat(workout.getId()).isNotNull();
        WorkoutAssert.assertThat(workout).hasType(manualEntry.getType());
        WorkoutAssert.assertThat(workout).isMatched();
        WorkoutAssert.assertThat(workout).hasDuration(sameTypeGarmin.getActivity().getDuration());
        WorkoutAssert.assertThat(workout).hasDate(sameTypeGarmin.getDate());
        WorkoutAssert.assertThat(workout).hasRating(manualEntry.getRating());
        WorkoutAssert.assertThat(workout).hasEntryTime(manualEntry.getEntryTime());
        WorkoutAssert.assertThat(workout).hasComment(manualEntry.getComment());
        WorkoutAssert.assertThat(workout).hasManualId(manualEntry.getId());
        WorkoutAssert.assertThat(workout).hasGarminId(Optional.of(sameTypeGarmin.getId()));
    }

    @Test
    public void matchExistingWorkoutToGarminByType() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        Collection<Workout> workouts = matcher.match(null, manualEntries, null);
        assertThat(workouts).hasSize(1);

        Workout actualWorkout = Iterables.getOnlyElement(workouts);
        assertManualWorkout(manualEntry, actualWorkout);

        GarminEntry differentTypeGarmin = GarminEntryStub.create("DIFFERENT TYPE");
        GarminEntry sameTypeGarmin = GarminEntryStub.create(manualEntry.getType());
        Collection<GarminEntry> garminEntries = Lists.newArrayList(differentTypeGarmin, sameTypeGarmin);

        Collection<Workout> actual2 = matcher.match(workouts, manualEntries, garminEntries);
        assertThat(actual2).hasSize(1);

        Workout matchedWorkout = Iterables.getOnlyElement(actual2);

        assertThat(matchedWorkout.getId()).isNotNull();
        WorkoutAssert.assertThat(matchedWorkout).hasType(manualEntry.getType());
        WorkoutAssert.assertThat(matchedWorkout).isMatched();
        WorkoutAssert.assertThat(matchedWorkout).hasDuration(sameTypeGarmin.getActivity().getDuration());
        WorkoutAssert.assertThat(matchedWorkout).hasDate(sameTypeGarmin.getDate());
        WorkoutAssert.assertThat(matchedWorkout).hasRating(manualEntry.getRating());
        WorkoutAssert.assertThat(matchedWorkout).hasEntryTime(manualEntry.getEntryTime());
        WorkoutAssert.assertThat(matchedWorkout).hasComment(manualEntry.getComment());
        WorkoutAssert.assertThat(matchedWorkout).hasManualId(manualEntry.getId());
        WorkoutAssert.assertThat(matchedWorkout).hasGarminId(Optional.of(sameTypeGarmin.getId()));
    }

    @Test
    public void sameGarminEntryIsNotMatchedMultipleTimes() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        Workout workout = WorkoutStub.create(manualEntry.getType());
        Collection<Workout> workouts = Collections.singletonList(workout);

        GarminEntry garminEntry = GarminEntryStub.create(manualEntry.getType());
        Collection<GarminEntry> garminEntries = Collections.singletonList(garminEntry);

        Collection<Workout> actual = matcher.match(workouts, manualEntries, garminEntries);
        assertThat(actual).hasSize(1);

        Workout actualWorkout = Iterables.getOnlyElement(actual);
        assertThat(actualWorkout.getGarminId()).contains(garminEntry.getId());
    }

    private static void assertManualWorkout(ManualEntry entry, Workout workout)
    {
        assertThat(workout.getId()).isNotNull();
        WorkoutAssert.assertThat(workout).hasType(entry.getType());
        WorkoutAssert.assertThat(workout).isNotMatched();
        WorkoutAssert.assertThat(workout).hasDuration(entry.getDuration());
        WorkoutAssert.assertThat(workout).hasDate(entry.getDate());
        WorkoutAssert.assertThat(workout).hasRating(entry.getRating());
        WorkoutAssert.assertThat(workout).hasEntryTime(entry.getEntryTime());
        WorkoutAssert.assertThat(workout).hasComment(entry.getComment());
        WorkoutAssert.assertThat(workout).hasManualId(entry.getId());
    }
}