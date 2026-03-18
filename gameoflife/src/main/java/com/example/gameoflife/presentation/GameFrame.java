package com.example.gameoflife.presentation;

import com.example.gameoflife.logic.IGameEngine;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Objects;

/**
 * Main application window for Conway's Game of Life.
 *
 * <p>On startup, automatically shows {@link RulesDialog} unless the user
 * has previously suppressed it. A {@code ? Rules} button in the control bar
 * reopens the dialog at any time.</p>
 */
public final class GameFrame extends JFrame {

    private final IGameEngine engine;
    private final GamePanel   gamePanel;

    private final JLabel  generationLabel;
    private final JLabel  hintLabel;
    private final JButton startButton;
    private final JButton stopButton;

    /**
     * Constructs and immediately shows the main window, then conditionally
     * shows the rules dialog on first launch.
     *
     * @param engine    the game engine to control; must not be null
     * @param gamePanel the rendering / drawing panel to embed; must not be null
     */
    public GameFrame(IGameEngine engine, GamePanel gamePanel) {
        super("Conway's Game of Life");
        this.engine          = Objects.requireNonNull(engine,    "Engine must not be null");
        this.gamePanel       = Objects.requireNonNull(gamePanel, "GamePanel must not be null");
        this.generationLabel = new JLabel("Generation: 0");
        this.hintLabel       = new JLabel("  Draw with left-click   |   Erase with right-click  ");
        this.startButton     = new JButton("▶  Start");
        this.stopButton      = new JButton("⏸  Stop");

        buildUI();
        registerEngineListener();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Show the rules dialog after the main window is visible so it
        // centres correctly on top of it, and so Swing has fully painted the frame.
        SwingUtilities.invokeLater(() -> RulesDialog.showIfNotSuppressed(this));
    }

    // -------------------------------------------------------------------------
    // UI construction
    // -------------------------------------------------------------------------

    private void buildUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(gamePanel,         BorderLayout.CENTER);
        add(buildTopBar(),     BorderLayout.NORTH);
        add(buildControlBar(), BorderLayout.SOUTH);
    }

    /** Thin hint bar at the top. */
    private JPanel buildTopBar() {
        final JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 3));
        bar.setBackground(new Color(240, 248, 255));
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        hintLabel.setFont(hintLabel.getFont().deriveFont(Font.PLAIN, 12f));
        hintLabel.setForeground(new Color(60, 60, 90));
        bar.add(hintLabel);

        return bar;
    }

    /** Button + generation counter bar at the bottom. */
    private JPanel buildControlBar() {
        final JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        bar.setBorder(BorderFactory.createEtchedBorder());

        final JButton stepButton  = new JButton("⏭  Step");
        final JButton resetButton = new JButton("↺  Reset");
        final JButton clearButton = new JButton("Clear All");
        final JButton rulesButton = new JButton("? Rules");

        startButton.addActionListener(e -> onStart());
        stopButton .addActionListener(e -> onStop());
        stepButton .addActionListener(e -> onStep());
        resetButton.addActionListener(e -> onReset());
        clearButton.addActionListener(e -> onReset());
        rulesButton.addActionListener(e -> new RulesDialog(this).setVisible(true));

        stopButton.setEnabled(false);

        bar.add(startButton);
        bar.add(stopButton);
        bar.add(stepButton);
        bar.add(resetButton);
        bar.add(clearButton);
        bar.add(rulesButton);                                   // ← new
        bar.add(new JLabel("  |  "));

        generationLabel.setFont(generationLabel.getFont().deriveFont(Font.BOLD, 13f));
        bar.add(generationLabel);

        return bar;
    }

    // -------------------------------------------------------------------------
    // Engine listener
    // -------------------------------------------------------------------------

    private void registerEngineListener() {
        engine.setGenerationListener((board, generation) ->
            SwingUtilities.invokeLater(() -> {
                gamePanel.updateBoard(board, generation);
                generationLabel.setText("Generation: " + generation);
            })
        );
    }

    // -------------------------------------------------------------------------
    // Button handlers — all called on the EDT
    // -------------------------------------------------------------------------

    private void onStart() {
        engine.start();
        startButton.setEnabled(false);
        stopButton .setEnabled(true);
        hintLabel.setText("  Simulation running  —  drawing still works  ");
    }

    private void onStop() {
        engine.stop();
        startButton.setEnabled(true);
        stopButton .setEnabled(false);
        hintLabel.setText("  Draw with left-click   |   Erase with right-click  ");
    }

    private void onStep() {
        if (engine.isRunning()) onStop();
        engine.step();
    }

    private void onReset() {
        engine.reset();
        startButton.setEnabled(true);
        stopButton .setEnabled(false);
        generationLabel.setText("Generation: 0");
        hintLabel.setText("  Draw with left-click   |   Erase with right-click  ");
    }
}
