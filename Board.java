import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener, ActionListener
{
    private Square[][] grid;
    private boolean gameRunning = true;
    final int BOMBCOUNT = 50;
    final int DIMENSION = 20;
    private int flagCount;
    private JLabel gameInfo;
    
    public Board (int width, int height, JLabel gameInfo)
    {
        this.setLayout(new GridLayout(DIMENSION,DIMENSION));
        this.setSize(width, height);
        grid = new Square[DIMENSION][DIMENSION];
        this.flagCount = 0;
        this.createBoard();
        this.gameInfo = gameInfo;
        this.updateDisplay();
    }

    private void createBoard()
    {
        for (int i = 0; i < DIMENSION; i ++)
        {
            for (int j = 0; j < DIMENSION; j ++)
            {
                grid[i][j] = new Square();
                this.add(this.grid[i][j]);
                this.grid[i][j].addMouseListener(this);
            }
        }

        this.placeBombs();
        this.placeNumbers();
        this.gameRunning = true;
    }

    private void placeBombs()
    {
        int count = 0;
        int currentBombs = 0;
        double ratio =  ((double)BOMBCOUNT/ (double)DIMENSION /(double)DIMENSION);
        for(int i = 0; i < DIMENSION; i ++)
        {
            for (int j = 0; j < DIMENSION; j++)
            {
                if (currentBombs < BOMBCOUNT && Math.random() > (1.0 - (ratio)))
                {
                    grid[i][j].setBomb();
                    currentBombs += 1;
                }
                else if(count == (DIMENSION*DIMENSION) - (BOMBCOUNT-currentBombs))
                {
                    grid[i][j].setBomb();
                    currentBombs += 1;
                }
                count += 1;
            }
        }
    }

    private void placeNumbers()
    {
        for (int i = 0; i < DIMENSION; i++)
        {
            for (int j = 0; j < DIMENSION; j++)
            {
                int bombs = checkForBombs(i, j);
                if (!grid[i][j].isABomb())
                {
                    grid[i][j].placeNumber(bombs);
                }
            }
        }
    }

    private int checkForBombs(int i, int j)
    {
        int bombs = 0;
        if (i > 0 && grid[i-1][j].isABomb()) //UP
        bombs += 1;
        if (i < grid.length-1 && grid[i+1][j].isABomb()) //DOWN
        bombs += 1;
        if (j > 0 && grid[i][j-1].isABomb()) //LEFT
        bombs += 1;
        if (j < grid[i].length-1 && grid[i][j+1].isABomb()) //RIGHT
        bombs += 1;
        if (i > 0 && j > 0 && grid[i-1][j-1].isABomb()) //TOP LEFT
        bombs += 1;
        if (i > 0 && j < grid[i].length-1 && grid[i-1][j+1].isABomb()) //TOP RIGHT
        bombs += 1;
        if (i < grid.length-1 && j > 0 && grid[i+1][j-1].isABomb()) //BOTTOM LEFT
        bombs +=1;
        if (i < grid.length-1 && j < grid[i].length-1 && grid[i+1][j+1].isABomb()) //BOTTOM RIGHT
        bombs += 1;
        
        return bombs;
    }

    private void reset()
    {
        for(int i = 0; i < DIMENSION; i++)
        {
            for(int j = 0; j < DIMENSION; j++)
            {
                grid[i][j].reset();
            }
        }
        this.placeBombs();
        this.placeNumbers();
        this.flagCount = 0;
        this.updateDisplay();
        this.gameRunning = true;
    }

    private void open(Square s, int i, int j)
    {
        this.gameRunning = !s.isABomb();
        s.open(this.grid, i, j);
        if(!this.gameRunning)
        {
            this.openAll();
        }
    }
    
    private void openAll()
    {
        for(int i = 0; i < DIMENSION; i++)
        {
            for(int j = 0; j < DIMENSION; j++)
            {
                grid[i][j].open(this.grid, i, j);
            }     
        }
    }

    private void toggleFlag(Square s)
    {
        s.toggleFlag();
        this.updateFlags();
    }

    private void updateFlags()
    {
        this.flagCount = 0;
        for (int i = 0; i < DIMENSION; i++)
        {
            for (int j = 0; j < DIMENSION; j ++)
            {
                if(grid[i][j].isFlagged())
                {
                    this.flagCount += 1;
                }
            }
        }
    }

    private void updateDisplay()
    {
        this.gameInfo.setText("Bombs:" + BOMBCOUNT + "\t \t Flags Placed:" + this.flagCount);
    }

    private void checkForWin()
    {
        int squaresToOpen = (DIMENSION*DIMENSION) - BOMBCOUNT;
        if (this.gameRunning)
        {
            int squaresOpened = 0;
            for (int i = 0; i < DIMENSION; i++)
            {
                for (int j = 0; j < DIMENSION; j++)
                {
                    if (grid[i][j].isOpened())
                    {
                        squaresOpened+=1;
                    }
                }
            }
            if(squaresToOpen == squaresOpened && flagCount == BOMBCOUNT)
            {
                this.gameInfo.setText("YOU WIN!!!");
                this.gameRunning = false;
            }
        }
    }

    @Override public void mousePressed(MouseEvent e)
    {
        int mouseButton = e.getButton();
        if (gameRunning)
        {
            Square s = (Square) e.getSource();
            for(int i = 0; i < DIMENSION; i++)
            {
                for(int j  = 0; j < DIMENSION; j++)
                {
                    if (s == grid[i][j])
                    {
                        if (mouseButton == 1 && !s.isFlagged())
                        this.open(s, i, j);
                        else if (mouseButton == 3 && !s.isOpened())
                        this.toggleFlag(s);
                    }
                }
            }
            this.updateFlags();
            this.updateDisplay();
            this.checkForWin();
        }
    }

    @Override public void actionPerformed(ActionEvent e) 
    {
        this.reset();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}