import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame
{
    final Dimension SIZE = new Dimension(910, 700);
    private Board board;
    private JLabel label;
    private JLabel gameInfo;
    private JButton reset;

    public Minesweeper()
    {
        this.setTitle("MINESWEEPER");
        this.setLayout(null);
        this.setSize(SIZE);
        this.setUpLabel();
        this.setUpGameInfo();
        this.setUpBoard();
        this.reset = new JButton();
        this.reset.addActionListener(this.board);
        this.reset.setBounds(600,600,150,50);
        this.reset.setText("Reset");
        this.add(reset);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setUpLabel()
    {
        this.label = new JLabel();
        this.label.setFont(this.label.getFont().deriveFont(64.0f));
        this.label.setBounds(0, 520, 500, 150);
        this.label.setText("Minesweeper!");
        this.add(label);
    }

    private void setUpGameInfo()
    {
        this.gameInfo = new JLabel();
        this.gameInfo.setBounds(490, 530, 400, 100);
        this.gameInfo.setFont(this.gameInfo.getFont().deriveFont(30.0f));
        this.add(gameInfo);
    }

    private void setUpBoard()
    {
        this.board = new Board(900, 560, this.gameInfo);
        this.add(board);
    }

    private void play()
    {
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        Minesweeper game = new Minesweeper();
        game.play();
    }
}