package files;

import java.time.LocalDateTime;

public abstract class Creature implements Comparable
{
    // Instance Fields
    private String name;
    private String creationDate;
    private int HP;
    private int AC;
    private int STR;
    private int DEX;
    private int CON;
    private Movement position;
    private int roll;
    private int x;
    private int y;

    private int totalMovement = 5;
    private int currentMovement = 5;

    // Constructors
    public Creature()
    {
        this.name = "No one";
        this.creationDate = LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getDayOfMonth() + "-" +LocalDateTime.now().getYear();
        this.HP = 50;
        this.AC = 15;
        this.STR = 0;
        this.DEX = 0;
        this.CON = 0;
    }

    // Constructor for creating a new character during the character creation menu
    public Creature(String n, int str, int dex, int con)
    {
        this.name = n;
        this.creationDate = LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getDayOfMonth() + "-" +LocalDateTime.now().getYear();

        if(con - 5 < 0)
        {
            this.HP = 50;
        }
        else
        {
            this.HP = 50 + (con - 5);
        }

        if(dex - 5 < 0)
        {
            this.AC = 15;
        }
        else
        {
            this.AC = 15 + (dex -5);
        }

        this.STR = str;
        this.DEX = dex;
        this.CON = con;
        this.position = new Movement();
    }

    // Constructor for file loading
    public Creature(String n, String cd, int hp, int str, int dex, int con)
    {
        this.name = n;
        this.creationDate = cd;
        this.HP = hp;

        if(dex - 5 < 0)
        {
            this.AC = 15;
        }
        else
        {
            this.AC = 15 + (dex -5);
        }

        this.STR = str;
        this.DEX = dex;
        this.CON = con;
        this.position = new Movement();
    }

    public Creature(String n, int hp, int ac, int str, int dex, int con)
    {
        this.name = n;
        this.HP = hp;
        this.AC = ac;
        this.STR = str;
        this.DEX = dex;
        this.CON = con;
        this.position = new Movement();
    }

    // Methods
    public abstract void attack(Creature opponent);

    public void takeDamage(int damage)
    {
        if(this.HP - damage < 0)
        {
            this.HP = 0;
        }
        else
        {
            this.HP -= damage;
        }
    }

    public static int calculateModifier(int stat)
    {
        return stat - 5;
    }

    // Setters
    public void setName(String name)
    {
        this.name = name;
    }

    public void setCreationDate(String cd)
    {
        this.creationDate = cd;
    }

    public void setHP(int HP)
    {
        this.HP = HP;
    }

    public void setAC(int AC)
    {
        this.AC = AC;
    }

    public void setSTR(int STR)
    {
        this.STR = STR;
    }

    public void setDEX(int DEX)
    {
        this.DEX = DEX;
    }

    public void setCON(int CON)
    {
        this.CON = CON;
    }

    public void setPosition(Movement p)
    {
        this.position = p;
    }

    public void setRoll(int r)
    {
        this.roll = r;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Getters
    public String getName()
    {
        return this.name;
    }

    public String getCreationDate()
    {
        return this.creationDate;
    }

    public int getHP()
    {
        return this.HP;
    }

    public int getAC()
    {
        return this.AC;
    }

    public int getSTR()
    {
        return this.STR;
    }

    public int getDEX()
    {
        return this.DEX;
    }

    public int getCON()
    {
        return this.CON;
    }

    public Movement getPosition()
    {
        return this.position;
    }

    public int getRoll()
    {
        return this.roll;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getCurrentMovement() {
        return this.currentMovement;
    }

    public void setCurrentMovement(int movement) {
        this.currentMovement = movement;
    }

    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Object o)
    {
        Creature c = (Creature) o;

        return c.roll - this.roll;
    }
}
