package com.github.leblancjs.blocoffee;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TransitionTest {
    private static final String CURRENT_STATE = "current.state";
    private static final String EVENT = "event";
    private static final String NEXT_STATE = "next.state";

    private static final String STRING_REPRESENTATION = String.format(
            "Transition{currentState=%s, event=%s, nextState=%s}",
            CURRENT_STATE, EVENT, NEXT_STATE);

    @Test(expected = NullPointerException.class)
    public void constructor_requiresCurrentState() {
        new Transition<>(null, EVENT, NEXT_STATE);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_requiresEvent() {
        new Transition<>(CURRENT_STATE, null, NEXT_STATE);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_requiresNextState() {
        new Transition<>(CURRENT_STATE, EVENT, null);
    }

    @Test
    public void constructor_initialisesFields() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        assertEquals(CURRENT_STATE, transition.getCurrentState());
        assertEquals(EVENT, transition.getEvent());
        assertEquals(NEXT_STATE, transition.getNextState());
    }

    @Test
    public void equals_doesNotMatchWithNothing() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        assertNotEquals(transition, null);
    }

    @Test
    public void equals_doesNotMatchWithOtherClasses() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        final Transition<Object, String> transitionToCompareWith = new Transition<>(CURRENT_STATE, new Object(), NEXT_STATE);
        assertNotEquals(transition, transitionToCompareWith);
    }

    @Test
    public void equals_doesNotMatchWhenCurrentStatesAreDifferent() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        final Transition<String, String> transitionToCompareWith = new Transition<>("different.current.state", EVENT, NEXT_STATE);
        assertNotEquals(transition, transitionToCompareWith);
    }

    @Test
    public void equals_doesNotMatchWhenEventsAreDifferent() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        final Transition<String, String> transitionToCompareWith = new Transition<>(CURRENT_STATE, "different.event", NEXT_STATE);
        assertNotEquals(transition, transitionToCompareWith);
    }

    @Test
    public void equals_doesNotMatchWhenNextStatesAreDifferent() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        final Transition<String, String> transitionToCompareWith = new Transition<>(CURRENT_STATE, EVENT, "different.next.state");
        assertNotEquals(transition, transitionToCompareWith);
    }

    @Test
    public void equals_matchesWithItself() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        assertEquals(transition, transition);
    }

    @Test
    public void equals_matchesWithIdenticalTransition() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        final Transition<String, String> transitionToCompareWith = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        assertEquals(transition, transitionToCompareWith);
    }

    @Test
    public void hashCode_doesNotUseReferenceAsHashCode() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        assertNotEquals(System.identityHashCode(transition), transition.hashCode());
    }

    @Test
    public void toString_formatsStringRepresentationProperly() {
        final Transition<String, String> transition = new Transition<>(CURRENT_STATE, EVENT, NEXT_STATE);
        assertEquals(STRING_REPRESENTATION, transition.toString());
    }
}
