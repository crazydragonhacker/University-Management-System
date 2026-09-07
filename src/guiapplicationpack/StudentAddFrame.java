/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiapplicationpack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import sun.util.calendar.BaseCalendar;


public class StudentAddFrame extends JDialog
{
    private JTextField txtID,txtName,txtFather,txtAddress,txtDOB,txtPhone,txtEmail;
    private JComboBox cmbGender,cmbCourse,cmbSems;
    private JButton btnAddNew,btnUpdate,btnCancel,btnReturn;
    private String[] gender ={"Male","Female","Trans","Other"};
    private String[] course ={"BTech","BCA","BSc","BBA","MTech","MCA","MSc","MBA"};
    private String[] sems = {"3","4","5","6","7","8"};
    private int sIDSequence =0;
    private Connection con = null;
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
        JTextField temp = new JTextField();;
        temp.setFont(new Font("Courier New", 1, 18));
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        temp.setHorizontalAlignment(JLabel.CENTER);
        temp.setBounds(x,y,w,h);
        add(temp);
        return temp;
    }
    private JComboBox makeComboBox(int x,int y,int w,int h,String[] items)
    {
        JComboBox temp = new JComboBox(items);
        temp.setFont(new Font("Courier New", 1, 18));
        temp.setBounds(x,y,w,h);
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        ((JLabel)temp.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
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
                if(ob == btnAddNew)
                {
                    setReset();
                    Date dt = new Date();
                    int y = dt.getYear()+1900;
                    int m = dt.getMonth()+1;
                    sIDSequence++;
                    String sid = String.format("s-%04d-%02d-%03d",y,m,sIDSequence);
                    txtID.setText(sid);
                    txtName.grabFocus();
                }
                else if(ob == btnUpdate) 
                {
                    try
                    {
                        pst1.setString(1, txtID.getText());
                        pst1.setString(2, txtName.getText());
                        pst1.setString(3, txtFather.getText());
                        pst1.setString(4,(String) cmbGender.getSelectedItem());
                        pst1.setString(5, txtAddress.getText());
                        pst1.setString(6, txtDOB.getText());
                        pst1.setString(7, txtPhone.getText());
                        pst1.setString(8, txtEmail.getText());
                        pst1.setString(9,(String) cmbCourse.getSelectedItem());
                        pst1.setString(10,(String) cmbSems.getSelectedItem());
                        pst1.executeUpdate();
                        
                        pst2.setString(1,txtID.getText());
                        pst2.setString(2,txtID.getText());
                        pst2.setString(3,"Student");
                        pst2.executeUpdate();
                        
                        setReset();
                        btnAddNew.grabFocus();
                        JOptionPane.showMessageDialog(null, "New Student Registered Succesfully");
                        
                        
                    }
                    catch(Exception ex)
                    {
                       JOptionPane.showMessageDialog(null, ex);
                    }
                    
                    
                }
                else if(ob== btnCancel)
                {
                    setReset();
                    btnAddNew.grabFocus();
                    sIDSequence--;
                }
                
            }
        });
        super.add(temp);
        return temp;
    }
    private void setReset()
    {
        txtID.setText("");
        txtName.setEnabled(!txtName.isEnabled());
        txtFather.setEnabled(!txtFather.isEnabled());
        txtFather.setText("");
        cmbGender.setEnabled(!cmbGender.isEnabled());
        cmbGender.setSelectedIndex(0);
        txtAddress.setEnabled(!txtAddress.isEnabled());
        txtAddress.setText("");
        
        txtDOB.setEnabled(!txtDOB.isEnabled());
        txtDOB.setText("");
        
        txtPhone.setEnabled(!txtPhone.isEnabled());
        txtPhone.setText("");
        
        txtEmail.setEnabled(!txtEmail.isEnabled());
        txtEmail.setText("");
        
        cmbCourse.setEnabled(!cmbCourse.isEnabled());
        cmbCourse.setSelectedIndex(0);
        
        cmbSems.setEnabled(!cmbSems.isEnabled());
        cmbSems.setSelectedIndex(5);
        btnAddNew.setEnabled(!btnAddNew.isEnabled());
        btnUpdate.setEnabled(!btnUpdate.isEnabled());
        btnCancel.setEnabled(!btnCancel.isEnabled());
        btnReturn.setEnabled(!btnReturn.isEnabled());
        
        
    }
    
   public StudentAddFrame()
   {
       try
       {
           Border brdr1 = BorderFactory.createLineBorder(Color.RED,2);
           Border brdr2 = BorderFactory.createLineBorder(Color.BLUE,2);
           Border brdr3 = BorderFactory.createCompoundBorder(brdr1,brdr2);
           JLabel caption = new JLabel("NEW STUDENT REGISTRATION");
           caption.setFont(new Font("Verdana",1,22));
           caption.setHorizontalAlignment(JLabel.CENTER);
           caption.setOpaque(true);
           caption.setBackground(Color.YELLOW);
           caption.setForeground(Color.red);
           caption.setBounds(10,10,500,50);
           super.add(caption);
           
           makeLabel("STUDENT ID GENERATED",10,70,250,30);
           txtID = makeTextField(260,70,250,30);
           txtID.setEditable(false);
           
           makeLabel("ENTER STUDENT NAME",10,110,250,30);
           txtName = makeTextField(260,110,250,30);
           
           makeLabel("ENTER FATHER NAME",10,150,250,30);
           txtFather= makeTextField(260,150,250,30);
           
           makeLabel("SELECT GENDER STATUS",10,190,250,30);
           cmbGender = makeComboBox(260,190,250,30,gender);
           
           makeLabel("ENTER LOCAL ADDRESS",10,230,250,30);
           txtAddress= makeTextField(260,230,250,30);
           
            makeLabel("ENTER DATE OF BIRTH ",10,270,250,30);
           txtDOB= makeTextField(260,270,250,30);
           
            makeLabel("ENTER PHONE NUMBER",10,310,250,30);
           txtPhone= makeTextField(260,310,250,30);
           
            makeLabel("ENTER EMAIL ADDRESS",10,350,250,30);
           txtEmail= makeTextField(260,350,250,30);
           
           makeLabel("SELECT COURSE ENROLLED",10,390,250,30);
           cmbCourse = makeComboBox(260,390,250,30,course);
           
           makeLabel("NUMBER OF SEMETERS",10,430,250,30);
           cmbSems = makeComboBox(260,430,250,30,sems);
           
           btnAddNew = makeButton("Add New", 26,470,100,30);
           btnAddNew.setEnabled(false);
           
           btnUpdate = makeButton("Update", 152,470,100,30);
           btnCancel = makeButton("Cancel", 278,470,100,30);
           btnReturn = makeButton("Return", 404,470,100,30);
           btnReturn.setEnabled(false);
           setReset();
           
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321");
           pst1= con.prepareStatement("INSERT INTO STUDENT_MASTER VALUES (?,?,?,?,?,?,?,?,?,? )");
           pst2= con.prepareStatement("INSERT INTO USER VALUES (?,?,?)");
           
           Statement sst = con.createStatement();
           ResultSet rst = sst.executeQuery("SELECT STUDENT_ID FROM STUDENT_MASTER ORDER BY STUDENT_ID");
           if(rst.next())
           {
               String sid = rst.getString(1);
               sIDSequence= Integer.parseInt(sid.substring(sid.lastIndexOf("-")+1));// it picks -00003 so we add +1 then index becomes 00003 so parseint 0003 = 3 
           }
           else 
           {
               sIDSequence = 0;
           }
           sst.close();
                   
       }  
           
        catch (Exception ex)
       {
           JOptionPane.showMessageDialog(null, ex);
       }
   }
}
