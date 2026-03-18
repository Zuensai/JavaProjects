package com.example.gameoflife.presentation;

import com.example.gameoflife.data.Board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Objects;

/**
 * Translates a {@link Board} into pixels on a {@link Graphics2D} surface.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Calculate cell size from panel dimensions and board dimensions.</li>
 *   <li>Fill each cell with the alive or dead colour.</li>
 *   <li>Optionally draw grid lines when cells are large enough to warrant them.</li>
 * </ul>
 *
 * <p>This class is stateless; the same instance can be used by multiple
 * panels and called from any thread.</p>
 */
public final class GridRenderer {

    // Visual constants --------------------------------------------------------

    /** Colour used for live cells. */
    private static final Color COLOR_ALIVE     = new Color(30, 30, 30);

    /** Colour used for dead cells. */
    private static final Color COLOR_DEAD      = new Color(245, 245, 245);

    /** Colour used for grid lines (only shown when cells are large enough). */
    private static final Color COLOR_GRID_LINE = new Color(200, 200, 200);

    /**
     * Minimum cell size in pixels at which grid lines are drawn.
     * Below this threshold, drawing grid lines would obscure the cells.
     */
    private static final int GRID_LINE_MIN_CELL_SIZE = 5;

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Renders the board state onto the supplied graphics context.
     *
     * <p>Cell size is derived automatically from the panel dimensions so
     * the grid always fills the available space.</p>
     *
     * @param board       the board to render; must not be null
     * @param g2d         target graphics context; must not be null
     * @param panelWidth  available width in pixels; must be &gt; 0
     * @param panelHeight available height in pixels; must be &gt; 0
     */
    public void render(Board board, Graphics2D g2d, int panelWidth, int panelHeight) {
        Objects.requireNonNull(board, "Board must not be null");
        Objects.requireNonNull(g2d,  "Graphics2D must not be null");
        if (panelWidth  <= 0) throw new IllegalArgumentException("Panel width must be positive");
        if (panelHeight <= 0) throw new IllegalArgumentException("Panel height must be positive");

        final int cols = board.getWidth();
        final int rows = board.getHeight();

        // Integer cell dimensions; fill remainder with background
        final int cellW = panelWidth  / cols;
        final int cellH = panelHeight / rows;

        // Ensure at least 1 pixel per cell to avoid zero-size rectangles
        final int effectiveCellW = Math.max(1, cellW);
        final int effectiveCellH = Math.max(1, cellH);

        // Disable anti-aliasing for sharp pixel edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Fill background (covers any remainder pixels not covered by cells)
        g2d.setColor(COLOR_DEAD);
        g2d.fillRect(0, 0, panelWidth, panelHeight);

        // Draw each cell
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                final int x = c * effectiveCellW;
                final int y = r * effectiveCellH;

                if (board.getCell(r, c).isAlive()) {
                    g2d.setColor(COLOR_ALIVE);
                    g2d.fillRect(x, y, effectiveCellW, effectiveCellH);
                }
                // Dead cells are already covered by the background fill
            }
        }

        // Draw optional grid lines
        if (effectiveCellW >= GRID_LINE_MIN_CELL_SIZE
                && effectiveCellH >= GRID_LINE_MIN_CELL_SIZE) {
            g2d.setColor(COLOR_GRID_LINE);
            for (int r = 0; r <= rows; r++) {
                g2d.drawLine(0, r * effectiveCellH,
                             cols * effectiveCellW, r * effectiveCellH);
            }
            for (int c = 0; c <= cols; c++) {
                g2d.drawLine(c * effectiveCellW, 0,
                             c * effectiveCellW, rows * effectiveCellH);
            }
        }
    }
}
