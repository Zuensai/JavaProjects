package com.example.gameoflife.logic;

import com.example.gameoflife.data.Board;
import com.example.gameoflife.data.Cell;
import com.example.gameoflife.data.GameConfig;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Drives the Game of Life loop: start, stop, single-step, and reset.
 *
 * <h3>Threading model</h3>
 * <ul>
 *   <li>The automatic loop runs on a daemon {@link Timer} thread.</li>
 *   <li>All public methods are {@code synchronized} to protect shared
 *       mutable state ({@code currentBoard}, {@code generation},
 *       {@code timer}) from concurrent access.</li>
 *   <li>The {@link IGenerationListener} callback is invoked from whichever
 *       thread calls {@link #step()} — either the Timer thread or the
 *       Swing EDT (for manual steps). Presentation-layer listeners are
 *       responsible for marshalling work back onto the EDT via
 *       {@code SwingUtilities.invokeLater}.</li>
 * </ul>
 */
public final class GameEngine implements IGameEngine {

    private final IGameRules        rules;
    private final GameConfig        config;
    private final BoardInitializer  initializer;

    private Board               currentBoard;
    private long                generation;
    private Timer               timer;          // null when stopped
    private IGenerationListener listener;

    /**
     * Constructs the engine and seeds the first board.
     *
     * @param rules       rule engine; must not be null
     * @param config      game configuration; must not be null
     * @param initializer board seeder; must not be null
     */
    public GameEngine(IGameRules rules, GameConfig config, BoardInitializer initializer) {
        this.rules       = Objects.requireNonNull(rules,       "Rules must not be null");
        this.config      = Objects.requireNonNull(config,      "Config must not be null");
        this.initializer = Objects.requireNonNull(initializer, "Initializer must not be null");
        this.currentBoard = initializer.createBoard(config);
        this.generation   = 0;
    }

    // -------------------------------------------------------------------------
    // IGameEngine implementation
    // -------------------------------------------------------------------------

    @Override
    public synchronized void start() {
        if (timer != null) return; // already running — no-op

        timer = new Timer("GameLoop", /* daemon */ true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                step();
            }
        }, config.getTickIntervalMs(), config.getTickIntervalMs());
    }

    @Override
    public synchronized void stop() {
        if (timer == null) return; // already stopped — no-op
        timer.cancel();
        timer = null;
    }

    @Override
    public synchronized void step() {
        currentBoard = rules.computeNextGeneration(currentBoard);
        generation++;
        fireListener();
    }

    @Override
    public synchronized void reset() {
        stop();
        currentBoard = initializer.createBoard(config);
        generation   = 0;
        fireListener();
    }

    @Override
    public synchronized Board getCurrentBoard() {
        return currentBoard;
    }

    @Override
    public synchronized long getGeneration() {
        return generation;
    }

    @Override
    public synchronized boolean isRunning() {
        return timer != null;
    }

    @Override
    public synchronized void setGenerationListener(IGenerationListener listener) {
        this.listener = listener; // null is valid (removes existing listener)
    }

    @Override
    public synchronized void setCellAlive(int row, int col, boolean alive) {
        // Delegate bounds-checking to Board.setCell (throws IndexOutOfBoundsException)
        currentBoard.setCell(row, col, new Cell(alive));
        fireListener(); // repaint immediately so the user sees their stroke
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /** Notifies the listener if one has been registered. */
    private void fireListener() {
        if (listener != null) {
            listener.onGenerationAdvanced(currentBoard, generation);
        }
    }
}
