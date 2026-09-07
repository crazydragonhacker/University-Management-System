package guiapplicationpack;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JDialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sun.swing.table.DefaultTableCellHeaderRenderer;




public class StudentSnapshot extends JDialog
{
    private JTable tblStuDetail;
    private JScrollPane spnStuDetail;
    private DefaultTableModel tblModel;
    private DefaultTableColumnModel tblColModel;
    private TableColumn colStudentID, colStuName, colCourse, ColSem, colGrade;
    
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
    
    
        
    public StudentSnapshot()
    {
        try
        {
            Date dt = new Date();
            String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Aug", "Sep", "Oct", "Nov", "Dec"};
            int d = dt.getDate();
            int m = dt.getMonth();
            int y = dt.getYear() + 1900; // System epoch is different from real date. The year created by the date class is behind 1900 from current year, that is why to generate current year, it must be added by 1900.
            String msg = String.format("%02d-%s-%d", d, month[m], y);
            Border brdr1 = BorderFactory.createLineBorder(Color.RED, 2);
            Border brdr2 = BorderFactory.createLineBorder(Color.BLUE, 2);
            Border brdr3 = BorderFactory.createCompoundBorder(brdr1, brdr2);
            JLabel caption = new JLabel("THE NEW SNAPSHOT VIEW OF STUDENTS AS ON " + msg);
            caption.setFont(new Font("verdana",1,22));
            caption.setHorizontalAlignment(JLabel.CENTER);
            caption.setOpaque(true);
            caption.setBackground(Color.YELLOW);
            caption.setForeground(Color.red);
            caption.setBorder(brdr3);
            caption.setBounds(10,10,870,50);
            super.add(caption);
            
            //table column model
            tblColModel = new DefaultTableColumnModel();
            colStudentID = makeTableColumn(0, 200, "STUDENT ID");
            colStuName = makeTableColumn(1, 250, "STUDENT NAME");
            colCourse = makeTableColumn(2, 140, "COURSE");
            ColSem = makeTableColumn(3, 140, "SEMESTER");
            colGrade = makeTableColumn(4, 140, "GRADE");
            
           
            tblModel = new DefaultTableModel();
            tblModel.setColumnCount(5);
            
            tblStuDetail = new JTable(tblModel, tblColModel);
            tblStuDetail.setFont(new Font("Verdana", 1, 12));
            tblStuDetail.setRowHeight(25);
            tblStuDetail.setBackground(new Color(255,255,200));
            tblStuDetail.setEnabled(false);
            tblStuDetail.getTableHeader().setFont(new Font("Verdana", 1, 12));
            tblStuDetail.getTableHeader().setBackground(Color.RED);
            tblStuDetail.getTableHeader().setForeground(Color.YELLOW);
            spnStuDetail = new JScrollPane(tblStuDetail);
            spnStuDetail.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnStuDetail.setBounds(10, 70, 870, 290);
            super.add(spnStuDetail);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321");
            Statement sst = con.createStatement(); //For joining the tables: Student_master(alias:SM) table = StudentID, name and course | Student_grade(alias:SG) table = Student Semester and grade
            ResultSet rst = sst.executeQuery("SELECT SM.STUDENT_ID, NAME, COURSE, SG.SEMESTER, GRADE FROM STUDENT_MASTER AS SM,STUDENT_GRADE AS SG WHERE SM.STUDENT_ID = SG.STUDENT_ID ORDER BY SM.STUDENT_ID, SG.SEMESTER");
            while(rst.next())
            {
                String[] row = {rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5)};
                tblModel.addRow(row);
            }
            con.close();
        
        
        
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
}
