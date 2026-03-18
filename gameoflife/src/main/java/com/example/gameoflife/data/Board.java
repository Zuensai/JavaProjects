package com.example.gameoflife.data;

import java.util.Objects;

/**
 * Represents the entire Game of Life board as a 2-D grid of {@link Cell} objects.
 *
 * <p>Internally stored as {@code Cell[rows][cols]} (row-major order).
 * The Board itself is mutable (cells can be set), but each individual
 * {@link Cell} is immutable.</p>
 *
 * <p>Coordinate convention: {@code (row, col)} where row 0 is the top.</p>
 */
public final class Board {

    /** Grid stored as [row][col], i.e. Cell[height][width]. */
    private final Cell[][] grid;
    private final int width;
    private final int height;

    /**
     * Creates a blank board of the given dimensions with all cells dead.
     *
     * @param width  number of columns; must be &gt; 0
     * @param height number of rows;   must be &gt; 0
     * @throws IllegalArgumentException if width or height are not positive
     */
    public Board(int width, int height) {
        if (width  <= 0) throw new IllegalArgumentException("Width must be positive, got: "  + width);
        if (height <= 0) throw new IllegalArgumentException("Height must be positive, got: " + height);

        this.width  = width;
        this.height = height;
        this.grid   = new Cell[height][width];

        // Initialise every cell as dead
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = new Cell(false);
            }
        }
    }

    /**
     * Copy-constructor — creates a deep copy of another board.
     * Because {@link Cell} is immutable, cell references can be shared safely.
     *
     * @param source the board to copy; must not be null
     */
    public Board(Board source) {
        Objects.requireNonNull(source, "Source board must not be null");
        this.width  = source.width;
        this.height = source.height;
        this.grid   = new Cell[height][width];
        for (int r = 0; r < height; r++) {
            System.arraycopy(source.grid[r], 0, this.grid[r], 0, width);
        }
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /**
     * Returns the cell at the given row and column.
     *
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @return the {@link Cell} at that position
     * @throws IndexOutOfBoundsException if coordinates are out of range
     */
    public Cell getCell(int row, int col) {
        validateCoords(row, col);
        return grid[row][col];
    }

    /**
     * Places a cell at the given row and column.
     *
     * @param row  row index (0-based)
     * @param col  column index (0-based)
     * @param cell the cell to place; must not be null
     * @throws IndexOutOfBoundsException if coordinates are out of range
     * @throws IllegalArgumentException  if cell is null
     */
    public void setCell(int row, int col, Cell cell) {
        validateCoords(row, col);
        Objects.requireNonNull(cell, "Cell must not be null");
        grid[row][col] = cell;
    }

    /** @return number of columns */
    public int getWidth() {
        return width;
    }

    /** @return number of rows */
    public int getHeight() {
        return height;
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private void validateCoords(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            throw new IndexOutOfBoundsException(
                String.format("Coordinates (%d, %d) out of bounds for %d×%d board",
                              row, col, height, width));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                sb.append(grid[r][c]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
