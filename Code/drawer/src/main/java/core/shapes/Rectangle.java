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
public class Rectangle extends CenteredShape {

    private int largeur, hauteur;

    public Rectangle(int largeur, int hauteur, int xCentre, int yCentre, double angleRotation, Color color){
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.angleRotation = angleRotation;
        this.color = color;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        Polygon pol = new Polygon(
            new int[]{xCentre - largeur / 2, xCentre + largeur / 2, xCentre + largeur / 2, xCentre - largeur / 2},
            new int[]{yCentre - hauteur / 2, yCentre - hauteur / 2, yCentre + hauteur / 2, yCentre + hauteur / 2},
             4);
        return pol.contains(mouseX, mouseY);
    }

    @Override
    public void draw(Graphics2D g2d) {
        
        int xCoinSuperieurGauche = xCentre - largeur / 2;
        int yCoinSuperieurGauche = yCentre - hauteur / 2;

        // Création de la transformation pour la rotation
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angleRotation), xCentre, yCentre);
        g2d.transform(rotation);

        // Dessin du rectangle
        g2d.drawRect(xCoinSuperieurGauche, yCoinSuperieurGauche, largeur, hauteur);

        g2d.dispose(); // Libère les ressources graphiques
    }


    @Override
    public double getPerimetre() {
        return 2 * (largeur + hauteur);
    }

    @Override
    public double getAire() {
        return largeur * hauteur;
    }

    public void callShapeCreation(int x, int y){
        Appli.callRectangleCreation(x, y);
    }
    
}
