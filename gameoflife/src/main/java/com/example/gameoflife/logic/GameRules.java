package com.example.gameoflife.logic;

import com.example.gameoflife.data.Board;
import com.example.gameoflife.data.Cell;

import java.util.Objects;

/**
 * Applies Conway's four Game of Life rules to compute the next generation.
 *
 * <p>Rules sourced from Wikipedia — Conway's Game of Life:</p>
 * <ol>
 *   <li>Any live cell with fewer than two live neighbours dies (underpopulation).</li>
 *   <li>Any live cell with two or three live neighbours lives on to the next generation.</li>
 *   <li>Any live cell with more than three live neighbours dies (overpopulation).</li>
 *   <li>Any dead cell with exactly three live neighbours becomes alive (reproduction).</li>
 * </ol>
 *
 * <p>All rule transitions are applied <em>simultaneously</em> to produce a
 * pure function of the preceding generation. This class is stateless and
 * thread-safe.</p>
 *
 * <p>Boundary treatment: cells outside the finite grid are treated as permanently
 * dead (fixed boundary), which is the standard finite-board interpretation.</p>
 */
public final class GameRules implements IGameRules {

    @Override
    public Board computeNextGeneration(Board current) {
        Objects.requireNonNull(current, "Current board must not be null");

        final int height = current.getHeight();
        final int width  = current.getWidth();
        final Board next = new Board(width, height);

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                final int     liveNeighbours = countLiveNeighbours(current, r, c);
                final boolean currentlyAlive = current.getCell(r, c).isAlive();
                final boolean nextAlive      = applyRules(currentlyAlive, liveNeighbours);
                next.setCell(r, c, new Cell(nextAlive));
            }
        }

        return next;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Applies Conway's four rules and returns the cell's next alive state.
     *
     * @param alive          whether the cell is currently alive
     * @param liveNeighbours number of alive cells in the Moore neighbourhood
     * @return next alive state
     */
    private boolean applyRules(boolean alive, int liveNeighbours) {
        if (alive) {
            // Rule 1: underpopulation → dies (< 2 neighbours)
            // Rule 2: survival       → lives (2 or 3 neighbours)
            // Rule 3: overpopulation → dies (> 3 neighbours)
            return liveNeighbours == 2 || liveNeighbours == 3;
        } else {
            // Rule 4: reproduction   → born (exactly 3 neighbours)
            return liveNeighbours == 3;
        }
    }

    /**
     * Counts alive cells in the 8-cell Moore neighbourhood of {@code (row, col)}.
     * Cells outside the board boundaries are treated as dead (fixed boundary).
     *
     * @param board the current board
     * @param row   row of the cell under examination
     * @param col   column of the cell under examination
     * @return number of alive neighbours (0–8)
     */
    private int countLiveNeighbours(Board board, int row, int col) {
        final int height = board.getHeight();
        final int width  = board.getWidth();
        int count = 0;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // skip the cell itself

                final int r = row + dr;
                final int c = col + dc;

                // Fixed boundary: ignore out-of-bounds neighbours (treat as dead)
                if (r >= 0 && r < height && c >= 0 && c < width) {
                    if (board.getCell(r, c).isAlive()) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
