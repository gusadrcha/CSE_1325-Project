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


    @Override
    public String toString()
    {
        return this.getName() + " - " + this.type;
    }
}
