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
            System.out.println(attacker.getName().toUpperCase() + "'S ATTACK WAS A HIT");
            attackingDamage = GameUtility.rollDice(attacker.getWeapon().getDamage()) + Player.calculateModifier(attacker.getSTR());
            opponent.setHP(opponent.getHP() - attackingDamage);

            if(opponent.getHP() <= 0)
            {
                opponent.setHP(0);
            }
        }
        else
        {
            System.out.println(attacker.getName() + "'s attack was a miss...");
        }
    }
    public static boolean disarm(Player attacker, Player opponent)
    {
        int attackerRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(attacker.getSTR());
        int opponentRoll = GameUtility.rollDice("1d20") + Player.calculateModifier(opponent.getSTR());

        if(attackerRoll > opponentRoll)
        {
            System.out.println(attacker.getName() + " DISARMED " + opponent.getName() + "\n");
            return true;
        }

        System.out.println(attacker.getName() +  " was not able to disarm " + opponent.getName() + "\n");
        return false;
    }
//    public static void main(String[] args)
//    {
//        //----- TESTING PURPOSES -----
//        Player Player1 = new Player("Goose", 10, 10, 10, new Weapon("Greataxe", "1d12", 4));
//        Player Player2 = new Player("Dummy", 10, 10, 10, new Weapon("Greataxe", "1d12", 4));
//
//        int roundsDisarmed1 = 0;
//        int roundsDisarmed2 = 0;
//
//        System.out.println(Player1);
//        System.out.println(Player2);
//
//        Player2.setDisarmed(Combat.disarm(Player1, Player2));
//
//        if(!Player2.getIsDisarmed())
//        {
//            Player1.setDisarmed(Combat.disarm(Player2, Player1));
//        }
//
//        int roundNumber = 1;
//
//        while(Player1.getHP() > 0 && Player2.getHP() > 0)
//        {
//            System.out.println("ROUND " + roundNumber + "\n--------");
//
//            if(Player1.getIsDisarmed() == false && roundsDisarmed1 == 0)
//            {
//                Combat.attack(Player1, Player2);
//                System.out.println("Player2's Health: " + Player2.getHP() + "\n");
//            }
//            else
//            {
//                roundsDisarmed1--;
//            }
//
//            if(Player2.getIsDisarmed() == false && roundsDisarmed2 == 0)
//            {
//                Combat.attack(Player2, Player1);
//                System.out.println("Player1's Health: " + Player1.getHP() + "\n");
//            }
//            else
//            {
//                roundsDisarmed2--;
//            }
//
//            roundNumber++;
//        }
//
//        System.out.println("\n" + Player1);
//        System.out.println(Player2);
//    }
}
