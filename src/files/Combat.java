package files;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Combat
{
    public static void monsterTurn(Creature monster, Map GAME_MAP)
    {
        Random number = new Random();
        ArrayList<Creature> creaturesAdjacent = GAME_MAP.getAdjacentCreatures(monster);
        Creature temp;

        if(creaturesAdjacent.size() == 0)
        {
            return;
        }
        else
        {
            if(creaturesAdjacent.size() == 1)
            {
                temp = creaturesAdjacent.get(0);
            }
            else
            {
                temp = creaturesAdjacent.get(number.nextInt(creaturesAdjacent.size()));
            }

            if(temp instanceof Monster)
            {
                return;
            }
            else
            {
                monster.attack(temp);

                if(temp.getHP() == 0)
                {
                    System.out.println(monster.getName() + " killed " + temp.getName());
                    GAME_MAP.removeCharacter(temp.getPosition().getRowValue(), temp.getPosition().getColumnValue());
                }
            }
        }
    }

    public static void playerTurnPVE(Player player, Scanner gameInput, Map GAME_MAP)
    {
        int timesMoved = 0;
        int playerChoice;
        ArrayList<Creature> adjacentCreatures;
        boolean hasMoved = false, hasAttacked = false;
        Random number = new Random();

        while((!hasMoved && timesMoved != 5) || !hasAttacked)
        {
            if(GAME_MAP.checkAdjacency(player.getPosition().getRowValue(), player.getPosition().getColumnValue()))
            {
                System.out.print("\nGAME: " + player.getName() + " is next to a monster!!\n");
            }

            if(hasMoved && player.getIsDisarmed())
            {
                break;
            }

            System.out.println("\n" + player.getName() + " do you want to");
            System.out.println("1. Attack");
            System.out.println("2. Move Player\n");

            playerChoice = gameInput.nextInt();
            System.out.println();

            switch(playerChoice)
            {
                case 1:
                    if(!player.getIsDisarmed())
                    {
                        if(!hasAttacked)
                        {
                            if (GAME_MAP.checkAdjacency(player.getPosition().getRowValue(), player.getPosition().getColumnValue()))
                            {
                                adjacentCreatures = GAME_MAP.getAdjacentCreatures(player);
                                Creature temp;

                                if(adjacentCreatures.size() == 0)
                                {
                                    System.out.println(player.getName() + " is not near another creature.\n");
                                    return;
                                }
                                else
                                {
                                    if(adjacentCreatures.size() == 1)
                                    {
                                        temp = adjacentCreatures.get(0);
                                    }
                                    else
                                    {
                                        temp = adjacentCreatures.get(number.nextInt(adjacentCreatures.size()));

                                        while(temp instanceof Player)
                                        {
                                            temp = adjacentCreatures.get(number.nextInt(adjacentCreatures.size()));
                                        }
                                    }

                                    player.attack(temp);

                                    if(temp.getHP() == 0)
                                    {
                                        System.out.println(player.getName() + " killed " + temp.getName());
                                        GAME_MAP.removeCharacter(temp.getPosition().getRowValue(), temp.getPosition().getColumnValue());
                                    }
                                }
                                hasAttacked = true;
                            }
                            else
                            {
                                System.out.println("GAME: " + player.getName() + " is not close to another monster.");
                            }
                        }
                        else
                        {
                            System.out.println("GAME: " + player.getName() + " has already attempted an attack.");
                        }
                    }
                    break;

                case 2:
                    if(hasMoved && timesMoved == 5)
                    {
                        System.out.println("GAME: " + player.getName() +  " has already moved.");
                    }

                    while(timesMoved != 5 && !hasMoved)
                    {
                        GAME_MAP.printMap();

                        System.out.println("1. Move Up");
                        System.out.println("2. Move Down");
                        System.out.println("3. Move Left");
                        System.out.println("4. Move Right");
                        System.out.println("5. Forfeit Move\n");

                        playerChoice = gameInput.nextInt();
                        System.out.println();

                        switch (playerChoice)
                        {
                            case 1:
                                if(player.getPosition().moveUp(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved up one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 2:
                                if(player.getPosition().moveDown(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved down one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 3:
                                if(player.getPosition().moveLeft(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved left one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 4:
                                if(player.getPosition().moveRight(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved right one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 5:
                                System.out.println("GAME: " + player.getName() + " chose to stay in the same spot.");
                                timesMoved = 5;
                                hasMoved = true;
                                break;

                            default:
                                System.out.println("GAME: " + "Wrong input.");
                        }

                        if(GAME_MAP.checkAdjacency(player.getPosition().getRowValue(), player.getPosition().getColumnValue()))
                        {
                            hasMoved = true;
                            timesMoved = 5;
                        }
                        else if(timesMoved == 5)
                        {
                            hasAttacked = true;
                            hasMoved = true;
                        }
                    }
                    break;

                default:
                    System.out.println("GAME: Wrong input.");
            }
            GAME_MAP.printMap();
        }
    }

    public static void playerTurnPVP(Player player, Scanner gameInput, Map GAME_MAP)
    {
        ArrayList<Creature> adjacentCreatures;
        int playerChoice;
        int timesMoved = 0;
        boolean hasMoved = false, hasAttacked = false;
        Random number = new Random();

        while((!hasMoved && timesMoved != 5) || !hasAttacked)
        {
            if(GAME_MAP.checkAdjacency(player.getPosition().getRowValue(), player.getPosition().getColumnValue()))
            {
                System.out.print("\nGAME: " + player.getName() + " is next to another player!!\n");
            }

            if(hasMoved && player.getIsDisarmed())
            {
                break;
            }

            System.out.println("\n" + player.getName() + " do you want to");
            System.out.println("1. Attack");
            System.out.println("2. Attempt to disarm");
            System.out.println("3. Move Player\n");

            playerChoice = gameInput.nextInt();
            System.out.println();

            switch(playerChoice)
            {
                case 1:
                    if(!player.getIsDisarmed())
                    {
                        if (!hasAttacked)
                        {
                            if (GAME_MAP.checkAdjacency(player.getPosition().getRowValue(), player.getPosition().getColumnValue()))
                            {
                                adjacentCreatures = GAME_MAP.getAdjacentCreatures(player);

                                Creature temp;

                                if(adjacentCreatures.size() == 1)
                                {
                                    temp = adjacentCreatures.get(0);
                                    player.attack(temp);
                                }
                                else
                                {
                                    temp = adjacentCreatures.get(number.nextInt(adjacentCreatures.size()));
                                    player.attack(temp);
                                }

                                if(temp.getHP() == 0)
                                {
                                    System.out.println("GAME: " + player.getName() + " killed " + temp.getName());
                                }

                                hasAttacked = true;
                            }
                            else {
                                System.out.println("GAME: " + player.getName() + " is not close to another player.");
                            }
                        }
                        else
                        {
                            System.out.println("GAME: " + player.getName() + " has already attempted and attack or disarm.");
                        }
                    }
                    else
                    {
                        System.out.println("GAME: " + player.getName() + " is disarmed and cannot attack.");
                    }
                    break;

                case 2:
                    if(!player.getIsDisarmed())
                    {
                        if (!hasAttacked)
                        {
                            if (GAME_MAP.checkAdjacency(player.getPosition().getRowValue(), player.getPosition().getColumnValue()))
                            {
                                adjacentCreatures = GAME_MAP.getAdjacentCreatures(player);

                                if(adjacentCreatures.size() == 1)
                                {
                                    Combat.disarm(player, (Player) adjacentCreatures.get(0));
                                }
                                else
                                {
                                    Combat.disarm(player, (Player) adjacentCreatures.get(number.nextInt(adjacentCreatures.size())));
                                }
                                hasAttacked = true;
                            }
                            else
                            {
                                System.out.println("GAME: " + player.getName() + " is not close to another player.");
                            }
                        }
                        else
                        {
                            System.out.println("GAME: " + player.getName() + " has already attempted an attack or disarm.");
                        }
                    }
                    else
                    {
                        System.out.println("GAME: " + player.getName() + " is disarmed and cannot disarm.");
                    }
                    break;

                case 3:
                    if(hasMoved && timesMoved == 5)
                    {
                        System.out.println("GAME: " + player.getName() +  " has already moved.");
                    }

                    while(timesMoved != 5 && !hasMoved)
                    {
                        GAME_MAP.printMap();

                        System.out.println("1. Move Up");
                        System.out.println("2. Move Down");
                        System.out.println("3. Move Left");
                        System.out.println("4. Move Right");
                        System.out.println("5. Forfeit Move\n");

                        playerChoice = gameInput.nextInt();
                        System.out.println();

                        switch (playerChoice)
                        {
                            case 1:
                                if(player.getPosition().moveUp(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved up one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 2:
                                if(player.getPosition().moveDown(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved down one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 3:
                                if(player.getPosition().moveLeft(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved left one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 4:
                                if(player.getPosition().moveRight(GAME_MAP, player))
                                {
                                    System.out.println("GAME: " + player.getName() + " has moved right one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 5:
                                System.out.println("GAME: " + player.getName() + " chose to stay in the same spot.");
                                timesMoved = 5;
                                hasMoved = true;
                                break;

                            default:
                                System.out.println("GAME: " + "Wrong input.");
                        }

                        if(GAME_MAP.checkAdjacency(player.getPosition().getRowValue(), player.getPosition().getColumnValue()))
                        {
                            hasMoved = true;
                            timesMoved = 5;
                        }
                        else if(timesMoved == 5)
                        {
                            hasAttacked = true;
                            hasMoved = true;
                        }
                    }
                    break;

                default:
                    System.out.println("GAME: Wrong input.");
            }
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
