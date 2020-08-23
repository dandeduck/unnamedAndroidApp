package com.navapp.navigation.util;

import com.google.maps.model.LatLng;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AtomicUponArrival extends SimpleUponArrival {
    private final AtomicReference<LatLng> currentLocation;
    private final AtomicReference<LatLng> nextLocation;

    protected AtomicUponArrival(long minDurationInSeconds, AtomicReference<LatLng> currentLocation, AtomicReference<LatLng> nextLocation) {
        super(minDurationInSeconds);
        this.currentLocation = currentLocation;
        this.nextLocation = nextLocation;
    }

    @Override
    protected LatLng currentLocation() {
        return currentLocation.get();
    }

    @Override
    protected LatLng nextLocation() {
        return nextLocation.get();
    }
}
