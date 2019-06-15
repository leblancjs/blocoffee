package com.github.leblancjs.blocoffee;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class BlocSupervisorTest {
    @Test
    public void instance_instantiatesOnlyOnce() {
        final BlocSupervisor supervisor = BlocSupervisor.instance();
        final BlocSupervisor supervisorToCompareWith = BlocSupervisor.instance();
        assertEquals(supervisor, supervisorToCompareWith);
    }

    @Test
    public void delegate_canBeAbsent() {
        final BlocSupervisor supervisor = BlocSupervisor.instance();
        supervisor.setDelegate(null);
        assertEquals(Optional.empty(), supervisor.getDelegate());
    }

    @Test
    public void delegate_canBePresent() {
        final BlocSupervisor supervisor = BlocSupervisor.instance();
        final BlocDelegate expectedDelegate = new NoOpBlocDelegate();
        supervisor.setDelegate(expectedDelegate);
        
        final Optional<BlocDelegate> delegate = supervisor.getDelegate();
        assertNotNull(delegate);
        assertTrue(delegate.isPresent());
        assertEquals(expectedDelegate, delegate.get());
    }

    private static class NoOpBlocDelegate implements BlocDelegate {
        @Override
        public void onEvent(Bloc bloc, Object event) {
            // Does nothing...
        }

        @Override
        public void onTransition(Bloc bloc, Transition transition) {
            // Does nothing...
        }

        @Override
        public void onError(Bloc bloc, Throwable error) {
            // Does nothing...
        }
    }
}
