package com.example.gameoflife.presentation;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.prefs.Preferences;

/**
 * A non-modal dialog that explains Conway's Game of Life rules.
 *
 * <p>Shown automatically on first launch (unless the user has previously
 * ticked "Don't show this again"), and available on demand via the
 * {@code ? Rules} button in the main window.</p>
 *
 * <p>The suppression preference is stored via {@link Preferences} under the
 * key {@value #PREF_KEY}, scoped to this class so it survives app restarts.</p>
 */
public final class RulesDialog extends JDialog {

    /** Preferences key used to persist the "don't show again" choice. */
    public static final String PREF_KEY = "rulesDialogSuppressed";

    private static final Preferences PREFS =
            Preferences.userNodeForPackage(RulesDialog.class);

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    /**
     * Creates a new RulesDialog centred on the given parent frame.
     *
     * @param parent the owning {@link GameFrame}; must not be null
     */
    public RulesDialog(JFrame parent) {
        // false = non-modal: parent window stays fully interactive
        super(parent, "How to Play — Conway's Game of Life", false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        buildContent();

        pack();
        setLocationRelativeTo(parent); // centre on the main window
    }

    // -------------------------------------------------------------------------
    // Static helpers
    // -------------------------------------------------------------------------

    /**
     * Returns {@code true} if the user has previously asked to suppress
     * the startup dialog.
     *
     * @return suppression preference value
     */
    public static boolean isSuppressed() {
        return PREFS.getBoolean(PREF_KEY, false);
    }

    /**
     * Convenience factory: constructs and shows the dialog only if the user
     * has not suppressed it. Used by {@link GameFrame} on startup.
     *
     * @param parent the owning frame
     */
    public static void showIfNotSuppressed(JFrame parent) {
        if (!isSuppressed()) {
            new RulesDialog(parent).setVisible(true);
        }
    }

    // -------------------------------------------------------------------------
    // UI construction
    // -------------------------------------------------------------------------

    private void buildContent() {
        final JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBorder(BorderFactory.createEmptyBorder(20, 28, 16, 28));
        root.setBackground(Color.WHITE);

        root.add(buildHeader(),  BorderLayout.NORTH);
        root.add(buildRules(),   BorderLayout.CENTER);
        root.add(buildFooter(),  BorderLayout.SOUTH);

        setContentPane(root);
    }

    /** Big title + subtitle */
    private JPanel buildHeader() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        final JLabel title = new JLabel("Conway's Game of Life");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setForeground(new Color(30, 30, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel subtitle = new JLabel("Four simple rules. Endless patterns.");
        subtitle.setFont(subtitle.getFont().deriveFont(Font.ITALIC, 13f));
        subtitle.setForeground(new Color(100, 100, 120));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(18));
        panel.add(new JSeparator());
        panel.add(Box.createVerticalStrut(18));

        return panel;
    }

    /** The four Conway rules + mouse control reminder */
    private JPanel buildRules() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Section heading
        final JLabel heading = new JLabel("The Rules  —  applied every generation, to every cell:");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 12f));
        heading.setForeground(new Color(60, 60, 90));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(heading);
        panel.add(Box.createVerticalStrut(12));

        // The four rules
        panel.add(ruleRow("😢", "Fewer than 2 live neighbours",
                "the cell dies of loneliness"));
        panel.add(Box.createVerticalStrut(8));

        panel.add(ruleRow("😊", "2 or 3 live neighbours",
                "the cell is happy and survives"));
        panel.add(Box.createVerticalStrut(8));

        panel.add(ruleRow("😵", "More than 3 live neighbours",
                "the cell dies of overcrowding"));
        panel.add(Box.createVerticalStrut(8));

        panel.add(ruleRow("🐣", "A dead cell with exactly 3 neighbours",
                "a new cell is born!"));

        // Separator before mouse controls
        panel.add(Box.createVerticalStrut(18));
        panel.add(new JSeparator());
        panel.add(Box.createVerticalStrut(12));

        // Mouse controls reminder
        final JLabel mouseHeading = new JLabel("Drawing on the grid:");
        mouseHeading.setFont(mouseHeading.getFont().deriveFont(Font.BOLD, 12f));
        mouseHeading.setForeground(new Color(60, 60, 90));
        mouseHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(mouseHeading);
        panel.add(Box.createVerticalStrut(8));

        panel.add(mouseRow("🖱 Left-click or drag",   "— paint cells alive"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(mouseRow("🖱 Right-click or drag",  "— erase cells"));

        panel.add(Box.createVerticalStrut(18));

        return panel;
    }

    /** "Got it" button + "Don't show again" checkbox */
    private JPanel buildFooter() {
        final JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(Color.WHITE);

        // Don't-show-again checkbox (left-aligned)
        final JCheckBox suppress = new JCheckBox("Don't show this again");
        suppress.setBackground(Color.WHITE);
        suppress.setFont(suppress.getFont().deriveFont(Font.PLAIN, 12f));
        suppress.setForeground(new Color(100, 100, 100));
        suppress.setSelected(isSuppressed());

        // "Got it" button (right-aligned)
        final JButton gotIt = new JButton("Got it, let's play! 🎮");
        gotIt.setFont(gotIt.getFont().deriveFont(Font.BOLD, 13f));
        gotIt.addActionListener(e -> {
            PREFS.putBoolean(PREF_KEY, suppress.isSelected());
            dispose();
        });

        final JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkPanel.setBackground(Color.WHITE);
        checkPanel.add(suppress);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(gotIt);

        panel.add(checkPanel,  BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    // -------------------------------------------------------------------------
    // Row builders
    // -------------------------------------------------------------------------

    /** One rule row: emoji  |  bold condition  →  plain outcome */
    private static JPanel ruleRow(String emoji, String condition, String outcome) {
        final JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(emojiLabel.getFont().deriveFont(18f));

        final JLabel condLabel = new JLabel(condition);
        condLabel.setFont(condLabel.getFont().deriveFont(Font.BOLD, 13f));
        condLabel.setForeground(new Color(40, 40, 40));

        final JLabel arrowLabel = new JLabel("→");
        arrowLabel.setForeground(new Color(150, 150, 150));

        final JLabel outcomeLabel = new JLabel(outcome);
        outcomeLabel.setFont(outcomeLabel.getFont().deriveFont(Font.PLAIN, 13f));
        outcomeLabel.setForeground(new Color(80, 80, 80));

        row.add(emojiLabel);
        row.add(condLabel);
        row.add(arrowLabel);
        row.add(outcomeLabel);

        return row;
    }

    /** One mouse-control row: bold action  |  plain description */
    private static JPanel mouseRow(String action, String description) {
        final JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel actionLabel = new JLabel(action);
        actionLabel.setFont(actionLabel.getFont().deriveFont(Font.BOLD, 13f));
        actionLabel.setForeground(new Color(40, 40, 40));

        final JLabel descLabel = new JLabel(description);
        descLabel.setFont(descLabel.getFont().deriveFont(Font.PLAIN, 13f));
        descLabel.setForeground(new Color(80, 80, 80));

        row.add(actionLabel);
        row.add(descLabel);

        return row;
    }
}
