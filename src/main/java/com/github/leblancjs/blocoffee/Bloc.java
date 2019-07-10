package com.github.leblancjs.blocoffee;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A Bloc takes a stream of events as an input and transforms them into a
 * stream of states as an output.
 *
 * @param <Event> the type of events in the input stream
 * @param <State> the type of state in the ouput stream
 */
public abstract class Bloc<Event, State> implements AutoCloseable {
    private final PublishSubject<Event> eventSubject;
    private final BehaviorSubject<State> stateSubject;
    private final Disposable stateSubjectDisposable;
    private final State initialState;

    public Bloc(final State initialState) {
        eventSubject = PublishSubject.create();
        stateSubject = BehaviorSubject.createDefault(initialState);
        this.initialState = initialState;

        stateSubjectDisposable = bindStateSubject();
    }

    public abstract void onEvent(final Event event);

    public abstract void onTransition(final Transition<Event, State> transition);

    public abstract void onError(final Throwable error);

    public void dispatch(final Event event) {
        try {
            BlocSupervisor.instance().getDelegate().ifPresent(d -> d.onEvent(this, event));
            onEvent(event);
            eventSubject.onNext(event);
        } catch (final Exception error) {
            handleError(error);
        }
    }

    public Observable<State> transform(final Observable<Event> events, final Function<Event, Observable<State>> next) {
        return events.concatMap(next);
    }

    private Disposable bindStateSubject() {
        // TODO: Figure out how to manage transitions...
        final AtomicReference<Event> currentEvent = new AtomicReference<>();

        return transform(eventSubject, event -> {
//            currentEvent.set(event);
            return mapEventToState(event).doOnError(this::handleError);
        }).forEach(nextState -> {
            final State currentState = getCurrentState();
            if (currentState.equals(nextState) || stateSubject.hasComplete()) {
                return;
            }

//            final Transition<Event, State> transition = new Transition<>(currentState, currentEvent.get(), nextState);
//            BlocSupervisor.instance().getDelegate().ifPresent(d -> d.onTransition(this, transition));
//            onTransition(transition);
            stateSubject.onNext(nextState);
        });
    }

    private void handleError(final Throwable error) {
        BlocSupervisor.instance().getDelegate().ifPresent(d -> d.onError(this, error));
        onError(error);
    }

    public abstract Observable<State> mapEventToState(final Event event);

    public State getInitialState() {
        return initialState;
    }

    public State getCurrentState() {
        return stateSubject.getValue();
    }

    @Override
    public void close() throws Exception {
        stateSubjectDisposable.dispose();
    }
}
