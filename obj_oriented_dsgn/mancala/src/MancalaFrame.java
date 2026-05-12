import javax.swing.*;
import java.awt.*;

public class MancalaFrame extends JFrame implements MancalaController.GameListener {
    private final MancalaController controller;

    private final StartScreenPanel startScreenPanel;
    private final StatusPanel statusPanel = new StatusPanel();
    private final ControlPanel controlPanel = new ControlPanel();

    private JPanel gamePanel;
    private BoardPanel boardPanel;
    private BoardStyle currentStyle;

    public MancalaFrame(MancalaController controller) {
        super("Mancala");
        this.controller = controller;

        controller.addGameListener(this);

        startScreenPanel = new StartScreenPanel();
        setContentPane(startScreenPanel);

        wireStartScreen();
        wireControls();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void wireStartScreen() {
        startScreenPanel.getStartButton().addActionListener(e -> {
            currentStyle = startScreenPanel.getSelectedStyle();
            int stones = startScreenPanel.getSelectedStones();

            controller.startGame(stones);
            showGameScreen();
        });
    }

    private void wireControls() {
        controlPanel.getUndoButton().addActionListener(e -> controller.undo());
        controlPanel.getNewGame3Button().addActionListener(e -> controller.startGame(3));
        controlPanel.getNewGame4Button().addActionListener(e -> controller.startGame(4));
    }

    private void showGameScreen() {
        boardPanel = new BoardPanel(controller, currentStyle);

        gamePanel = new JPanel(new BorderLayout(20, 20));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setBackground(currentStyle.getBoardBackground());

        statusPanel.applyStyle(currentStyle);
        controlPanel.applyStyle(currentStyle);

        gamePanel.add(statusPanel, BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(controlPanel, BorderLayout.SOUTH);

        setContentPane(gamePanel);
        refreshLabels();
        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    private void refreshLabels() {
        statusPanel.setTurnText("Current Turn: Player " + controller.getCurrentPlayer());

        MancalaController.MoveResult res = controller.getLastMoveResult();
        if (res == MancalaController.MoveResult.EXTRA_TURN) {
            statusPanel.setStatusText("Extra Turn! Go again.");
        } else if (res == MancalaController.MoveResult.INVALID_PIT) {
            statusPanel.setStatusText("Invalid move! Pick your own non-empty pit!");
        } else if (res == MancalaController.MoveResult.SUCCESS) {
            statusPanel.setStatusText("Game in progress.");
        } else if (res == MancalaController.MoveResult.GAME_OVER) {
            BoardModel.Player winner = controller.getWinner();
            statusPanel.setStatusText(winner == null ? "It's a tie!" : "Player " + winner + " wins!");
        } else if (res == MancalaController.MoveResult.UNDO_SUCCESS) {
            statusPanel.setStatusText("Undo successful.");
        } else if (res == MancalaController.MoveResult.UNDO_LIMIT_REACHED) {
            statusPanel.setStatusText("Undo failed: 3-undos per turn limit reached.");
        } else if (res == MancalaController.MoveResult.UNDO_ALREADY_USED) {
            statusPanel.setStatusText("Undo failed: You must make a move before undoing again.");
        } else if (res == MancalaController.MoveResult.UNDO_INVALID) {
            statusPanel.setStatusText("Undo failed: No move to undo.");
        } else {
            statusPanel.setStatusText("Welcome to Mancala!");
        }

        if (boardPanel != null) {
            boardPanel.refresh();
        }
    }

    @Override
    public void onGameStateChanged() {
        refreshLabels();
    }

    @Override
    public void onGameOver(BoardModel.Player winner) {
        String msg = (winner == null) ? "It's a tie!"
                : "Player " + winner + " wins!";
        statusPanel.setStatusText(msg);
        refreshLabels();
        JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    // getters for testing
    public JLabel getStatusLabel() {
        return statusPanel.getStatusLabel();
    }

    public JLabel getTurnLabel() {
        return statusPanel.getTurnLabel();
    }

    public JButton getUndoButton() {
        return controlPanel.getUndoButton();
    }

    public JButton getNewGame3Button() {
        return controlPanel.getNewGame3Button();
    }

    public JButton getNewGame4Button() {
        return controlPanel.getNewGame4Button();
    }

    public StartScreenPanel getStartScreenPanel() {
        return startScreenPanel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
}
