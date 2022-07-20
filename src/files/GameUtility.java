import java.util.Random;

/**
 * The following class will be used to control game utilities
 * such as rolling dice, etc.
 */
public class GameUtility
{
        /**
         * Generates a random number based on the dice type.
         * @param diceType - The type and number of dice (e.g. 2d6).
         * @return The number rolled.
         */
        public static int rollDice(String diceType)
        {
            // variables
            int NUM;
            int CONSTANT;
            int DICE;
            int randomNumber = 0;

            // checks to see if the input is valid
            if(diceType.matches("\\d*[d]\\d+[+]{0,1}\\d*"))
            {
                // checks to see if there is a plus sign
                if (diceType.charAt(diceType.length() - 1) == '+')
                {
                    CONSTANT = 0;
                }
                else if (diceType.indexOf('+') == -1)
                {
                    CONSTANT = 0;
                }
                else
                {
                    int offset = diceType.indexOf('+') + 1;
                    CONSTANT = Integer.parseInt(diceType.substring(offset));
                }

                // checks to see if there is a specific number for the amount of times to roll
                if(diceType.indexOf('d') == 0)
                {
                    NUM = 1;
                }
                else
                {
                    NUM = Integer.parseInt(diceType.substring(0,diceType.indexOf('d')));
                }

                // gets the die number
                if(CONSTANT != 0)
                {
                    DICE = Integer.parseInt(diceType.substring(diceType.indexOf('d') + 1, diceType.indexOf('+')));
                }
                else
                {
                    if(diceType.indexOf('+') == -1)
                    {
                        DICE = Integer.parseInt(diceType.substring(diceType.indexOf('d') + 1));
                    }
                    else
                    {
                        DICE = Integer.parseInt(diceType.substring(diceType.indexOf('d') + 1, diceType.indexOf('+')));
                    }
                }

                // creates a Random object
                Random number = new Random();

                for(int i = 0; i < NUM; i++)
                {
                    randomNumber += number.nextInt(DICE);
                }

                randomNumber += CONSTANT;

                return randomNumber;
            }
            else
            {
                System.out.println("Invalid input");
            }

            return randomNumber;
        }
}
