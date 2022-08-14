package files.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import files.*;

public class GUIController implements SpriteMoveListener
{
    private MainPanel gamePanel;
    private ArrayList<Monster> monsters;

    private ArrayList<Creature> allCreatures;

    private Map referenceMap = new Map(10, 10);

    public GUIController(Player[] currentPlayers)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Combat");
        gamePanel = new MainPanel();
        gamePanel.addSpriteMoveListener(this);
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);

        allCreatures = new ArrayList<>();

        for(int i = 0; i < currentPlayers.length; i++)
        {
            if(currentPlayers[i] != null)
            {
                currentPlayers[i].setPosition(new Movement(10, 10));
                currentPlayers[i].setX(currentPlayers[i].getPosition().getRowValue());
                currentPlayers[i].setY(currentPlayers[i].getPosition().getColumnValue());
                addCreature(currentPlayers[i], currentPlayers[i].getAvatarPath());
            }
        }
    }


    public void addCreature(Creature p, String assetPath)
    {
        allCreatures.add(p);
        int idx = gamePanel.addSprite(assetPath, p.getX(), p.getY());
    }

    @Override
    public void spriteMoved(int id, int x, int y) {
        Creature p = allCreatures.get(id);
        p.move(x, y);
    }

    @Override
    public boolean canMove(int id) {
        Creature p = allCreatures.get(id);
        return p.getCurrentMovement() > 0;
    }

    @Override
    public boolean canMoveTo(int id, int x, int y) {
        // incoming x and y are in relative pixel coordinates, convert to grid coordinates
        Point p = gamePanel.getGridLocation(x, y);

        Creature player = allCreatures.get(id);
        int dx = Math.abs(p.x - player.getX());
        int dy = Math.abs(p.y - player.getY());
        int min = Math.min(dx, dy);
        int max = Math.max(dx, dy);
        int diagonalSteps = min;
        int straightSteps = max - min;

        int distance = (int) (Math.sqrt(2) * diagonalSteps + straightSteps);

        System.out.println("Distance = " + distance);

        return player.getCurrentMovement() >= distance;
    }

    public void setMonsters(ArrayList<Monster> monsters)
    {
        this.monsters = monsters;
    }

    public void deployMonsters()
    {
        for(int i = 0; i < monsters.size(); i++)
        {
            System.out.println(monsters.size());
            monsters.get(i).setPosition(new Movement(10, 10));
            monsters.get(i).setX(monsters.get(i).getPosition().getRowValue());
            monsters.get(i).setY(monsters.get(i).getPosition().getColumnValue());
            addCreature(monsters.get(i), monsters.get(i).getMonsterAvatarPath());
        }
    }
}
