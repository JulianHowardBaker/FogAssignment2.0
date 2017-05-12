package Web.DTO.CPGenerator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Domain.Carport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import static java.awt.Color.RED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import Services.GenCarport;

public class SideBYSide extends JFrame implements ActionListener{
    String[] portTypes = {"Back", "Flat"};
    JComboBox portTypesList = new JComboBox(portTypes);
    JFrame myFrame = new JFrame();
    JLabel lblText = new JLabel();
    JPanel container = new JPanel();
    JPanel panelOne = new JPanel();
    JPanel panelTwo = new JPanel();
    JPanel panelThree = new JPanel();
    JPanel cards = new JPanel(new CardLayout());
    final static String fstring = "fStringtxt";
    final static String sstring = "sstringtxt";
    int curHeight = 50;
    int curWidth = 50;
    int curLength = 50;
    
    public SideBYSide(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        //JPanel container = new JPanel();
//        JPanel panelOne = new JPanel();
//        JPanel panelTwo = new JPanel();
        //JPanel panelThree = new JPanel();
        myFrame.add(container);
        cards.add(panelTwo, fstring);
        cards.add(panelThree, sstring);
        panelOne.add(new JLabel("1"));
        panelTwo.add(new JLabel("2"));

        
        
        container.setLayout(new GridLayout(1, 2));
        container.add(panelOne);
        container.add(cards);
        
        this.add(container);
        
        final MyRectangle myRectangle = new MyRectangle(20, 100, 50, 50, Color.RED);
        final MyTriangle mt = new MyTriangle(75,35,Color.ORANGE);
        final flatPort genFlatPort = new flatPort(20, 20, 50, 50, Color.RED);
        final shedSideRoof ssr = new shedSideRoof(150, 100, 50, 50, Color.RED);
        final sideFlatPort genSideFlatPort = new sideFlatPort(150, 20, 50, 50, Color.RED);
        final flatPortRoof genFlatPortRoof = new flatPortRoof(20, 200, 50, 50, Color.RED);
        final flatPortRoofSide genFlatPortRoofSide = new flatPortRoofSide(150, 200, 50, 50, Color.RED);
        final shedFlat genShedFlat = new shedFlat(20, 250, 50, 50, Color.RED);
        final sideShedFlat genSideShedFlat = new sideShedFlat(150, 250, 50, 50, Color.RED);
        portTypesList.setSelectedIndex(1);
        portTypesList.addActionListener(this);
        
        JButton createPortButton = new JButton("Generate Carport");
        
        panelOne.add(portTypesList);
        panelOne.add(lblText);
       
        panelThree.add(lblText);
       
        
        JSlider jslidheight = new JSlider();
        jslidheight.setValue(50);
        jslidheight.setMaximum(250);
        jslidheight.setMinorTickSpacing(5);
        jslidheight.setMajorTickSpacing(10);
        jslidheight.setPaintTicks(true);
        jslidheight.setPaintLabels(true);

        // We'll just use the standard numeric labels for now...
        jslidheight.setLabelTable(jslidheight.createStandardLabels(50));

        JLabel Lwidth = new JLabel("Width");
        JLabel Lheight = new JLabel("Height");
        
        
        JSlider jslidwidth = new JSlider();
        jslidwidth.setValue(50);
        jslidwidth.setMaximum(250);
        jslidwidth.setMinorTickSpacing(5);
        jslidwidth.setMajorTickSpacing(10);
        jslidwidth.setPaintTicks(true);
        jslidwidth.setPaintLabels(true);

        // We'll just use the standard numeric labels for now...
        jslidwidth.setLabelTable(jslidwidth.createStandardLabels(50));
        
         JSlider jslidlength = new JSlider();
        jslidlength.setValue(50);
        jslidlength.setMaximum(1000);
        jslidlength.setMinorTickSpacing(20);
        jslidlength.setMajorTickSpacing(50);
        jslidlength.setPaintTicks(true);
        jslidlength.setPaintLabels(true);

        // We'll just use the standard numeric labels for now...
        jslidlength.setLabelTable(jslidlength.createStandardLabels(100));
        
        
        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);

                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                myRectangle.draw(g2d);
                mt.drawMe(g2d, new Point(20,100));
                ssr.draw(g2d);
                genFlatPort.draw(g2d);
                genSideFlatPort.draw(g2d);
                genFlatPortRoof.draw(g2d);
                genFlatPortRoofSide.draw(g2d);
                genShedFlat.draw(g2d);
                genSideShedFlat.draw(g2d);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };

        jslidheight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSlider jslidheight = (JSlider) ce.getSource();
                

                if (!jslidheight.getValueIsAdjusting()) {
                   
                    int newH = jslidheight.getValue();

                    
                    myRectangle.setHeight(newH);
                    //mt.setHeight(mt.getHeight()+(newH/3));
                    mt.setHeight(0.7*newH);
                    ssr.setHeight(newH);
                    genFlatPort.setHeight(newH);
                    genSideFlatPort.setHeight(newH);
                    genFlatPortRoof.setHeight(newH);
                    genFlatPortRoofSide.setHeight(newH);
                    genSideShedFlat.setHeight(newH);
                    genShedFlat.setHeight(newH);
                    curHeight = newH;
                    System.out.println("newH: "+ newH);
                    
                    panel.repaint();
                }
            }
        });
        
        jslidwidth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                
                JSlider jslidwidth = (JSlider) ce.getSource();

                if (!jslidwidth.getValueIsAdjusting()) {
                    int newW = jslidwidth.getValue();
                    

                    myRectangle.setWidth(newW);
                    mt.setWidth(newW+(newW/2));
                    genFlatPort.setWidth(newW);
                    genFlatPortRoof.setWidth(newW);
                    genShedFlat.setWidth(newW);
                    curWidth = newW;
                    System.out.println("newW: " + newW + ",");

                    panel.repaint();
                }
            }
        });
        
        jslidlength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                
                JSlider jslidlength = (JSlider) ce.getSource();

                if (!jslidlength.getValueIsAdjusting()) {
                    int newL = jslidlength.getValue();
                    ssr.setLength(newL);
                    genSideFlatPort.setLength(newL);
                    genFlatPortRoofSide.setLength(newL);
                    genSideShedFlat.setLength(newL);
                    curLength = newL;
                    System.out.println("newL: " + newL + ",");

                    panel.repaint();
                }
            }
        });
        
        panelOne.add(jslidheight);
        panelOne.add(jslidwidth);
        panelOne.add(jslidlength);
        panelOne.add(createPortButton);
        panelTwo.add(panel);
        
        createPortButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        // display/center the jdialog when the button is pressed
        JDialog d = new JDialog(myFrame, "Customized Carport", true);
        d.setSize(new Dimension(400,400));
        JPanel pan=new JPanel();
        
        //Generate the UUID KEY
        String uuid = GenCarport.generateUUID();
      
        //Generate Inventory & Push to DB
          try {
              Carport CusCP = new Carport("Custom", uuid, curHeight, curWidth, curLength, "Standard", "Flat");
              GenCarport.generateCP(CusCP);
          } catch (Exception ex) {
              Logger.getLogger(SideBYSide.class.getName()).log(Level.SEVERE, null, ex);
          }
        JTextArea cusPortInfo = new JTextArea(
          "Your Carport: "
        + "\n\n "
        + " Height: "+ curHeight+"cm"
        + " Width: "+ curWidth+"cm"
        + " Length: "+ curLength+"cm"
        + " Price: kr"
        + "\n"
        + " Unique Key : "
        + "-------------------"
        + uuid +"\n"
        + "------------------");
        cusPortInfo.setFont(new Font("Serif", Font.ITALIC, 16));
        cusPortInfo.setLineWrap(true);
        cusPortInfo.setWrapStyleWord(true);
        cusPortInfo.setOpaque(false);
        cusPortInfo.setEditable(false);
        pan.add(cusPortInfo);
        d.add(pan);
        
        
        //d.setLocationRelativeTo(myFrame);
        d.setVisible(true);
        //d.setSize(new Dimension(400, 400));
        
      }
    });

    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SideBYSide();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       /* if (e.getSource() == portTypesList) {
            JComboBox cb = (JComboBox)e.getSource();
            String msg = (String)cb.getSelectedItem();
            switch (msg) {
                case "Back": lblText.setText("Message 1");
                break;
                case "Flat": lblText.setText("Message 2");
                break;
                default: lblText.setText("We seem to have a problem");
            }
            }
        */
        
        if (e.getSource() == portTypesList){
            JComboBox cb = (JComboBox)e.getSource();
            String msg = (String)cb.getSelectedItem();
             
            if (msg.equals("Back"))
            {
           //     cards.show("fstring", "panelTwo");
                
                
                lblText.setText("Back yo");
            }
            if (msg.equals("Flat"))
            {
           //     cards.show(sstring, "Flat");
               
              
              
                
            
        }
        }
      }
        
    }

class MyRectangle {

    private int x, y, width, height;
    private final Color color;

    public MyRectangle(int x, int y, int w, int h, Color color) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D g2d) {
        Color prevState = g2d.getColor();

        g2d.setColor(color);

        g2d.drawRect(x, y, width, height);
           
        g2d.setColor(prevState);
        
        
//        Draw the lines on the back shed depending on height of shed
        int lenny = 0;
        
        while ((lenny+12) < height) {
        lenny = lenny+10;
        g2d.drawLine(x,lenny+y,x+width,lenny+y);
        }
        
        
        g2d.setColor(Color.yellow);
        
        g2d.fillRect((int) (width*0.25)+x, y, 7, height);
        g2d.fillRect((int) (width*1.25)+x, y, 7, height);
        //g2d.drawOval(10,10,50,50);
//        MyTriangle mt = new MyTriangle(50,70,Color.ORANGE);
       //mt.drawMe(g2d, new Point(100,100));

       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

    



 class MyTriangle

{

    protected int height;

    protected int width;

    protected Color color;

    public MyTriangle(int width, int height, Color color)

    {

        this.height = height;

        this.width= width;

        this.color= color;
    }

    public void drawMe(Graphics g, Point location)

    {

      g.setColor(color);

        Point point2 = new Point(location.x+width,location.y);

        Point point3 = new Point(location.x+(width/2),location.y - height);

        g.drawLine(location.x,location.y,point2.x,point2.y);

        g.drawLine(location.x,location.y,point3.x,point3.y);

        g.drawLine(point2.x,point2.y,point3.x,point3.y);

                     

    }
    
    public void setWidth(int w) {
        this.width = w;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setHeight(double h) 
    {
        this.height = (int) h;
    }
    
    public int getHeight()
    {
        return height;
    }

}

class shedSideRoof {

    private int x, y, length, height;
    private final Color color;

    public shedSideRoof(int x, int y, int l, int h, Color color) {
        this.x = x;
        this.y = y;
        this.length = l;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D gd) {
//        System.out.println(x+" "+y);
//        Color prevState = gd.getColor();
//
//        gd.setColor(color);
//        
//        Rectangle2D.Double rect = new Rectangle.Double(x, y, length, height);
//       // gd.drawRect(x, y, length, height);
//           
//        gd.setColor(prevState);               
//        //gd.setColor(Color.yellow);
//        
//        double theta = Math.toRadians(10);
//        AffineTransform transform = new AffineTransform();
//        transform.rotate(theta);
//        transform.translate(x, y); 
//        Shape rotatedRect = transform.createTransformedShape(rect);
//        System.out.println("Before rotated Rect, X:"+x+" Y: "+y);
//        gd.draw(rotatedRect);
//        gd.fillRect((int) x, y, 7, height);
//        System.out.println("Leg .. X:"+x+" Y:"+y);

//        Angle of roof
        int slope = 10;
        
        gd.drawLine(x, y, x+slope, y - height);
        gd.drawLine(x, y, x+length, y);
        gd.drawLine(x+slope, y-height, x+length+slope, y-height);
        gd.drawLine(x+length, y, x+length+slope, y-height);
        
        int r=x;
        while (r+20<x+length)
        {
            r=r+15;
            gd.drawLine(r, y, r+slope, y-height);
        }
        
        gd.fillRect((int) x, y, 7, height);
        gd.fillRect((int) x+(length/3), y, 5, (int) (height*0.75));
        gd.fillRect((int) x+(length*2/3), y, 7, height);
        //gd.fillRect((int) x+length, y, 7, height);
        gd.drawLine(x+length-slope, y, x+length-slope, y+height);
        gd.drawLine(x+length+slope, y+height, x+length-slope, y+height);
        gd.drawLine(x+length+slope, y+height, x+length+slope, y-height);

        
        
//        Generate side shed
       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLength(int l) {
        this.length = l;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class flatPort {

    private int x, y, width, height;
    private final Color color;

    public flatPort(int x, int y, int w, int h, Color color) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D g2d) {
        Color prevState = g2d.getColor();

        g2d.setColor(color);

        
           
        g2d.setColor(prevState);
        
      
        g2d.drawLine(x, y, x+width+(width/2), y);
        g2d.setColor(Color.yellow);
        
        g2d.fillRect((int) (width*0.25)+x, y, 7, height);
        g2d.fillRect((int) (width*1.25)+x, y, 7, height);
        g2d.fillRect ((int) (width*0.5)+x, y, 5, (int) (height*0.75));
        //g2d.fillRect ((int) (width)+x, y, 5, (int) (height*0.75));
        g2d.fillRect ((int) (width/2)+x+width, y, 5, (int) (height*0.75));
        //g2d.drawOval(10,10,50,50);
//        MyTriangle mt = new MyTriangle(50,70,Color.ORANGE);
       //mt.drawMe(g2d, new Point(100,100));

       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class sideFlatPort {

    private int x, y, length, height;
    private final Color color;

    public sideFlatPort(int x, int y, int l, int h, Color color) {
        this.x = x;
        this.y = y;
        this.length = l;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D g2d) {
        Color prevState = g2d.getColor();

        g2d.setColor(color);

        
           
        g2d.setColor(prevState);
        
      
        g2d.drawLine(x, y, x+length, y);
        g2d.setColor(Color.yellow);
        
        g2d.fillRect((int) (length*0.25)+x, y, 7, height);
        g2d.fillRect((int) x+length, y, 7, height);
        g2d.fillRect ((int) (length*0.75)+x, y, 5, (int) (height*0.75));
        //g2d.fillRect ((int) (width)+x, y, 5, (int) (height*0.75));
        g2d.fillRect ((int) x, y, 5, (int) (height*0.75));
        //g2d.drawOval(10,10,50,50);
//        MyTriangle mt = new MyTriangle(50,70,Color.ORANGE);
       //mt.drawMe(g2d, new Point(100,100));

       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLength(int l) {
        this.length = l;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class flatPortRoof {

    private int x, y, width, height;
    private final Color color;

    public flatPortRoof(int x, int y, int w, int h, Color color) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D g2d) {
        Color prevState = g2d.getColor();

        g2d.setColor(color);

        
           
        g2d.setColor(prevState);
        
      
        g2d.drawLine(x, y, x+width+(width/2), y);
        g2d.setColor(Color.yellow);
        
        g2d.fillRect((int) (width*0.25)+x, y, 7, height);
        g2d.fillRect((int) (width*1.25)+x, y, 7, height);
        g2d.fillRect ((int) (width*0.5)+x, y, 5, (int) (height*0.75));
        //g2d.fillRect ((int) (width)+x, y, 5, (int) (height*0.75));
        g2d.fillRect ((int) (width/2)+x+width, y, 5, (int) (height*0.75));
        //g2d.drawOval(10,10,50,50);
//        MyTriangle mt = new MyTriangle(50,70,Color.ORANGE);
       //mt.drawMe(g2d, new Point(100,100));

//       DRAW THE ROOF
 

        g2d.drawLine(x,y, (int)(x+((width*1.5+5)/2)), y-(height/2));
        g2d.drawLine((int) (x+((width*1.5+5)/2)), y-(height/2), (width/2)+x+width+5,y);
            

       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class flatPortRoofSide {

    private int x, y, length, height;
    private final Color color;

    public flatPortRoofSide(int x, int y, int l, int h, Color color) {
        this.x = x;
        this.y = y;
        this.length = l;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D gd) {
//        System.out.println(x+" "+y);
//        Color prevState = gd.getColor();
//
//        gd.setColor(color);
//        
//        Rectangle2D.Double rect = new Rectangle.Double(x, y, length, height);
//       // gd.drawRect(x, y, length, height);
//           
//        gd.setColor(prevState);               
//        //gd.setColor(Color.yellow);
//        
//        double theta = Math.toRadians(10);
//        AffineTransform transform = new AffineTransform();
//        transform.rotate(theta);
//        transform.translate(x, y); 
//        Shape rotatedRect = transform.createTransformedShape(rect);
//        System.out.println("Before rotated Rect, X:"+x+" Y: "+y);
//        gd.draw(rotatedRect);
//        gd.fillRect((int) x, y, 7, height);
//        System.out.println("Leg .. X:"+x+" Y:"+y);

//        Angle of roof
        int slope = 10;
        
        gd.drawLine(x, y, x+slope, y - height);
        gd.drawLine(x, y, x+length, y);
        gd.drawLine(x+slope, y-height, x+length+slope, y-height);
        gd.drawLine(x+length, y, x+length+slope, y-height);
        
        int r=x;
        while (r+20<x+length)
        {
            r=r+15;
            gd.drawLine(r, y, r+slope, y-height);
        }
        
        gd.fillRect((int) x, y, 7, height);
        gd.fillRect((int) x+(length/3), y, 5, (int) (height*0.75));
        gd.fillRect((int) x+(length*2/3), y, 7, height);
        gd.fillRect((int) x+length-5, y, 5, (int) (height*0.75));
        //gd.fillRect((int) x+length, y, 7, height);
        //gd.drawLine(x+length-slope, y, x+length-slope, y+height);
       // gd.drawLine(x+length+slope, y+height, x+length-slope, y+height);
        gd.drawLine(x+length+(slope), y, x+length+slope, y-height);
        gd.drawLine(x+length, y, x+length+(slope), y);
        
        
//        Generate side shed
       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLength(int l) {
        this.length = l;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class shedFlat {
private int x, y, width, height;
    private final Color color;

    public shedFlat(int x, int y, int w, int h, Color color) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D g2d) {
        Color prevState = g2d.getColor();

        g2d.setColor(color);

        g2d.drawRect(x, y, width, height);
           
        g2d.setColor(prevState);
        
        
//        Draw the lines on the back shed depending on height of shed
        int lenny = 0;
        
        while ((lenny+12) < height) {
        lenny = lenny+10;
        g2d.drawLine(x,lenny+y,x+width,lenny+y);
        }
        g2d.setColor(RED);
        g2d.drawLine(x+width,y,x+width+(width/2),y);
      
        
        g2d.fillRect((int) (width*0.25)+x, y, 7, height);
        g2d.fillRect((int) (width*1.25)+x, y, 7, height);
        //g2d.drawOval(10,10,50,50);
//        MyTriangle mt = new MyTriangle(50,70,Color.ORANGE);
       //mt.drawMe(g2d, new Point(100,100));

       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class sideShedFlat {
private int x, y, length, height;
    private final Color color;

    public sideShedFlat(int x, int y, int l, int h, Color color) {
        this.x = x;
        this.y = y;
        this.length = l;
        this.height = h;
        this.color = color;
    }

    void draw(Graphics2D g2d) {
        Color prevState = g2d.getColor();

        g2d.setColor(color);

        g2d.drawRect(x+length, y, length, height);
           
        g2d.setColor(prevState);
        
        
//        Draw the lines on the back shed depending on height of shed
        int lenny = 0;
        
        while ((lenny+12) < height) {
        lenny = lenny+10;
        g2d.drawLine(x+length,lenny+y,(x+length*2),lenny+y);
        }
        g2d.setColor(RED);
        g2d.drawLine(x+length,y,x+length+(length/2),y);
        
//        DRAW ROOF
        g2d.drawLine(x, y, x+length, y);
        
        g2d.fillRect((int) x, y, 7, height);
        g2d.fillRect((int) (x*1.10), y, 5, (int) (height*0.75));
        //g2d.drawOval(10,10,50,50);
//        MyTriangle mt = new MyTriangle(50,70,Color.ORANGE);
       //mt.drawMe(g2d, new Point(100,100));

       
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLength(int l) {
        this.length = l;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}    
