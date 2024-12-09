import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessingGame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private int randomNum;
    private int attempts = 0;
    private int highScore = 0;
    private long startTime;
    private JLabel guessLabel;
    private JTextField guessField;
    private JButton submitButton;
    private JButton giveUpButton;
    private JButton newGameButton;
    private JLabel attemptsLabel;
    private JLabel highScoreLabel;
    private JLabel timeLabel;
    private Timer timer;

    public GuessingGame() {
        super("Number Guessing Game");
        setLayout(new FlowLayout());
        guessLabel = new JLabel("Enter your guess: ");
        add(guessLabel);
        guessField = new JTextField(10);
        add(guessField);
        submitButton = new JButton("Submit");
        add(submitButton);
        submitButton.addActionListener(this);
        giveUpButton = new JButton("Give Up");
        add(giveUpButton);
        giveUpButton.addActionListener(this);
        newGameButton = new JButton("New Game");
        add(newGameButton);
        newGameButton.addActionListener(this);
        attemptsLabel = new JLabel("Attempts: " + attempts);
        add(attemptsLabel);
        highScoreLabel = new JLabel("High Score: " + highScore);
        add(highScoreLabel);
        timeLabel = new JLabel("Time: 0 seconds");
        add(timeLabel);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                timeLabel.setText("Time: " + elapsedTime + " seconds");
            }
        });
        timer.start();
        randomNum = new Random().nextInt(101);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                if (guess < 0 || guess > 100) {
                    JOptionPane.showMessageDialog(this, "Please enter a number between 0 and 100.");
                } else if (guess == randomNum) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You guessed the number in " + attempts + " attempts.");
                    if (attempts < highScore || highScore == 0) {
                        highScore = attempts;
                        highScoreLabel.setText("High Score: " + highScore);
                    }
                    newGame();
                } else {
                    attempts++;
                    attemptsLabel.setText("Attempts: " + attempts);
                    if (guess < randomNum) {
                        JOptionPane.showMessageDialog(this, "Your guess is too low. Try again.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Your guess is too high. Try again.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        } else if (e.getSource() == giveUpButton) {
            JOptionPane.showMessageDialog(this, "The number was " + randomNum + ". Better luck next time!");
            newGame();
        } else if (e.getSource() == newGameButton) {
            newGame();
        }
    }

    private void newGame() {
        randomNum = new Random().nextInt(101);
        attempts = 0;
        attemptsLabel.setText("Attempts: " + attempts);
        guessField.setText("");
        startTime = System.currentTimeMillis();
    }

    public static void main(String[] args) {
        new GuessingGame();
    }
}
