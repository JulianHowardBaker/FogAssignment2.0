/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import DAL.Repositories.GenerateCarportDAO;
import Domain.Carport;
import Domain.Carport_Has_Inventory;
import Domain.Inventory;

/**
 *
 * @author Sean
 */
public class SectionGeneration implements ICarportDesign {
    private List<Inventory> invList;
    private Carport cp;
    private GenerateCarportDAO dao;
    private int NoSuppBeams = 0;
    private int NoPosts = 0;
    private int NoBrackets = 0;
    private int NoRoofPieces = 0;
    
    @Override
   public void initPartGeneration(Carport cpp) throws Exception
   {
       this.cp = cpp;
       this.invList = new ArrayList<>();
       //New DAO Object
       dao = new GenerateCarportDAO();
       //Generate Carport Support Posts
       genSupportPosts();
       genSupportBeams();
       genCrossSupport();
       genWindbreakSides();
       genWindbreakEnds();
       genRoofPieces();
       genBeamCrossSuppBrackets();
       genPostBeamBolts();
       genBracketScrews();
       genRoofScrews();
       genGutter();
       genWindbreakEndCover();
       genWindbreakSidesCover();
       
       for (Inventory inv: invList)
       {
           System.out.println(inv);
       }
       //Convert invList into a list of Inventory ID's + Counter
       HashMap<Integer, Integer> idList = invIDCounter(invList);
       
       
       //Old Version of JAVA ZZzz..
//       idList.entrySet().forEach((entry) -> {
//           System.out.println(entry.getKey()+" : "+entry.getValue());
//        });

       //Convert to ArrayList of Carport_Has_Inventory Objects
       ArrayList<Carport_Has_Inventory> cpinvlist = genCPInvObjects(idList, cp.getCarportID());
       
       //Push to DB in mass batch insert
       dao.InsertCarportInventory(cpinvlist);
   }
   
    @Override
   public void genSupportPosts() throws SQLException
   {
       //Wood Type
       String WoodType = "TrykImp";
       
       //Material Type
       String Material = "Bræt";
       
       //Calc amount of posts every 500 Carport length(500cm) - add another 2 posts
       int SupPos_AMT = ((cp.getLength()/500)+2)*2;
        setNoPosts(SupPos_AMT);
       //Currently using standardised post depth and width
       int SupPosDepth = 100;
       int SupPosWidth = 100;
       
       //Calc Post Length (90cm in ground+height)
       int SupPostLength = 90+cp.getHeight();
       
       Inventory inv = new Inventory(SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
       
       
       //Does match exist in DB 
       if (dao.validateSupPos(inv))
       {
           //Check DB - is there enough inventory in stock
           Inventory invFinal = new Inventory(SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invFinal);
           // loop adding calculated amount of posts to List
           for(int i=0; i<SupPos_AMT; i++)
           {
               invList.add(new Inventory(inv_id, SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }          
       }
       else{
           int tempLength = inv.getLength();
           //If no match return closest match, 600cm max length!
           if (tempLength <= 600)
           {               
               //Sets the new length
               SupPostLength = dao.calcMinLength(inv);
                  Inventory invFinal = new Inventory(SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invFinal);
               // loop adding calculated amount of posts to List
               for(int i=0; i<SupPos_AMT; i++)
               {
                   invList.add(new Inventory(inv_id, SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType));
               }  
                    
           }
           //this only works for carports with length < 12m
           else 
           {
               inv.setLength(inv.getLength()/2);
               int newSupPostLength = dao.calcMinLength(inv);
               Inventory invFinal = new Inventory(newSupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
               //get ID matching parameters from SQL Query  
               int inv_id = dao.getBrædtID(invFinal);
               // loop adding calculated amount of posts to List
               for(int i=0; i<(SupPos_AMT*2); i++)
               {
                   invList.add(new Inventory(inv_id, newSupPostLength, SupPosDepth, SupPosWidth, Material, WoodType));
               }  
               
           }
           //Check DB - is there enough inventory in stock   
       }
   }
   
    @Override
   public void genSupportBeams() throws SQLException
   {
        //Wood Type
       String WoodType = "TrykImp";
       
       //Material Type
       String Material = "Bræt";
       
       //Calc amount of posts every 500 Carport length(500cm) - add another 2 posts
       int SupPos_AMT = 2;
       
       //Currently using standardised post depth and width
       int SupPosDepth = 45;
       int SupPosWidth = 195;
       
       //Calc Post Length 
       int SupPostLength = cp.getLength();
       
       //New Inventory Object to carry to DB
       Inventory invSuppBeam = new Inventory(SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
       
       
       if(invSuppBeam.getLength() <= 600)
       {
           //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invSuppBeam);
           
           Inventory invSuppBeamFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invSuppBeamFinal);
           
           for(int i=0; i<(SupPos_AMT); i++)
           {
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
       }
        else if(invSuppBeam.getLength() > 600 && invSuppBeam.getLength() <= 1200)
        {
               //Calc lowest value in DB
           invSuppBeam.setLength(invSuppBeam.getLength()/2);
           int tempLength = dao.calcMinLengthSuppBeam(invSuppBeam);
           
           Inventory invSuppBeamFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invSuppBeamFinal);
           
           for(int i=0; i<(SupPos_AMT*2); i++)
           {
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
        }
   }
   
    @Override
   public void genCrossSupport() throws SQLException
   {
       //Wood Type
       String WoodType = "TrykImp";
       
       //Material Type
       String Material = "Bræt";
       
       //Calc amount of posts every 80cm Carport length - add another 1 CrossBeam
       int SupPos_AMT = ((cp.getLength()+79)/80)+1;
        setNoSuppBeams(SupPos_AMT);
       
       //Currently using standardised post depth and width
       int SupPosDepth = 38;
       int SupPosWidth = 73;
       
       //Calc Post Length 
       int SupPostLength = cp.getWidth();
       
       //New Inventory Object to carry to DB
       Inventory invSuppBeam = new Inventory(SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
       
       if(invSuppBeam.getLength() <= 600)
       {
           //Set Suppbeams variable
            setNoSuppBeams(SupPos_AMT);
           //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invSuppBeam);
           
           Inventory invSuppBeamFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invSuppBeamFinal);
           
           for(int i=0; i<(SupPos_AMT); i++)
           {
               
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
       }
        else if(invSuppBeam.getLength() > 600 && invSuppBeam.getLength() <= 1200)
        {
           invSuppBeam.setLength(invSuppBeam.getLength()/2);
               //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invSuppBeam);
           
           Inventory invSuppBeamFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invSuppBeamFinal);
           
           for(int i=0; i<(SupPos_AMT*2); i++)
           {
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
        }       
   }
   
    @Override
   public void genWindbreakSides() throws SQLException
   {
       //Wood Type
       String WoodType = "TrykImp";
       
       //Material Type
       String Material = "Bræt";
       
       //Calc amount of posts every 80cm Carport length - add another 1 CrossBeam
       int SupPos_AMT = 2;
       
       //Currently using standardised post depth and width
       int SupPosDepth = 25;
       int SupPosWidth = 150;
       
       //Calc Post Length 
       int SupPostLength = cp.getLength();
       
       //New Inventory Object to carry to DB
       Inventory invWindbreak = new Inventory(SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
       
       if(invWindbreak.getLength() <= 600)
       {
           //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invWindbreak);
           
           Inventory invWindbreakFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invWindbreakFinal);
           
           for(int i=0; i<(SupPos_AMT); i++)
           {
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
       }
        else if(invWindbreak.getLength() > 600 && invWindbreak.getLength() <= 1200)
        {
            
               //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invWindbreak);
           
           Inventory invWindbreakFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invWindbreakFinal);
           
           for(int i=0; i<(SupPos_AMT*2); i++)
           {
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
        }       
   }
   
    @Override
   public void genWindbreakEnds() throws SQLException
   {
       //Wood Type
       String WoodType = "TrykImp";
       
       //Material Type
       String Material = "Bræt";
       
       //Calc amount of posts every 80cm Carport length - add another 1 CrossBeam
       int SupPos_AMT = 2;
       
       //Currently using standardised post depth and width
       int SupPosDepth = 25;
       int SupPosWidth = 150;
       
       //Calc Post Length 
       int SupPostLength = cp.getWidth();
       
       //New Inventory Object to carry to DB
       Inventory invWindbreak = new Inventory(SupPostLength, SupPosDepth, SupPosWidth, Material, WoodType);
      
       if(invWindbreak.getLength() <= 600)
       {
           //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invWindbreak);
            
           Inventory invWindbreakFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
           //get ID matching parameters from SQL Query  
           int inv_id = dao.getBrædtID(invWindbreakFinal);
       
           for(int i=0; i<(SupPos_AMT); i++)
           {
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
       }
        else if(invWindbreak.getLength() > 600 && invWindbreak.getLength() <= 1200)
        {
            invWindbreak.setLength(invWindbreak.getLength()/2);
               //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invWindbreak);
             Inventory invWindbreakFinal = new Inventory(tempLength, SupPosDepth, SupPosWidth, Material, WoodType);
       //get ID matching parameters from SQL Query  
       int inv_id = dao.getBrædtID(invWindbreakFinal);
       
           for(int i=0; i<(SupPos_AMT*2); i++)
           {
               invList.add(new Inventory(inv_id, tempLength, SupPosDepth, SupPosWidth, Material, WoodType));
           }  
        }       
   }
           
    @Override
   public void genRoofPieces() throws SQLException
   {
       //Calc amount of RoofPieces needed for Carport
       int RP_AMT = (cp.getWidth()+108/109);
        setNoRoofPieces(RP_AMT);
       //Set RoofWidth
       int RPWidth = 109;
       
       //Set Length
       int RPLength = cp.getWidth();
       
       //Set Material 
       String RPMaterial = "Plastmo";
       
       //Set Type
       String RPType = "Ecolite Blåtonet";
       
       //New Inventory Object to carry to DB
       Inventory invRoofPiece = new Inventory(RPMaterial, RPType, RPWidth, RPLength);
       
     
       if(invRoofPiece.getLength() <= 600)
       {
           //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthRoofPiece(invRoofPiece);
           Inventory invRoofPieceFinal = new Inventory(RPMaterial, RPType, RPWidth, tempLength);
             //get ID matching parameters from SQL Query  
           int inv_id = dao.getRoofPieceID(invRoofPieceFinal);
           
           for(int i=0; i<(RP_AMT); i++)
           {
               invList.add(new Inventory(inv_id, RPMaterial, RPType, RPWidth, tempLength));
           }  
       }
        else if(invRoofPiece.getLength() > 600 && invRoofPiece.getLength() <= 1200)
        {
            invRoofPiece.setLength(invRoofPiece.getLength()/2);
               //Calc lowest value in DB 
           int tempLength = dao.calcMinLengthSuppBeam(invRoofPiece);
           
           Inventory invRoofPieceFinal = new Inventory(RPMaterial, RPType, RPWidth, tempLength);
             //get ID matching parameters from SQL Query  
           int inv_id = dao.getRoofPieceID(invRoofPieceFinal);
           
           for(int i=0; i<(RP_AMT*2); i++)
           {
               invList.add(new Inventory(inv_id, RPMaterial, RPType, RPWidth, tempLength));
           }  
        }   
   }
   
    @Override
   public void genBeamCrossSuppBrackets() throws SQLException
   {
       //Set # of Cross Brackets needed
       int NoCrossBrackets = getNoSuppBeams() * 2;
        setNoBrackets(NoCrossBrackets);
       //Set Material 
       String CBMaterial = "Vinkelbeslag";
       
       // Set Type;
       String CBType = "35";
       
       //New Inventory Object to carry to DB
       Inventory bcsb = new Inventory(CBMaterial, CBType);
       
       //get ID matching parameters from SQL Query  
       int inv_id = dao.getSuppBracketID(bcsb);
       
       
       for(int i=0; i<(NoCrossBrackets); i++)
           {
               invList.add(new Inventory(inv_id, CBMaterial, CBType));
           }
   }
   
    @Override
   public void genPostBeamBolts() throws SQLException
   {
       //Set # of Bolts
       int NoPostBolts = getNoPosts() * 2;
       
       //Set Length
       int PBLength = 120;
       
       //Set Width
       int PBWidth = 10;
       
       //Set Material
       String PBMaterial = "Bræddebolt";
               
       //Set Type
       String PBType = "Bræddebolt";
       
        //New Inventory Object to carry to DB
       Inventory PBB = new Inventory(PBMaterial, PBType, PBWidth, PBLength);
       
       //get ID matching parameters from SQL Query  
       int inv_id = dao.getSuppBeamBoltsID(PBB);
       
       for(int i=0; i<(NoPostBolts); i++)
           {
               invList.add(new Inventory(inv_id, PBMaterial, PBType, PBWidth, PBLength));
           }
   }
   
    @Override
   public void genBracketScrews() throws SQLException
   {
       //Set No # of bracket screws
       int BracketScrews_AMT = getNoBrackets() * 10;
       
       //Set Length
       int BSLength = 70;
       
       //Set Width
       double BSWidth = 4.5;
       
       //Set Material
       String BSMaterial = "Skruer";
               
       //Set Type
       String BSType = "Skruer";
       
       //New Inventory Object to carry to DB
       Inventory invBracketScrew = new Inventory(BSMaterial, BSType, BSWidth, BSLength);
       
       //get ID matching parameters from SQL Query  
       int inv_id = dao.getSuppBeamBoltsID(invBracketScrew);
       
       //Calculate how many boxes of screws needed
       int minBox = dao.boxAmtScrewBox(invBracketScrew);
       int NoBracketBox = (BracketScrews_AMT / (minBox+1))+1;
       
       for(int i=0; i<(NoBracketBox); i++)
           {
               invList.add(new Inventory(inv_id, BSMaterial, BSType, BSWidth, BSLength, minBox));
           }
   }
   
    @Override
   public void genRoofScrews() throws SQLException
   {
       //Set No # of bracket screws
       int BracketScrews_AMT = getNoBrackets() * 10;
       
       //Set Length
       int BSLength = 60;
       
       //Set Width
       double BSWidth = 4.5;
       
       //Set Material
       String BSMaterial = "Skruer";
               
       //Set Type
       String BSType = "Skruer";
       
       //New Inventory Object to carry to DB
       Inventory invRoofScrew = new Inventory(BSMaterial, BSType, BSWidth, BSLength);
       
        //get ID matching parameters from SQL Query  
       int inv_id = dao.getSuppBeamBoltsID(invRoofScrew);
       
       //Calculate how many boxes of screws needed
       int minBox = dao.boxAmtScrewBox(invRoofScrew);
       int NoBracketBox = (BracketScrews_AMT / (minBox+1))+1;
       
       for(int i=0; i<(NoBracketBox); i++)
           {
               invList.add(new Inventory(inv_id, BSMaterial, BSType, BSWidth, BSLength, minBox));
           }
   }
   
    @Override
   public void genGutter() throws SQLException
   {
       System.out.println("Gutter not ready");
   }

    @Override
    public void genWindbreakEndCover() throws SQLException {
        
        //Set No # of covers neededs
       int covers_AMT = ((cp.getWidth()*2)/99)+1;
       
       //Set Length
       int CoverLength = 100;
       
       //Set Material
       String CoverMaterial = "Cover";
               
       //Set Type
       String CoverType = "Aluminium";
        
       //New Inventory Object to carry to DB
       Inventory invCover = new Inventory(CoverMaterial, CoverLength, CoverType);
       
       //get ID matching parameters from SQL Query  
       int inv_id = dao.getCoverID(invCover);
       
       for(int i=0; i<(covers_AMT); i++)
           {
               invList.add(new Inventory(inv_id, CoverMaterial, CoverLength, CoverType));
           }
    }

    @Override
    public void genWindbreakSidesCover() throws SQLException {
        //Set No # of covers neededs
       int covers_AMT = ((cp.getLength()*2)/99)+1;
       
       //Set Length
       int CoverLength = 100;
       
       //Set Material
       String CoverMaterial = "Cover";
               
       //Set Type
       String CoverType = "Aluminium";
        
        //New Inventory Object to carry to DB
       Inventory invCover = new Inventory(CoverMaterial, CoverLength, CoverType);
       
       //get ID matching parameters from SQL Query  
       int inv_id = dao.getCoverID(invCover);
       
       for(int i=0; i<(covers_AMT); i++)
           {
               invList.add(new Inventory(inv_id, CoverMaterial, CoverLength, CoverType));
           }
    }
    
    public HashMap<Integer, Integer> invIDCounter(List<Inventory> invList)
    {
            HashMap<Integer, Integer> invCounter = new HashMap<Integer, Integer>();
            for (Inventory inv : invList)
            {
                if (invCounter.containsKey(inv.getInventory_ID()))
                {
                    int val = invCounter.get(inv.getInventory_ID());
                    invCounter.put(inv.getInventory_ID(), val+1);
                }
                else
                {
                    invCounter.put(inv.getInventory_ID(), 1);
                }
            }
            return invCounter;
    }
    
    public ArrayList<Carport_Has_Inventory> genCPInvObjects(HashMap<Integer, Integer> idcounter, int cpID)
    {
        ArrayList<Carport_Has_Inventory> cpinvlist = new ArrayList<Carport_Has_Inventory>();
        for(Map.Entry<Integer, Integer> entry : idcounter.entrySet()) {
            int inv_id = entry.getKey();
            int inv_amt = entry.getValue();
            
            cpinvlist.add(new Carport_Has_Inventory(cpID, inv_id, inv_amt));
        }
        return cpinvlist;
    }

    /**
     * @return the NoSuppBeams
     */
    public int getNoSuppBeams() {
        return NoSuppBeams;
    }

    /**
     * @param NoSuppBeams the NoSuppBeams to set
     */
    public void setNoSuppBeams(int NoSuppBeams) {
        this.NoSuppBeams = NoSuppBeams;
    }

    /**
     * @return the NoPosts
     */
    public int getNoPosts() {
        return NoPosts;
    }

    /**
     * @param NoPosts the NoPosts to set
     */
    public void setNoPosts(int NoPosts) {
        this.NoPosts = NoPosts;
    }

    /**
     * @return the NoBrackets
     */
    public int getNoBrackets() {
        return NoBrackets;
    }

    /**
     * @param NoBrackets the NoBrackets to set
     */
    public void setNoBrackets(int NoBrackets) {
        this.NoBrackets = NoBrackets;
    }

    /**
     * @return the NoRoofPieces
     */
    public int getNoRoofPieces() {
        return NoRoofPieces;
    }

    /**
     * @param NoRoofPieces the NoRoofPieces to set
     */
    public void setNoRoofPieces(int NoRoofPieces) {
        this.NoRoofPieces = NoRoofPieces;
    }
    
     
}
