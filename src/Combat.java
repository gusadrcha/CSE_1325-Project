public class Combat
{
    public static boolean rollD20(Player one, Player two)
    {
        int playerOneRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(one.getDEX());
        int playerTwoRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(two.getDEX());

        if(playerTwoRoll == playerOneRoll)
        {
            return rollD20(one, two);
        }

        System.out.println("\nROLLING D20 DICE...");

        return playerOneRoll > playerTwoRoll;
    }

    public static void attack(Player attacker, Player opponent)
    {
        if(opponent.getHP() == 0)
        {
            return;
        }

        int attackingPlayerRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(attacker.getDEX()) + attacker.getWeapon().getHitBonus();
        int attackingDamage;

        if(attackingPlayerRoll >= opponent.getAC())
        {
            System.out.print(attacker.getName().toUpperCase() + "'S ATTACK WAS A HIT ");
            attackingDamage = GameUtility.rollDice(attacker.getWeapon().getDamage()) + Player.calculateModifier(attacker.getSTR());
            System.out.println("DAMAGE DEALT: " + attackingDamage + "\n");
            opponent.setHP(opponent.getHP() - attackingDamage);

            if(opponent.getHP() <= 0)
            {
                opponent.setHP(0);
            }

            System.out.println(opponent.getName() + " HAS " + opponent.getHP() + " HP REMAINING\n");
        }
        else
        {
            System.out.println(attacker.getName() + "'S ATTACK WAS A MISS...\n");
        }
    }

    public static boolean disarm(Player attacker, Player opponent)
    {
        int attackerRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(attacker.getSTR());
        int opponentRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(opponent.getSTR());

        if(attackerRoll > opponentRoll)
        {
            System.out.println(attacker.getName() + " DISARMED " + opponent.getName() + "\n");
            opponent.setDisarmed(true);
            return true;
        }

        System.out.println(attacker.getName() +  " WAS NOT ABLE TO DISARM " + opponent.getName() + "\n");
        return false;
    }
}
