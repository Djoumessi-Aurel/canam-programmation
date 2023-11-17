package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.shapes.Carre;
import core.shapes.Cercle;
import core.shapes.Ellipse;
import core.shapes.Hexagone;
import core.shapes.Losange;
import core.shapes.Rectangle;
import core.shapes.Shape;
import utils.Functions;

public class LeftPanel extends JPanel {

    /* Panel qui affiche les propriétés géométriques clés des formes sélectionnées */
    JLabel labelTitre = new JLabel();
    JPanel mainPanel = new JPanel();

    private static final int MAX_LINE_WIDTH = 300;
    private static final int MAX_LINE_HEIGTH = 30;

    public LeftPanel(){
        super();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(4, 4, 4, 4));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        labelTitre.setFont(new Font("Book Antiqua", Font.BOLD, 13));
        labelTitre.setOpaque(true);

        add(labelTitre, BorderLayout.NORTH);
        add(mainPanel);
        setPreferredSize(new Dimension(180, 800));
        labelTitre.setMinimumSize(new Dimension(180, 30));
    }

    
    public void reset(){
        mainPanel.removeAll();
        labelTitre.setText("Aucune forme sélectionnée");
        repaint();
    }

    public void setShape(Shape shape){
        mainPanel.removeAll();

        if(shape instanceof Carre){
            decrireCarre((Carre)shape);
        }
        else if(shape instanceof Rectangle){
            decrireRectangle((Rectangle)shape);
        }
        else if(shape instanceof Losange){
            decrireLosange((Losange)shape);
        }
        else if(shape instanceof Hexagone){
            decrireHexagone((Hexagone)shape);
        }
        else if(shape instanceof Cercle){
            decrireCercle((Cercle)shape);
        }
        else if(shape instanceof Ellipse){
            decrireEllipse((Ellipse)shape);
        }

        decrireShape(shape);

        Functions.setTextEditable(this, false);
        repaint();
        revalidate();
    }

    public void decrireCarre(Carre carre){  //Caractéristiques du carré (son côté)
        labelTitre.setText("<html><p><u>Forme sélectionnée</u></p><h2>Carré</h2></html>");

        JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
        ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
        mainPanel.add(panel);
			{
				JLabel label = new JLabel("Côté:         ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(carre.getCote())));
			}     
    }

    public void decrireRectangle(Rectangle rectangle){  //Caractéristiques du carré (son côté)
        labelTitre.setText("<html><p><u>Forme sélectionnée</u></p><h2>Rectangle</h2></html>");

        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);
			{
				JLabel label = new JLabel("Largueur:   ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(rectangle.getLargeur())));
			} 
        } 
        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);
			{
				JLabel label = new JLabel("Hauteur:    ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(rectangle.getHauteur())));
			} 
        }   
    }

    public void decrireLosange(Losange losange){  //Caractéristiques du carré (son côté)
        labelTitre.setText("<html><p><u>Forme sélectionnée</u></p><h2>Losange</h2></html>");

        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);
			{
				JLabel label = new JLabel("Diagonale X:   ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(losange.getDiagonaleX())));
			} 
        } 
        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);
			{
				JLabel label = new JLabel("Diagonale Y:    ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(losange.getDiagonaleY())));
			} 
        }
        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);
			{
				JLabel label = new JLabel("Côté:         ");
				panel.add(label);
                panel.add(new JTextField(Functions.toString2(losange.getCote())));
			} 
        }    
    }


    public void decrireHexagone(Hexagone hexagone){  //Caractéristiques du carré (son côté)
        labelTitre.setText("<html><p><u>Forme sélectionnée</u></p><h2>Hexagone</h2></html>");

        JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
        ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
        mainPanel.add(panel);
			{
				JLabel label = new JLabel("Côté:         ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(hexagone.getCote())));
			}     
    }

    public void decrireCercle(Cercle cercle){  //Caractéristiques du carré (son côté)
        labelTitre.setText("<html><p><u>Forme sélectionnée</u></p><h2>Cercle</h2></html>");

        JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
        ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
        mainPanel.add(panel);
			{
				JLabel label = new JLabel("Rayon:       ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(cercle.getRayon())));
			}     
    }
    
    public void decrireEllipse(Ellipse ellipse){  //Caractéristiques du carré (son côté)
        labelTitre.setText("<html><p><u>Forme sélectionnée</u></p><h2>Ellipse</h2></html>");

        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);
			{
				JLabel label = new JLabel("Largueur:   ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(ellipse.getLargeur())));
			} 
        } 
        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);
			{
				JLabel label = new JLabel("Hauteur:    ");
				panel.add(label);
                panel.add(new JTextField(String.valueOf(ellipse.getHauteur())));
			} 
        }   
    }



    public void decrireShape(Shape shape){ //Caractéristiques communes à toutes les formes (aire et périmètre)
        {
            JPanel panel = new JPanel(); panel.setMaximumSize(new Dimension(MAX_LINE_WIDTH, MAX_LINE_HEIGTH));
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);

            JLabel label = new JLabel("Aire:          ");
            panel.add(label);
            panel.add(new JTextField(Functions.toString2(shape.getAire())));
        }
        {
            JPanel panel = new JPanel();
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            mainPanel.add(panel);

            JLabel label = new JLabel("Périmètre: ");
            panel.add(label);
            panel.add(new JTextField(Functions.toString2(shape.getPerimetre())));
        }
    }
}
