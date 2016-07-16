package com.weizilla.workout.logger.garmin;

import com.google.common.collect.Lists;
import com.weizilla.garmin.ActivityDownloader;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.store.GarminEntryStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GarminManagerTest
{
    @Mock
    private GarminEntryStore store;
    @Mock
    private ActivityDownloader downloader;
    private GarminManager manager;
    private List<Activity> activities;
    private Activity activity;

    @Before
    public void setUp() throws Exception
    {
        manager = new GarminManager(store, downloader);
        activity = ActivityStub.create();
        activities = Collections.singletonList(activity);
    }

    @Test
    public void getsAllEntriesFromStore() throws Exception
    {
        when(store.getAll()).thenReturn(activities);
        List<Activity> actual = manager.getAllEntries();
        assertThat(actual).isSameAs(activities);
    }

    @Test
    public void storesDownloadedEntriesInStore() throws Exception
    {
        when(downloader.download()).thenReturn(activities);

        List<Activity> downloaded = manager.refreshEntries();
        verify(store).putAll(activities);
        assertThat(downloaded).isEqualTo(activities);
    }

    @Test
    public void onlyStoresNewActivities() throws Exception
    {
        Activity existing = ActivityStub.create();
        Activity newActivity = ActivityStub.create();
        Set<Long> existingId = Collections.singleton(existing.getId());
        List<Activity> all = Lists.newArrayList(existing, newActivity);

        when(store.getIds()).thenReturn(existingId);
        when(downloader.download()).thenReturn(all);

        manager.refreshEntries();

        verify(store).putAll(Collections.singletonList(newActivity));
        verify(store, never()).put(existing);
    }

    @Test
    public void onlyReturnsNewActivities() throws Exception
    {
        Activity existing = ActivityStub.create();
        Activity newActivity = ActivityStub.create();
        Set<Long> existingId = Collections.singleton(existing.getId());
        List<Activity> all = Lists.newArrayList(existing, newActivity);

        when(store.getIds()).thenReturn(existingId);
        when(downloader.download()).thenReturn(all);

        List<Activity> actual = manager.refreshEntries();
        assertThat(actual).hasSize(1);
        assertThat(actual).containsExactly(newActivity);
    }
}