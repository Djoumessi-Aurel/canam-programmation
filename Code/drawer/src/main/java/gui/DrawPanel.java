package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import core.Appli;
import core.shapes.CenteredShape;


public class DrawPanel extends JPanel {
    
    private double scale = 1.0;
    private int mouseX = 0;
    private int mouseY = 0;
    private static final double zoomMax = 4;
    private static final double zoomMin = 0.3;
    private DrawPanel thisPanel = this;


    public DrawPanel(){
        this.initEvents();
        this.setBackground(Color.WHITE);
        setPreferredSize(new Dimension(3000, 3000));

        this.setSize(3000, 3000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if(Appli.isCreatingShape || Appli.isMovingShape){
            // On dessine une croix au bout du curseur
            g2d.drawLine(mouseX - 10, mouseY, mouseX + 10, mouseY);
            g2d.drawLine(mouseX, mouseY - 10, mouseX, mouseY + 10);
        }
        
        g2d.scale(scale, scale);

        // On dessine les formes
        for(int i=0; i<Appli.shapeList.size(); i++){

            if(i == Appli.selectedIndex) Appli.shapeList.get(i).drawSelected(g2d);
            else if(i == Appli.hoveredIndex) Appli.shapeList.get(i).drawHovered(g2d);
            else Appli.shapeList.get(i).drawSimple(g2d);
        }

    }

    public void initEvents(){
        
        // Gestion du déplacement dans la zone de dessin
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e){

                int newX = (int)(mouseX/scale);
                int newY = (int)(mouseY/scale);

                if(Appli.isMovingShape && Appli.selectedIndex != -1){ // Déplacement de la forme
                    CenteredShape shape =  (CenteredShape)Appli.shapeList.get(Appli.selectedIndex);
                    shape.setXCentre(newX);
                    shape.setYCentre(newY);

                    Appli.afterEdition(); // Le déplacement de forme est une modification

                    Appli.isMovingShape = false;
                    // Appli.selectedIndex = -1;
                    MainWindow.checkIfFormeSelectionnee();
                }

                if(Appli.isCreatingShape){ // Création d'une forme
                    Appli.isCreatingShape = false;
                    Appli.creatingShape.callShapeCreation(newX, newY);
                    Appli.selectedIndex = -1;
                    MainWindow.checkIfFormeSelectionnee();
                    return;
                }

                for(int i=0; i<Appli.shapeList.size(); i++){
                    if(Appli.shapeList.get(i).isHovered(newX, newY)){
                        Appli.selectedIndex = i;
                        MainWindow.checkIfFormeSelectionnee();
                        repaint();
                        return;
                    }
                }
                Appli.selectedIndex = -1;
                MainWindow.checkIfFormeSelectionnee();
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                int newX = (int)(mouseX/scale);
                int newY = (int)(mouseY/scale);

                MainWindow.statusBar.setPositionValue(newX, newY);

                if(Appli.isCreatingShape || Appli.isMovingShape) repaint(); // Redessiner le panel lorsque la souris se déplace

                if(Appli.hoveredIndex > -1){
                    if(Appli.shapeList.get(Appli.hoveredIndex).isHovered(newX, newY)){//Si c'est le même élément qui est survolé
                        return;
                    }
                    else{
                        repaint();
                    }
                    Appli.hoveredIndex = -1;
                }
                for(int i=0; i<Appli.shapeList.size(); i++){
                    if(Appli.shapeList.get(i).isHovered(newX, newY)){
                        Appli.hoveredIndex = i;
                        repaint();
                    }
                }
            }
        });


        // Gestion du zoom dans la zone de dessin
        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.isControlDown()){
                    int notches = e.getWheelRotation();
                
                    if (notches < 0) {
                        zoomPlus();
                    } else {
                        zoomMoins();
                    }
                }
                else{
                    getParent().dispatchEvent(
                        SwingUtilities.convertMouseEvent(thisPanel, e, getParent()));
                }
                
            }
            
        });

    }

    public void zoomPlus(){
        if(scale < DrawPanel.zoomMax) scale += 0.1;
        repaint();
        MainWindow.statusBar.setZoomValue(scale);
    }

     public void zoomMoins(){
        if(scale > DrawPanel.zoomMin) scale -= 0.1;
        repaint();
        MainWindow.statusBar.setZoomValue(scale);
    }

    public void zoomReset(){
        scale = 1.0;
    }

    public double getScale(){
        return scale;
    }


}

