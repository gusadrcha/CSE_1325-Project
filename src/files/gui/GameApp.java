package files.gui;

import javax.swing.*;

public class GameApp
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {new MainMenuGUIController();});
    }
}
