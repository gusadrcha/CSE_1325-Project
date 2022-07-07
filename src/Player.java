import java.time.LocalDateTime;

public class Player
{
    // Instance Fields
    private String name;
    private String creationDate;
    private int HP = 50;
    private int AC = 15;
    private int STR;
    private int DEX;
    private int CON;
    private boolean isDisarmed = false;
    private Weapon weapon;
    private Movement position;

    // Constructors

    public Player()
    {
        name = "Anonymous";
        creationDate = LocalDateTime.now().toString();
        STR = 1;
        DEX = 1;
        CON = 1;
        weapon = new Weapon();
        position = new Movement();
    }

    public Player(String n, int str, int dex, int con, Weapon w)
    {
        name = n;
        creationDate = LocalDateTime.now().toString();
        AC += Player.calculateModifier(dex);
        HP += Player.calculateModifier(AC);
        STR = str;
        DEX = dex;
        CON = con;
        weapon = w;
        position = new Movement();
    }

    // Setters

    public void setName(String n)
    {
        name = n;
    }

    public void setHP(int hp)
    {
        HP = hp;
    }

    public void setAC(int ac)
    {
        AC = ac;
    }

    public void setSTR(int str)
    {
        STR = str;
    }

    public void setDEX(int dex)
    {
        DEX = dex;
    }

    public void setCON(int con)
    {
        CON = con;
    }

    public void setDisarmed(boolean d)
    {
        isDisarmed = d;
    }

    public void setWeapon(Weapon w)
    {
        weapon = w;
    }

    public void setPosition(Movement p)
    {
        position = p;
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

    public boolean getIsDisarmed()
    {
        return isDisarmed;
    }

    public Weapon getWeapon()
    {
        return this.weapon;
    }

    public Movement getPosition()
    {
        return this.position;
    }

    // Method(s)
    public static int calculateModifier(int stat)
    {
        return stat - 5;
    }

    // Override(s)
    @Override
    public String toString()
    {
        return String.format("Name: %s\nCreation Date: %s\nHP: %d\nSTR: %d\nDEX: %d\nCON: %d\n", name, creationDate,HP, STR, DEX, CON);
    }
}
