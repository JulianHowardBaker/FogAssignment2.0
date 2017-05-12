/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author Sean
 */
public class Carport_Has_Inventory {
    private int CarportID;
    private int Inventory_ID;
    private int AMT;

    
    public Carport_Has_Inventory(int CPid, int  INVid, int AMT)
    {
        this.CarportID = CPid;
        this.Inventory_ID = INVid;
        this.AMT = AMT;
    }

    /**
     * @return the CarportID
     */
    public int getCarportID() {
        return CarportID;
    }

    /**
     * @param CarportID the CarportID to set
     */
    public void setCarportID(int CarportID) {
        this.CarportID = CarportID;
    }

    /**
     * @return the Inventory_ID
     */
    public int getInventory_ID() {
        return Inventory_ID;
    }

    /**
     * @param Inventory_ID the Inventory_ID to set
     */
    public void setInventory_ID(int Inventory_ID) {
        this.Inventory_ID = Inventory_ID;
    }

    /**
     * @return the AMT
     */
    public int getAMT() {
        return AMT;
    }

    /**
     * @param AMT the AMT to set
     */
    public void setAMT(int AMT) {
        this.AMT = AMT;
    }
    
}
