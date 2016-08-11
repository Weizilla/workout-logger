package com.weizilla.workout.logger.garmin;

import com.google.common.collect.Lists;
import com.weizilla.garmin.ActivityDownloader;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.store.GarminEntryStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
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
    @Captor
    private ArgumentCaptor<List<GarminEntry>> entriesCaptor;
    private GarminManager manager;
    private Activity activity;
    private List<Activity> activities;
    private GarminEntry entry;
    private List<GarminEntry> entries;

    @Before
    public void setUp() throws Exception
    {
        manager = new GarminManager(store, downloader);
        activity = ActivityStub.create();
        activities = Collections.singletonList(activity);
        entry = GarminEntryStub.create();
        entries = Collections.singletonList(entry);
    }

    @Test
    public void getsAllEntriesFromStore() throws Exception
    {
        when(store.getAll()).thenReturn(entries);
        Collection<GarminEntry> actual = manager.getAllEntries();
        assertThat(actual).isSameAs(entries);
    }

    @Test
    public void storesDownloadedEntriesInStore() throws Exception
    {
        when(downloader.download()).thenReturn(activities);

        Collection<GarminEntry> downloaded = manager.refreshEntries();
        verify(store).putAll(entriesCaptor.capture());
        assertThat(entriesCaptor.getValue()).extracting(GarminEntry::getActivity).containsExactly(activity);
        assertThat(downloaded).extracting(GarminEntry::getActivity).containsExactly(activity);
    }

    @Test
    public void onlyStoresNewActivities() throws Exception
    {
        GarminEntry existing = GarminEntryStub.create();
        GarminEntry newEntry = GarminEntryStub.create();
        Set<Long> existingId = Collections.singleton(existing.getId());
        Activity newActivity = newEntry.getActivity();
        List<Activity> activities = Lists.newArrayList(existing.getActivity(), newActivity);

        when(store.getIds()).thenReturn(existingId);
        when(downloader.download()).thenReturn(activities);

        manager.refreshEntries();

        verify(store).putAll(entriesCaptor.capture());
        assertThat(entriesCaptor.getValue()).extracting(GarminEntry::getActivity).containsExactly(newActivity);
        verify(store, never()).put(existing);
    }

    @Test
    public void onlyReturnsNewActivities() throws Exception
    {
        GarminEntry existing = GarminEntryStub.create();
        GarminEntry newEntry = GarminEntryStub.create();
        Set<Long> existingId = Collections.singleton(existing.getId());
        Activity newActivity = newEntry.getActivity();
        List<Activity> activities = Lists.newArrayList(existing.getActivity(), newActivity);

        when(store.getIds()).thenReturn(existingId);
        when(downloader.download()).thenReturn(activities);

        Collection<GarminEntry> actual = manager.refreshEntries();
        assertThat(actual).hasSize(1);
        assertThat(actual).extracting(GarminEntry::getActivity).containsExactly(newActivity);
    }
}