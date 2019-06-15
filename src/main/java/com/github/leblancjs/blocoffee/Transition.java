package com.github.leblancjs.blocoffee;

import java.util.Objects;

/**
 * Occurs when an Event is dispatched after mapEventToState has been called,
 * but before the Bloc's State has been updated.
 * <p>
 * A Transition consists of the currentState, the event which was dispatched,
 * and the nextState.
 *
 * @param <Event> the type of the event that was dispatched
 * @param <State> the type of the state
 */
public class Transition<Event, State> {
    private final State currentState;
    private final Event event;
    private final State nextState;

    public Transition(State currentState, Event event, State nextState) {
        this.currentState = Objects.requireNonNull(currentState);
        this.event = Objects.requireNonNull(event);
        this.nextState = Objects.requireNonNull(nextState);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Transition<?, ?> that = (Transition<?, ?>) object;

        return Objects.equals(currentState, that.currentState) &&
                Objects.equals(event, that.event) &&
                Objects.equals(nextState, that.nextState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState, event, nextState);
    }

    @Override
    public String toString() {
        return "Transition{" +
                "currentState=" + currentState +
                ", event=" + event +
                ", nextState=" + nextState +
                '}';
    }

    public State getCurrentState() {
        return currentState;
    }

    public Event getEvent() {
        return event;
    }

    public State getNextState() {
        return nextState;
    }
}
