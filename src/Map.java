import java.util.Random;

public class Map
{
    // Instance Fields
    private Player[][] MAP_GRID;
    private int numRows;
    private int numColumns;

    // Constructors
    public Map()
    {
        MAP_GRID = new Player[25][25];
        numRows = 25;
        numColumns = 25;
    }

    public Map(int r, int c)
    {
        MAP_GRID = new Player[r][c];
        numRows = r;
        numColumns = c;
    }

    // Getters
    public int getNumRows()
    {
        return this.numRows;
    }

    public int getNumColumns()
    {
        return this.numColumns;
    }

    // Methods

    public void printMap()
    {
        System.out.print("     ");
        for(int i = 0; i < this.getNumColumns(); i++)
        {
            System.out.printf("C%d ", i + 1);
        }

        System.out.println();

        for(int i = 0; i < this.getNumRows(); i++)
        {
            System.out.printf("R%-3d: ", i + 1);
            for (int j = 0; j < this.getNumColumns(); j++)
            {
                if (this.MAP_GRID[i][j] != null)
                {
                    System.out.print("" + 1 + "  ");
                } else
                {
                    System.out.print("" + 0 + "  ");
                }
            }

            System.out.println();
        }
    }
    public void insertCharacter(Player temp)
    {
        Random random = new Random();
        int row = temp.getPosition().getRowValue();
        int column = temp.getPosition().getColumnValue();

        if(this.MAP_GRID[row - 1][column - 1] != null)
        {
            temp.getPosition().setRowValue(random.nextInt(1, this.getNumRows()));
            temp.getPosition().setColumnValue(random.nextInt(1, this.getNumColumns()));

            this.insertCharacter(temp);
        }
        else
        {
            this.setCharacter(row, column, temp);
        }
    }

    public void removeCharacter(int r, int c)
    {
        this.MAP_GRID[r - 1][c - 1] = null;
    }

    public boolean isOccupied(int r, int c)
    {
        if(r >= this.numRows || r <= 0)
        {
            return false;
        }

        if(c >= this.numColumns || c <= 0)
        {
           return false;
        }

        if(this.MAP_GRID[r - 1][c - 1] != null)
        {
            return true;
        }

        return false;
    }

    public void setCharacter(int r, int c, Player temp)
    {
        this.MAP_GRID[r - 1][c - 1] = temp;
    }
}
