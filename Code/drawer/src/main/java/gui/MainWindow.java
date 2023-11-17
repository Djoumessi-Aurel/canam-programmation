package gui;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import core.Appli;
import core.database.BaseAccess;
import core.database.Dessin;
import core.database.DessinManager;
import core.shapes.Carre;
import core.shapes.Cercle;
import core.shapes.Ellipse;
import core.shapes.Hexagone;
import core.shapes.Losange;
import core.shapes.Rectangle;
import core.shapes.Shape;
import gui.dialogs.OuvirDessinDialog;
import utils.Functions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    private static final int WIDTH = 950;
    private static final int HEIGHT = 650;
    public static final String appName = "Shape Drawer";

    private JPanel contentPane, mainPanel;
    public static DrawPanel drawingPanel;
    public static StatusBar statusBar;
    public static LeftPanel leftPanel;
    private JMenuBar menuBar;
    private JToolBar toolBar;

    public MainWindow() {
        refreshTitle();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        initMenu();
        initToolBar();
        initKeyboardEvents();
        initWindowEvents();
        fillContentPane();

        checkIfFormeSelectionnee();


        try {
			setIconImage(ImageIO.read(MainWindow.class.getResource("/images/icon-pencil.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

        BaseAccess.db = new BaseAccess();

        setVisible(true);
    }

    public void fillContentPane(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        drawingPanel = new DrawPanel();
        JScrollPane scrollPane = new JScrollPane(drawingPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        statusBar = new StatusBar();
        contentPane.add(statusBar, BorderLayout.SOUTH);

        leftPanel = new LeftPanel();
        leftPanel.reset();
        contentPane.add(leftPanel, BorderLayout.EAST);
    }

    public void initMenu(){
        menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
        // Menu Fichier
		JMenu menuFichier = new JMenu("Fichier");
		menuFichier.setMnemonic('F');
		menuBar.add(menuFichier);
		
		menuFichier.add(actNew);
		menuFichier.add(actSave);
		menuFichier.add(actOpen);
		menuFichier.add(actClose);		
		menuFichier.addSeparator();
		menuFichier.add(actQuit);

        // Menu Création
        JMenu menuCreation = new JMenu("Création");
		menuCreation.setMnemonic('C');
		menuBar.add(menuCreation);

		menuCreation.add(actCarre);
		menuCreation.add(actRectangle);
		menuCreation.add(actLosange);
		menuCreation.add(actHexagone);
		menuCreation.add(actCercle);            
		menuCreation.add(actEllipse);

        // Menu Action
        JMenu menuAction = new JMenu("Action");
		menuAction.setMnemonic('t');
		menuBar.add(menuAction);

		menuAction.add(actMove);
        menuAction.add(actEdit);
		menuAction.add(actDelete);

        // Menu Vue
        JMenu menuVue = new JMenu("Vue");
		menuVue.setMnemonic('V');
		menuBar.add(menuVue);

		menuVue.add(actZoomP);
		menuVue.add(actZoomM);
        
		// Menu Aide
		JMenu menuAide = new JMenu("Aide");
		menuAide.setMnemonic('i');
		menuBar.add(menuAide);
		
		menuAide.add(actHelp);
		menuAide.add(actAbout);
    }

    public void initToolBar(){
        toolBar = new JToolBar();
        contentPane.add(toolBar, BorderLayout.NORTH);
        toolBar.add( actSave ).setHideActionText( true );
        toolBar.add( actOpen ).setHideActionText( true );
        toolBar.addSeparator();
        toolBar.add( actCarre ).setHideActionText( true );
        toolBar.add( actRectangle ).setHideActionText( true );
        toolBar.add( actLosange ).setHideActionText( true );
        toolBar.add( actHexagone ).setHideActionText( true );
        toolBar.add( actCercle ).setHideActionText( true );
        toolBar.add( actEllipse ).setHideActionText( true );
        toolBar.addSeparator();
        toolBar.add( actMove ).setHideActionText( true );
        toolBar.add( actEdit ).setHideActionText( true );
        toolBar.add( actDelete ).setHideActionText( true );
        toolBar.addSeparator();
        toolBar.add( actZoomP ).setHideActionText( true );
        toolBar.add( actZoomM ).setHideActionText( true );
    }

    public void initKeyboardEvents(){
        
            AbstractAction escapeAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Appli.cancelAction();
                }
            };

            // Association de l'action à la touche Échap pour la JFrame
            contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
            contentPane.getActionMap().put("escape", escapeAction);
    }

    public void initWindowEvents(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(Appli.saved) quitter(); // Si c'est déjà enregistré, on peut fermer directement

                int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    quitter();
                } else {
                }
            }
        });
    }

    /* Nos diverses actions */
    private AbstractAction actNew = new AbstractAction() {  
        {
            putValue( Action.NAME, "Nouveau dessin" );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-new.png")) );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_N );
            putValue( Action.SHORT_DESCRIPTION, "Nouveau dessin (CTRL+N)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            nouveau();
        }
    };

    private AbstractAction actSave = new AbstractAction() {  
        {
            putValue( Action.NAME, "Enregistrer le dessin" );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-save.png")) );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_S );
            putValue( Action.SHORT_DESCRIPTION, "Enregistrer le dessin (CTRL+S)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            enregistrer();
        }
    };

    private AbstractAction actOpen = new AbstractAction() {  
        {
            putValue( Action.NAME, "Ouvrir un dessin" );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-open.png")) );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_O );
            putValue( Action.SHORT_DESCRIPTION, "Ouvrir un dessin (CTRL+O)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            ouvrir();
        }
    };

    private AbstractAction actClose = new AbstractAction() {  
        {
            putValue( Action.NAME, "Fermer le dessin" );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-close.png")) );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_F );
            putValue( Action.SHORT_DESCRIPTION, "Fermer le dessin (CTRL+W)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            fermer("Fermeture");
        }
    };

    private AbstractAction actQuit = new AbstractAction() {  
        {
            putValue( Action.NAME, "Quitter" );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-quit.png")) );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_Q );
            putValue( Action.SHORT_DESCRIPTION, "Quitter (CTRL+Q)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            quitter();
        }
    };

    private AbstractAction actCarre = new AbstractAction() {  
        {
            putValue( Action.NAME, "Carré" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_A );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-square.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Dessiner un carré" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.isCreatingShape = true;
            Appli.creatingShape = new Carre();
            statusBar.setActionValue("Cliquez dans la zone de dessin pour placer le centre du carré...");
        }
    };

    private AbstractAction actRectangle = new AbstractAction() {  
        {
            putValue( Action.NAME, "Rectangle" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_R );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-rectangle.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Dessiner un rectangle" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.isCreatingShape = true;
            Appli.creatingShape = new Rectangle();
            statusBar.setActionValue("Cliquez dans la zone de dessin pour placer le centre du rectangle...");
        }
    };

    private AbstractAction actLosange = new AbstractAction() {  
        {
            putValue( Action.NAME, "Losange" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_L );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-losange.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Dessiner un losange" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.isCreatingShape = true;
            Appli.creatingShape = new Losange();
            statusBar.setActionValue("Cliquez dans la zone de dessin pour placer le centre du losange...");
        }
    };

    private AbstractAction actHexagone = new AbstractAction() {  
        {
            putValue( Action.NAME, "Hexagone" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_H );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-hexagon.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Dessiner un hexagone" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.isCreatingShape = true;
            Appli.creatingShape = new Hexagone();
            statusBar.setActionValue("Cliquez dans la zone de dessin pour placer le centre de l'hexagone...");
        }
    };

    private AbstractAction actCercle = new AbstractAction() {  
        {
            putValue( Action.NAME, "Cercle" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_C );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-circle.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Dessiner un cercle" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.isCreatingShape = true;
            Appli.creatingShape = new Cercle();
            statusBar.setActionValue("Cliquez dans la zone de dessin pour placer le centre du cercle...");
        }
    };

    private AbstractAction actEllipse = new AbstractAction() {  
        {
            putValue( Action.NAME, "Ellipse" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_E );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-ellipse.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Dessiner une ellipse" );
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.isCreatingShape = true;
            Appli.creatingShape = new Ellipse();
            statusBar.setActionValue("Cliquez dans la zone de dessin pour placer le centre de l'ellipse...");
        }
    };

    private static AbstractAction actMove = new AbstractAction() {  
        {
            putValue( Action.NAME, "Déplacer la forme sélectionnée" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_D );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-move.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Déplacer la forme sélectionnée (CTRL+D)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.isCreatingShape = false;
            Appli.isMovingShape = true;
            statusBar.setActionValue("Cliquez dans la zone de dessin pour placer le nouveau centre de la forme");
        }
    };

    private static AbstractAction actEdit = new AbstractAction() {  
        {
            putValue( Action.NAME, "Modifier la forme sélectionnée" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_M );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-edit.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Modifier la forme sélectionnée (CTRL+E)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            Appli.callShapeEdition(Appli.selectedIndex);
        }
    };

    private static AbstractAction actDelete = new AbstractAction() {  
        {
            putValue( Action.NAME, "Supprimer la forme sélectionnée" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_S );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-delete.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Supprimer la forme sélectionnée (Suppr.)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            if(Appli.selectedIndex != -1){
                    Appli.shapeList.remove(Appli.selectedIndex);

                    Appli.selectedIndex = -1;
                    Appli.hoveredIndex = -1;
                    MainWindow.checkIfFormeSelectionnee();

                    drawingPanel.repaint();
                }
        }
    };

    private AbstractAction actZoomP = new AbstractAction() {  
        {
            putValue( Action.NAME, "Zoom +" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_Z );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-zoomp.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Zoom + (Ctrl+Shift-P)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            drawingPanel.zoomPlus();
        }
    };

    private AbstractAction actZoomM = new AbstractAction() {  
        {
            putValue( Action.NAME, "Zoom -" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_M );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-zoomm.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Zoom - (Ctrl+Shift-M)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {
            drawingPanel.zoomMoins();
        }
    };

    private AbstractAction actHelp = new AbstractAction() {  
        {
            putValue( Action.NAME, "Aide" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_I );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-help.png")) );
            putValue( Action.SHORT_DESCRIPTION, "Aide  (F1)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {

            JPanel panel = new JPanel();
            JLabel label = new JLabel();
            
            label.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.add(new JScrollPane(label), BorderLayout.CENTER);

            try {
                String texteAide = Functions.readURLToString(MainWindow.class.getResource("/aide.htm"));
                label.setText(texteAide);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, panel, "Aide", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    private AbstractAction actAbout = new AbstractAction() {  
        {
            putValue( Action.NAME, "\u00C0 propos" );
            putValue( Action.MNEMONIC_KEY, KeyEvent.VK_P );
            putValue( Action.SMALL_ICON, new ImageIcon(MainWindow.class.getResource("/images/icon-about.png")) );
            putValue( Action.SHORT_DESCRIPTION, "\u00C0 propos  (Shift-F1)" );
            putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.SHIFT_DOWN_MASK) ); 
        }
        
        @Override public void actionPerformed( ActionEvent e ) {

            JPanel panel = new JPanel();
            JLabel label = new JLabel();
            
            label.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.add(new JScrollPane(label), BorderLayout.CENTER);

            try {
                String texteApropos = Functions.readURLToString(MainWindow.class.getResource("/apropos.htm"));
                label.setText(texteApropos);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, panel, "\u00C0 propos...", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    public static void checkIfFormeSelectionnee(){
        if(Appli.selectedIndex == -1){
            statusBar.setActionValue("");
            actMove.setEnabled(false);
            actEdit.setEnabled(false);
            actDelete.setEnabled(false);

            leftPanel.reset();
        }
        else{
            statusBar.setActionValue("Forme sélectionnée: vous pouvez la déplacer, la modifier ou la supprimer");
            actMove.setEnabled(true);
            actEdit.setEnabled(true);
            actDelete.setEnabled(true);

            leftPanel.setShape(Appli.shapeList.get(Appli.selectedIndex));
        }

    }

    public boolean fermer(String titre)
   {
    if (!Appli.saved)//si le fichier n'est pas enregistré...
    {
		int i = JOptionPane.showConfirmDialog(null,
				"Enregistrer les modifications apportées au dessin actuel?", titre,JOptionPane.YES_NO_CANCEL_OPTION);
		if (i == JOptionPane.YES_OPTION)
		{
			if (this.enregistrer()) //si l'enregistrement est validé...
			{
				this.fermerDessin();
			}
            else return false;
		}
		else if (i == JOptionPane.NO_OPTION)
		{   
			this.fermerDessin();
		}
        else if (i == JOptionPane.CANCEL_OPTION || i == JOptionPane.CLOSED_OPTION)
		{   
			return false;
		}
		
	}
    
    else //si le fichier est déjà enregistré ou "nouveau et non modifié"
    {
     this.fermerDessin();
    }

    return true;
    
   }

   public void fermerDessin(){
        Appli.reset();
        refreshTitle();

        statusBar.removeAll();
        toolBar.removeAll();
        mainPanel.removeAll();
        contentPane.removeAll();

        initMenu();
        initToolBar();
        fillContentPane();

        repaint();
        revalidate();
   }

   public void quitter(){
    if(fermer("Quitter l'application")){

        BaseAccess.db.releaseConnection();
        System.exit(0);
    }
   }

   public void nouveau(){
    fermer("Créer un nouveau dessin");
   }

   public void ouvrir(){
    if(fermer("Ouvrir un dessin")){

        int id = new OuvirDessinDialog(this).ouvrir();
        if(id == -1) return;

        Dessin dess = DessinManager.get(id);
        if(dess != null){
            
                ArrayList<Shape> liste = dess.getArrayList();
                if(liste != null){
                    Appli.ID_DESSIN = dess.getId();
                    Appli.fileName = dess.getNom();
                    Appli.shapeList = liste;
                    refreshTitle();
                }

        }
    }
   }

   public boolean enregistrer(){

    if(!Appli.saved){

        String nom = null;
        if(Appli.fileName == null){
            nom = JOptionPane.showInputDialog(this, "Entrez le nom du dessin:",
         "Enregistrer le dessin...", JOptionPane.QUESTION_MESSAGE);
        }
        else{ //Le dessin existe déjà en BD
            nom = Appli.fileName;
        }

        if (nom != null && !nom.trim().isEmpty()) {
            int newId = DessinManager.enregistrerDessin(nom);

            if(newId == -1){
                System.out.println("Echec de l'enregistrement!");
                return false;
            }

            System.out.println("Id = " + newId);
            Appli.saved = true;
            Appli.ID_DESSIN = newId;
            Appli.fileName = nom;
            refreshTitle();
            statusBar.refreshSaveStatus();
        } else {
            if(nom != null){ // L'utilisateur a entré un nom invalide
                JOptionPane.showMessageDialog(null, "Le nom que vous avez entré n'est pas valide.",
                 "Attention!", JOptionPane.WARNING_MESSAGE);
            }
            return false;
        }

    }
    return true;
   }


   public void refreshTitle(){
    setTitle((Appli.fileName != null ? Appli.fileName : "Nouveau dessin") + " - " + appName);
   }

				

    public static void main(String[] args) {
        try {
			
			FlatLightLaf.setup();
			UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf"); // Style de l'interface graphique
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {

			e1.printStackTrace();
		}
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
