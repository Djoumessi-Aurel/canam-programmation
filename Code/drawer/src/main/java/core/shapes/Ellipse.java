package core.shapes;

import core.Appli;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.Functions;


@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Ellipse extends CenteredShape {

    private int largeur, hauteur;

    public Ellipse(int largeur, int hauteur, int xCentre, int yCentre, double angleRotation, Color color){
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.angleRotation = angleRotation;
        this.color = color;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        double a = 0.5 * largeur, b = 0.5 * hauteur;

        double[] rot = Functions.rotatePoint(mouseX, mouseY, xCentre, yCentre, Math.toRadians(-angleRotation));
        
        double xNormalized = (double)(rot[0] - xCentre) / a;
        double yNormalized = (double)(rot[1] - yCentre) / b;
        return (xNormalized * xNormalized + yNormalized * yNormalized) <= 1;
    }

    @Override
    public void draw(Graphics2D g2d) {

        int xCoinSuperieurGauche = xCentre - (largeur / 2);
        int yCoinSuperieurGauche = yCentre - (hauteur / 2);

        // Création de la transformation pour la rotation
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angleRotation), xCentre, yCentre);
        g2d.transform(rotation);

        // Dessin du rectangle
        g2d.drawOval(xCoinSuperieurGauche, yCoinSuperieurGauche, largeur, hauteur);

        g2d.dispose(); // Libère les ressources graphiques
    }

    @Override
    public double getPerimetre() {
        double a = 0.5 * largeur, b = 0.5 * hauteur;
        return Math.PI * (3*(a+b) - Math.sqrt((3*a + b)*(a + 3*b)));
    }

    @Override
    public double getAire() {
        double a = 0.5 * largeur, b = 0.5 * hauteur;
        return Math.PI * a * b;
    }

    @Override
    public void callShapeCreation(int x, int y) {
        Appli.callEllipseCreation(x, y);
    }
}
