package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.Appli;
import core.shapes.Cercle;
import utils.FocusComponentListener;
import utils.Functions;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;

public class CercleDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

    private JSpinner spinnerRayon, spinnerX, spinnerY;
    private JLabel labelCouleur;

    private int shapeIndex = -1;

	
	public CercleDialog(JFrame parent, int xCentre, int yCentre) {
		super(parent);
		setModal(true);
		setTitle("Dessiner un cercle");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        spinnerRayon = new JSpinner(new SpinnerNumberModel(40, 1, 1000, 1));
        spinnerX = new JSpinner(new SpinnerNumberModel(xCentre, 0, 3000, 1));
        spinnerY = new JSpinner(new SpinnerNumberModel(yCentre, 0, 3000, 1));
        JButton btCreer;

        {
			JPanel panel1 = new JPanel();
			FlowLayout fl_panel1 = (FlowLayout) panel1.getLayout();
			fl_panel1.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel1);
			{
				JLabel labelNom = new JLabel("Couleur: ");
                labelCouleur = new JLabel("       ");
                labelCouleur.setOpaque(true);
                labelCouleur.setBackground(Appli.defaultColor);
                JButton colorButton = new JButton("Choisir une couleur");

                // Action du bouton pour ouvrir le JColorChooser
                colorButton.addActionListener(e -> {
                    Color selectedColor = JColorChooser.showDialog(panel1, "Choisir une couleur", labelCouleur.getBackground());
                    if (selectedColor != null) {
                        labelCouleur.setBackground(selectedColor);
                    }
                });

				panel1.add(labelNom);
                panel1.add(labelCouleur);
                panel1.add(colorButton);
                labelNom.addMouseListener(new FocusComponentListener(colorButton));
			}
		}

		{
			JPanel panel1 = new JPanel();
			FlowLayout fl_panel1 = (FlowLayout) panel1.getLayout();
			fl_panel1.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel1);
			{
				JLabel labelNom = new JLabel("Rayon: ");
				panel1.add(labelNom);
                panel1.add(spinnerRayon);
                labelNom.addMouseListener(new FocusComponentListener(spinnerRayon));
			}
		}

        {
            JPanel panelGroup = Functions.createGroupPanel("Coordonnées du centre", Color.BLACK);
            panelGroup.setLayout(new BoxLayout(panelGroup, BoxLayout.PAGE_AXIS));
            {
                JPanel panel2 = new JPanel();
                FlowLayout fl_panel2 = (FlowLayout) panel2.getLayout();
                fl_panel2.setAlignment(FlowLayout.LEFT);
                panelGroup.add(panel2);
				JLabel labelNom = new JLabel("x: ");
				panel2.add(labelNom);
                panel2.add(spinnerX);
                labelNom.addMouseListener(new FocusComponentListener(spinnerX));
			}
            {
                JPanel panel2 = new JPanel();
                FlowLayout fl_panel2 = (FlowLayout) panel2.getLayout();
                fl_panel2.setAlignment(FlowLayout.LEFT);
                panelGroup.add(panel2);
				JLabel labelNom = new JLabel("y: ");
				panel2.add(labelNom);
                panel2.add(spinnerY);
                labelNom.addMouseListener(new FocusComponentListener(spinnerY));
			}
            contentPanel.add(panelGroup);
        }

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btCreer = new JButton("Valider");
				btCreer.setActionCommand("OK");
				btCreer.addActionListener(this);
				buttonPane.add(btCreer);
				getRootPane().setDefaultButton(btCreer);
			}
			{
				JButton btAnnuler = new JButton("Annuler");
				btAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();//On ferme la boîte de dialogue
					}
				});
				btAnnuler.setActionCommand("Cancel");
				buttonPane.add(btAnnuler);
			}
		}
        
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

        int rayon = (int)spinnerRayon.getValue();
        int xCentre = (int)spinnerX.getValue();
        int yCentre = (int)spinnerY.getValue();
        Color color = labelCouleur.getBackground();

        Cercle cercle = new Cercle(rayon, xCentre, yCentre, color);

        if(shapeIndex == -1){
            Appli.createShape(cercle);
        }
        else{
            Appli.modifyShape(shapeIndex, cercle);
        }

        dispose();
	}
	
	public void gererModification(int shapeIndex, Cercle cercle)//Modifications à faire lorsque la fenêtre va servir à MODIFIER les informations
	{
        this.shapeIndex = shapeIndex;

		this.setTitle("Modifier le cercle");
        spinnerRayon.setValue(cercle.getRayon());
        spinnerX.setValue(cercle.getXCentre());
        spinnerY.setValue(cercle.getYCentre());
		labelCouleur.setBackground(cercle.getColor());
		labelCouleur.setBackground(cercle.getColor());
	}

}
