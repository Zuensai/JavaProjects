package com.example.gameoflife.logic;

import com.example.gameoflife.data.Board;

/**
 * Contract for the game engine that drives the Game of Life loop.
 *
 * <p>Separates loop control (start / stop / step / reset) from rule
 * computation ({@link IGameRules}), keeping each class focused on a
 * single responsibility.</p>
 */
public interface IGameEngine {

    /**
     * Starts the automatic game loop at the interval specified by
     * {@link com.example.gameoflife.data.GameConfig#getTickIntervalMs()}.
     * Does nothing if the loop is already running.
     */
    void start();

    /**
     * Pauses the automatic game loop. Does nothing if already stopped.
     */
    void stop();

    /**
     * Advances the game by exactly one generation, regardless of whether
     * the automatic loop is running.
     */
    void step();

    /**
     * Stops the loop, re-seeds the board with a fresh random state, and
     * resets the generation counter to zero.
     */
    void reset();

    /**
     * Returns a snapshot of the current board state.
     *
     * @return the current {@link Board}; never null
     */
    Board getCurrentBoard();

    /**
     * Returns the current generation counter (0 = initial seed).
     *
     * @return generation count
     */
    long getGeneration();

    /**
     * Returns {@code true} if the automatic loop is currently running.
     *
     * @return running state
     */
    boolean isRunning();

    /**
     * Registers a listener that will be notified after every generation
     * advance. Replaces any previously registered listener.
     *
     * @param listener the listener to register; may be null to unregister
     */
    void setGenerationListener(IGenerationListener listener);

    /**
     * Sets the alive state of a single cell on the current board.
     *
     * <p>This is the primary way the presentation layer lets the user
     * draw cells onto the canvas. Triggers a listener notification so
     * the panel repaints immediately.</p>
     *
     * @param row   row index (0-based)
     * @param col   column index (0-based)
     * @param alive {@code true} to make the cell alive, {@code false} to kill it
     * @throws IndexOutOfBoundsException if the coordinates are out of range
     */
    void setCellAlive(int row, int col, boolean alive);
}
