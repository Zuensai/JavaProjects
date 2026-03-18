package com.example.gameoflife.logic;

import com.example.gameoflife.data.Board;

/**
 * Contract for applying Conway's Game of Life rules to produce the
 * next generation from a given board state.
 *
 * <p>Implementations must be stateless and pure: the same input board
 * always produces the same output board, with no side effects.</p>
 */
public interface IGameRules {

    /**
     * Computes and returns the next-generation board by applying the
     * four Conway rules simultaneously to every cell.
     *
     * @param current the current board state; must not be null
     * @return a new {@link Board} representing the next generation
     */
    Board computeNextGeneration(Board current);
}
