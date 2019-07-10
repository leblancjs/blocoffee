package com.github.leblancjs.blocoffee;

import io.reactivex.Observable;
import io.reactivex.subjects.UnicastSubject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BlocTest {
    private BlocDelegate delegate;

    @Before
    public void setup() {
        delegate = mock(BlocDelegate.class);
        BlocSupervisor.instance().setDelegate(delegate);
    }

    @Test
    public void constructor_initialisesState() {
        final Bloc<TestEvent, TestState> bloc = new TestBloc(TestState.INITIAL);
        assertEquals(TestState.INITIAL, bloc.getInitialState());
        assertEquals(TestState.INITIAL, bloc.getCurrentState());
    }

    @Test
    public void dispatch_notifiesSupervisorOfEvent() {
        final Bloc<TestEvent, TestState> bloc = new TestBloc(TestState.INITIAL);
        bloc.dispatch(TestEvent.ALL_IS_GOOD);
        verify(delegate).onEvent(bloc, TestEvent.ALL_IS_GOOD);
    }

    @Test
    public void dispatch_triggersOnEventCallback() {
        final BlocDelegate callbackDelegate = mock(BlocDelegate.class);
        final Bloc<TestEvent, TestState> bloc = new TestBloc(TestState.INITIAL, callbackDelegate);
        bloc.dispatch(TestEvent.ALL_IS_GOOD);
        verify(callbackDelegate).onEvent(bloc, TestEvent.ALL_IS_GOOD);
    }

    @Test
    public void dispatch_yieldsNextState() {
        final Bloc<TestEvent, TestState> bloc = new TestBloc(TestState.INITIAL);
        bloc.dispatch(TestEvent.ALL_IS_GOOD);
        assertEquals(TestState.GOOD, bloc.getCurrentState());
    }

    @Ignore
    @Test
    public void dispatch_handlesErrors() {
        final BlocDelegate callbackDelegate = mock(BlocDelegate.class);
        final Bloc<TestEvent, TestState> bloc = new TestBloc(TestState.INITIAL, callbackDelegate);
        bloc.dispatch(TestEvent.ERROR);
        verify(callbackDelegate).onError(bloc, any(Exception.class));
        verify(delegate).onError(bloc, any(Exception.class));
    }

    private enum TestEvent {
        ALL_IS_GOOD,
        DARK_CLOUDS_HAVE_FORMED,
        ERROR
    }

    private enum TestState {
        INITIAL,
        GOOD,
        BAD,
        UGLY
    }

    private static class TestBloc extends Bloc<TestEvent, TestState> {
        private final BlocDelegate mock;

        public TestBloc(final TestState initialState) {
            super(initialState);
            mock = null;
        }

        public TestBloc(final TestState initialState, final BlocDelegate mock) {
            super(initialState);
            this.mock = mock;
        }

        @Override
        public void onEvent(final TestEvent event) {
            getMock().ifPresent(m -> m.onEvent(this, event));
        }

        @Override
        public void onTransition(final Transition<TestEvent, TestState> transition) {
            getMock().ifPresent(m -> m.onTransition(this, transition));
        }

        @Override
        public void onError(final Throwable error) {
            getMock().ifPresent(m -> m.onError(this, error));
        }

        @Override
        public Observable<TestState> mapEventToState(final TestEvent event) {
            final UnicastSubject<TestState> state = UnicastSubject.create();

            switch (event) {
                case ALL_IS_GOOD:
                    state.onNext(TestState.GOOD);
                    break;
                case DARK_CLOUDS_HAVE_FORMED:
                    state.onNext(TestState.BAD);
                    state.onNext(TestState.UGLY);
                    break;
                case ERROR:
                    throw new NullPointerException();
                default:
                    state.onNext(getCurrentState());
                    break;
            }

            return state;
        }

        private Optional<BlocDelegate> getMock() {
            return Optional.ofNullable(mock);
        }
    }
}
