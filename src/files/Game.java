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

    public static void combatLoopForPVP(ArrayList<Creature> creatures, Scanner gameInput, Map GAME_MAP)
    {
        while(creatures.size() > 1)
        {
            System.out.print("\nROUND START\n-----------\n");

            GAME_MAP.printMap();

            for(int i = 0; i < creatures.size(); i++)
            {
                Combat.playerTurnPVP((Player) creatures.get(i), gameInput, GAME_MAP);
                GameUtility.removeDeadCharacters(creatures, GAME_MAP);
                System.out.println("TURN ENDED\n----------");
            }

            for(Creature currentCreature : creatures)
            {
                if(((Player) currentCreature).getIsDisarmed())
                {
                    ((Player) currentCreature).setDisarmTimer(((Player) currentCreature).getDisarmTimer() + 1);

                    if(((Player) currentCreature).getDisarmTimer() > 1)
                    {
                        ((Player) currentCreature).setDisarmTimer(-1);
                        ((Player) currentCreature).setDisarmed(false);
                    }
                }
            }

            System.out.println("ROUND OVER\n----------\n\n\n\n\n\n\n\n\n");
        }

        System.out.println("GAME: " + creatures.get(0).getName() + " wins!");
        System.out.println("GAME: " + "Game over.");
    }

    public static void combatLoopForPVE(ArrayList<Creature> creatures, Scanner gameInput, Map GAME_MAP)
    {
        boolean isPlayerAlive = true;
        boolean monstersAlive = true;

        while(isPlayerAlive && monstersAlive)
        {
            System.out.print("\nROUND START\n-----------\n");

            for (int i = 0; i < creatures.size(); i++)
            {
                if(creatures.get(i) instanceof Player)
                {
                    Combat.playerTurnPVE((Player) creatures.get(i), gameInput, GAME_MAP);
                    GameUtility.removeDeadCharacters(creatures, GAME_MAP);
                }
                else
                {
                    Combat.monsterTurn(creatures.get(i), GAME_MAP);
                    GameUtility.removeDeadCharacters(creatures, GAME_MAP);
                }

                if(creatures.get(0).getHP() == 0)
                {
                    isPlayerAlive = false;

                    System.out.println("GAME: Player has died.\nGAME: Monsters win!\nGAME: Returning to main menu.");

                    GAME_MAP.clearMap();
                }

                if(creatures.size() == 1)
                {
                    monstersAlive = false;

                    System.out.println("GAME: All monsters have died.\n GAME:Player wins!\nGAME: Returning to main menu");

                    GAME_MAP.clearMap();
                }
            }

            System.out.println("ROUND OVER\n----------\n\n\n\n\n\n\n\n\n");
        }
    }


    public static void gameLoopForPVP(ArrayList<Creature> creatures, Scanner gameInput, Map GAME_MAP)
    {
        // checks to see if any players were created
        if(creatures.isEmpty())
        {
            System.out.println("GAME: " + "You have not created any players.\nReturning to main menu...\n");
            return;
        }

        for(Creature currentCreature : creatures)
        {
            GAME_MAP.insertCharacter(currentCreature);
        }

        GAME_MAP.printMap();

        GameUtility.rollInitiative(creatures);

        combatLoopForPVP(creatures, gameInput, GAME_MAP);
    }

    public static void gameLoopForPVE(ArrayList<Creature> creatures, Map GAME_MAP, Scanner gameInput)throws FileNotFoundException
    {
        if(creatures.isEmpty())
        {
            System.out.println("Player was not made.\nReturning to main menu.\n");
            return;
        }

        if(creatures.size() > 1)
        {
            System.out.println("PVE is meant for one player\n You must remove until one remains");

            while(creatures.size() > 1)
            {
                for (int i = 0; i < creatures.size(); i++)
                {
                    System.out.println(i+1 + ". " + creatures.get(i).getName());
                }

                try
                {
                    creatures.remove(gameInput.nextInt() - 1);
                }
                catch(ArrayIndexOutOfBoundsException e)
                {
                    System.out.println("GAME: Entered a number outside of the amount of players.\nTry Again.");
                }
            }
        }

        int choice;

        System.out.println("How many monsters do you want to play against?");
        choice = gameInput.nextInt();

        loadMonsters(creatures, choice);

        for(Creature currentPlayer : creatures)
        {
            GAME_MAP.insertCharacter(currentPlayer);
        }

        GameUtility.rollInitiative(creatures);

        GAME_MAP.printMap();

        combatLoopForPVE(creatures, gameInput, GAME_MAP);
    }
    public static void saveCharacter(ArrayList<Creature> creatures, Scanner gameInput)throws IOException
    {
        System.out.println("Which character would you like to save?");
        int choice;

        for(int i = 0; i < creatures.size(); i++)
        {
            System.out.println((i + 1) + ": " + creatures.get(i).getName());
        }

        System.out.print("> ");
        choice = gameInput.nextInt() - 1;

        PrintWriter save_file = new PrintWriter("src/data/saved/players/"+ creatures.get(choice).getName() + ".csv");

        if(creatures.get(choice) instanceof Player)
        {
            save_file.print(creatures.get(choice).getName() + ",");
            save_file.print(creatures.get(choice).getCreationDate() + ",");
            save_file.print(creatures.get(choice).getHP() + ",");
            save_file.print(creatures.get(choice).getSTR() + ",");
            save_file.print(creatures.get(choice).getDEX() + ",");
            save_file.print(creatures.get(choice).getCON() + ",");
            save_file.print(((Player) creatures.get(choice)).getWeapon().getName() + ",");
            save_file.print(((Player) creatures.get(choice)).getWeapon().getDamage() + ",");
            save_file.print(((Player) creatures.get(choice)).getWeapon().getHitBonus() + "\n");
        }

        save_file.close();
    }

    public static void loadCharacter(ArrayList<Creature> creatures, Scanner gameInput)throws FileNotFoundException
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

        if(!creatures.isEmpty())
        {
            for(int i = 0; i < creatures.size(); i++)
            {
                if(creatures.get(i).getName().equalsIgnoreCase(temp.getName()))
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
                creatures.add(temp);
                System.out.println(temp.getName() + " was added to the roster!\n");
            }
        }
        else
        {
            System.out.println(temp.getName() + " was added to the roster!\n");
            creatures.add(temp);
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

    public static void loadMonsters(ArrayList<Creature> creatures, int count)throws FileNotFoundException
    {
        File directory = new File("src/data/saved/monsters");
        String[] monsterFiles = directory.list();
        Random fileNumber = new Random();

        for(int i = 0; i < count; i++)
        {
            Scanner monsterFile = new Scanner(new File("src/data/saved/monsters/" + monsterFiles[fileNumber.nextInt(0,3)]));

            creatures.add(Monster.loadFromCsv(monsterFile.nextLine()));
        }
    }
    public static void main(String[] args) throws IOException
    {
        // Necessary game variables
        ArrayList<Creature> Creatures = new ArrayList<>();
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

                    System.out.println("1. Play with Random Monsters");
                    System.out.println("2. Play Players Only (PVP)");
                    System.out.println("3. Back");

                    mainMenuChoice = gameInput.nextInt();

                    if(mainMenuChoice == 1)
                    {
                        gameLoopForPVE(Creatures, GAME_MAP, gameInput);
                    }
                    else if (mainMenuChoice == 2)
                    {
                        gameLoopForPVP(Creatures, gameInput, GAME_MAP);
                    }
                    break;

                case 2:
                    Creatures.add(characterCreationMenu(Weapons));
                    break;

                case 3:
                    loadCharacter(Creatures, gameInput);
                    break;

                case 4:
                    saveCharacter(Creatures, gameInput);
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
