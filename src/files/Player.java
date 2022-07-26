package files;

public class Player extends Creature
{
    // Instance Fields
    private boolean isDisarmed = false;
    private Weapon weapon;
    private int disarmTimer = -1;

    // Constructors
    public Player()
    {
        super();
        weapon = new Weapon();
    }

    // Constructor used to create a new player during the character menu
    public Player(String n, int str, int dex, int con, Weapon w)
    {
        super(n, str, dex, con);
        weapon = w;
    }

    // Constructor used for when loading in a character from save file
    public Player(String n, String cd, int hp, int str, int dex, int con, Weapon w)
    {
        super(n, cd, hp, str, dex, con);
        weapon = w;
    }

    // Setters
    public void setWeapon(Weapon w)
    {
        weapon = w;
    }

    public void setDisarmed(boolean disarmed)
    {
        isDisarmed = disarmed;
    }

    public void setDisarmTimer(int dt)
    {
        disarmTimer = dt;
    }

    // Getters
    public int getDisarmTimer()
    {
        return this.disarmTimer;
    }

    public boolean getIsDisarmed()
    {
        return this.isDisarmed;
    }

    public Weapon getWeapon()
    {
        return this.weapon;
    }

    // Method(s)
    public static Player loadFromCsv(String input)
    {
        try
        {
            // temp variables
            String[] csvLine = input.trim().split(",");
            String name = null, creationDate = null, weaponName = null, weaponDT = null;
            int hp = 0, str = 0, dex  = 0, con = 0, wb = 0;

            if(!GameUtility.validateName(csvLine[0]))
                return null;

            if(csvLine.length != 9)
                throw new CsvReadException(input);

            name = csvLine[0];
            creationDate = csvLine[1];
            hp = Integer.parseInt(csvLine[2]);
            str = Integer.parseInt(csvLine[3]);
            dex = Integer.parseInt(csvLine[4]);
            con = Integer.parseInt(csvLine[5]);
            weaponName = csvLine[6];
            weaponDT = csvLine[7];
            wb = Integer.parseInt(csvLine[8]);

            return new Player(name, creationDate, hp, str, dex, con, new Weapon(weaponName, weaponDT, wb));
        }
        catch(CsvReadException e)
        {
            System.out.println(e.toString());
            return null;
        }
        catch(NumberFormatException e)
        {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public void attack(Creature opponent)
    {
        int rollHit = this.rollHit();

        if(rollHit < 0)
            rollHit = 0;

        System.out.print("GAME: "+ this.getName() + " attacks " + opponent.getName() + " with " + this.getWeapon().getName() + " (" + rollHit + " to hit)");

        if(rollHit >= opponent.getAC())
        {
            int attackingDamage = this.getWeapon().rollDamage();

            if(attackingDamage < 0)
            {
                attackingDamage = 0;
            }

            opponent.takeDamage(attackingDamage);

            System.out.print("...HITS!\n");
            System.out.print(opponent.getName() + " took " + attackingDamage + " amount of damage.\n\n");
        }
        else
        {
            System.out.print("...MISSES!\n");
        }
    }

    public int rollHit()
    {
        return GameUtility.rollDice("1d20") + Creature.calculateModifier(this.getDEX()) + this.weapon.getHitBonus();
    }

    // Override(s)
    @Override
    public String toString()
    {
        return String.format("Name: %s\nCreation Date: %s\nHP: %d\nSTR: %d\nDEX: %d\nCON: %d\n", this.getName(), this.getCreationDate(),this.getHP(), this.getSTR(), this.getDEX(), this.getCON());
    }
}
