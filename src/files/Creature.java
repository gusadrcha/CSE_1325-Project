package files;


import java.time.LocalDateTime;

public abstract class Creature
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
        position = p;
    }

    // Getters
    public String getName()
    {
        return name;
    }

    public String getCreationDate()
    {
        return creationDate;
    }

    public int getHP()
    {
        return HP;
    }

    public int getAC()
    {
        return AC;
    }

    public int getSTR()
    {
        return STR;
    }

    public int getDEX()
    {
        return DEX;
    }

    public int getCON()
    {
        return CON;
    }

    public Movement getPosition()
    {
        return this.position;
    }
}
