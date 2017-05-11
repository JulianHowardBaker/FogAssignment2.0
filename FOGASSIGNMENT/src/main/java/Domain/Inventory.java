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
public class Inventory {
    private int Inventory_ID;
    private String Title;
    private int Length;
    private int Depth;
    private double Width;
    private String Material;
    private String Type;
    private int Box_AMT;
    private int Price;
    
    //Full Constructor
    public Inventory(int Inventory_ID, String Title, int Length, int Depth, double Width, String Material, String Type, int Box_AMT, int Price)
    {
        this.Inventory_ID = Inventory_ID;
        this.Title = Title;
        this.Length = Length;
        this.Depth = Depth;
        this.Width = Width;
        this.Material = Material;
        this.Type = Type;
        this.Box_AMT = Box_AMT;
        this.Price = Price;
    }
    
    //Wood Constructor
    public Inventory(int Inventory_ID, String Title, int Length, int Depth, double Width, String Material, String Type, int Price)
    {
        this.Inventory_ID = Inventory_ID;
        this.Title = Title;
        this.Length = Length;
        this.Depth = Depth;
        this.Width = Width;
        this.Material = Material;
        this.Type = Type;
        this.Price = Price;
    }
    
    //No ID Wood Constructor
    public Inventory(int Length, int Depth, double Width, String Material, String Type)
    {
        this.Length = Length;
        this.Depth = Depth;
        this.Width = Width;
        this.Material = Material;
        this.Type = Type;
    }
    
    //ID Wood Constructor
    public Inventory(int inv_ID, int Length, int Depth, double Width, String Material, String Type)
    {
        this.Inventory_ID = inv_ID;
        this.Length = Length;
        this.Depth = Depth;
        this.Width = Width;
        this.Material = Material;
        this.Type = Type;
    }
    
    //Constructor // Binder(Screw,Bolt etc..)
    public Inventory(int Inventory_ID, String Title, String Material, int Box_AMT, int Price)
    {
        this.Inventory_ID = Inventory_ID;
        this.Title = Title;
        this.Material = Material;
        this.Box_AMT = Box_AMT;
        this.Price = Price;
    }
    
    //Roofpart pr Bolt Constructor
    public Inventory(String Material, String Type, double Width, int Length)
    {
        this.Material = Material;
        this.Type = Type;
        this.Width = Width;
        this.Length = Length;
    }
    
    
     //Roofpart pr Bolt Constructor
    public Inventory(int id, String Material, String Type, double Width, int Length)
    {
        this.Inventory_ID = id;
        this.Material = Material;
        this.Type = Type;
        this.Width = Width;
        this.Length = Length;
    }
    
    public Inventory(String Material, String Type, double Width, int Length, int Box_AMT)
    {
        this.Material = Material;
        this.Type = Type;
        this.Width = Width;
        this.Length = Length;
        this.Box_AMT = Box_AMT;
    }
    
     public Inventory(int id, String Material, String Type, double Width, int Length, int Box_AMT)
    {
        this.Inventory_ID = id;
        this.Material = Material;
        this.Type = Type;
        this.Width = Width;
        this.Length = Length;
        this.Box_AMT = Box_AMT;
    }

    //Bracket Constructor
    public Inventory(String Material, String Type)
    {
        this.Material = Material;
        this.Type = Type;
    }
    
    //Bracket Constructor with ID
    public Inventory(int id, String Material, String Type)
    {
        this.Inventory_ID = id;   
        this.Material = Material;
        this.Type = Type;
    }
    
     //Cover Constructor
    public Inventory(String Material, int Length, String Type)
    {
        this.Material = Material;
        this.Length = Length;
        this.Type = Type;
    }
    
         //Cover Constructor with ID
    public Inventory(int id, String Material, int Length, String Type)
    {
        this.Inventory_ID = id;
        this.Material = Material;
        this.Length = Length;
        this.Type = Type;
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
     * @return the Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title the Title to set
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return the Length
     */
    public int getLength() {
        return Length;
    }

    /**
     * @param Length the Length to set
     */
    public void setLength(int Length) {
        this.Length = Length;
    }

    /**
     * @return the Depth
     */
    public int getDepth() {
        return Depth;
    }

    /**
     * @param Depth the Depth to set
     */
    public void setDepth(int Depth) {
        this.Depth = Depth;
    }

    /**
     * @return the Width
     */
    public double getWidth() {
        return Width;
    }

    /**
     * @param Width the Width to set
     */
    public void setWidth(double Width) {
        this.Width = Width;
    }

    /**
     * @return the Material
     */
    public String getMaterial() {
        return Material;
    }

    /**
     * @param Material the Material to set
     */
    public void setMaterial(String Material) {
        this.Material = Material;
    }

    /**
     * @return the Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type the WoodType to set
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return the Box_AMT
     */
    public int getBox_AMT() {
        return Box_AMT;
    }

    /**
     * @param Box_AMT the Box_AMT to set
     */
    public void setBox_AMT(int Box_AMT) {
        this.Box_AMT = Box_AMT;
    }

    /**
     * @return the Price
     */
    public int getPrice() {
        return Price;
    }

    /**
     * @param Price the Price to set
     */
    public void setPrice(int Price) {
        this.Price = Price;
    }
    
     @Override
    public String toString() 
    {
        return "Inventory{" + "ID=" + Inventory_ID + ", Material=" + Material + ", Type= " + Type + '}';
    }
    
}
