package files.gui;

import files.Monster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PreGamePanel extends JPanel implements ActionListener
{
    JPanel mainPanel;
    JList<Monster> monsterList;
    ArrayList<Monster> monstersSelected;
    Monster[] monstersToSelectFrom;
    JList monstersSelectedList;
    JButton submitButton;
    private int selectedItem = -1;

    public PreGamePanel(ActionListener listener)
    {
        loadMonstersFromCsv();

        monstersSelected = new ArrayList<>();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(listener);

        createMainPanel();
    }

    private void createMainPanel()
    {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        monsterList = new JList(monstersToSelectFrom);
        monsterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        monsterList.addListSelectionListener(listSelectionEvent ->
        {
            if (listSelectionEvent.getValueIsAdjusting())
            {
                selectedItem = monsterList.getSelectedIndex();
            }
        });

        JScrollPane scrollPane = new JScrollPane(monsterList);
        scrollPane.setPreferredSize(new Dimension(200, 180));


        mainPanel.add(scrollPane, constraints);

        // Add a button for adding a monster
        JButton button = new JButton("->");
        button.addActionListener(this);

        constraints.gridx = 1;
        mainPanel.add(button, constraints);

        // Add the selected monsters list
        monstersSelectedList = new JList(monstersSelected.toArray());
        JScrollPane cartScrollPane = new JScrollPane(monstersSelectedList);
        cartScrollPane.setPreferredSize(new Dimension(200, 180));

        constraints.gridx = 2;
        mainPanel.add(cartScrollPane, constraints);

        //Add submit button
        submitButton.setEnabled(false);

        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(submitButton, constraints);

        //Label for directions
        JLabel submitLabel = new JLabel("Click submit when ready");
        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(submitLabel, constraints);

        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if (selectedItem == -1)
        {
            return;
        }

        Monster selectedMonster = monstersToSelectFrom[selectedItem];
        monstersSelected.add(selectedMonster);
        monstersSelectedList.setListData(monstersSelected.toArray());
        submitButton.setEnabled(true);
    }

    public void loadMonstersFromCsv()
    {
        Scanner monsterInput;

        String[] monsterFiles = new File("src/data/saved/monsters/").list();
        monstersToSelectFrom = new Monster[monsterFiles.length];

        try
        {
            for(int i = 0; i < monsterFiles.length; i++)
            {
                monsterInput = new Scanner(new File("src/data/saved/monsters/" + monsterFiles[i]));

                if(monsterInput == null)
                {
                    throw new FileNotFoundException();
                }

                String[] data = monsterInput.nextLine().trim().split(",");

                int hp = Integer.parseInt(data[2]);
                int ac = Integer.parseInt(data[3]);
                int str = Integer.parseInt(data[4]);
                int dex = Integer.parseInt(data[5]);
                int con = Integer.parseInt(data[6]);

                monstersToSelectFrom[i] = new Monster(data[0], data[1], hp, ac, str, dex, con);
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Monster> getMonstersSelected() {
        return monstersSelected;
    }
}
