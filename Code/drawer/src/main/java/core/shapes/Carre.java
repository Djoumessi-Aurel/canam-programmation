package core.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import core.Appli;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Carre extends CenteredShape {

    private int cote;

    public Carre(int cote, int xCentre, int yCentre, double angleRotation, Color color){
        this.cote = cote;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.angleRotation = angleRotation;
        this.color = color;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        Polygon pol = new Polygon(
            new int[]{xCentre - cote / 2, xCentre + cote / 2, xCentre + cote / 2, xCentre - cote / 2},
            new int[]{yCentre - cote / 2, yCentre - cote / 2, yCentre + cote / 2, yCentre + cote / 2},
             4);
        return pol.contains(mouseX, mouseY);
    }
    

    @Override
    public void draw(Graphics2D g2d){
        // Calcul des coordonnées du coin supérieur gauche du carré
        int xCoinSuperieurGauche = xCentre - cote / 2;
        int yCoinSuperieurGauche = yCentre - cote / 2;

        // Création de la transformation pour la rotation
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angleRotation), xCentre, yCentre);
        g2d.transform(rotation);

        // Dessin du carré
        g2d.drawRect(xCoinSuperieurGauche, yCoinSuperieurGauche, cote, cote);

        g2d.dispose(); // Libère les ressources graphiques
    }


    @Override
    public double getPerimetre() {
        return 4 * cote;
    }

    @Override
    public double getAire() {
        return cote * cote;
    }

    public void callShapeCreation(int x, int y){
        Appli.callSquareCreation(x, y);
    }
    
}
