package files.gui;

import files.Creature;
import files.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PlayerPanel extends JPanel
{
    private JLabel nameLabel;
    private JButton avatarLabel;
    private int buttonNumber;

    public PlayerPanel(ActionListener listener, int buttonNumber)
    {
        this.buttonNumber = buttonNumber;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        nameLabel = new JLabel("No Character");
        avatarLabel = new JButton();

        nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setName("No Character");
        this.add(Box.createVerticalGlue());

        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatarLabel.setText("Empty");
        avatarLabel.setName("" + this.buttonNumber);
        avatarLabel.setPreferredSize(new Dimension(200, 200));
        avatarLabel.addActionListener(listener);

        this.add(nameLabel);
        this.add(avatarLabel);

        var emptyborder = new EmptyBorder(0, 10, 10, 0);
        var blackline = BorderFactory.createLineBorder(Color.BLACK);

        this.setBorder(new CompoundBorder(blackline, emptyborder));
    }

    public void setPlayer(Player p)
    {
        nameLabel.setText(p.getName());
        nameLabel.setName(p.getName());

        File imagePath = new File(p.getAvatarPath());

        avatarLabel.setText("");

        try
        {
            Image img = ImageIO.read(imagePath.getAbsoluteFile());
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            avatarLabel.setIcon(new ImageIcon(img));
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }

        avatarLabel.setVisible(true);
    }

    public void removePlayer()
    {
        nameLabel.setText("No Character");
        avatarLabel.setIcon(null);
        avatarLabel.setText("Empty");
    }

    public int getButtonNumber()
    {
        return this.buttonNumber;
    }

    public JLabel getNameLabel()
    {
        return nameLabel;
    }
}
