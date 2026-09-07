package guiapplicationpack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class ProfessorEditFrame extends JDialog
{
    private JTextField txtID,txtName,txtAddress,txtPhone,txtEmail,txtDOB,txtDOJ,txtDegree;
    private JComboBox cmbGender;
    private JButton btnSearch,btnUpdate,btnCancel,btnReturn;
    private String[] gender = {"Male","Female","Other"};
    private Connection con = null;
    private PreparedStatement pst1 = null;
    private PreparedStatement pst2 = null;
    private PreparedStatement pst3 = null;
    private PreparedStatement pst4 = null;
    private PreparedStatement pst5 = null;
    
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
                if(ob == btnSearch)
                {
                    try
                    {
                        if(txtID.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "PROFESSOR ID NOT ENTERED");
                            txtID.grabFocus();
                        }
                        else
                        {
                            pst1.setString(1,txtID.getText());
                            pst2.setString(1,txtID.getText());
                            ResultSet rst1 = pst1.executeQuery();
                            ResultSet rst2 = pst2.executeQuery();
                            if(rst1.next())
                            {
                                txtName.setText(rst1.getString(1));
                                txtAddress.setText(rst1.getString(2));
                                cmbGender.setSelectedItem(rst1.getString(3));
                                txtPhone.setText(rst1.getString(4));
                                txtEmail.setText(rst1.getString(5));
                                txtDOB.setText(rst1.getString(6));
                                txtDOJ.setText(rst1.getString(7));
                                String degree = "";
                                if(rst2.next()) degree = rst2.getString(1);
                                while(rst2.next())degree += ","+rst2.getString(1);
                                txtDegree.setText(degree);
                                setReset();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "PROFESSOR NOT FOUND");
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
                else if(ob == btnUpdate)
                {
                    try
                    {
                        pst3.setString(1, txtName.getText());
                        pst3.setString(2, txtAddress.getText());
                        pst3.setString(3, (String)cmbGender.getSelectedItem());
                        pst3.setString(4, txtPhone.getText());
                        pst3.setString(5, txtEmail.getText());
                        pst3.setString(6, txtDOB.getText());
                        pst3.setString(7, txtDOJ.getText());
                        pst3.setString(8, txtID.getText());
                        pst3.executeUpdate();
                        
                        pst4.setString(1,txtID.getText());
                        pst4.executeUpdate();
                        
                        String[] degree = txtDegree.getText().split(",");
                        for(String temp:degree)
                        {
                            pst5.setString(1, txtID.getText());
                            pst5.setString(2, temp);
                            pst5.executeUpdate();
                        }
                        setReset();
                        clearFields();
                        btnSearch.grabFocus();
                        JOptionPane.showMessageDialog(null, "Professor Updated Successfully");
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
                else if(ob == btnCancel)
                {
                    setReset();
                    clearFields();
                    btnSearch.grabFocus();
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
    private void setReset()
    {
        try
        {
            txtID.setEnabled(!txtID.isEnabled());
            txtName.setEnabled(!txtName.isEnabled());
            txtAddress.setEnabled(!txtAddress.isEnabled());
            cmbGender.setEnabled(!cmbGender.isEnabled());
            txtPhone.setEnabled(!txtPhone.isEnabled());
            txtEmail.setEnabled(!txtEmail.isEnabled());
            txtDOB.setEnabled(!txtDOB.isEnabled());
            txtDOJ.setEnabled(!txtDOJ.isEnabled());
            txtDegree.setEnabled(!txtDegree.isEnabled());
            
            btnSearch.setEnabled(!btnSearch.isEnabled());
            btnUpdate.setEnabled(!btnUpdate.isEnabled());
            btnCancel.setEnabled(!btnCancel.isEnabled());
            btnReturn.setEnabled(!btnReturn.isEnabled());
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    private void clearFields()
    {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtDOB.setText("");
        txtDOJ.setText("");
        txtDegree.setText("");
        cmbGender.setSelectedIndex(0);
    }
    public ProfessorEditFrame()
    {
        try
        {
            Border brdr1 = BorderFactory.createLineBorder(Color.RED, 2);
            Border brdr2 = BorderFactory.createLineBorder(Color.BLUE, 2);
            Border brdr3 = BorderFactory.createCompoundBorder(brdr1, brdr2);
            JLabel caption = new JLabel("NEW PROFESSOR REGISTRATION");
            caption.setFont(new Font("verdana",1,22));
            caption.setHorizontalAlignment(JLabel.CENTER);
            caption.setOpaque(true);
            caption.setBackground(Color.YELLOW);
            caption.setForeground(Color.red);
            caption.setBorder(brdr3);
            caption.setBounds(10,10,500,50);
            super.add(caption);
            
            makeLabel("PROFESSOR ID GENERATED",10,70,250,30);
            txtID = makeTextField(260,70,250,30);
            txtID.setEnabled(false);
            makeLabel("ENTER PROFESSOR NAME",10,110,250,30);
            txtName = makeTextField(260,110,250,30);
            makeLabel("ENTER LOCAL ADDRESS",10,150,250,30);
            txtAddress = makeTextField(260,150,250,30);
            makeLabel("SELECT GENDER STATUS",10,190,250,30);
            cmbGender = makeComboBox(260,190,250,30,gender);
            makeLabel("ENTER PHONE NUMBER",10,230,250,30);
            txtPhone = makeTextField(260,230,250,30);
            makeLabel("ENTER EMAIL ADDRESS",10,270,250,30);
            txtEmail = makeTextField(260,270,250,30);
            makeLabel("ENTER DATE OF BIRTH",10,310,250,30);
            txtDOB = makeTextField(260,310,250,30);
            makeLabel("ENTER DATE OF JOIN",10,350,250,30);
            txtDOJ = makeTextField(260,350,250,30);
            makeLabel("DEGREE COMMA SEPARATED",10,390,250,30);
            txtDegree = makeTextField(260,390,250,30);
            
            btnSearch = makeButton("Search",26,430,100,30);
            btnSearch.setEnabled(false);
            btnUpdate = makeButton("Update",152,430,100,30);
            btnCancel = makeButton("Cancel",278,430,100,30);
            btnReturn = makeButton("Return",404,430,100,30);
            btnReturn.setEnabled(false);
            setReset();
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321");
            pst1 = con.prepareStatement("SELECT NAME,ADDRESS,GENDER,PHONE,EMAIL,DOB,DOJ FROM PROFESSOR_MASTER WHERE PROFESSOR_ID = ?");
            pst2 = con.prepareStatement("SELECT DEGREE FROM PROFESSOR_DEGREE WHERE PROFESSOR_ID = ?");
            pst3 = con.prepareStatement("UPDATE PROFESSOR_MASTER SET NAME=?,ADDRESS=?,GENDER=?,PHONE=?,EMAIL=?,DOB=?,DOJ=? WHERE PROFESSOR_ID=?");
            pst4 = con.prepareStatement("DELETE FROM PROFESSOR_DEGREE WHERE PROFESSOR_ID=?");
            pst5 = con.prepareStatement("INSERT INTO PROFESSOR_DEGREE VALUES(?,?)");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}

