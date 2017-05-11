/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.Repositories;

import DAL.DataAccessObject;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import DAL.DataAccessObject;
import Domain.Carport;
import Domain.Inventory;
import Domain.Carport_Has_Inventory;

/**
 *
 * @author Sean
 */
public class GenerateCarportDAO {

    
    public GenerateCarportDAO() throws Exception
    {
    }
    
    public void InsertCarportInventory(LinkedList<Carport_Has_Inventory> queries) throws SQLException
    {
        Connection stmt = null;
        String sql = "insert into Carport_Has_Inventory (Carport_ID, Inventory_ID, Inventory_AMT) values (?, ?, ?)";
        PreparedStatement ps = null;
        
        final int batchSize = 1000;
        int cnt = 0;
        try
        {
            stmt = DataAccessObject.getConnection();
            ps = stmt.prepareStatement(sql);
            for (Carport_Has_Inventory query : queries) {
                ps.setInt(1, query.getCarportID());
                ps.setInt(2, query.getInventory_ID());
                ps.setInt(3, query.getAMT());
                ps.addBatch();
                
                if(++cnt % batchSize == 0) 
                {
                    ps.executeBatch();
                }
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     public void InsertCarportInventory(ArrayList<Carport_Has_Inventory> queries) throws SQLException
    {
        Connection stmt = DataAccessObject.getConnection();
        String sql = "insert into Carport_Has_Inventory (Carport_ID, Inventory_ID, Inventory_AMT) values (?, ?, ?)";
        PreparedStatement ps = stmt.prepareStatement(sql);
        
        final int batchSize = 1000;
        int cnt = 0;
        try
        {
            
            
            for (Carport_Has_Inventory query : queries) {
                ps.setInt(1, query.getCarportID());
                ps.setInt(2, query.getInventory_ID());
                ps.setInt(3, query.getAMT());
                ps.addBatch();
                
                if(++cnt % batchSize == 0) 
                {
                    ps.executeBatch();
                }
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ps.executeBatch();
        ps.close();
        DataAccessObject.releaseConnection(stmt);
    }
     
    public void InsertCustomCarport(Carport cp) throws SQLException
    {
        Connection stmt = null;
                PreparedStatement ps = null;
        final int batchSize = 1000;
        int cnt = 0;
        String sql = "insert into Carport (Title, UUID, Height, Width, Length, CPType, RoofType) values (?, ?, ?, ?, ?, ?, ?)";
        
        
        try
        {
                stmt = DataAccessObject.getConnection();
                ps = stmt.prepareStatement(sql);
                ps.setString(1, cp.getTitle());
                ps.setString(2, cp.getUUID());
                ps.setInt(3, cp.getHeight());
                ps.setInt(4, cp.getWidth());
                ps.setInt(5, cp.getLength());
                ps.setString(6, cp.getCarportType());
                ps.setString(7, cp.getRoofType());
                ps.addBatch();
                
                if(++cnt % batchSize == 0) 
                {
                    ps.executeBatch();
                }
                ps.executeBatch();
                
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       //DataAccessObject.releaseConnection(stmt);
    }
    
    //Returns true if carport with UUID exists
    public boolean ValidateUUID(String uuid) throws SQLException
    {
          boolean status=false;  
        Connection stmt = null;
        try
        {    
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps=stmt.prepareStatement(  
                "select * from Carport where UUID=?");  
            ps.setString(1,uuid);    
            ResultSet rs=ps.executeQuery();  
            status=rs.next();  
        }catch(Exception e){System.out.println(e);}  
            return status;  
    }
    
    //Return Carport Object From Valid UUID
    public Carport CarportFromUUID(String xuuid) throws SQLException
    {
        Connection stmt = null;
        String sql = "SELECT * FROM Carport\n" +
            "WHERE UUID = '"+xuuid+"';";
        Carport cp = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try 
        {
            stmt = DataAccessObject.getConnection();
            ps = stmt.prepareStatement(sql);
            rs = ps.executeQuery(sql);           
            while (rs.next()) 
            {
                int cpID = rs.getInt("Carport_ID");
                String cpTitle = rs.getString("Title");
                String cpUUID = rs.getString("UUID");
                int cpHeight = rs.getInt("Height");
                int cpWidth = rs.getInt("Width");
                int cpLength = rs.getInt("Length");
                cp = new Carport(cpID, cpTitle, cpUUID, cpHeight, cpWidth, cpLength);
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cp;
    }
    
    public boolean validateSupPos(Inventory inv) throws SQLException
    {
         boolean status=false;  
        Connection stmt = null;
        try
        {    
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps=stmt.prepareStatement(  
                "select * from Inventory where Length=?");  
            ps.setInt(1,inv.getLength());    
            ResultSet rs=ps.executeQuery();  
            status=rs.next();  
        }catch(Exception e){System.out.println(e);}  
            return status;  
    }

    public int calcMinLength(Inventory inv) throws SQLException
    {
      Connection stmt = null;
      int invLength = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT MIN(Length)\n" +
                    "FROM Inventory\n" +
                    "WHERE Length>=?\n" +
                    "AND Depth = ?\n" +
                    "AND Width = ?\n" +
                    "AND Type = ? ");
           
            ps.setInt(1,inv.getLength()); 
            ps.setInt(2, inv.getDepth());
            ps.setDouble(3, inv.getWidth());
            ps.setString(4, inv.getType());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invLength = rs.getInt("MIN(Length)");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invLength;
    }
    
    //Wood 
    public int calcMinLengthSuppBeam(Inventory inv) throws SQLException
    {
      Connection stmt = null;
      int invLength = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT MIN(Length)\n" +
                    "FROM Inventory\n" +
                    "WHERE Length>=?\n" +
                    "AND Depth = ?\n" +
                    "AND Width = ?\n" +
                    "AND Type = ?");
           
            ps.setInt(1,inv.getLength()); 
            ps.setInt(2,inv.getDepth());
            ps.setDouble(3,inv.getWidth());
            ps.setString(4, inv.getType());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invLength = rs.getInt("MIN(Length)");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invLength;
    }
    
    //RoofPiece
    public int calcMinLengthRoofPiece(Inventory inv) throws SQLException
    {
      Connection stmt = null;
      int invLength = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT MIN(Length)\n" +
                    "FROM Inventory\n" +
                    "WHERE Length>=?\n" +
                    "AND Material = ?\n" +
                    "AND Width = ?\n" +
                    "AND Type = ?");
           
            ps.setInt(1,inv.getLength()); 
            ps.setString(2,inv.getMaterial());
            ps.setDouble(3,inv.getWidth());
            ps.setString(4, inv.getType());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invLength = rs.getInt("MIN(Length)");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invLength;
    }
    
    public int boxAmtScrewBox(Inventory inv) throws SQLException
    {
      Connection stmt = null;
      int boxAmt = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT MIN(Box_AMT)\n" +
                    "FROM Inventory\n" +
                    "WHERE Length=?\n" +
                    "AND Width= ?\n" +
                    "AND Material= ?\n" +
                    "AND Type= ? ");
           
            ps.setInt(1,inv.getLength()); 
            ps.setDouble(2, inv.getWidth());
            ps.setString(3, inv.getMaterial());
            ps.setString(4, inv.getType());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                boxAmt = rs.getInt("MIN(Box_AMT)");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return boxAmt;
    }
    
    public void giveInventoryIdList(List<Inventory> invlist)
    {
        ArrayList idList = null;
        Connection stmt = null;
        int invID = 0;
        for (Inventory inv : invlist)
        {
            if (inv.getMaterial().equals("Brædt")) 
            {
                try
                {    
                    stmt = DataAccessObject.getConnection();
                    PreparedStatement ps=stmt.prepareStatement(  
                            "SELECT *\n" +
                            "FROM Inventory\n" +
                            "WHERE Length=?\n" +
                            "AND Width= ?\n" +
                            "AND Depth=  ?\n" +
                            "AND Type= ? ");  
                    ps.setInt(1,inv.getLength());  
                    ps.setDouble(1,inv.getWidth());
                    ps.setInt(1,inv.getDepth());
                    ps.setString(1,inv.getType());
                    ResultSet rs=ps.executeQuery();  
                    while (rs.next()) 
                        {
                            invID = rs.getInt("Inventory_ID");
                        }   
                    idList.add(invID);
                }catch(Exception e){System.out.println(e);}
            }     
        }          
    }
    
    public int getBrædtID(Inventory inv) throws SQLException
    {
        Connection stmt = null;
      int invID = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT *\n" +
                    "FROM Inventory\n" +
                    "WHERE Length=?\n" +
                    "AND Depth = ?\n" +
                    "AND Width = ?\n" +
                    "AND Type = ?");
           
            ps.setInt(1,inv.getLength()); 
            ps.setInt(2,inv.getDepth());
            ps.setDouble(3,inv.getWidth());
            ps.setString(4, inv.getType());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invID = rs.getInt("Inventory_ID");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invID;
    }

    public int getRoofPieceID(Inventory inv) throws SQLException
    {
        Connection stmt = null;
      int invID = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT *\n" +
                    "FROM Inventory\n" +
                    "WHERE Length=?\n" +
                    "AND Material = ?\n" +
                    "AND Width = ?\n" +
                    "AND Type = ?");
           
            ps.setInt(1, inv.getLength()); 
            ps.setString(2, inv.getMaterial());
            ps.setDouble(3, inv.getWidth());
            ps.setString(4, inv.getType());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invID = rs.getInt("Inventory_ID");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invID;
    }
    
    public int getSuppBracketID(Inventory inv) throws SQLException
    {
        Connection stmt = null;
      int invID = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT *\n" +
                    "FROM Inventory\n" +
                    "WHERE Material = ?\n" +
                    "AND Type = ?");

            ps.setString(1, inv.getMaterial());
            ps.setString(2, inv.getType());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invID = rs.getInt("Inventory_ID");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invID;
    }
    
      public int getSuppBeamBoltsID(Inventory inv) throws SQLException
    {
        Connection stmt = null;
      int invID = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT *\n" +
                    "FROM Inventory\n" +
                    "WHERE Material = ?\n" +
                    "AND Type = ?\n" +
                    "AND Width = ?\n" +
                    "AND Length = ?");
           
            ps.setString(1, inv.getMaterial());
            ps.setString(2, inv.getType());
            ps.setDouble(3, inv.getWidth());
            ps.setInt(4, inv.getLength());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invID = rs.getInt("Inventory_ID");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invID;
    }
      
       public int getCoverID(Inventory inv) throws SQLException
    {
        Connection stmt = null;
      int invID = 0;
      try
      {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT *\n" +
                    "FROM Inventory\n" +
                    "WHERE Material = ?\n" +
                    "AND Type = ?\n" +
                    "AND Length = ?");
           
            ps.setString(1, inv.getMaterial());
            ps.setString(2, inv.getType());
            ps.setInt(3, inv.getLength());
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                invID = rs.getInt("Inventory_ID");
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return invID;
    }
       
     public List<Carport_Has_Inventory> generateMaterialList (int carportid)
     {
         Connection stmt = null;
         List<Carport_Has_Inventory> materialList = new ArrayList<>();
         try
         {
            stmt = DataAccessObject.getConnection();
            PreparedStatement ps = stmt.prepareStatement(
                    "SELECT *\n" +
                    "FROM Carport_Has_Inventory\n" +
                    "WHERE Carport_ID = ?");

            ps.setInt(1, carportid);
            ResultSet rs = ps.executeQuery();           
            while (rs.next()) 
            {
                int CPID = rs.getInt("Carport_ID");
                int invID = rs.getInt("Inventory_ID");
                int invAmt = rs.getInt("Inventory_AMT");
                materialList.add(new Carport_Has_Inventory(CPID, invID, invAmt));
            }    
        }     
        catch (SQLException ex) 
        {
            Logger.getLogger(GenerateCarportDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

         return materialList;
     }
       
       
}