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

        System.out.println("\n" + "GAME: Rolling dice to see who goes first...\n");

        return playerOneRoll > playerTwoRoll;
    }

    public static void attack(Player attacker, Player opponent)
    {
        if(opponent.getHP() == 0)
        {
            return;
        }

        int rollHit = attacker.rollHit();

        System.out.print("GAME: "+ attacker.getName() + " attacks " + opponent.getName() + " with " + attacker.getWeapon().getName() + " (" + rollHit + " to hit)");

        if(rollHit >= opponent.getAC())
        {
            int attackingDamage = attacker.getWeapon().rollDamage() + Player.calculateModifier(attacker.getSTR());

            if(attackingDamage < 0)
            {
                attackingDamage = 0;
            }

            opponent.setHP(opponent.getHP() - attackingDamage);

            if(opponent.getHP() <= 0)
            {
                opponent.setHP(0);
            }

            System.out.print("...HITS!\n");

            System.out.print(opponent.getName() + " took " + attackingDamage + " amount of damage.\n\n");
        }
        else
        {
            System.out.print("...MISSES!\n ");
        }
    }

    public static boolean disarm(Player attacker, Player opponent)
    {
        int attackerRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(attacker.getSTR());
        int opponentRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(opponent.getSTR());

        if(attackerRoll > opponentRoll)
        {
            System.out.print("GAME: " + attacker.getName() + " has disarmed " + opponent.getName() + "\n");
            opponent.setDisarmed(true);
            return true;
        }

        System.out.println("GAME: " + attacker.getName() +  " was not able to disarm " + opponent.getName() + "\n");
        return false;
    }
}
