package core.database;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;

import core.shapes.Shape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Dessin {
    
    private int id;
    private String nom;
    private Date date_creation;
    private Date date_modification;
    private byte[] data;

    // Retrouver la liste de formes à partir des données brutes (le tableau d'octets data)
    public ArrayList<Shape> getArrayList(){

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bis);

            Object data = ois.readObject();
            if(data instanceof ArrayList<?>){
                return (ArrayList<Shape>) data;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        
        return null;
    }
}
