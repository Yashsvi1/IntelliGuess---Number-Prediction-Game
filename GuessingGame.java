import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GuessingGame extends JFrame {
    static ArrayList<Integer> scoreBoard = new ArrayList<>();
    private JTextField guessInput;
    private JLabel messageLabel;
    private int randomNumber;
    private int numberRange;
    private int guessCount;

    public GuessingGame() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        getContentPane().add(panel, BorderLayout.CENTER);

        messageLabel = new JLabel("Welcome to the Number Guessing Game!", JLabel.CENTER);
        panel.add(messageLabel);

        JButton playButton = new JButton("Play the Game");
        panel.add(playButton);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JButton scoreButton = new JButton("Score Board");
        panel.add(scoreButton);
        scoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayScoreBoard();
            }
        });

        JButton exitButton = new JButton("Exit the Game");
        panel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        guessInput = new JTextField();
        guessInput.setVisible(false);
        guessInput.setHorizontalAlignment(JTextField.CENTER);
        panel.add(guessInput);
        guessInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guessNumber();
            }
        });

        // Highlight the guess input area
        Border highlightedBorder = BorderFactory.createLineBorder(Color.BLUE, 2);
        guessInput.setBorder(highlightedBorder);
    }

    private void startGame() {
        String rangeInput = JOptionPane.showInputDialog(this, "Enter the range for the number (e.g., 1-100):");
        try {
            numberRange = Integer.parseInt(rangeInput);
            randomNumber = generateRandomNumber(numberRange);
            guessCount = 0;
            messageLabel.setText("Guess a number between 1 and " + numberRange + ":");
            guessInput.setVisible(true);
            guessInput.requestFocus();
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid input. Please enter a valid number.");
        }
    }

    private int generateRandomNumber(int range) {
        Random random = new Random();
        return random.nextInt(range) + 1;
    }

    private void guessNumber() {
        try {
            int userGuess = Integer.parseInt(guessInput.getText());
            guessCount++;
            if (userGuess > randomNumber) {
                messageLabel.setText("Lower! Try again:");
            } else if (userGuess < randomNumber) {
                messageLabel.setText("Higher! Try again:");
            } else {
                messageLabel.setText("Correct! You guessed the number in " + guessCount + " tries.");
                scoreBoard.add(guessCount);
                guessInput.setVisible(false);
                displayScoreBoard();
            }
            guessInput.setText("");
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid input. Please enter a valid number.");
        }
    }

    private void displayScoreBoard() {
        Collections.sort(scoreBoard);
        StringBuilder scores = new StringBuilder("<html>Score Board:<br>");
        for (Integer score : scoreBoard) {
            scores.append("Finished in ").append(score).append(" tries<br>");
        }
        scores.append("</html>");
        JOptionPane.showMessageDialog(this, scores.toString(), "Score Board", JOptionPane.INFORMATION_MESSAGE);
        messageLabel.setText("Welcome to the Number Guessing Game!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuessingGame().setVisible(true);
            }
        });
    }
}
