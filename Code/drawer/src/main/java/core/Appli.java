package core;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JDialog;

import core.shapes.Carre;
import core.shapes.Cercle;
import core.shapes.Ellipse;
import core.shapes.Hexagone;
import core.shapes.Rectangle;
import core.shapes.Losange;
import core.shapes.Shape;
import gui.MainWindow;
import gui.dialogs.CarreDialog;
import gui.dialogs.CercleDialog;
import gui.dialogs.EllipseDialog;
import gui.dialogs.HexagoneDialog;
import gui.dialogs.LosangeDialog;
import gui.dialogs.RectangleDialog;

public class Appli {
    
    public static ArrayList<Shape> shapeList = new ArrayList<Shape>();
    public static boolean isCreatingShape = false; // Lorsqu'on clique sur un menu pour créer une forme, ça vaut true
                                                // Dans le cas contraire, ça vaut false
    public static Shape creatingShape; // La forme qu'on veut créer à un moment donné

    public static boolean isMovingShape = false;

    public static int hoveredIndex = -1; // Index de la forme survolée
    public static int selectedIndex = -1; // Index de la forme sélectionnée
    public static boolean saved = true;

    public static String fileName = null;
    public static int ID_DESSIN = -1; // ID du dessin en base de données (-1 si le dessin n'est pas encore enregistré)


    public static Color defaultColor = Color.BLACK;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void reset(){
        shapeList = new ArrayList<Shape>();
        isCreatingShape = false;
        creatingShape = null;
        isMovingShape = false;
        hoveredIndex = -1;
        selectedIndex = -1;
        saved = true;

        // Valeurs pour un dessin qui n'existe pas en BD
        fileName = null;
        ID_DESSIN = -1;
    }

    public static void callSquareCreation(int x, int y){ //Lance la fenêtre de création d'un carré
        JDialog  dial = new CarreDialog(null, x, y);
        dial.setVisible(true);
        MainWindow.drawingPanel.repaint();
    }

    public static void callRectangleCreation(int x, int y){ //Lance la fenêtre de création d'un carré
        JDialog  dial = new RectangleDialog(null, x, y);
        dial.setVisible(true);
        MainWindow.drawingPanel.repaint();
    }

    public static void callLosangeCreation(int x, int y){ //Lance la fenêtre de création d'un carré
        JDialog  dial = new LosangeDialog(null, x, y);
        dial.setVisible(true);
        MainWindow.drawingPanel.repaint();
    }

    public static void callHexagonCreation(int x, int y){ //Lance la fenêtre de création d'un carré
        JDialog  dial = new HexagoneDialog(null, x, y);
        dial.setVisible(true);
        MainWindow.drawingPanel.repaint();
    }

    public static void callCircleCreation(int x, int y){ //Lance la fenêtre de création d'un carré
        JDialog  dial = new CercleDialog(null, x, y);
        dial.setVisible(true);
        MainWindow.drawingPanel.repaint();
    }

    public static void callEllipseCreation(int x, int y){ //Lance la fenêtre de création d'un carré
        JDialog  dial = new EllipseDialog(null, x, y);
        dial.setVisible(true);
        MainWindow.drawingPanel.repaint();
    }

    public static void createShape(Shape shape){
        Appli.shapeList.add(shape);
        afterCreation();
    }

    public static void modifyShape(int index, Shape shape){
        Appli.shapeList.set(index, shape);
        MainWindow.leftPanel.setShape(shape);
        afterEdition();
    }

    public static void afterCreation(){
        System.gc();
        MainWindow.drawingPanel.repaint();

        Appli.saved = false;
        MainWindow.statusBar.setActionValue("");
        MainWindow.statusBar.refreshSaveStatus();
    }

    public static void afterEdition(){
        System.gc();
        MainWindow.drawingPanel.repaint();

        Appli.saved = false;
        MainWindow.statusBar.refreshSaveStatus();
    }

    public static void cancelAction(){ // Annule une action en cours (par exple lorsqu'on appuie sur Echap)

        Appli.isCreatingShape = false;
        Appli.isMovingShape = false;
        Appli.selectedIndex = -1;
        MainWindow.checkIfFormeSelectionnee();

        MainWindow.statusBar.setActionValue("");
        MainWindow.drawingPanel.repaint();
    }



    public static void callShapeEdition(int shapeIndex){ //Lance la fenêtre de modification de la forme à l'indice shapeIndex

        if(shapeIndex < 0 || shapeIndex >= Appli.shapeList.size()) return;

        Shape shape = Appli.shapeList.get(shapeIndex);
        JDialog dial = null;

        if(shape instanceof Carre){
            dial = new CarreDialog(null, 0, 0);
            ((CarreDialog)dial).gererModification(shapeIndex, (Carre)shape);
        }

        else if(shape instanceof Rectangle){
            dial = new RectangleDialog(null, 0, 0);
            ((RectangleDialog)dial).gererModification(shapeIndex, (Rectangle)shape);
        }

        else if(shape instanceof Losange){
            dial = new LosangeDialog(null, 0, 0);
            ((LosangeDialog)dial).gererModification(shapeIndex, (Losange)shape);
        }

        else if(shape instanceof Hexagone){
            dial = new HexagoneDialog(null, 0, 0);
            ((HexagoneDialog)dial).gererModification(shapeIndex, (Hexagone)shape);
        }

        else if(shape instanceof Cercle){
            dial = new CercleDialog(null, 0, 0);
            ((CercleDialog)dial).gererModification(shapeIndex, (Cercle)shape);
        }

        else if(shape instanceof Ellipse){
            dial = new EllipseDialog(null, 0, 0);
            ((EllipseDialog)dial).gererModification(shapeIndex, (Ellipse)shape);
        }

        if(dial != null){
            dial.setVisible(true);
            MainWindow.drawingPanel.repaint();
        }
    }

}
