package com.example.gameoflife.presentation;

import com.example.gameoflife.data.Board;
import com.example.gameoflife.logic.IGameEngine;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * Swing panel that displays the Game of Life grid and lets the user
 * draw or erase cells interactively with the mouse.
 *
 * <h3>Mouse controls</h3>
 * <ul>
 *   <li><strong>Left-click / left-drag</strong>  — paint cells alive</li>
 *   <li><strong>Right-click / right-drag</strong> — erase cells (kill)</li>
 * </ul>
 */
public final class GamePanel extends JPanel {

    private static final int PREFERRED_WIDTH  = 800;
    private static final int PREFERRED_HEIGHT = 600;

    private final GridRenderer renderer;
    private final IGameEngine  engine;

    private volatile Board board;

    /**
     * Constructs a GamePanel wired to the given engine for live cell edits.
     *
     * @param renderer     the renderer used to paint cells; must not be null
     * @param initialBoard the board to display before the game starts; must not be null
     * @param engine       the engine to call when the user draws; must not be null
     */
    public GamePanel(GridRenderer renderer, Board initialBoard, IGameEngine engine) {
        this.renderer = Objects.requireNonNull(renderer,     "Renderer must not be null");
        this.board    = Objects.requireNonNull(initialBoard, "Initial board must not be null");
        this.engine   = Objects.requireNonNull(engine,      "Engine must not be null");

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setDoubleBuffered(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        attachMouseListeners();
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Updates the displayed board. Must be called on the Swing EDT.
     */
    public void updateBoard(Board board, long generation) {
        this.board = Objects.requireNonNull(board, "Board must not be null");
        repaint();
    }

    // -------------------------------------------------------------------------
    // Mouse interaction
    // -------------------------------------------------------------------------

    private void attachMouseListeners() {
        final MouseAdapter adapter = new MouseAdapter() {

            private int activeButton = 0;

            @Override
            public void mousePressed(MouseEvent e) {
                activeButton = e.getButton();
                paintCellAt(e.getX(), e.getY(), activeButton);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                paintCellAt(e.getX(), e.getY(), activeButton);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                activeButton = 0;
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    /**
     * Converts a pixel coordinate to a board cell and tells the engine to
     * set its alive state.
     */
    private void paintCellAt(int pixelX, int pixelY, int button) {
        final Board snapshot = board;
        if (snapshot == null) return;

        final int cols   = snapshot.getWidth();
        final int rows   = snapshot.getHeight();
        final int panelW = getWidth();
        final int panelH = getHeight();

        if (panelW <= 0 || panelH <= 0) return;

        final int cellW = Math.max(1, panelW / cols);
        final int cellH = Math.max(1, panelH / rows);

        final int col = pixelX / cellW;
        final int row = pixelY / cellH;

        if (row < 0 || row >= rows || col < 0 || col >= cols) return;

        final boolean alive = (button == MouseEvent.BUTTON1);

        try {
            engine.setCellAlive(row, col, alive);
        } catch (IndexOutOfBoundsException ignored) {
            // clamping above prevents this; ignore gracefully
        }
    }

    // -------------------------------------------------------------------------
    // Swing painting
    // -------------------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Board snapshot = board;
        if (snapshot == null) return;

        final Graphics2D g2d = (Graphics2D) g.create();
        try {
            renderer.render(snapshot, g2d, getWidth(), getHeight());
        } finally {
            g2d.dispose();
        }
    }
}
