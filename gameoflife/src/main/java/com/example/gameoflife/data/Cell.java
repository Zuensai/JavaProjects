package com.example.gameoflife.data;

/**
 * Represents a single cell on the Game of Life board.
 *
 * <p>Cells are immutable. To change state, create a new Cell via
 * {@link #withAlive(boolean)}. This prevents accidental mutation of board state
 * and allows safe sharing across threads.</p>
 */
public final class Cell {

    private final boolean alive;

    /**
     * Constructs a Cell with the given alive state.
     *
     * @param alive {@code true} if the cell is alive, {@code false} if dead
     */
    public Cell(boolean alive) {
        this.alive = alive;
    }

    /**
     * Returns {@code true} if this cell is alive.
     *
     * @return alive state
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Returns a new Cell with the specified alive state.
     * The original cell is unchanged (immutable).
     *
     * @param alive the desired alive state
     * @return a new Cell instance
     */
    public Cell withAlive(boolean alive) {
        return new Cell(alive);
    }

    @Override
    public String toString() {
        return alive ? "O" : ".";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cell other)) return false;
        return this.alive == other.alive;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(alive);
    }
}
