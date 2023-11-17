package core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;


public class BaseAccess {
    
    @Getter@Setter
    private Connection conn;
    public static BaseAccess db;
    
    public BaseAccess()
    {
      try{
            String url = "jdbc:sqlite:drawshapes.db";
			conn = DriverManager.getConnection(url);
            System.out.println("Connexion établie avec succès.");
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            
        }  
    }

    public void releaseConnection(){
        try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    
}
