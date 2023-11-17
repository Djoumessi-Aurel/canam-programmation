package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.Appli;

public class StatusBar extends JPanel {
    private JLabel zoomLabel, actionLabel, positionLabel,saveLabel;

    public StatusBar(){
        zoomLabel = new JLabel("Zoom: 100%");
        actionLabel = new JLabel();
        positionLabel = new JLabel();
        positionLabel.setBorder(new EmptyBorder(0, 0, 0, 15));

        saveLabel = new JLabel();
        saveLabel.setBorder(new EmptyBorder(0, 0, 0, 15));

        JPanel pan = new JPanel();
        pan.add(saveLabel); pan.add(positionLabel);

        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        add(actionLabel, BorderLayout.CENTER);
        add(zoomLabel, BorderLayout.EAST);
        add(pan, BorderLayout.WEST);

        actionLabel.setForeground(Color.BLUE);
        zoomLabel.setFont(new Font("Times", Font.BOLD, 12));
    }

    public void setZoomValue(double zoom){
        zoomLabel.setText("Zoom: " + (int)(zoom * 100) + "%");
    }

    public void setActionValue(String action){
        actionLabel.setText(action);
    }

    public void setPositionValue(int x, int y){
        positionLabel.setText("X: " + x + "px, Y: " + y + "px" );
    }

    public void refreshSaveStatus(){
        saveLabel.setText(Appli.saved ? "" : "Modifié");
    }

}
