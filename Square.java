import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Square extends JPanel
{
    final Color UNOPENED = new Color(144, 252, 3);
    final Color OPENED = new Color(156, 247, 180);
    final Color BOMB = new Color(255, 0, 0);
    final Border BORDER = BorderFactory.createLineBorder(Color.black, 2);
    private JLabel label = new JLabel();
    private boolean isBomb = false;
    private boolean isOpened = false;
    private boolean isFlagged = false;
    private int adjBombs = 0;

    public Square()
    {
        this.label.setText("");
        this.add(label);
        this.setBackground(UNOPENED);
        this.setBorder(BORDER);
    }
    
    public boolean isABomb()
    {
        return isBomb;
    }

    public void open(Square[][] grid, int i, int j)
    {
        this.isOpened = true;
        this.isFlagged = false;
        this.label.setText("");
        if (this.isBomb)
        {
            this.setBackground(BOMB);
            return;
        }
        this.setBackground(OPENED);
        if (this.adjBombs > 0)
        {
            this.label.setText(""+adjBombs);
            return;
        }

        //The Square is blank, so open the adj ones
        if (i > 0 && !grid[i-1][j].isOpened()) //UP
        grid[i-1][j].open(grid, i-1, j);

        if (i < grid.length-1 && !grid[i+1][j].isOpened()) //DOWN
        grid[i+1][j].open(grid, i+1, j);
        
        if (j > 0 && !grid[i][j-1].isOpened()) //LEFT
        grid[i][j-1].open(grid, i, j-1);
        
        if (j < grid[i].length-1 && !grid[i][j+1].isOpened()) //RIGHT
        grid[i][j+1].open(grid, i, j+1);

        if (i > 0 && j > 0 && !grid[i-1][j-1].isOpened()) //TOP LEFT
        grid[i-1][j-1].open(grid, i-1, j-1);
        
        if (i < grid.length-1 && j > 0 && !grid[i+1][j-1].isOpened()) //TOP RIGHT
        grid[i+1][j-1].open(grid, i+1, j-1);
        
        if (i > 0 && j < grid[i].length-1 && !grid[i-1][j+1].isOpened()) //BOTTOM LEFT
        grid[i-1][j+1].open(grid, i-1, j+1);
        
        if (i < grid.length-1 && j < grid[i].length-1 && !grid[i+1][j+1].isOpened()) //BOTTOM RIGHT
        grid[i+1][j+1].open(grid, i+1, j +1);
        return;
    }
    
    public void toggleFlag()
    {
        if(this.isFlagged())
        {
            this.label.setText("");
            this.isFlagged = false;
        }
        else
        {
            this.label.setSize(this.getWidth()/2, this.getHeight()/2);
            this.label.setText("!");
            this.isFlagged = true;
        }
    }
    
    public void placeNumber(int bombs)
    {
        this.adjBombs = bombs;
    }

    public boolean isFlagged()
    {
        return this.isFlagged;
    }
    
    public boolean isOpened()
    {
        return this.isOpened;
    }

    public void setBomb()
    {
        this.isBomb = true;
    }

    public void reset()
    {
        this.isBomb = false;
        this.isFlagged = false;
        this.isOpened = false;
        this.label.setText("");
        this.setBackground(UNOPENED);
    }
}