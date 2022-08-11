package files.gui;

import files.Creature;
import files.Player;
import files.Weapon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class EditPanel extends JPanel implements DocumentListener, ActionListener{
    private JTextField nameTextField;
    private JComboBox<File> avatarComboBox;
    private JComboBox<Weapon> weaponComboBox;
    private JLabel avatarLabel;
    private JButton submitButton;
    private StatPanel statPanel;
    private int buttonNumber;

    public EditPanel(ActionListener listener, int buttonNumber)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        JLabel namePanel = new JLabel("Name:", SwingConstants.CENTER);
        nameTextField = new JTextField("");
        submitButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");

        File[] files = new File("src/data/img/").listFiles();
        avatarComboBox = new JComboBox<File>(files);
        avatarLabel = new JLabel();
        avatarComboBox.addActionListener(this);

        statPanel = new StatPanel(new StatListener());

        Weapon[] weapons = new Weapon[5];

        try
        {
            loadFromCsv(weapons);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        weaponComboBox = new JComboBox<>(weapons);

        constraints.weightx = 1;
        constraints.weighty = 1;

        // Avatar Picture
        setDefault();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.CENTER;
        add(avatarLabel, constraints);

        // Stats
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(statPanel, constraints);

        // The drop-down for the image
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.CENTER;
        add(avatarComboBox, constraints);

        // The label for the name
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        namePanel.setSize(new Dimension(50,20));
        add(namePanel, constraints);

        // The name text field
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.ipadx = 40;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        nameTextField.getDocument().addDocumentListener(this);
        nameTextField.setPreferredSize(new Dimension(200, 25));
        add(nameTextField, constraints);

        // Add new panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        submitButton.addActionListener(listener);
        submitButton.setActionCommand("EditPanel.Submit");
        submitButton.setEnabled(false);
        buttonPanel.add(submitButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        cancelButton.addActionListener(listener);
        cancelButton.setActionCommand("EditPanel.Cancel");
        buttonPanel.add(cancelButton, constraints);

        // Layout for Submit and Cancel buttons
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        add(buttonPanel, constraints);

        // Weapon Box
        constraints.gridx = 2;
        constraints.gridy = 2;
        add(weaponComboBox, constraints);

        this.buttonNumber = buttonNumber;
    }

    public void clearFields() {
        nameTextField.setText("");
    }

    public String getPlayerName()
    {
        return nameTextField.getText();
    }

    public Weapon getWeapon()
    {
        return weaponComboBox.getItemAt(weaponComboBox.getSelectedIndex());
    }

    public int getStr()
    {
       return statPanel.getStr();
    }

    public int getDex()
    {
        return statPanel.getDex();
    }

    public int getCon()
    {
        return statPanel.getCon();
    }

    public String getAvatarPath()
    {
        int index = avatarComboBox.getSelectedIndex();
        File image = avatarComboBox.getItemAt(index);
        return image.getPath();
    }

    public int getButtonNumber() {
        return buttonNumber;
    }

    public void setPlayer(Player p)
    {
        nameTextField.setText(p.getName());

        Creature temp = (Creature) p;

        statPanel.setStr(temp.getSTR());
        statPanel.setDex(temp.getDEX());
        statPanel.setCon(temp.getCON());

        statPanel.setAvailablePointsFromPlayer(p);

        File imagePath = new File(p.getAvatarPath());
        try
        {
            Image img = ImageIO.read(imagePath.getAbsoluteFile());
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            avatarLabel.setIcon(new ImageIcon(img));
            avatarComboBox.setSelectedItem(imagePath);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        weaponComboBox.setSelectedItem(p.getWeapon().getName());
    }

    public void setDefault()
    {
        File imagePath = avatarComboBox.getItemAt(0);
        try
        {
            Image img = ImageIO.read(imagePath.getAbsoluteFile());
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            avatarLabel.setIcon(new ImageIcon(img));
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        submitButton.setEnabled(nameTextField.getDocument().getLength() != 0);
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        submitButton.setEnabled(nameTextField.getDocument().getLength() != 0);
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int index = avatarComboBox.getSelectedIndex();
        File imagePath = avatarComboBox.getItemAt(index);
        try
        {
            Image img = ImageIO.read(imagePath.getAbsoluteFile());
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            avatarLabel.setIcon(new ImageIcon(img));
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private class StatListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equalsIgnoreCase("STR +"))
            {
                if(statPanel.getAvailablePoints() != 0 && statPanel.getStr() < 10)
                {
                    statPanel.setStr(statPanel.getStr() + 1);
                    statPanel.setAvailablePoints(statPanel.getAvailablePoints() - 1);
                    statPanel.getAvailablePointsLabel().setText("Available Points: " + statPanel.getAvailablePoints());
                }
            }

            if(e.getActionCommand().equalsIgnoreCase("DEX +"))
            {
                if(statPanel.getAvailablePoints() != 0 && statPanel.getDex() < 10)
                {
                    statPanel.setDex(statPanel.getDex() + 1);
                    statPanel.setAvailablePoints(statPanel.getAvailablePoints() - 1);
                    statPanel.getAvailablePointsLabel().setText("Available Points: " + statPanel.getAvailablePoints());
                }
            }

            if(e.getActionCommand().equalsIgnoreCase("CON +"))
            {
                if(statPanel.getAvailablePoints() != 0 && statPanel.getCon() < 10)
                {
                    statPanel.setCon(statPanel.getCon() + 1);
                    statPanel.setAvailablePoints(statPanel.getAvailablePoints() - 1);
                    statPanel.getAvailablePointsLabel().setText("Available Points: " + statPanel.getAvailablePoints());
                }
            }

            if(e.getActionCommand().equalsIgnoreCase("STR -"))
            {
                if(statPanel.getStr() > 0 && statPanel.getAvailablePoints() < 15)
                {
                    statPanel.setStr(statPanel.getStr() - 1);
                    statPanel.setAvailablePoints(statPanel.getAvailablePoints() + 1);
                    statPanel.getAvailablePointsLabel().setText("Available Points: " + statPanel.getAvailablePoints());
                }
            }

            if(e.getActionCommand().equalsIgnoreCase("DEX -"))
            {
                if(statPanel.getDex() > 0 && statPanel.getAvailablePoints() < 15)
                {
                    statPanel.setDex(statPanel.getDex() - 1);
                    statPanel.setAvailablePoints(statPanel.getAvailablePoints() + 1);
                    statPanel.getAvailablePointsLabel().setText("Available Points: " + statPanel.getAvailablePoints());
                }
            }

            if(e.getActionCommand().equalsIgnoreCase("CON -"))
            {
                if(statPanel.getCon() > 0 && statPanel.getAvailablePoints() < 15)
                {
                    statPanel.setCon(statPanel.getCon() - 1);
                    statPanel.setAvailablePoints(statPanel.getAvailablePoints() + 1);
                    statPanel.getAvailablePointsLabel().setText("Available Points: " + statPanel.getAvailablePoints());
                }
            }
        }
    }

    public void loadFromCsv(Weapon[] weaponList)throws FileNotFoundException
    {
        File weaponFile = new File("src/data/weapons.csv");
        Scanner weaponInput = new Scanner(weaponFile);
        weaponInput.useDelimiter(",|\\n");

        for(int i = 0; i < 5; i++)
        {
            weaponList[i] = new Weapon(weaponInput.next(), weaponInput.next(), Integer.parseInt(weaponInput.next()));
        }
    }
}