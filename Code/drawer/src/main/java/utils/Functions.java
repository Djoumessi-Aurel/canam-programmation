package utils;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import core.shapes.Shape;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;

public class Functions {

    // Méthode pour créer un JPanel avec un titre (TitledBorder)
    public static JPanel createGroupPanel(String title, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Création du bord avec titre pour le JPanel
        Border border = BorderFactory.createLineBorder(color); // Bordure simple
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border, title);
        panel.setBorder(titledBorder);

        return panel;
    }

    public static double[] rotatePoint(double x, double y, double centerX, double centerY, double angleRadians) {
        if(angleRadians == 0) return new double[]{x, y};
        double[] result = new double[2];

        // Translation du point par rapport au centre
        double translatedX = x - centerX;
        double translatedY = y - centerY;

        // Formules de rotation
        result[0] = translatedX * Math.cos(angleRadians) - translatedY * Math.sin(angleRadians);
        result[1] = translatedX * Math.sin(angleRadians) + translatedY * Math.cos(angleRadians);

        // Translation inverse pour ramener le point à sa position d'origine
        result[0] += centerX;
        result[1] += centerY;

        return result;
    }

    public static double arrondi2(double d){
        long val = Math.round(d * 100);
        return val/100.;
    }

    public static String toString2(double d){

        long val = Math.round(d);
        if(val == d) return String.valueOf(val);

        return String.valueOf(Functions.arrondi2(d));
    }


    /*FONCTION POUR DISABLE(false) OU ENABLE(true) UN JPanel ET TOUT SON CONTENU*/
	public static void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            setPanelEnabled((JPanel) component, isEnabled);
	        }
	        component.setEnabled(isEnabled);
	    }
	}

    // Active/désactive la modification des zones de texte d'un JPanel
    public static void setTextEditable(JPanel panel, Boolean editable) {

	    Component[] components = panel.getComponents();

	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            setTextEditable((JPanel) component, editable);
	        }
            else if (component instanceof JTextComponent) {
	            ((JTextComponent) component).setEditable(editable);
	        }
	    }
	}


    public static String readURLToString(URL url) throws IOException {
        StringBuilder content = new StringBuilder();
        URLConnection connection = url.openConnection();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n"); // Ajoute chaque ligne au contenu
            }
        }
        return content.toString();
    }


    // Méthode pour sérialiser objet en tableau de bytes
    public static byte[] convertirEnBytes(Serializable object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        return bos.toByteArray();
    }


}
