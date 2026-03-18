package com.example.gameoflife.logic;

import com.example.gameoflife.data.Board;

/**
 * Observer callback that the {@link IGameEngine} invokes after every
 * generation advance (whether triggered by the timer or by a manual step).
 *
 * <p>Implementations must be non-blocking. The callback may be fired from
 * a background thread; presentation-layer implementors are responsible for
 * marshalling work onto the Swing Event Dispatch Thread (EDT).</p>
 */
@FunctionalInterface
public interface IGenerationListener {

    /**
     * Called after a new board state has been computed.
     *
     * @param board      the freshly computed board; never null
     * @param generation the 1-based generation counter
     */
    void onGenerationAdvanced(Board board, long generation);
}
