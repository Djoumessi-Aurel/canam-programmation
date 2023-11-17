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
public class Losange extends CenteredShape {

    int diagonaleX, diagonaleY;

    public Losange(int diagonaleX, int diagonaleY, int xCentre, int yCentre, double angleRotation, Color color){
        this.diagonaleX = diagonaleX;
        this.diagonaleY = diagonaleY;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.angleRotation = angleRotation;
        this.color = color;
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        Polygon pol = new Polygon(
            new int[]{xCentre, xCentre + diagonaleX / 2, xCentre, xCentre - diagonaleX / 2},
            new int[]{yCentre - diagonaleY / 2, yCentre, yCentre + diagonaleY / 2, yCentre},
             4);
        return pol.contains(mouseX, mouseY);
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Calcul des sommets du losange à partir du centre, des diagonales et de l'angle
        int xGauche = xCentre - diagonaleX / 2;
        int xDroite = xCentre + diagonaleX / 2;
        int yHaut = yCentre - diagonaleY / 2;
        int yBas = yCentre + diagonaleY / 2;

        // Création d'un objet Path2D pour dessiner le losange
        Path2D path = new Path2D.Double();
        path.moveTo(xCentre, yHaut); // Début du chemin

        // Tracer les quatre côtés du losange
        path.lineTo(xDroite, yCentre);
        path.lineTo(xCentre, yBas);
        path.lineTo(xGauche, yCentre);
        path.closePath(); // Fermer le chemin

        // Création de la transformation pour la rotation
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angleRotation), xCentre, yCentre);
        g2d.transform(rotation);

        g2d.draw(path); // On dessine le losange

        g2d.dispose(); // Libère les ressources graphiques
    }

    @Override
    public double getPerimetre() {
        double cote = getCote();
        return cote * 4;
    }

    @Override
    public double getAire() {
        return 0.5 * diagonaleX * diagonaleY;
    }

    public double getCote(){
        return 0.5 * Math.sqrt(diagonaleX * diagonaleX + diagonaleY * diagonaleY);
    }

    @Override
    public void callShapeCreation(int x, int y) {
        Appli.callLosangeCreation(x, y);
    }
    
}
