/*
 */
package guiapplicationpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;


public class StudentDeleteFrame extends JDialog
{
    private JTextField txtID, txtName, txtFather, txtAddress, txtDOB, txtPhone, txtEmail;
    private JComboBox cmbGender, cmbCourse, cmbSems;
    private JButton btnSearch, btnDelete, btnReturn;
    private String[] gender = {"Male", "Female", "Trans", "Others"};
    private String[] course = {"B.tech", "BCA", "BSC", "BBA", "MTECH", "MCA", "MSC", "MBA"};
    private String[] sems = {"3","4","5","6","7","8"};
    
    private Connection con = null;//Connection class Object creation
    private PreparedStatement pst1 = null;
    private PreparedStatement pst2 = null;
    
    
    private JLabel makeLabel(String cap,int x,int y,int w,int h)
    {
        JLabel temp = new JLabel(cap);
        temp.setFont(new Font("Courier New",1,18));
        temp.setBounds(x,y,w,h);
        super.add(temp);
        return temp;
    }
    private JTextField makeTextField(int x,int y,int w,int h)
    {
        JTextField temp = new JTextField();
        temp.setFont(new Font("Courier New", 1, 18));
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        temp.setBounds(x,y,w,h);
        temp.setEnabled(false);
        temp.setHorizontalAlignment(JTextField.CENTER);
        add(temp);
        return temp;
    }
    private JComboBox makeComboBox(int x,int y,int w,int h,String[] items)
    {
        JComboBox temp = new JComboBox(items);
        temp.setFont(new Font("Courier New", 1, 18));
        temp.setBounds(x,y,w,h);
        
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        temp.setEnabled(false);
        ((JLabel)temp.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);  //This make the GUI control the fields and make them translucent
        add(temp);
        return temp;
    }
    private JButton makeButton(String caption,int x,int y,int w,int h)
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
                Object ob = e.getSource();
                if(ob == btnSearch)
                {
                    try
                    {
                        if(txtID.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "STUDENT ID NOT ENTERED");
                            txtID.grabFocus();
                        }
                        else
                        {
                            pst1.setString(1, txtID.getText());
                            ResultSet rst = pst1.executeQuery();
                            if(rst.next())
                            {
                                txtName.setText(rst.getString(1));
                                txtFather.setText(rst.getString(2));
                                cmbGender.setSelectedItem(rst.getString(3));
                                txtAddress.setText(rst.getString(4));
                                txtDOB.setText(rst.getString(5));
                                txtPhone.setText(rst.getString(6));
                                txtEmail.setText(rst.getString(7));
                                cmbCourse.setSelectedItem(rst.getString(8));
                                cmbSems.setSelectedItem(rst.getString(9));
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "STUDENT NOT FOUND");
                                txtID.setText("");
                                txtID.grabFocus();
                            }
                        }
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
                else if(ob == btnDelete) //I want final confirmation from user in delete button
                {
                    try
                    {
                        int confirm = JOptionPane.showConfirmDialog(null, "ARE YOU SURE TO DELETE THE RECORD?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if(confirm == 0)
                        {
                            pst2.setString(1, txtID.getText());
                            pst2.executeUpdate();
                            clearFields();
                            JOptionPane.showMessageDialog(null, "Student Deleted Successfully");
                        }
                        txtID.grabFocus();
                        txtID.selectAll();
                        
                        
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
                
                else if(ob == btnReturn)
                {
                    dispose();
                }
            }
        });
        super.add(temp);
        return temp;
    }
    
    
    private void clearFields()
    {
        txtID.setText("");
        txtName.setText("");
        txtFather.setText("");
        cmbGender.setSelectedIndex(0);
        txtAddress.setText("");
        txtDOB.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        cmbCourse.setSelectedIndex(0);
        cmbSems.setSelectedIndex(5);
    }
    
    //Constructor
    public StudentDeleteFrame()
    {
        try
        {
            Border brdr1 = BorderFactory.createLineBorder(Color.RED, 2);
            Border brdr2 = BorderFactory.createLineBorder(Color.BLUE, 2);
            Border brdr3 = BorderFactory.createCompoundBorder(brdr1, brdr2);
            JLabel caption = new JLabel("DELETING AN EXISTING STUDENT");
            caption.setFont(new Font("verdana",1,22));
            caption.setHorizontalAlignment(JLabel.CENTER);
            caption.setOpaque(true);
            caption.setBackground(Color.YELLOW);
            caption.setForeground(Color.red);
            caption.setBorder(brdr3);
            caption.setBounds(10,10,500,50);
            super.add(caption);
            
            makeLabel("ENTER STUDENT ID",10,70,250,30);
            txtID = makeTextField(260,70,250,30);
            txtID.setEnabled(true);
            makeLabel("ENTER STUDENT NAME",10,110,250,30);
            txtName = makeTextField(260,110,250,30);
            makeLabel("ENTER FATHER'S NAME",10,150,250,30);
            txtFather = makeTextField(260,150,250,30);
            makeLabel("SELECT GENDER STATUS",10,190,250,30);
            cmbGender = makeComboBox(260,190,250,30,gender);
            makeLabel("ENTER LOCAL ADDRESS",10,230,250,30);
            txtAddress = makeTextField(260,230,250,30);
            makeLabel("ENTER DATE OF BIRTH",10,270,250,30);
            txtDOB = makeTextField(260,270,250,30);
            makeLabel("ENTER PHONE NUMBER",10,310,250,30);
            txtPhone = makeTextField(260,310,250,30);
            makeLabel("ENTER EMAIL ADDRESS",10,350,250,30);
            txtEmail = makeTextField(260,350,250,30);
            makeLabel("SELECT COURSE ENROLLED",10,390,250,30);
            cmbCourse = makeComboBox(260,390,250,30,course);
            makeLabel("NUMBER OF SEMESTERS",10,430,250,30);
            cmbSems = makeComboBox(260,430,250,30,sems);
            
            btnSearch = makeButton("Search",55,470,100,30);
            btnDelete = makeButton("Delete",210,470,100,30);
            btnReturn = makeButton("Return",365,470,100,30);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321");
            pst1 = con.prepareStatement("SELECT NAME,FATHER_NAME,GENDER,ADDRESS,DOB,PHONE,EMAIL,COURSE,SEMESTERS FROM STUDENT_MASTER WHERE STUDENT_ID=?");
            pst2 = con.prepareStatement("DELETE FROM STUDENT_MASTER WHERE STUDENT_ID=?");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    
}
