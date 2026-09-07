package guiapplicationpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;



public class SignupFrame extends JDialog 
{
    private JTextField txtUid;
    private JPasswordField txtPwd, txtCnfPwd;
    private JComboBox cmbRole;
    private JButton btnSubmit, btnReset, btnReturn;
    private String[] role = {"Select Role", "Admin"};      //Only Admin has priviledge over signup
    
    private JLabel makeLabel(String cap,int x,int y,int w,int h)    //Make label function
    {
        JLabel temp = new JLabel(cap);
        temp.setFont(new Font("Courier New",1,16));
        temp.setBounds(x,y,w,h);
        super.add(temp);
        return temp;
    }
    private JComponent makeTextBox(int x,int y,int w,int h,int mode)    //Make Text field function
    {
        JComponent temp = null;
        if(mode == 1)
            temp = new JTextField();
        else if(mode == 2)
            temp = new JPasswordField();
        temp.setFont(new Font("Courier New", 1, 18));
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        temp.setBounds(x,y,w,h);
        add(temp);
        return temp;
    }
    private JComboBox makeComboBox(int x,int y,int w,int h,String[] items)  //Make Combobox fucntion
    {
        JComboBox temp = new JComboBox(items);
        temp.setFont(new Font("Verdana", 1, 14));
        temp.setBounds(x,y,w,h);
        ((JLabel)temp.getRenderer()).setHorizontalAlignment(JLabel.CENTER);  //render for taking a property from JLabel, a little like typecast
        add(temp);
        return temp;
    }
    private JButton makeButton(String caption,int x,int y,int w,int h)  //Make Button function
    {
        JButton temp = new JButton(caption);
        temp.setBounds(x,y,w,h);
        temp.setFont(new Font("Verdana", 1, 12));
        temp.setMargin(new Insets(0,0,0,0));
        temp.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Object ob = e.getSource();//Creation of 'NEW USER REGISTRATION' window

                    if(ob==btnSubmit)
                    {
                        if(cmbRole.getSelectedIndex()==0)
                        {
                            JOptionPane.showMessageDialog(null, "ROLE NOT SELECTED");
                            cmbRole.grabFocus();
                        }
                        else if(txtUid.getText().equals("")||txtPwd.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "CREDENTIALS NOT PROVIDED PROPERLY");
                            txtUid.grabFocus();
                        }
                        else if(!txtPwd.getText().equals(txtCnfPwd.getText()))
                        {
                            JOptionPane.showMessageDialog(null, "PASSWORD CONFIORMED IS NOT RESEMBLING TO THE PASSWORD ENTERED");
                            txtCnfPwd.setText("");
                            txtCnfPwd.grabFocus();
                        }
                        else
                        {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321"); //Here 'unisys?autoReconnect' - unisys is the db name and '4321' is the pwd for logging in MYSql
                            String sql = "SELECT USERID FROM USER WHERE USERID = ?";
                            PreparedStatement pst1 = con.prepareStatement(sql);
                            pst1.setString(1, txtUid.getText());
                            ResultSet rst = pst1.executeQuery();
                            if(rst.next())
                            {
                                JOptionPane.showMessageDialog(null, "USER ID ALREADY PRESENT");
                                reset();
                            }
                            else
                            {
                                sql = "INSERT INTO USER VALUES(?,?,?)";
                                PreparedStatement pst2 = con.prepareStatement(sql);
                                pst2.setString(1, txtUid.getText());
                                pst2.setString(2, txtPwd.getText());
                                pst2.setString(3, (String)cmbRole.getSelectedItem());
                                pst2.executeUpdate();
                                JOptionPane.showMessageDialog(null, "USER REGISTERED SUCCESSFULLY");
                                con.close();
                                reset();
                            }
                        }
                    }
                    else if(ob==btnReset)
                    {
                        reset();
                    }
                    else if(ob==btnReturn) //This button returns to the previous window after closing the current window
                    {
                        dispose();
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        super.add(temp);
        return temp;
    }
    private void reset()
    {
        txtUid.setText("");
        txtPwd.setText("");
        txtCnfPwd.setText("");
        cmbRole.setSelectedIndex(0);
        txtUid.grabFocus();
    }

    public SignupFrame()
    {
        Border brdr1 = BorderFactory.createLineBorder(Color.RED, 2); //Constructor for New User Registration Window
        Border brdr2 = BorderFactory.createLineBorder(Color.BLUE, 2); 
        Border brdr3 = BorderFactory.createCompoundBorder(brdr1, brdr2); 
        JLabel caption = new JLabel("NEW USER REGISTRATION");
        caption.setFont(new Font("verdana",1,24));
        caption.setHorizontalAlignment(JLabel.CENTER);
        caption.setOpaque(true);
        caption.setBackground(Color.YELLOW);
        caption.setForeground(Color.red);
        caption.setBorder(brdr3);
        caption.setBounds(10,10,470,50);
        super.add(caption);
        
        makeLabel("ENTER USER ID", 10, 70, 250, 30);
        txtUid = (JTextField) makeTextBox(250, 70, 230, 30, 1); //For mode1(text field) and mode2 we have them pointer towards Jcomponent class
        txtUid.setHorizontalAlignment(JTextField.CENTER);

        makeLabel("ENTER PASSWORD", 10, 110, 250, 30);
        txtPwd = (JPasswordField) makeTextBox(250, 110, 230, 30, 2);    //Password Field at mode2, similarly it goes to private JComponent makeTextBox in LoginMain.java
        txtPwd.setHorizontalAlignment(JLabel.CENTER);
        txtPwd.setEchoChar('*');

        makeLabel("CONFIRM PASSWORD", 10, 150, 250, 30);
        txtCnfPwd = (JPasswordField) makeTextBox(250, 150, 230, 30, 2);
        txtCnfPwd.setHorizontalAlignment(JLabel.CENTER);
        txtCnfPwd.setEchoChar('*');

        makeLabel("SELECT ROLE/PRIVILEGE", 10, 190, 250, 30);
        cmbRole = makeComboBox(250, 190, 230, 30, role);

        btnSubmit = makeButton("Submit", 50, 230, 100, 30);
        btnReset  = makeButton("Reset", 200, 230, 100, 30);
        btnReturn = makeButton("Return", 350, 230, 100, 30);      // This refers to else if(ob==btnReturn)
        
    }
}
