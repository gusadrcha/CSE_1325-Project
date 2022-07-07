import java.io.File;
import java.io.IOException;
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
                        characterCreationChoice = 5;
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

            DEX = random.nextInt(availablePoints);
            availablePoints -= DEX;

            CON = random.nextInt(availablePoints);
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

    public static void main(String[] args) throws IOException
    {
        //----- GAME VARIABLES -----//
        ArrayList<Player> players = new ArrayList<>();
        Map GAME_MAP = new Map();

        //----- FILE WEAPON STUFF ----- //
        Scanner inputFile = new Scanner(new File("/Users/gustavochavez/Documents/College/Summer_Semester_2022/CSE_1325/Phase_1/Phase_1/src/weapons.csv"));
        inputFile.useDelimiter("[,]|\\n");

        ArrayList<Weapon> weaponList = new ArrayList<>();

        String name;
        String damage;
        int bonus;

        while(inputFile.hasNext())
        {
            name = inputFile.next();
            damage = inputFile.next();
            bonus = Integer.parseInt(inputFile.next());

            weaponList.add(new Weapon(name, damage, bonus));
        }
        inputFile.close();

        //----- MENU SELECTION VARIABLES ----- //
        Scanner appInput = new Scanner(System.in);
        int mainMenuChoice = 0;
        while(mainMenuChoice != 3)
        {
            System.out.println("1. Start Game");
            System.out.println("2. Create Characters");
            System.out.println("3. Exit");

            mainMenuChoice = appInput.nextInt();

            switch(mainMenuChoice)
            {
                case 1:
                    break;

                case 2:
                    players.add(characterCreationMenu(weaponList));

                    System.out.println("Creating second player...\n");

                    players.add(characterCreationMenu(weaponList));

                    for(int i = 0; i < players.size(); i++)
                    {
                        System.out.println("Player " + (i + 1) + "\n--------");
                        System.out.println(players.get(i));
                    }

                    break;

                case 3:
                    mainMenuChoice = 3;
                    break;

                default:
                    System.out.println("Wrong input");
            }
        }

    }
}
