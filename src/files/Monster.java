package files;

public class Monster extends Creature
{
    enum MonsterType
    {
        Humanoid, Fiend, Dragon
    }

    private MonsterType type;

    public Monster()
    {
        super();
    }

    public Monster(String n, int str, int dex, int con, MonsterType type)
    {
        super(n, str, dex, con);
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
            String[] csvLine = input.trim().split(",");
            String name = csvLine[0];
            int str, dex, con;

            if(!GameUtility.validateName(name))
            {
                System.out.println("HERE");
                return null;
            }

            str = Integer.parseInt(csvLine[1]);
            dex = Integer.parseInt(csvLine[2]);
            con = Integer.parseInt(csvLine[3]);

            return new Monster(name, str, dex, con, Monster.MonsterType.Humanoid);
    }

    @Override
    public String toString()
    {
        return this.getName() + " - " + this.type;
    }
}
