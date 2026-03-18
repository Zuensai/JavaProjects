package com.example.gameoflife;

import com.example.gameoflife.data.GameConfig;
import com.example.gameoflife.logic.BoardInitializer;
import com.example.gameoflife.logic.GameEngine;
import com.example.gameoflife.logic.GameRules;
import com.example.gameoflife.logic.IGameEngine;
import com.example.gameoflife.logic.IGameRules;
import com.example.gameoflife.presentation.GameFrame;
import com.example.gameoflife.presentation.GamePanel;
import com.example.gameoflife.presentation.GridRenderer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application entry point.
 *
 * <p>Wires the three layers together and hands off to the Swing EDT:</p>
 * <ol>
 *   <li><strong>Data</strong>   — {@link GameConfig} (configuration)</li>
 *   <li><strong>Logic</strong>  — {@link GameRules}, {@link BoardInitializer},
 *                                 {@link GameEngine} (game loop)</li>
 *   <li><strong>Presentation</strong> — {@link GridRenderer}, {@link GamePanel},
 *                                       {@link GameFrame} (Swing window)</li>
 * </ol>
 *
 * <p>All Swing construction happens inside {@link SwingUtilities#invokeLater}
 * to comply with Swing's single-thread rule (EDT).</p>
 *
 * <p>Run via Maven: {@code mvn compile exec:java}</p>
 */
public final class Main {

    private Main() {
        // Utility class — do not instantiate
    }

    public static void main(String[] args) {
        // Use the system look-and-feel for native-feeling widgets on every OS
        applySystemLookAndFeel();

        // All Swing construction must happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(Main::launchApplication);
    }

    // -------------------------------------------------------------------------
    // Wiring
    // -------------------------------------------------------------------------

    /**
     * Constructs every layer and wires them together.
     * Must be called on the Swing EDT.
     */
    private static void launchApplication() {

        // ── 1. Data layer ──────────────────────────────────────────────────
        final GameConfig config = GameConfig.defaults();
        // Override any parameter here, e.g.:
        //   new GameConfig(100, 75, 80, 0.30)

        // ── 2. Logic layer ─────────────────────────────────────────────────
        final IGameRules       rules       = new GameRules();
        final BoardInitializer initializer = new BoardInitializer();
        final IGameEngine      engine      = new GameEngine(rules, config, initializer);

        // ── 3. Presentation layer ──────────────────────────────────────────
        final GridRenderer renderer  = new GridRenderer();
        final GamePanel    gamePanel = new GamePanel(renderer, engine.getCurrentBoard(), engine);
        new GameFrame(engine, gamePanel);

        // GameFrame's constructor registers itself as the engine listener
        // and calls setVisible(true) — the window is now live.
    }

    // -------------------------------------------------------------------------
    // Look-and-feel
    // -------------------------------------------------------------------------

    private static void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Not critical — fall back to the default cross-platform LAF
            System.err.println("Could not set system look-and-feel: " + e.getMessage());
        }
    }
}
