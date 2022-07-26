package files;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    // Instance Fields
    private Creature[][] MAP_GRID;
    private int numRows;
    private int numColumns;

    // Constructors
    public Map() {
        MAP_GRID = new Creature[25][25];
        numRows = 25;
        numColumns = 25;
    }

    public Map(int r, int c) {
        MAP_GRID = new Creature[r][c];
        numRows = r;
        numColumns = c;
    }

    // Getters
    public int getNumRows() {
        return this.numRows;
    }

    public int getNumColumns() {
        return this.numColumns;
    }

    // Methods
    public void printMap() {
        System.out.print("        ");
        for (int i = 0; i < this.getNumColumns(); i++) {
            System.out.printf(" %2c%-2d", 'C', i + 1);
        }

        System.out.println();

        for (int i = 0; i < this.getNumRows(); i++) {
            System.out.printf("R%-3d: ", i + 1);
            for (int j = 0; j < this.getNumColumns(); j++) {
                if (this.MAP_GRID[i][j] != null) {
                    if (this.MAP_GRID[i][j] instanceof Player) {
                        System.out.printf("%5c", this.MAP_GRID[i][j].getName().charAt(0));
                    } else {
                        System.out.printf("%5c", 'M');
                    }
                } else {
                    System.out.printf("%5c", '.');
                }
            }

            System.out.println();
        }
    }

    // THIS FUNCTION TAKES CARE OF THE SITUATION IN WHICH A MONSTER IS SET TO ANOTHER SPOT AS ANOTHER MONSTER
    public void insertCharacter(Creature temp) {
        Random random = new Random();
        int row = temp.getPosition().getRowValue();
        int column = temp.getPosition().getColumnValue();

        if (this.MAP_GRID[row - 1][column - 1] != null) {
            temp.getPosition().setRowValue(random.nextInt(1, this.getNumRows()));
            temp.getPosition().setColumnValue(random.nextInt(1, this.getNumColumns()));

            this.insertCharacter(temp);
        } else {
            this.setCharacter(row, column, temp);
        }
    }

    public void removeCharacter(int r, int c)
    {
        this.MAP_GRID[r - 1][c - 1] = null;
    }

    public boolean isSpotOccupied(int r, int c) {
        if (r >= this.numRows || r <= 0) {
            return false;
        }

        if (c >= this.numColumns || c <= 0) {
            return false;
        }

        if (this.MAP_GRID[r - 1][c - 1] != null) {
            return true;
        }

        return false;
    }

    public void setCharacter(int r, int c, Creature temp) {
        this.MAP_GRID[r - 1][c - 1] = temp;
    }

    public boolean checkAdjacency(int row, int column)
    {
        // CHECKS RIGHT
        if (column != this.numColumns && this.MAP_GRID[row - 1][column] != null)
        {
            return true;
        }
        // CHECKS LEFT
        else if (column != 1 && this.MAP_GRID[row - 1][column - 2] != null)
        {
            return true;
        }
        // CHECKS BELOW
        else if (row != this.numRows && this.MAP_GRID[row][column - 1] != null)
        {
            return true;
        }
        // CHECKS ABOVE
        else if (row != 1 && this.MAP_GRID[row - 2][column - 1] != null)
        {
            return true;
        }

        return false;
    }

    public void clearMap() {
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColumns; j++) {
                if (this.MAP_GRID[i][j] != null) {
                    this.MAP_GRID[i][j] = null;
                }
            }
        }
    }

    public Creature getCreature(int r, int c)
    {
        return MAP_GRID[r - 1][c - 1];
    }

    public ArrayList<Creature> getAdjacentCreatures(Creature currentCreature)
    {
        ArrayList<Creature> creaturesAdjacent = new ArrayList<>();

        int currentCreatureRow = currentCreature.getPosition().getRowValue();
        int currentCreatureColumn = currentCreature.getPosition().getColumnValue();

        // CHECKS RIGHT
        if(currentCreatureColumn != this.numColumns && this.MAP_GRID[currentCreatureRow - 1][currentCreatureColumn] != null)
        {
            creaturesAdjacent.add(this.MAP_GRID[currentCreatureRow - 1][currentCreatureColumn]);
        }
        // CHECKS LEFT
        if(currentCreatureColumn != 1 && this.MAP_GRID[currentCreatureRow - 1][currentCreatureColumn - 2] != null)
        {
            creaturesAdjacent.add(this.MAP_GRID[currentCreatureRow - 1][currentCreatureColumn - 2]);
        }
        // CHECKS BELOW
        if(currentCreatureRow != this.numRows && this.MAP_GRID[currentCreatureRow][currentCreatureColumn - 1] != null)
        {
            creaturesAdjacent.add(this.MAP_GRID[currentCreatureRow][currentCreatureColumn - 1]);
        }
        // CHECKS ABOVE
        if(currentCreatureRow != 1 && this.MAP_GRID[currentCreatureRow - 2][currentCreatureColumn - 1] != null)
        {
            creaturesAdjacent.add(this.MAP_GRID[currentCreatureRow - 2][currentCreatureColumn - 1]);
        }

        return creaturesAdjacent;
    }
}

