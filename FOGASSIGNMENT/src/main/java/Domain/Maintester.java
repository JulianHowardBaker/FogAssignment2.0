/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;
import DAL.Repositories.GenerateCarportDAO;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Sean
 */
public class Maintester {
    public static void main(String[] args) throws Exception
    {
        GenerateCarportDAO dao = new GenerateCarportDAO();
        Carport cp = new Carport("hi", "2232", 3, 3, 4, "kek", "err");
        
        //dao.InsertCustomCarport(cp);
        
        Carport_Has_Inventory inv = new Carport_Has_Inventory(1,1,5);
        Carport_Has_Inventory inv2 = new Carport_Has_Inventory(1,2,6);
        ArrayList<Carport_Has_Inventory> invList = new ArrayList<>();
        invList.add(inv);
        invList.add(inv2);
        dao.InsertCarportInventory(invList);
    }
}
