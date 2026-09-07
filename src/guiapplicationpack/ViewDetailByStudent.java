package guiapplicationpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class ViewDetailByStudent extends JDialog
{
    private JLabel lblID, lblName, lblFather, lblGender, lblAddress, lblDOB, lblPhone, lblEmail, lblSems, lblCourse;
    private JButton btnPrint, btnReturn;
    private Connection con = null;
    private PreparedStatement pst = null;
    private ViewDetailByStudent me = this;
    
    private JLabel makeLabel(String cap,int x,int y,int w,int h,int mode)
    {
        JLabel temp = new JLabel(cap);
        temp.setFont(new Font("Courier New",1,18));
        temp.setBounds(x,y,w,h);
        if(mode == 2)  //For the right texts
        {
            temp.setOpaque(true);
            temp.setBackground(Color.WHITE);
            Border brdr = BorderFactory.createLineBorder(Color.BLACK, 1);
            temp.setBorder(brdr);
            temp.setHorizontalAlignment(JLabel.CENTER);
        }
        super.add(temp);
        return temp;
    }
    
    private void search(String id)
    {
        try 
        {
            pst.setString(1, id);
            ResultSet rst = pst.executeQuery();
            if(rst.next())
            {
                lblName.setText(rst.getString(1));
                lblFather.setText(rst.getString(2));
                lblGender.setText(rst.getString(3));
                lblAddress.setText(rst.getString(4));
                lblDOB.setText(rst.getString(5));
                lblPhone.setText(rst.getString(6));
                lblEmail.setText(rst.getString(7));
                lblCourse.setText(rst.getString(8));
                lblSems.setText(rst.getString(9));
            }
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex);
        }
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
                
                try 
                {
                    //Making a printable area
                    if(ob==btnPrint)
                    {
                        PrinterJob job = PrinterJob.getPrinterJob();
                        job.setJobName("Print Data");
                        job.setPrintable(new Printable()
                        {
                            public int print(Graphics pg, PageFormat pf, int pageNum)
                            {
                                pf.setOrientation(PageFormat.LANDSCAPE);
                                if(pageNum>0) return Printable.NO_SUCH_PAGE;
                                Graphics2D g2 = (Graphics2D)pg;
                                g2.translate(pf.getImageableX(), pf.getImageableY());
                                g2.scale(1.0,1.0);
                                me.paint(g2);
                                return Printable.PAGE_EXISTS;
                            }
                        });
                        boolean ok = job.printDialog();
                        if(ok) job.print();
                    } 
                    else if(ob==btnReturn)
                    {
                        dispose();
                    }
                } 
                catch (Exception ex) 
                {
                    JOptionPane.showMessageDialog(null, ex);
                }
                        
            }
        });
        super.add(temp);
        return temp;
    }
    
    public ViewDetailByStudent()
    {
        try
        {
            Border brdr1 = BorderFactory.createLineBorder(Color.RED, 2);
            Border brdr2 = BorderFactory.createLineBorder(Color.BLUE, 2);
            Border brdr3 = BorderFactory.createCompoundBorder(brdr1, brdr2);
            JLabel caption = new JLabel("PERSONAL DETAILS OF STUDENT");
            caption.setFont(new Font("verdana",1,22));
            caption.setHorizontalAlignment(JLabel.CENTER);
            caption.setOpaque(true);
            caption.setBackground(Color.YELLOW);
            caption.setForeground(Color.red);
            caption.setBorder(brdr3);
            caption.setBounds(10,10,500,50);
            super.add(caption);
            
            
            makeLabel("STUDENT ID", 10, 70, 250, 30, 1);
            lblID = makeLabel("", 260, 70, 250, 30, 2);
            lblID.setText(System.getProperty("student_id"));
            
            makeLabel("STUDENT NAME", 10, 110, 250, 30, 1);
            lblName = makeLabel("", 260, 110, 250, 30, 2);
            
            makeLabel("FATHER's NAME", 10, 150, 250, 30, 1);
            lblFather = makeLabel("", 260, 150, 250, 30, 2);
            
            makeLabel("GENDER STATUS", 10, 190, 250, 30, 1);
            lblGender = makeLabel("", 260, 190, 250, 30, 2);
            
            makeLabel("LOCAL ADDRESS", 10, 230, 250, 30, 1);
            lblAddress = makeLabel("", 260, 230, 250, 30, 2);
            
            makeLabel("DATE OF BIRTH", 10, 270, 250, 30, 1);
            lblDOB = makeLabel("", 260, 270, 250, 30, 2);
            
            makeLabel("PHONE NUMBER", 10, 310, 250, 30, 1);
            lblPhone = makeLabel("", 260, 310, 250, 30, 2);
            
            makeLabel("EMAIL ADDRESS", 10, 350, 250, 30, 1);
            lblEmail = makeLabel("", 260, 350, 250, 30, 2);
            
            makeLabel("COURSE ENROLLED", 10, 390, 250, 30, 1);
            lblCourse = makeLabel("", 260, 390, 250, 30, 2);
            
            makeLabel("NUMBER OF SEMESTERS", 10, 430, 250, 30, 1);
            lblSems = makeLabel("", 260, 430, 250, 30, 2);
           
            btnPrint = makeButton("Print", 94, 470, 120, 30);
            btnReturn = makeButton("Return", 308, 470, 120, 30);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321");
            pst = con.prepareStatement("SELECT NAME, FATHER_NAME, GENDER, ADDRESS, DOB, PHONE, EMAIL, COURSE, SEMESTER FROM STUDENT_MASTER WHERE STUDENT_ID=?");
            
            search(System.getProperty("student_id"));
            
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    
    
}
