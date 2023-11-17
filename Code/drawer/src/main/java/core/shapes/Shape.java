package core.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public abstract class Shape implements Serializable {

    protected double angleRotation = 0; // en degrés
    protected Color color = Color.BLACK;

    public abstract boolean isHovered(int mouseX, int mouseY);

    public abstract void draw(Graphics2D g2d);

    public abstract double getPerimetre();
    public abstract double getAire();

    public abstract void callShapeCreation(int x, int y);

    public void drawSimple(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create(); // Crée une copie du contexte graphique
        g2d.setColor(this.color);
        draw(g2d);        
    }

    public void drawHovered(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        float[] dashPattern = {5, 5}; // Longueur du trait, espacement
        BasicStroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, dashPattern, 0);
        g2d.setStroke(dashedStroke);
        g2d.setColor(Color.BLACK);

        draw(g2d);
    }

    public void drawSelected(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        float[] dashPattern = {5, 5}; // Longueur du trait, espacement
        BasicStroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, dashPattern, 0);
        g2d.setStroke(dashedStroke);
        g2d.setColor(this.color);

        draw(g2d);
    }
       
}
