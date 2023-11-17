package gui.dialogs;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import core.database.BaseAccess;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class OuvirDessinDialog extends JDialog {
    private JTable table;
    private JButton openButton;
    private JButton cancelButton;
    private JLabel labelNorth;

    private int selectedId = -1;

    public OuvirDessinDialog(JFrame parent) {
        super(parent, "Ouvrir un dessin...", true);

        // Création de la table
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rend toutes les cellules non modifiables
            }
        };
        model.addColumn("ID");
        model.addColumn("Nom");
        model.addColumn("Date Création");
        model.addColumn("Date Modification");
        table.setModel(model);

        // Récupération de la colonne "ID" et définition de sa taille préférée
        TableColumn idColumn = table.getColumnModel().getColumn(0); // 0 est l'indice de la colonne "ID"
        idColumn.setPreferredWidth(20);

        // Remplissage de la table avec les données de la base de données
        try {
            String query = "SELECT id, nom, date_creation, date_modification FROM dessins";
            PreparedStatement statement = BaseAccess.db.getConn().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getInt("id"));
                row.add(resultSet.getString("nom"));
                row.add(resultSet.getString("date_creation"));
                row.add(resultSet.getString("date_modification"));
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            dispose();
        }

        // Création des boutons
        openButton = new JButton("Ouvrir");
        openButton.setEnabled(false);
        cancelButton = new JButton("Annuler");

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    selectedId = -1;
                    System.out.println("-1");
                } else {
                    selectedId = Integer.valueOf(table.getValueAt(selectedRow, 0).toString());
                    System.out.println(selectedId); // Affiche l'ID de la ligne sélectionnée
                }

                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la boîte de dialogue
            }
        });


        // Détection de la sélection de ligne
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permet de sélectionner une seule ligne à la fois

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        openButton.setEnabled(true);
                    }
                    else openButton.setEnabled(false);
                }
            }
        });


        // Ajout des composants à la boîte de dialogue
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(openButton);
        buttonsPanel.add(cancelButton);

        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        labelNorth = new JLabel("Liste des dessins enregistrés");
        labelNorth.setFont(new Font("Tahoma", Font.BOLD, 11));
        labelNorth.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(labelNorth, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }

    public int ouvrir(){ //renvoie l'id du dessin qu'on doit ouvrir
        setVisible(true);
        return selectedId;
    }

}
