package com.example.gameoflife.logic;

import com.example.gameoflife.data.Board;
import com.example.gameoflife.data.Cell;
import com.example.gameoflife.data.GameConfig;

import java.util.Objects;
import java.util.Random;

/**
 * Seeds a {@link Board} with a random initial pattern based on the
 * {@link GameConfig#getInitialDensity() initial density}.
 *
 * <p>Instances can be constructed with an optional seed for reproducible
 * sequences, which is particularly useful in testing.</p>
 */
public final class BoardInitializer {

    private final Random random;

    /**
     * Creates an initialiser backed by a non-deterministic random source.
     */
    public BoardInitializer() {
        this.random = new Random();
    }

    /**
     * Creates an initialiser backed by a seeded random source for
     * reproducible board layouts.
     *
     * @param seed the random seed
     */
    public BoardInitializer(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Creates and returns a new {@link Board} seeded according to the
     * density specified in {@code config}.
     *
     * <p>Each cell is independently set alive with probability
     * {@link GameConfig#getInitialDensity()}.</p>
     *
     * @param config the game configuration; must not be null
     * @return a freshly seeded board
     */
    public Board createBoard(GameConfig config) {
        Objects.requireNonNull(config, "GameConfig must not be null");

        final Board board = new Board(config.getWidth(), config.getHeight());

        for (int r = 0; r < config.getHeight(); r++) {
            for (int c = 0; c < config.getWidth(); c++) {
                final boolean alive = random.nextDouble() < config.getInitialDensity();
                board.setCell(r, c, new Cell(alive));
            }
        }

        return board;
    }
}
