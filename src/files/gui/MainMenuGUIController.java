package files.gui;

import files.Creature;
import files.Monster;
import files.Player;
import files.Weapon;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuGUIController implements MenuListener
{
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    Player[] currentPlayers = new Player[4];
    private JFrame mainMenuFrame;
    private GUIController gamePanel;
    private JMenuBar statusBar;
    private JMenuBar mainMenuBar;
    private JPanel centerPanel = new JPanel(new GridBagLayout());
    private PlayerPanel[] playerPanels = new PlayerPanel[4];
    private Boolean[] isSaved = new Boolean[4];
    private Boolean[] hasFile = new Boolean[4];
    private String[] playerFileName = new String[4];
    private EditPanel editPanel;
    private int choice;
    private JPopupMenu promptSaveSlot;
    private JPopupMenu promptLoadSlot;
    private JPopupMenu selectedSlot;
    private PreGamePanel preGamePanel;
    private ArrayList<Monster> monstersForBattle;

    public MainMenuGUIController()
    {
        createMainMenu();
        createMainMenuBar();
        createStatusBar();
        createPartyPanel();
        createCenterPanel();

        for(int i = 0; i < 4; i++)
        {
            currentPlayers[i] = null;
            hasFile[i] = false;
            isSaved[i] = false;
            playerFileName[i] = null;
        }

        mainMenuFrame.setVisible(true);
    }

    private void createMainMenu()
    {
        mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setSize(dim.width / 2, dim.height / 2);
        mainMenuFrame.setLocation((dim.width - mainMenuFrame.getWidth()) / 2, (dim.height - mainMenuFrame.getHeight()) / 2);
        mainMenuFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void createMainMenuBar()
    {
        mainMenuBar = new JMenuBar();

        JMenu startGameMenu = new JMenu("Start Game");
        startGameMenu.setEnabled(false);

        JMenu createCharacterMenu = new JMenu("Create Character");
        JMenuItem slot1 = new JMenuItem("Slot 1");
        JMenuItem slot2 = new JMenuItem("Slot 2");
        JMenuItem slot3 = new JMenuItem("Slot 3");
        JMenuItem slot4 = new JMenuItem("Slot 4");

        slot1.addActionListener(new CreateCharacterMenuItemListener());
        slot2.addActionListener(new CreateCharacterMenuItemListener());
        slot3.addActionListener(new CreateCharacterMenuItemListener());
        slot4.addActionListener(new CreateCharacterMenuItemListener());

        createCharacterMenu.add(slot1);
        createCharacterMenu.add(slot2);
        createCharacterMenu.add(slot3);
        createCharacterMenu.add(slot4);

        JMenu loadCharacterMenu = new JMenu("Load Character");
        JMenuItem loadSlot1 = new JMenuItem("Slot 1");
        JMenuItem loadSlot2 = new JMenuItem("Slot 2");
        JMenuItem loadSlot3 = new JMenuItem("Slot 3");
        JMenuItem loadSlot4 = new JMenuItem("Slot 4");

        loadSlot1.addActionListener(new LoadCharacterMenuItemListener());
        loadSlot2.addActionListener(new LoadCharacterMenuItemListener());
        loadSlot3.addActionListener(new LoadCharacterMenuItemListener());
        loadSlot4.addActionListener(new LoadCharacterMenuItemListener());

        loadCharacterMenu.add(loadSlot1);
        loadCharacterMenu.add(loadSlot2);
        loadCharacterMenu.add(loadSlot3);
        loadCharacterMenu.add(loadSlot4);

        JMenu saveCharacterMenu = new JMenu("Save Character");
        JMenuItem saveSlot1 = new JMenuItem("Slot 1");
        JMenuItem saveSlot2 = new JMenuItem("Slot 2");
        JMenuItem saveSlot3 = new JMenuItem("Slot 3");
        JMenuItem saveSlot4 = new JMenuItem("Slot 4");

        saveSlot1.addActionListener(new SaveCharacterMenuItemListener());
        saveSlot2.addActionListener(new SaveCharacterMenuItemListener());
        saveSlot3.addActionListener(new SaveCharacterMenuItemListener());
        saveSlot4.addActionListener(new SaveCharacterMenuItemListener());

        saveCharacterMenu.add(saveSlot1);
        saveCharacterMenu.add(saveSlot2);
        saveCharacterMenu.add(saveSlot3);
        saveCharacterMenu.add(saveSlot4);

        saveCharacterMenu.setEnabled(false);

        JMenu exitMenu = new JMenu("Exit");

        startGameMenu.addMenuListener(this);
        createCharacterMenu.addMenuListener(this);
        loadCharacterMenu.addMenuListener(this);
        saveCharacterMenu.addMenuListener(this);
        exitMenu.addMenuListener(this);

        mainMenuBar.add(startGameMenu);
        mainMenuBar.add(createCharacterMenu);
        mainMenuBar.add(loadCharacterMenu);
        mainMenuBar.add(saveCharacterMenu);
        mainMenuBar.add(exitMenu);

        mainMenuFrame.add(mainMenuBar, BorderLayout.NORTH);
        mainMenuFrame.setJMenuBar(mainMenuBar);
    }

    private void createStatusBar()
    {
        statusBar = new JMenuBar();

        JLabel currentStatus = new JLabel("Status:");
        statusBar.add(currentStatus);

        mainMenuFrame.add(statusBar, BorderLayout.SOUTH);
    }

    private void createCenterPanel()
    {
        GridBagConstraints constraints = new GridBagConstraints();

        ActionListener centerPanelListener = new CenterPanelListener();

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setEnabled(false);

        JButton createCharacterButton = new JButton("Create Character");
        JButton loadCharacterButton = new JButton("Load Character");
        JButton saveCharacterButton = new JButton("Save Character");
        JButton exitButton = new JButton("Exit");

        startGameButton.addActionListener(centerPanelListener);
        createCharacterButton.addActionListener(centerPanelListener);
        loadCharacterButton.addActionListener(centerPanelListener);
        saveCharacterButton.addActionListener(centerPanelListener);
        exitButton.addActionListener(centerPanelListener);

        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(startGameButton, constraints);

        constraints.gridy = 2;
        constraints.gridx = 1;
        centerPanel.add(createCharacterButton, constraints);

        constraints.gridy = 3;
        constraints.gridx = 1;
        centerPanel.add(loadCharacterButton, constraints);

        constraints.gridy = 4;
        constraints.gridx = 1;
        centerPanel.add(saveCharacterButton, constraints);

        constraints.gridy = 5;
        constraints.gridx = 1;
        centerPanel.add(exitButton, constraints);

        mainMenuFrame.add(centerPanel, BorderLayout.CENTER);
    }

    private void createPartyPanel()
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.ipadx = 70;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.ipady = 40;
        for(int i = 0; i < playerPanels.length; i++)
        {
            playerPanels[i] = new PlayerPanel(new ButtonListener(), i);
            centerPanel.add(playerPanels[i], constraints);
            constraints.gridx++;
        }
    }

    private void showMainPanel()
    {
        if (centerPanel.isShowing()) {
            return;
        }

        mainMenuFrame.remove(editPanel);
        mainMenuFrame.add(centerPanel);
        mainMenuFrame.revalidate();  // recalculates the layout
        mainMenuFrame.repaint();
    }

    private void showEditPanel()
    {
        if (editPanel.isShowing()) {
            return;
        }

        mainMenuFrame.remove(centerPanel);
        mainMenuFrame.add(editPanel);
        mainMenuFrame.revalidate();  // recalculates the layout
        mainMenuFrame.repaint();
    }

    private void showSaveMenu()
    {
        if(currentPlayers[choice] == null)
        {
            JLabel temp = (JLabel) statusBar.getComponent(0);
            temp.setText("Status: Unable to save because slot is empty");
            return;
        }

        if(isSaved[choice])
        {
            saveData(playerFileName[choice], currentPlayers[choice]);
        }

        System.out.println(currentPlayers[choice]);

        if(!hasFile[choice])
        {
            JFileChooser fileChooser = new JFileChooser("src/data/saved/players/");
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

            int result = fileChooser.showSaveDialog(fileChooser);
            if(result == JFileChooser.APPROVE_OPTION)
            {
                playerFileName[choice] = fileChooser.getSelectedFile().getPath();
                Path p = Path.of(playerFileName[choice]);

                saveData(playerFileName[choice], currentPlayers[choice]);
            }

            hasFile[choice] = true;
        }
        else
        {
            saveData(playerFileName[choice], currentPlayers[choice]);
        }

        isSaved[choice] = true;

        JLabel temp = (JLabel) statusBar.getComponent(0);
        temp.setText("Status: Character Saved");
    }

    private void showLoadMenu()
    {
        JFileChooser fileChooser = new JFileChooser("src/data/saved/players/");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int result = fileChooser.showOpenDialog(fileChooser);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            playerFileName[choice] = fileChooser.getSelectedFile().getPath();
            Path p = Path.of(playerFileName[choice]);

            loadData(playerFileName[choice]);
        }

        hasFile[choice] = true;

        if(containsAtLeastOne())
        {
            JMenu startGameMenu = mainMenuBar.getMenu(0);
            startGameMenu.setEnabled(true);

            JMenu saveCharacterMenu = mainMenuBar.getMenu(3);
            saveCharacterMenu.setEnabled(true);

            JButton startGameButton = (JButton) centerPanel.getComponent(4);
            startGameButton.setEnabled(true);
        }
    }

    private void showPreGame()
    {
        preGamePanel = new PreGamePanel(new PreGameSubmitButton());
        mainMenuFrame.remove(centerPanel);
        mainMenuFrame.add(preGamePanel, BorderLayout.CENTER);
        mainMenuFrame.revalidate();
        mainMenuFrame.repaint();
    }
    private void showGame()
    {
        monstersForBattle = preGamePanel.getMonstersSelected();

        gamePanel.setMonsters(monstersForBattle);
        gamePanel.deployMonsters();
        mainMenuFrame.revalidate();
        mainMenuFrame.repaint();
        mainMenuFrame.setVisible(false);
    }

    @Override
    public void menuSelected(MenuEvent menuSelected)
    {
        JMenu temp = (JMenu) menuSelected.getSource();
        String action = temp.getActionCommand();

        switch(action)
        {
            case "Start Game":
                showPreGame();
                break;

            case "Exit":
                mainMenuFrame.dispose();
        }
    }

    @Override
    public void menuDeselected(MenuEvent e)
    {
        System.out.println("Menu deselected");
    }

    @Override
    public void menuCanceled(MenuEvent e)
    {
        System.out.println("idk");
    }

    private class CenterPanelListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent centerPanelButton)
        {
            String action = centerPanelButton.getActionCommand();

            switch(action)
            {
                case "Start Game":
                    showPreGame();
                    break;
                case "Create Character":

                    promptSaveSlot = new JPopupMenu("Select Save Slot");

                    PopupMenuEditListener listener = new PopupMenuEditListener();

                    JMenuItem saveSlot1 = new JMenuItem("Slot 1");
                    saveSlot1.addActionListener(listener);

                    JMenuItem saveSlot2 = new JMenuItem("Slot 2");
                    saveSlot2.addActionListener(listener);

                    JMenuItem saveSlot3 = new JMenuItem("Slot 3");
                    saveSlot3.addActionListener(listener);

                    JMenuItem saveSlot4 = new JMenuItem("Slot 4");
                    saveSlot4.addActionListener(listener);

                    JMenuItem cancelItem = new JMenuItem("Cancel");
                    cancelItem.addActionListener(listener);

                    promptSaveSlot.add(saveSlot1);
                    promptSaveSlot.add(saveSlot2);
                    promptSaveSlot.add(saveSlot3);
                    promptSaveSlot.add(saveSlot4);
                    promptSaveSlot.add(cancelItem);

                    promptSaveSlot.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);

                    promptSaveSlot.setVisible(true);
                    break;
                case "Load Character":
                    selectSlotToLoadMenu();
                    break;
                case "Save Character":
                    selectSlotToSaveMenu();
                    break;
                case "Exit":
                    mainMenuFrame.dispose();
            }
        }
    }

    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String buttonAction = event.getActionCommand();
            JButton temp = (JButton) event.getSource();

            switch(buttonAction)
            {
                case "Empty":
                    editPanel = new EditPanel(new EditPlayerListener(), Integer.parseInt(temp.getName()));
                    choice = Integer.parseInt(temp.getName());
                    showEditPanel();
                    break;

                default:
                    selectedSlot = new JPopupMenu();

                    PopupMenuSelectedCharacterListener listener = new PopupMenuSelectedCharacterListener();

                    JMenuItem removeItem = new JMenuItem("Remove");
                    removeItem.addActionListener(listener);

                    JMenuItem loadCharacterItem = new JMenuItem("Replace");
                    loadCharacterItem.addActionListener(listener);

                    JMenuItem cancelItem = new JMenuItem("Cancel");
                    cancelItem.addActionListener(listener);

                    selectedSlot.add(removeItem);
                    selectedSlot.add(loadCharacterItem);
                    selectedSlot.add(cancelItem);

                    selectedSlot.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);

                    choice = Integer.parseInt(temp.getName());
                    selectedSlot.setVisible(true);
            }
        }
    }

    private class EditPlayerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            String actionName = actionEvent.getActionCommand();
            switch (actionName)
            {
                case "EditPanel.Submit":
                    Player temp = new Player(editPanel.getPlayerName(), editPanel.getAvatarPath(), editPanel.getStr(), editPanel.getDex(), editPanel.getCon(), editPanel.getWeapon());
                    temp.setAvatarPath(editPanel.getAvatarPath());
                    playerPanels[choice].setPlayer(temp);
                    currentPlayers[choice] = temp;
                    System.out.printf("Player %s created.\n", currentPlayers[choice]);
                    editPanel.clearFields();
                    JLabel tempLabel = (JLabel) statusBar.getComponent(0);
                    tempLabel.setText("Status: Character created");

                    if(containsAtLeastOne())
                    {
                        JMenu startGameMenu = mainMenuBar.getMenu(0);
                        startGameMenu.setEnabled(true);

                        JMenu saveCharacterMenu = mainMenuBar.getMenu(3);
                        saveCharacterMenu.setEnabled(true);

                        JButton startGameButton = (JButton) centerPanel.getComponent(4);
                        startGameButton.setEnabled(true);
                    }

                    showMainPanel();
                    break;

                case "EditPanel.Cancel":
                    editPanel.clearFields();
                    showMainPanel();
                    break;
            }
        }

    }

    private class CreateCharacterMenuItemListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String action = event.getActionCommand();

            switch(action)
            {
                case "Slot 1":
                    choice = 0;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    break;

                case "Slot 2":
                    choice = 1;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    break;

                case "Slot 3":
                    choice = 2;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    break;

                case "Slot 4":
                    choice = 3;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    break;
            }
        }
    }

    private class LoadCharacterMenuItemListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String action = event.getActionCommand();

            Player copy = currentPlayers[choice];
            boolean loadSuccessful = false;

            switch(action)
            {
                case "Slot 1":
                    choice = 0;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;

                case "Slot 2":
                    choice = 1;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;

                case "Slot 3":
                    choice = 2;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;

                case "Slot 4":
                    choice = 3;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;
            }

            JLabel temp = (JLabel) statusBar.getComponent(0);
            if(loadSuccessful)
            {
                temp.setText("Status: Character Successfully Loaded");
            }
            else
            {
                temp.setText("Status: Character Unsuccessfully Loaded");
            }
        }
    }

    private class SaveCharacterMenuItemListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String action = event.getActionCommand();

            switch(action)
            {
                case "Slot 1":
                    choice = 0;
                    showSaveMenu();
                    break;

                case "Slot 2":
                    choice = 1;
                    showSaveMenu();
                    break;

                case "Slot 3":
                    choice = 2;
                    showSaveMenu();
                    break;

                case "Slot 4":
                    choice = 3;
                    showSaveMenu();
                    break;
            }
        }
    }

    private class PopupMenuEditListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String action = event.getActionCommand();

            switch (action)
            {
                case "Slot 1":
                    choice = 0;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    isSaved[choice] = false;
                    break;

                case "Slot 2":
                    choice = 1;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    isSaved[choice] = false;
                    break;

                case "Slot 3":
                    choice = 2;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    isSaved[choice] = false;
                    break;

                case "Slot 4":
                    choice = 3;
                    editPanel = new EditPanel(new EditPlayerListener(), choice);
                    showEditPanel();
                    isSaved[choice] = false;
                    break;

                case "Cancel":
                    break;
            }

            promptSaveSlot.setVisible(false);
        }
    }

    private class PopupMenuSaveListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String action = event.getActionCommand();

            switch(action)
            {
                case "Slot 1":
                    promptSaveSlot.setVisible(false);
                    choice = 0;
                    showSaveMenu();
                    break;

                case "Slot 2":
                    promptSaveSlot.setVisible(false);
                    choice = 1;
                    showSaveMenu();
                    break;

                case "Slot 3":
                    promptSaveSlot.setVisible(false);
                    choice = 2;
                    showSaveMenu();
                    break;

                case "Slot 4":
                    promptSaveSlot.setVisible(false);
                    choice = 3;
                    showSaveMenu();
                    break;

                case "Cancel":
                    promptSaveSlot.setVisible(false);
            }
        }
    }

    private class PopupMenuLoadListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String action = event.getActionCommand();

            Player copy = currentPlayers[choice];
            boolean loadSuccessful = false;

            switch(action)
            {
                case "Slot 1":
                    promptLoadSlot.setVisible(false);
                    choice = 0;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;

                case "Slot 2":
                    promptLoadSlot.setVisible(false);
                    choice = 1;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;

                case "Slot 3":
                    promptLoadSlot.setVisible(false);
                    choice = 2;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;

                case "Slot 4":
                    promptLoadSlot.setVisible(false);
                    choice = 3;
                    showLoadMenu();

                    if(currentPlayers[choice] != null && currentPlayers[choice] != copy)
                        loadSuccessful = true;

                    break;

                case "Cancel":
                    promptLoadSlot.setVisible(false);
                    break;
            }

            JLabel temp = (JLabel) statusBar.getComponent(0);
            if(loadSuccessful)
            {
                temp.setText("Status: Character Successfully Loaded");
            }
            else
            {
                temp.setText("Status: Character Unsuccessfully Loaded");
            }
        }
    }

    private class PopupMenuSelectedCharacterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            String action = event.getActionCommand();
            JLabel temp = (JLabel) statusBar.getComponent(0);

            switch(action)
            {
                case "Remove":
                    selectedSlot.setVisible(false);
                    playerPanels[choice].removePlayer();
                    currentPlayers[choice] = null;
                    temp.setText("Status: Character removed");

                    if(!containsAtLeastOne())
                    {
                        JMenu startGameMenu = mainMenuBar.getMenu(0);
                        startGameMenu.setEnabled(false);

                        JMenu saveCharacterMenu = mainMenuBar.getMenu(3);
                        saveCharacterMenu.setEnabled(false);

                        JButton startGameButton = (JButton) centerPanel.getComponent(4);
                        startGameButton.setEnabled(false);
                    }

                    break;

                case "Replace":
                    selectedSlot.setVisible(false);
                    showLoadMenu();
                    temp.setText("Status: Character replaced");
                    break;

                case "Cancel":
                    selectedSlot.setVisible(false);
                    break;

            }
        }
    }

    private class PreGameSubmitButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            monstersForBattle = preGamePanel.getMonstersSelected();
            startGame();
        }
    }

    public void saveData(String playerFileName, Player p)
    {
        try(PrintWriter writer = new PrintWriter(playerFileName))
        {
            writer.print(writePlayer(p));
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void loadData(String fileName)
    {
        try
        {
            File f = new File(fileName);
            Scanner fileData = new Scanner(f);

            String[] data = fileData.nextLine().trim().split(",");

            if(data.length != 8)
            {
                throw new IOException();
            }

            currentPlayers[choice] = new Player(data[0], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[3]), new Weapon(data[5], data[6], Integer.parseInt(data[7])));
            currentPlayers[choice].setAvatarPath(data[1]);

            playerPanels[choice].setPlayer(currentPlayers[choice]);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            JLabel temp = (JLabel) statusBar.getComponent(0);
            temp.setText("Status: BAD FILE! Unable to load character");
        }
    }

    public String writePlayer(Player player)
    {
        Creature temp = (Creature) player;
        return temp.getName() + "," + player.getAvatarPath() + "," + temp.getSTR() + "," + temp.getDEX() + "," + temp.getCON() + "," + player.getWeapon().getName() + "," +
                player.getWeapon().getDamage() + "," + player.getWeapon().getHitBonus();
    }

    public void selectSlotToSaveMenu()
    {
        promptSaveSlot = new JPopupMenu("Select Save Slot");

        PopupMenuSaveListener listener = new PopupMenuSaveListener();

        JMenuItem saveSlot1 = new JMenuItem("Slot 1");
        saveSlot1.addActionListener(listener);

        JMenuItem saveSlot2 = new JMenuItem("Slot 2");
        saveSlot2.addActionListener(listener);

        JMenuItem saveSlot3 = new JMenuItem("Slot 3");
        saveSlot3.addActionListener(listener);

        JMenuItem saveSlot4 = new JMenuItem("Slot 4");
        saveSlot4.addActionListener(listener);

        JMenuItem cancelItem = new JMenuItem("Cancel");
        cancelItem.addActionListener(listener);

        promptSaveSlot.add(saveSlot1);
        promptSaveSlot.add(saveSlot2);
        promptSaveSlot.add(saveSlot3);
        promptSaveSlot.add(saveSlot4);
        promptSaveSlot.add(cancelItem);

        promptSaveSlot.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);

        promptSaveSlot.setVisible(true);
    }

    public void selectSlotToLoadMenu()
    {
        promptLoadSlot = new JPopupMenu("Select Slot");

        PopupMenuLoadListener listener = new PopupMenuLoadListener();

        JMenuItem loadSlot1 = new JMenuItem("Slot 1");
        loadSlot1.addActionListener(listener);

        JMenuItem loadSlot2 = new JMenuItem("Slot 2");
        loadSlot2.addActionListener(listener);

        JMenuItem loadSlot3 = new JMenuItem("Slot 3");
        loadSlot3.addActionListener(listener);

        JMenuItem loadSlot4 = new JMenuItem("Slot 4");
        loadSlot4.addActionListener(listener);

        JMenuItem cancelItem = new JMenuItem("Cancel");
        cancelItem.addActionListener(listener);

        promptLoadSlot.add(loadSlot1);
        promptLoadSlot.add(loadSlot2);
        promptLoadSlot.add(loadSlot3);
        promptLoadSlot.add(loadSlot4);
        promptLoadSlot.add(cancelItem);

        promptLoadSlot.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);

        promptLoadSlot.setVisible(true);
    }

    public void startGame()
    {
        if(!containsAtLeastOne())
        {
            JLabel temp = (JLabel) statusBar.getComponent(0);
            temp.setText("Status: There are no characters loaded.");
            return;
        }

        gamePanel = new GUIController(currentPlayers);
        showGame();
    }

    public boolean containsAtLeastOne()
    {
        for(int i = 0; i < currentPlayers.length; i++)
        {
            if(currentPlayers[i] != null)
                return  true;
        }
        return false;
    }
}
