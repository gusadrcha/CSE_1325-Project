package files;

public class Monster extends Creature
{
    enum MonsterType
    {
        Humanoid, Fiend, Dragon
    }

    private MonsterType type;

    public Monster(String n, int hp , int ac, int str, int dex, int con, MonsterType type)
    {
        super(n, hp, ac, str, dex, con);
        this.type = type;
    }

    @Override
    public void attack(Creature target)
    {
        int rollHit = GameUtility.rollDice("1d20") + Creature.calculateModifier(this.getDEX());

        if(rollHit < 0)
            rollHit = 0;

        System.out.print("GAME: "+ this.getName() + " attacks " + target.getName() + " (" + rollHit + " to hit)");

        if(rollHit >= target.getAC())
        {
            int attackingDamage = GameUtility.rollDice("1d6" + Creature.calculateModifier(this.getSTR()));

            if(attackingDamage < 0)
            {
                attackingDamage = 0;
            }

            target.takeDamage(attackingDamage);

            System.out.print("...HITS!\n");
            System.out.print(target.getName() + " took " + attackingDamage + " amount of damage.\n\n");
        }
        else
        {
            System.out.print("...MISSES!\n");
        }

    }

    public static Monster loadFromCsv(String input)
    {
        try
        {
            String[] csvLine = input.trim().split(",");

            if(csvLine.length != 7)
                throw new CsvReadException(input);

            String name = csvLine[0], type = csvLine[6];
            int hp, ac, str, dex, con;

            if(!GameUtility.validateName(name))
            {
                return null;
            }

            hp = Integer.parseInt(csvLine[1]);
            ac = Integer.parseInt(csvLine[2]);
            str = Integer.parseInt(csvLine[3]);
            dex = Integer.parseInt(csvLine[4]);
            con = Integer.parseInt(csvLine[5]);

            if(type.equalsIgnoreCase("Humanoid"))
                return new Monster(name, hp, ac, str, dex, con, MonsterType.Humanoid);

            if(type.equalsIgnoreCase("Fiend"))
                return new Monster(name, hp, ac, str, dex, con, MonsterType.Fiend);

            if(type.equalsIgnoreCase("Dragon"))
                return new Monster(name, hp, ac, str, dex, con, MonsterType.Dragon);
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

        return null;
    }

    /*
    public void move(Map GAME_MAP, Creature player)
    {
        int timesMoved = 0;

        int monsterRow = this.getPosition().getRowValue();
        int monsterColumn = this.getPosition().getColumnValue();

        while(timesMoved != 5)
        {
            if(monsterRow <= player.getPosition().getRowValue() && )
            {
                this.getPosition().setRowValue(this.getPosition().getRowValue() + 1);
            }
            else
            {
                this.getPosition().setRowValue(this.getPosition().getRowValue() - 1);
            }
            timesMoved++;

        }
    }
     */

    @Override
    public String toString()
    {
        return this.getName() + " - " + this.type;
    }
}
