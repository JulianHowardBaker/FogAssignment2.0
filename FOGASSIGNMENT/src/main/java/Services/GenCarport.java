/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;
import DAL.Repositories.GenerateCarportDAO;
import Domain.Carport;
/**
 *
 * @author Sean
 */
public class GenCarport {
    
    public static String generateUUID()
    {
        String uuid = UUID.randomUUID().toString();
        //if condition (check UUID doesn't already exist in DB Table)
        
        return uuid;
    }

    
    public static void generateCP(Carport cp) throws Exception
    {
        GenerateCarportDAO gcdao = new GenerateCarportDAO();
        //inserts customer carport into DB
        Carport cpval = null;
        SectionGeneration sGen = new SectionGeneration();
        try{
        gcdao.InsertCustomCarport(cp);
        //generate Array of Inventory to add to DB
      
      
        //Adds inventory string to DB
        }catch(SQLException e) {
            System.err.println("Caught SQLException: " + e.getMessage());
        }
          cpval = gcdao.CarportFromUUID(cp.getUUID());
        sGen.initPartGeneration(cpval);
    }
    
    
}
