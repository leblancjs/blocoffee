package com.github.leblancjs.blocoffee;

/**
 * Handles events from all Blocs which are delegated by the BlocSupervisor.
 */
public interface BlocDelegate {
    /**
     * Called whenever an Event is dispatched to any Bloc with the given Bloc
     * and Event.
     *
     * @param bloc  the bloc to which the event was dispatched
     * @param event the event that was dispatched
     */
    void onEvent(Bloc bloc, Object event);

    /**
     * Called whenever a transition occurs in any Bloc with the given Bloc and
     * Transition.
     * <p>
     * A Transition occurs when a new Event is dispatched and mapEventToState
     * is executed.
     * <p>
     * onTransition is called before a Bloc's state has been updated.
     * <p>
     * A great spot to add universal logging/analytics.
     *
     * @param bloc       the bloc in which the transition occurred
     * @param transition the transition that occurred
     */
    void onTransition(Bloc bloc, Transition transition);

    /**
     * Called whenever an Exception is thrown in any Bloc with the given Bloc
     * and Exception.
     * <p>
     * A great spot to add universal error handling.
     *
     * @param bloc  the bloc in which the error was thrown
     * @param error the error that was thrown
     */
    void onError(Bloc bloc, Throwable error);
}
