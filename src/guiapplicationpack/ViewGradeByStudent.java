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
import javax.swing.JDialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ViewGradeByStudent extends JDialog
{
    private JLabel lblID, lblName, lblCourse;
    private JTable tblStuDetail;
    private JScrollPane spnStuDetail;
    private DefaultTableModel tblModel;
    private DefaultTableColumnModel tblColModel;
    private TableColumn colSem,colGrade;
    private JButton btnPrint, btnReturn;
    private ViewGradeByStudent me = null;
    
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
    
    private TableColumn makeTableColumn(int index, int width, String caption)
    {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumn temp = new TableColumn(index, width);
        temp.setHeaderValue(caption);
        temp.setResizable(false);
        temp.setCellRenderer(cellRenderer);
        tblColModel.addColumn(temp);
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
    
    public ViewGradeByStudent()
    {
        try 
        {
            Border brdr1 = BorderFactory.createLineBorder(Color.RED, 2);
            Border brdr2 = BorderFactory.createLineBorder(Color.BLUE, 2);
            Border brdr3 = BorderFactory.createCompoundBorder(brdr1, brdr2);
            JLabel caption = new JLabel("SEMESTER WISE GRADE DETAILS");
            caption.setFont(new Font("verdana",1,22));
            caption.setHorizontalAlignment(JLabel.CENTER);
            caption.setOpaque(true);
            caption.setBackground(Color.YELLOW);
            caption.setForeground(Color.red);
            caption.setBorder(brdr3);
            caption.setBounds(10,10,500,50);
            super.add(caption);
            
            makeLabel("Student ID", 10, 70, 200, 30, 1);
            lblID = makeLabel(" ", 210, 70, 300, 30, 2);
            makeLabel("Student Name", 10, 110, 200, 30, 1);
            lblName = makeLabel(" ", 210, 110, 300, 30, 2);
            makeLabel("Course Name", 10, 150, 200, 30, 1);
            lblCourse = makeLabel(" ", 210, 150, 300, 30, 2);
            
            tblColModel = new DefaultTableColumnModel();
            colSem = makeTableColumn(0, 140, "SEMESTER");
            colGrade = makeTableColumn(1, 140, "GRADE");
            
            tblModel = new DefaultTableModel();
            tblModel.setColumnCount(2);
            
            tblStuDetail = new JTable(tblModel, tblColModel);
            tblStuDetail.setFont(new Font("verdana",1,12));
            tblStuDetail.setRowHeight(25);
            tblStuDetail.setBackground(Color.WHITE);
            tblStuDetail.setEnabled(false);
            tblStuDetail.getTableHeader().setFont(new Font("verdana",1,12));
            tblStuDetail.getTableHeader().setBackground(Color.BLACK);
            tblStuDetail.getTableHeader().setForeground(Color.WHITE);
            spnStuDetail = new JScrollPane(tblStuDetail);
            spnStuDetail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnStuDetail.setBounds(10,190,500,223);
            super.add(spnStuDetail);
            
            btnPrint = makeButton("Print",94,423,120,30);
            btnReturn = makeButton("Return",308,423,120,30);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321");
            PreparedStatement pst1 = con.prepareStatement("SELECT NAME, COURSE FROM STUDENT_MASTER WHERE STUDENT_ID =?");
            PreparedStatement pst2 = con.prepareStatement("SELECT SEMESTER, GRADE FROM STUDENT_GRADE WHERE STUDENT_ID = ?");
            String sid = System.getProperty("student_id");
            pst1.setString(1, sid);
            pst2.setString(1, sid);
            
            ResultSet rst1 = pst1.executeQuery();
            ResultSet rst2 = pst2.executeQuery();
            
            if(rst1.next())
            {
                lblID.setText(sid);
                lblName.setText(rst1.getString(1));
                lblCourse.setText(rst1.getString(2));
            }
            
            while(rst2.next())
            {
                String[] row = {rst2.getString(1), rst2.getString(2)}; //One by one gradually adding rows
                tblModel.addRow(row);
            }
            con.close();
            
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
