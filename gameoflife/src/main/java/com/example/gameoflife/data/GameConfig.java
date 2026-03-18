package com.example.gameoflife.data;

/**
 * Immutable configuration for a Game of Life session.
 *
 * <p>Holds every tuneable parameter in one place so that it can be passed
 * down through the layers without scattering magic numbers.</p>
 */
public final class GameConfig {

    /** Default grid width in cells. */
    public static final int DEFAULT_WIDTH = 80;

    /** Default grid height in cells. */
    public static final int DEFAULT_HEIGHT = 60;

    /** Default timer tick interval in milliseconds (10 generations/sec). */
    public static final int DEFAULT_TICK_INTERVAL_MS = 100;

    /**
     * Default initial cell density.
     * 0.0 = completely empty canvas so the user can draw their own pattern.
     * Set to e.g. 0.35 if you prefer a random start instead.
     */
    public static final double DEFAULT_INITIAL_DENSITY = 0.0;

    private final int    width;
    private final int    height;
    private final int    tickIntervalMs;
    private final double initialDensity;

    /**
     * Creates a fully-specified configuration.
     *
     * @param width          number of columns; must be &gt; 0
     * @param height         number of rows;   must be &gt; 0
     * @param tickIntervalMs milliseconds between generated ticks; must be &gt; 0
     * @param initialDensity proportion of cells seeded alive; must be in [0, 1]
     */
    public GameConfig(int width, int height, int tickIntervalMs, double initialDensity) {
        if (width <= 0)
            throw new IllegalArgumentException("Width must be positive, got: " + width);
        if (height <= 0)
            throw new IllegalArgumentException("Height must be positive, got: " + height);
        if (tickIntervalMs <= 0)
            throw new IllegalArgumentException("Tick interval must be positive, got: " + tickIntervalMs);
        if (initialDensity < 0.0 || initialDensity > 1.0)
            throw new IllegalArgumentException(
                "Initial density must be in [0, 1], got: " + initialDensity);

        this.width          = width;
        this.height         = height;
        this.tickIntervalMs = tickIntervalMs;
        this.initialDensity = initialDensity;
    }

    /** Creates a configuration with all default values. */
    public static GameConfig defaults() {
        return new GameConfig(
            DEFAULT_WIDTH,
            DEFAULT_HEIGHT,
            DEFAULT_TICK_INTERVAL_MS,
            DEFAULT_INITIAL_DENSITY
        );
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /** @return number of columns */
    public int getWidth() { return width; }

    /** @return number of rows */
    public int getHeight() { return height; }

    /** @return timer period in milliseconds */
    public int getTickIntervalMs() { return tickIntervalMs; }

    /** @return proportion [0, 1] of cells seeded alive */
    public double getInitialDensity() { return initialDensity; }

    @Override
    public String toString() {
        return String.format("GameConfig{width=%d, height=%d, tick=%dms, density=%.2f}",
                             width, height, tickIntervalMs, initialDensity);
    }
}
