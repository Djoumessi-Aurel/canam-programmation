package core.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import core.Appli;
import utils.Functions;

public class DessinManager {
    
    public static Dessin get(int id)
	{
		try
        {
    	PreparedStatement prepare = BaseAccess.db.getConn().prepareStatement("SELECT * FROM dessins WHERE id = ?");
    	prepare.setInt(1, id);
    	ResultSet result = prepare.executeQuery();
		if(result.next()) {
			Date date_cr = Appli.dateFormat.parse(result.getString("date_creation"));
			Date date_mod = Appli.dateFormat.parse(result.getString("date_modification"));

			return new Dessin(id, result.getString("nom"), date_cr, date_mod
					, result.getBytes("data"));
			}
			else return null; //S'il n'y a pas de résultat...*/
		}
    
    catch(Exception e){
            e.printStackTrace();
        }
		return null;
	}

	public static int enregistrerDessin(String nom){
		if(Appli.ID_DESSIN == -1){
			return insertDessin(nom);
		}
		else{
			return updateDessin(Appli.ID_DESSIN);
		}
	}


	public static int insertDessin(String nom){ //Retourne l'id du dessin inséré

		byte[] data;
		try {
			data = Functions.convertirEnBytes(Appli.shapeList);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

		try
			{
			PreparedStatement prepare = BaseAccess.db.getConn().prepareStatement(
				"INSERT INTO dessins (nom, date_creation, date_modification, data)"
					+ " VALUES (?, strftime('%Y-%m-%d %H:%M:%S','now'), strftime('%Y-%m-%d %H:%M:%S','now'), ?)");
			prepare.setString(1, nom);
			prepare.setBytes(2, data);
			prepare.executeUpdate();

			ResultSet rs = BaseAccess.db.getConn().prepareStatement("select last_insert_rowid() as last_id;").executeQuery();
			return rs.getInt("last_id");
			}
		
		catch(Exception e){
				e.printStackTrace();
			}
		return -1;
	}


	public static int updateDessin(int id){

		byte[] data;
		try {
			data = Functions.convertirEnBytes(Appli.shapeList);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

		try
			{
			PreparedStatement prepare = BaseAccess.db.getConn().prepareStatement(
				"UPDATE dessins SET date_modification=strftime('%Y-%m-%d %H:%M:%S','now'), data=? WHERE id=?");

			prepare.setBytes(1, data);
			prepare.setInt(2, id);
			prepare.executeUpdate();
			}
		
		catch(Exception e){
				e.printStackTrace();
			}

		return id;
	}

}
