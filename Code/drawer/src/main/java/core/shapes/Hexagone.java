package core.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import core.Appli;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Hexagone extends CenteredShape {

    private int cote;

    public Hexagone(int cote, int xCentre, int yCentre, double angleRotation, Color color){
        this.cote = cote;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.angleRotation = angleRotation;
        this.color = color;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        int[] xPoints = new int[6], yPoints = new int[6];
        getSommets(xPoints, yPoints);
        Polygon pol = new Polygon(xPoints, yPoints, 6);

        return pol.contains(mouseX, mouseY);        
    }

    public void getSommets(int[] xPoints, int[] yPoints){// Calcul des sommets de l'hexagone à partir du centre et du côté
        
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            xPoints[i] = xCentre + (int) (cote * Math.cos(angle));
            yPoints[i] = yCentre + (int) (cote * Math.sin(angle));
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        int[] xPoints = new int[6], yPoints = new int[6];
        getSommets(xPoints, yPoints);

        // Création d'un objet Path2D pour dessiner l'hexagone
        Path2D path = new Path2D.Double();
        path.moveTo(xPoints[5], yPoints[5]); // Début du chemin
        for (int i = 0; i < 6; i++) {
            path.lineTo(xPoints[i], yPoints[i]);
        }
        path.closePath(); // Fermer le chemin

        // Création de la transformation pour la rotation
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angleRotation), xCentre, yCentre);
        g2d.transform(rotation);

        g2d.draw(path); // On dessine l'hexagone
        g2d.dispose();
    }

    @Override
    public double getPerimetre() {
        return 6 * cote;
    }

    @Override
    public double getAire() {
        return 1.5 * Math.sqrt(3) * cote * cote;
    }

    @Override
    public void callShapeCreation(int x, int y) {
        Appli.callHexagonCreation(x, y);
    }
    
    
}
