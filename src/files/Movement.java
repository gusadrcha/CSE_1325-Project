import java.util.Random;

public class Movement
{
    // Instance Variables
    private int rowValue;
    private int columnValue;

    // Constructors
    public Movement()
    {
        Random randomNum = new Random();

        rowValue = randomNum.nextInt(1,25);
        columnValue = randomNum.nextInt(1,25);
    }

    // Getters
    public int getRowValue()
    {
        return this.rowValue;
    }

    public int getColumnValue()
    {
        return this.columnValue;
    }

    // Setters
    public void setRowValue(int rv)
    {
        this.rowValue = rv;
    }

    public void setColumnValue(int cv)
    {
        this.columnValue = cv;
    }

    // Methods
    public void printPosition()
    {
        System.out.println("Player is at\nRow: " + this.rowValue + "\nColumn: " + this.columnValue);
    }

    public boolean moveRight(Map temp, Player player)
    {

        if(this.columnValue == temp.getNumColumns())
        {
            System.out.print("GAME: You are on the right border of the map.\n");
            System.out.print("GAME: Cannot move right.\n");
            return false;
        }
        else if(temp.isSpotOccupied(player.getPosition().getRowValue(), player.getPosition().getColumnValue() + 1))
        {
            System.out.print("GAME: Cannot move, obstacle in the way.\n");
            return false;
        }
        else
        {
            this.columnValue++;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue() - 1);
            return true;
        }
    }

    public boolean moveLeft(Map temp, Player player)
    {
        if(this.columnValue == 1)
        {
            System.out.print("GAME: You are on the left border of the map.\n");
            System.out.print("GAME: Cannot move left.\n");
            return false;
        }
        else if(temp.isSpotOccupied(player.getPosition().getRowValue(), player.getPosition().getColumnValue() - 1))
        {
            System.out.print("Cannot move, obstacle in the way.\n");
            return false;
        }
        else
        {
            this.columnValue--;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue() + 1);
            return true;
        }
    }

    public boolean moveUp(Map temp, Player player)
    {
        if(this.rowValue == 1)
        {
            System.out.print("GAME: You are on the top border of the map.\n");
            System.out.print("GAME: Cannot move up.\n");
            return false;
        }
        else if(temp.isSpotOccupied(player.getPosition().getRowValue() - 1, player.getPosition().getColumnValue()))
        {
            System.out.print("Cannot move, obstacle in the way.\n");
            return false;
        }
        else
        {
            this.rowValue--;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue() + 1, player.getPosition().getColumnValue());
            return true;
        }
    }

    public boolean moveDown(Map temp, Player player)
    {
        if(this.rowValue == temp.getNumRows())
        {
            System.out.print("GAME: You are on the bottom border of the map.\n");
            System.out.print("GAME: Cannot move down.\n");
            return false;
        }
        else if(temp.isSpotOccupied(player.getPosition().getRowValue() + 1, player.getPosition().getColumnValue()))
        {
            System.out.print("Cannot move, obstacle in the way.\n");
            return false;
        }
        else
        {
            this.rowValue++;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue() - 1, player.getPosition().getColumnValue());
            return true;
        }
    }
}
