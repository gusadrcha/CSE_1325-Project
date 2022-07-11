public class Weapon
{
    // Instance Fields
    private String name;
    private String damage;
    private int hitBonus;

    // Constructors
    public Weapon()
    {
        name = "Hands";
        damage = "1d6";
        hitBonus = 0;
    }

    public Weapon(String n, String dt, int hb)
    {
        name = n;
        damage = dt;
        hitBonus = hb;
    }

    // Getters
    public String getName()
    {
        return this.name;
    }

    public String getDamage()
    {
        return this.damage;
    }

    public int getHitBonus()
    {
        return this.hitBonus;
    }

    // Setters
    public void setName(String n)
    {
        this.name = n;
    }

    public void setDamage(String dt)
    {
        this.damage = dt;
    }

    public void setHitBonus(int hb)
    {
        this.hitBonus = hb;
    }

    // Methods
    public int rollDamage()
    {
        return GameUtility.rollDice(this.damage) + this.hitBonus;
    }

    // Overrides
    @Override
    public String toString()
    {
        return this.name + ", Damage: " + this.damage + ", Bonus To-Hit: " + this.hitBonus;
    }
}
