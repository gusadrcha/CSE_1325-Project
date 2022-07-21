package files;
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

        attacker.attack(opponent);
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
