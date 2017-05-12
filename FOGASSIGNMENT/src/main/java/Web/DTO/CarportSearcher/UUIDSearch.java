/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Web.DTO.CarportSearcher;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import DAL.Repositories.GenerateCarportDAO;
import Domain.Carport;

/**
 *
 * @author Sean
 */
public class UUIDSearch extends JFrame{
    Container container = new Container();
    GenerateCarportDAO gcdao;
    JFrame myFrame = new JFrame();
    
    public UUIDSearch() 
    {
        
        //create Generate Carport Object
        try {
            gcdao = new GenerateCarportDAO();
        } catch (Exception ex) {
            Logger.getLogger(UUIDSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Settings for java JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 100);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        
        container = getContentPane();
        
        //Create label
        JLabel labelText = new JLabel();
        labelText.setText ("Insert Your Custom Carport's Unique Key Here:");    
        
        //Create The UUID Text Field
        final JTextField uuidField = new JTextField(50);
        
        //Create Button For Searching UUID
        JButton uuidSearchBTN = new JButton("Search");
        
        //Add components to container and choose location(Note: Default is Layout is Border Layout)    
        //myFrame.add(container);
        container.add( labelText );
        container.add(uuidField, BorderLayout.SOUTH);
        container.add(uuidSearchBTN, BorderLayout.LINE_END);
        
        
        //Add action listener to 'Search' Button
          uuidSearchBTN.addActionListener(new ActionListener()
                       {
                            public void actionPerformed(ActionEvent ae)
                            {
                                final String UUID = uuidField.getText();
                                try {
                                    //Validate that Carport with UUID Exists in DB
                                    // IF UUID exists in carport
                                    if (gcdao.ValidateUUID(UUID))
                                    {
                                        System.out.println("Valid UUID"); 
                                        // popup gives information on carport
                                        
                                        //Creates object from UUID DB match
                                        Carport theCP = gcdao.CarportFromUUID(UUID);
                                        
                                        // Generates JDialog
                                        JDialog d = new JDialog(myFrame, "Customized Carport", true);
                                        d.setSize(new Dimension(400,400));
                                        JPanel pan=new JPanel();

                                        //Set Text Area within the JDialog
                                        JTextArea cusPortInfo = new JTextArea(
                                          "Your Carport: "
                                        + "\n\n "
                                        + " Height: "+ theCP.getHeight()+"cm"
                                        + " Width: "+ theCP.getWidth()+"cm"
                                        + " Length: "+ theCP.getLength()+"cm"
                                        + "\n\n"
                                        + " Unique Key : "
                                        + "-------------------"
                                        + theCP.getUUID() +"\n"
                                        + "------------------");
                                        
                                        //Formatting for TextArea
                                        cusPortInfo.setFont(new Font("Serif", Font.ITALIC, 16));
                                        cusPortInfo.setLineWrap(true);
                                        cusPortInfo.setWrapStyleWord(true);
                                        cusPortInfo.setOpaque(false);
                                        cusPortInfo.setEditable(false);
                                        
                                        //Adding JPanel to Dialog && JTextArea to JPanel
                                        pan.add(cusPortInfo);
                                        d.add(pan);
                                        //Make Visible
                                        d.setVisible(true);
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                    }       
                                    //Doesn't exists valid UUID
                                    else {                                   
                                        System.out.println("Invalid UUID, Please Try Again");
                                        //IF doesn't exist popup asking to try again
                                        JOptionPane.showMessageDialog(container,
                                        "The input key didn't match anything in our database, please try again!",
                                        "Invalid Key",
                                        JOptionPane.ERROR_MESSAGE);
                                     }
                                    
                                } catch (SQLException ex) {
                                    Logger.getLogger(UUIDSearch.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
        
       
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UUIDSearch();
            }
        });
    }
}

