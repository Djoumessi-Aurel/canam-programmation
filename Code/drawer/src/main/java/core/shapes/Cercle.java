package core.shapes;

import core.Appli;
import java.awt.Color;
import java.awt.Graphics2D;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Cercle extends CenteredShape {

    private int rayon;

    public Cercle(int rayon, int xCentre, int yCentre, Color color){
        this.rayon = rayon;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.color = color;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        int distSquare = (mouseX - xCentre) * (mouseX - xCentre) + (mouseY - yCentre) * (mouseY - yCentre);
        return distSquare <= rayon * rayon;
    }

    @Override
    public void draw(Graphics2D g2d) {

        int xCoinSuperieurGauche = xCentre - rayon;
        int yCoinSuperieurGauche = yCentre - rayon;

        g2d.drawOval(xCoinSuperieurGauche, yCoinSuperieurGauche, 2 * rayon, 2 * rayon);

        g2d.dispose(); // Libère les ressources graphiques
    }

    @Override
    public double getPerimetre() {
        return 2 * Math.PI * rayon;
    }

    @Override
    public double getAire() {
        return Math.PI * rayon * rayon;
    }

    @Override
    public void callShapeCreation(int x, int y) {
        Appli.callCircleCreation(x, y);
    }
}
