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

        rowValue = randomNum.nextInt(1,5);
        columnValue = randomNum.nextInt(1,5);
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

    public void moveRight(Map temp, Player player)
    {

        if(this.columnValue == temp.getNumColumns())
        {
            System.out.println("You are on the border of the map.\nCannot move right.");
            return;
        }
        else if(temp.isOccupied(player.getPosition().getRowValue(), player.getPosition().getColumnValue() + 1))
        {
            System.out.println("CANNOT MOVE");
            return;
        }
        else
        {
            this.columnValue++;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue() - 1);
        }
    }

    public void moveLeft(Map temp, Player player)
    {
        if(this.columnValue == 1)
        {
            System.out.println("You are on the border of the map.\nCannot move left.");
            return;
        }
        else if(temp.isOccupied(player.getPosition().getRowValue(), player.getPosition().getColumnValue() - 1))
        {
            System.out.println("CANNOT MOVE");
            return;
        }
        else
        {
            this.columnValue--;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue() + 1);
        }
    }

    public void moveUp(Map temp, Player player)
    {
        if(this.rowValue == 1)
        {
            System.out.println("You are on the top edge of the map.\nCannot move up.");
            return;
        }
        else if(temp.isOccupied(player.getPosition().getRowValue() - 1, player.getPosition().getColumnValue()))
        {
            System.out.println("CANNOT MOVE");
            return;
        }
        else
        {
            this.rowValue--;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue() + 1, player.getPosition().getColumnValue());
        }
    }

    public void moveDown(Map temp, Player player)
    {
        if(this.rowValue == temp.getNumRows())
        {
            System.out.println("You are on the bottom edge of the map.\nCannot move down.");
            return;
        }
        else if(temp.isOccupied(player.getPosition().getRowValue() + 1, player.getPosition().getColumnValue()))
        {
            System.out.println("CANNOT MOVE");
            return;
        }
        else
        {
            this.rowValue++;
            temp.setCharacter(player.getPosition().getRowValue(), player.getPosition().getColumnValue(), player);
            temp.removeCharacter(player.getPosition().getRowValue() - 1, player.getPosition().getColumnValue());
        }
    }

    public static void main(String[] args) {
//        Player Goose = new Player("Goose", 10, 10, 10, new Weapon());
//        Player Dummy = new Player("Dummy", 10, 10, 10, new Weapon());
//
//        Map currentMap = new Map(5, 5);
//
////        currentMap.insertCharacter(Goose);
//        currentMap.setCharacter(2,1,Goose);
//        Goose.getPosition().setRowValue(2);
//        Goose.getPosition().setColumnValue(1);
//
//        Goose.getPosition().printPosition();
//
//        currentMap.insertCharacter(Dummy);
//
//        currentMap.printMap();
//
//        Goose.getPosition().moveLeft(currentMap, Goose);
//
//        System.out.println("AFTER MOVING");
//
//        Goose.getPosition().printPosition();
//        currentMap.printMap();
    }
}
