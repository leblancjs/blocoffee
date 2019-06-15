package com.github.leblancjs.blocoffee;

import java.util.Optional;

/**
 * Oversees all Blocs and delegates responsibilities to the BlocDelegate.
 */
public final class BlocSupervisor {
    private static BlocSupervisor instance;

    private BlocDelegate delegate;

    private BlocSupervisor() {
        // Private constructor for singleton
    }

    public static BlocSupervisor instance() {
        if (instance == null) {
            instance = new BlocSupervisor();
        }

        return instance;
    }

    public Optional<BlocDelegate> getDelegate() {
        return Optional.ofNullable(delegate);
    }

    public void setDelegate(final BlocDelegate delegate) {
        this.delegate = delegate;
    }
}
