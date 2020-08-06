package com.navapp.data;

import android.content.Context;

import com.navapp.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RateRepositoryTest {

    private AppDatabase mDatabase;
    private RateRepository mRepository;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .build();

        mRepository = new RateRepository(mDatabase);
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void insertAndReadFromLive_withNewData_updatesLive() throws Exception {
        final Rate RATE = new Rate("some rate", 139);
        List<Rate> rates = mRepository.getAllRates();
        assertThat(rates, hasSize(0));

        mRepository.insert(RATE);

        rates = mRepository.getAllRates();
        assertThat(rates, hasSize(1));
        assertThat(rates.get(0), equalTo(RATE));
    }

    @Test
    public void getDefaultRate_hasNoDefault_returnsEmpty() throws Exception {
        Optional<Rate> defaultOptional = mRepository.getDefault();
        assertTrue(defaultOptional.isEmpty());
    }

    @Test
    public void setDefaultValueAndGetDefaultRate_didNotHaveDefault_returnsSetDefault() throws Exception {
        Rate RATE = new Rate("some rate", 139);
        mRepository.insert(RATE);
        // because the ID is generated by the DB
        RATE = mRepository.getAllRates().get(0);

        mRepository.setDefault(RATE);

        Optional<Rate> defaultOptional = mRepository.getDefault();
        assertFalse(defaultOptional.isEmpty());
        assertThat(defaultOptional.get(), equalTo(RATE));
    }

    @Test
    public void deleteDefaultRateAndGetDefaultRate_hadDefault_returnsEmpty() throws Exception {
        final Rate RATE = new Rate("some rate", 139);
        mRepository.insert(RATE);
        mRepository.setDefault(RATE);

        mRepository.delete(RATE);

        Optional<Rate> defaultOptional = mRepository.getDefault();
        assertTrue(defaultOptional.isEmpty());
    }
}
