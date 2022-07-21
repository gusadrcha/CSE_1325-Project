package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Game
{
    public static Player characterCreationMenu(ArrayList<Weapon> weaponList)
    {
        Scanner characterCreationInput = new Scanner(System.in);
        String characterName;
        int STR = 0, DEX = 0, CON = 0;
        int characterCreationChoice;

        System.out.println("\n---- CHARACTER CREATION ----\n");
        System.out.print("Enter Character Name: ");

        characterName = characterCreationInput.nextLine();

        System.out.println("\n1. Manual Stats");
        System.out.println("2. Random Stats");
        System.out.println("Manual or Random Stats");

        characterCreationChoice = characterCreationInput.nextInt();

        while(characterCreationChoice != 1 && characterCreationChoice != 2)
        {
            System.out.println("Try again...");
            characterCreationChoice = characterCreationInput.nextInt();
        }

        if(characterCreationChoice == 1)
        {
            int availablePoints = 10;

            while(characterCreationChoice != 5 && availablePoints != 0)
            {
                System.out.println("STR: " + STR);
                System.out.println("DEX: " + DEX);
                System.out.println("CON: " + CON);
                System.out.println("Remaining: " + availablePoints);

                System.out.println("1. Add STR");
                System.out.println("2. Add DEX");
                System.out.println("3. Add CON");
                System.out.println("4. Reset");
                System.out.println("5. Finish");
                System.out.print("> ");

                characterCreationChoice = characterCreationInput.nextInt();
                System.out.println();

                switch(characterCreationChoice)
                {
                    case 1:
                        STR++;
                        availablePoints--;
                        break;

                    case 2:
                        DEX++;
                        availablePoints--;
                        break;

                    case 3:
                        CON++;
                        availablePoints--;
                        break;

                    case 4:
                        STR = 0;
                        DEX = 0;
                        CON = 0;
                        availablePoints = 10;
                        System.out.println("RESET MADE");
                        break;

                    case 5:
                        break;

                    default:
                        System.out.println("Try Again...");
                }
            }
        }
        else if(characterCreationChoice == 2)
        {
            System.out.println("RANDOM STATS APPLIED");

            Random random = new Random();
            int availablePoints = 10;

            STR = random.nextInt(availablePoints);
            availablePoints -= STR;

            DEX = random.nextInt(1, availablePoints);
            availablePoints -= DEX;

            CON = availablePoints;
        }

        System.out.println("\n---- WEAPON SELECTION ----\n");

        for(int i = 0; i < weaponList.size(); i++)
        {
            System.out.println((i + 1) + "." + weaponList.get(i));
        }

        System.out.println("\nSelect Weapon: ");
        characterCreationChoice = characterCreationInput.nextInt();

        Player temp = new Player(characterName, STR, DEX, CON, weaponList.get(characterCreationChoice - 1));

        System.out.println("\n---- CHARACTER CREATED ----\n");
        System.out.println(temp);
        System.out.println("Is Character created okay? ");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("> ");

        characterCreationChoice = characterCreationInput.nextInt();

        if(characterCreationChoice == 1)
        {
            System.out.println("\nCharacter created...\n");
        }
        else
        {
            System.out.println("Character not saved...");
            characterCreationMenu(weaponList);
        }

        return temp;
    }

    public static void combatLoop(Player attacker, Player opponent, Scanner input, Map GAME_MAP)
    {
        int playerChoice;
        int timesMoved = 0;
        boolean hasMoved = false, hasAttacked = false;

        System.out.print("\nROUND START\n-----------");

        while((!hasMoved && timesMoved != 5) || !hasAttacked)
        {
            if(GAME_MAP.checkAdjacency(attacker.getPosition().getRowValue(), attacker.getPosition().getColumnValue()))
            {
                System.out.print("\nGAME: " + attacker.getName() + " is next to another player!!\n");
            }

            if(hasMoved && attacker.getIsDisarmed())
            {
                break;
            }

            System.out.println("\n" + attacker.getName() + " do you want to");
            System.out.println("1. Attack");
            System.out.println("2. Attempt to disarm");
            System.out.println("3. Move Player\n");

            playerChoice = input.nextInt();
            System.out.println();

            switch(playerChoice)
            {
                case 1:
                    if(!attacker.getIsDisarmed())
                    {
                        if (!hasAttacked)
                        {
                            if (GAME_MAP.checkAdjacency(attacker.getPosition().getRowValue(), attacker.getPosition().getColumnValue()))
                            {
                                Combat.attack(attacker, opponent);
                                hasAttacked = true;
                            }
                            else {
                                System.out.println("GAME: " + attacker.getName() + " is not close to another player.");
                            }
                        }
                        else
                        {
                            System.out.println("GAME: " + attacker.getName() + " has already attempted and attack or disarm.");
                        }
                    }
                    else
                    {
                        System.out.println("GAME: " + attacker.getName() + " is disarmed and cannot attack.");
                    }
                    break;

                case 2:
                    if(!attacker.getIsDisarmed())
                    {
                        if (!hasAttacked)
                        {
                            if (GAME_MAP.checkAdjacency(attacker.getPosition().getRowValue(), attacker.getPosition().getColumnValue()))
                            {
                                Combat.disarm(attacker, opponent);
                                hasAttacked = true;
                            }
                            else
                            {
                                System.out.println("GAME: " + attacker.getName() + " is not close to another player.");
                            }
                        }
                        else
                        {
                            System.out.println("GAME: " + attacker.getName() + " has already attempted an attack or disarm.");
                        }
                    }
                    else
                    {
                        System.out.println("GAME: " + attacker.getName() + " is disarmed and cannot disarm.");
                    }
                    break;

                case 3:
                    if(hasMoved && timesMoved == 5)
                    {
                        System.out.println("GAME: " + attacker.getName() +  " has already moved.");
                    }

                    while(timesMoved != 5 && !hasMoved)
                    {
                        GAME_MAP.printMap();

                        System.out.println("1. Move Up");
                        System.out.println("2. Move Down");
                        System.out.println("3. Move Left");
                        System.out.println("4. Move Right");
                        System.out.println("5. Forfeit Move\n");

                        playerChoice = input.nextInt();
                        System.out.println();

                        switch (playerChoice)
                        {
                            case 1:
                                if(attacker.getPosition().moveUp(GAME_MAP, attacker))
                                {
                                    System.out.println("GAME: " + attacker.getName() + " has moved up one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 2:
                                if(attacker.getPosition().moveDown(GAME_MAP, attacker))
                                {
                                    System.out.println("GAME: " + attacker.getName() + " has moved down one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 3:
                                if(attacker.getPosition().moveLeft(GAME_MAP, attacker))
                                {
                                    System.out.println("GAME: " + attacker.getName() + " has moved left one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 4:
                                if(attacker.getPosition().moveRight(GAME_MAP, attacker))
                                {
                                    System.out.println("GAME: " + attacker.getName() + " has moved right one space.\n");
                                    timesMoved++;
                                }
                                break;

                            case 5:
                                System.out.println("GAME: " + attacker.getName() + " chose to stay in the same spot.");
                                timesMoved = 5;
                                hasMoved = true;
                                break;

                            default:
                                System.out.println("GAME: " + "Wrong input.");
                        }

                        if(GAME_MAP.checkAdjacency(attacker.getPosition().getRowValue(), attacker.getPosition().getColumnValue()))
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

        System.out.println("ROUND OVER\n----------\n\n\n\n\n\n\n\n\n");

        if(opponent.getHP() == 0)
        {
            System.out.print("GAME: " + attacker.getName() + " HAS KILLED " + opponent.getName() + "\n");
            System.out.print("GAME: " + attacker.getName().toUpperCase() + " WINS!!\n");
        }
    }

    public static void gameLoop(ArrayList<Player> players, Map GAME_MAP)
    {
        // checks to see if any players were created
        if(players.isEmpty())
        {
            System.out.println("GAME: " + "You have not created any players.\nReturning to main menu...\n");
            return;
        }

        //----- GAME LOOP VARIABLES -----//
        Scanner playerInput = new Scanner(System.in);

        // places and prints out the map from the start
        GAME_MAP.insertCharacter(players.get(0));
        GAME_MAP.insertCharacter(players.get(1));
        System.out.println("\nGAME: " + "Player have been deployed\n");
        GAME_MAP.printMap();

        int player1timer = -1;
        int player2timer = -1;

        if(Combat.rollD20(players.get(0), players.get(1)))
        {
            while(players.get(0).getHP() != 0 && players.get(1).getHP() != 0)
            {
                combatLoop(players.get(0), players.get(1), playerInput, GAME_MAP);

                if(players.get(1).getHP() == 0)
                {
                    break;
                }

                combatLoop(players.get(1), players.get(0), playerInput, GAME_MAP);

                if(players.get(1).getHP() == 0)
                {
                    break;
                }

                if(players.get(0).getIsDisarmed())
                {
                    player1timer++;

                    if(player1timer == 2)
                    {
                        player1timer = -1;
                        players.get(0).setDisarmed(false);
                    }
                }

                if(players.get(1).getIsDisarmed())
                {
                    player2timer++;

                    if(player2timer == 2)
                    {
                        player2timer = -1;
                        players.get(1).setDisarmed(false);
                    }
                }
            }
        }
        else
        {
            while(players.get(0).getHP() != 0 && players.get(1).getHP() != 0)
            {
                combatLoop(players.get(1), players.get(0), playerInput, GAME_MAP);

                combatLoop(players.get(0), players.get(1), playerInput, GAME_MAP);

                if(players.get(0).getIsDisarmed())
                {
                    player1timer++;

                    if(player1timer == 2)
                    {
                        player1timer = -1;
                        players.get(0).setDisarmed(false);
                    }
                }

                if(players.get(1).getIsDisarmed())
                {
                    player2timer++;

                    if(player2timer == 2)
                    {
                        player2timer = -1;
                        players.get(1).setDisarmed(false);
                    }
                }
            }
        }

        System.out.println("GAME: " + "Game over.");
        playerInput.close();
    }

    public static void saveCharacter(ArrayList<Player> Players, Scanner gameInput)throws IOException
    {
        System.out.println("Which character would you like to save?");
        int choice;

        for(int i = 0; i < Players.size(); i++)
        {
            System.out.println((i + 1) + ": " + Players.get(i).getName());
        }

        System.out.print("> ");
        choice = gameInput.nextInt() - 1;

        PrintWriter save_file = new PrintWriter("src/data/saved/players/"+ Players.get(choice).getName() + ".csv");

        save_file.print(Players.get(choice).getName() + ",");
        save_file.print(Players.get(choice).getCreationDate() + ",");
        save_file.print(Players.get(choice).getHP() + ",");
        save_file.print(Players.get(choice).getSTR() + ",");
        save_file.print(Players.get(choice).getDEX() + ",");
        save_file.print(Players.get(choice).getCON() + ",");
        save_file.print(Players.get(choice).getWeapon().getName() + ",");
        save_file.print(Players.get(choice).getWeapon().getDamage() + ",");
        save_file.print(Players.get(choice).getWeapon().getHitBonus() + "\n");

        save_file.close();
    }

    public static void loadCharacter(ArrayList<Player> Players, Scanner gameInput)throws FileNotFoundException
    {
        File directory = new File("src/data/saved/players/");
        String[] filenames = directory.list();
        int choice;
        boolean isInRoster = false;

        System.out.println("Which character file would you like to load?");

        for(int i = 0; i < filenames.length; i++)
            System.out.println((i+1) + ": " + filenames[i]);

        choice = gameInput.nextInt() - 1;

        Scanner playerInfo = new Scanner(new File("src/data/saved/players/" + filenames[choice]));

        Player temp = Player.loadFromCsv(playerInfo.nextLine());

        if(temp == null)
        {
            return;
        }

        if(!Players.isEmpty())
        {
            for(int i = 0; i < Players.size(); i++)
            {
                if(Players.get(i).getName().equalsIgnoreCase(temp.getName()))
                {
                    isInRoster = true;
                }
            }

            if(isInRoster)
            {
                System.out.println(temp.getName() + " is already in the roster!");
                System.out.println("Exiting...\n");
            }
            else
            {
                Players.add(temp);
                System.out.println(temp.getName() + " was added to the roster!\n");
            }
        }
        else
        {
            System.out.println(temp.getName() + " was added to the roster!\n");
            Players.add(temp);
        }
    }

    public static void loadWeapons(ArrayList<Weapon> Weapons)throws FileNotFoundException
    {
        Scanner inputFile = new Scanner(new File("src/data/weapons.csv"));
        inputFile.useDelimiter("[,]|\\n");

        while(inputFile.hasNext())
        {
            Weapons.add(new Weapon(inputFile.next(), inputFile.next(), Integer.parseInt(inputFile.next())));
        }

        inputFile.close();
    }

    public static void main(String[] args) throws IOException
    {
        // Necessary game variables
        ArrayList<Player> Players = new ArrayList<>();
        ArrayList<Monster> Monsters = new ArrayList<>();
        ArrayList<Weapon> Weapons = new ArrayList<>();
        Scanner gameInput = new Scanner(System.in);
        Map GAME_MAP = new Map();

        loadWeapons(Weapons);

        int mainMenuChoice = 0;

        while (mainMenuChoice != 5)
        {
            System.out.println("1. Start Game");
            System.out.println("2. Create Characters");
            System.out.println("3. Load Character");
            System.out.println("4. Save Character");
            System.out.println("5. Exit");
            System.out.print("> ");

            mainMenuChoice = gameInput.nextInt();

            switch(mainMenuChoice)
            {
                case 1:
                    gameLoop(Players, GAME_MAP);
                    break;

                case 2:
                    Players.add(characterCreationMenu(Weapons));
                    break;

                case 3:
                    loadCharacter(Players, gameInput);
                    break;

                case 4:
                    saveCharacter(Players, gameInput);
                    break;

                case 5:
                    System.out.println("Quitting...");
                    break;

                default:
                    System.out.println("Wrong Input");
            }
        }
    }
}
