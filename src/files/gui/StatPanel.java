package files.gui;

import files.Creature;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class StatPanel extends JPanel
{
    private JButton strButtonIncrease;
    private JButton strButtonDecrease;

    private JButton dexButtonIncrease;
    private JButton dexButtonDecrease;

    private JButton conButtonIncrease;
    private JButton conButtonDecrease;

    private JButton randomStatsButton;

    private JLabel strNumLabel = new JLabel();
    private JLabel dexNumLabel = new JLabel();
    private JLabel conNumLabel = new JLabel();

    private JLabel availablePointsLabel = new JLabel();

    private int availablePoints = 15;
    private int str = 0;
    private int dex = 0;
    private int con = 0;

    public StatPanel(ActionListener buttonListener)
    {
        JPanel statPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        var emptyborder = new EmptyBorder(10, 10, 10, 5);
        var blackline = BorderFactory.createLineBorder(Color.BLACK);

        statPanel.setBorder(new CompoundBorder(blackline, emptyborder));

        JLabel strLabel = new JLabel("Strength");
        strNumLabel.setText("" + str);

        JLabel dexLabel = new JLabel("Dexterity");
        dexNumLabel.setText("" + dex);

        JLabel conLabel = new JLabel("Constitution");
        conNumLabel.setText("" + con);

        strButtonIncrease = new JButton("STR +");
        strButtonIncrease.addActionListener(buttonListener);

        strButtonDecrease = new JButton("STR -");
        strButtonDecrease.addActionListener(buttonListener);

        dexButtonIncrease = new JButton("DEX +");
        dexButtonIncrease.addActionListener(buttonListener);

        dexButtonDecrease = new JButton("DEX -");
        dexButtonDecrease.addActionListener(buttonListener);

        conButtonIncrease = new JButton("CON +");
        conButtonIncrease.addActionListener(buttonListener);

        conButtonDecrease = new JButton("CON -");
        conButtonDecrease.addActionListener(buttonListener);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 20;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.CENTER;
        statPanel.add(strLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.CENTER;
        statPanel.add(dexLabel, constraints);

        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.CENTER;
        statPanel.add(conLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 40;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.CENTER;
        statPanel.add(strNumLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.ipady = 40;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.CENTER;
        statPanel.add(dexNumLabel, constraints);

        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.ipady = 40;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.CENTER;
        statPanel.add(conNumLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        statPanel.add(strButtonIncrease, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        statPanel.add(strButtonDecrease, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        statPanel.add(dexButtonIncrease, constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        statPanel.add(dexButtonDecrease, constraints);

        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        statPanel.add(conButtonIncrease, constraints);

        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        statPanel.add(conButtonDecrease, constraints);

        availablePointsLabel.setText("Available Points: " + this.availablePoints);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        statPanel.add(availablePointsLabel, constraints);

        randomStatsButton = new JButton("Random");
        randomStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();

                setStr(0);
                setDex(0);
                setCon(0);
                setAvailablePoints(15);

                setStr(random.nextInt(availablePoints));
                availablePoints -= getStr();
                setAvailablePoints(availablePoints);

                setDex(random.nextInt(1, availablePoints));
                availablePoints -= getDex();
                setAvailablePoints(availablePoints);

                setCon(availablePoints);
                availablePoints -= getCon();

                setAvailablePoints(availablePoints);
            }
        });
        constraints.gridx = 3;
        constraints.gridy = 3;
        statPanel.add(randomStatsButton, constraints);

        add(statPanel, constraints);
    }

    public void setStr(int str)
    {
        this.str = str;
        this.strNumLabel.setText("" + str);
    }

    public void setDex(int dex)
    {
        this.dex = dex;
        this.dexNumLabel.setText("" + dex);
    }

    public void setCon(int con)
    {
        this.con = con;
        this.conNumLabel.setText("" + con);
    }

    public void setAvailablePointsFromPlayer(Creature p)
    {
        setAvailablePoints(15 - (p.getSTR() + p.getDEX() + p.getCON()));
    }

    public int getStr()
    {
        return str;
    }

    public int getDex()
    {
        return dex;
    }

    public int getCon()
    {
        return con;
    }

    public void setAvailablePoints(int availablePoints)
    {
        this.availablePoints = availablePoints;
        this.availablePointsLabel.setText("Available Points: " + availablePoints);
    }
    public int getAvailablePoints()
    {
        return availablePoints;
    }

    public JLabel getAvailablePointsLabel()
    {
        return availablePointsLabel;
    }
}
