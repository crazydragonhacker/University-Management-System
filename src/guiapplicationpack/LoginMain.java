/*
##Creating a table programatically without creating it manually.
##Converting the whole project a complete install file -- installshield.
##7.6.26-Making all the buttons available when admin is selected, and other buttons available when some othr option is selected.
##There are drivers to connect DB's (oracle, mySQL,etc) with java
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

import java.sql.Connection;//All class for connecting DB's
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

class LoginFrame extends JFrame
{
    private Font fnt = new Font("verdana", 1, 12);              //Font class Object
    private JComboBox cmbRole;                                  //Creating Combobox
    private JTextField txtUid;                                  //Creating a user ID field to enter info
    private JPasswordField txtPwd;                              //Creating a password field to enter password
    private JButton btnSignup, btnSignin, btnReset, btnExit;    //Creating buttons
    private String[] role = {"Select your role", "Admin", "Professor", "Student"};      //Defining the roles to enter into combobox
    
    private JLabel makeLabel(String cap,int x,int y,int w,int h) //Make label function

    {
        JLabel temp = new JLabel(cap);
        temp.setFont(new Font("Courier New",1,16));
        temp.setBounds(x,y,w,h);
        super.add(temp);
        return temp;
    }
    private JComponent makeTextBox(int x,int y,int w,int h,int mode) //Make Text field function

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
    private JComboBox makeComboBox(int x,int y,int w,int h,String[] items) //Make Combobox fucntion
    {
        JComboBox temp = new JComboBox(items);
        temp.setFont(new Font("Verdana", 1, 14));
        temp.setBounds(x,y,w,h);
        ((JLabel)temp.getRenderer()).setHorizontalAlignment(JLabel.CENTER); //render for taking a property from JLabel, a little like typecast
        temp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int listIndex = temp.getSelectedIndex(); //getting the index of the options in the combobox
                if(listIndex == 0) //Meaning when select role/priviledge is showing in output in combobox
                {
                    btnSignup.setEnabled(false);
                    btnSignin.setEnabled(false);
                    btnReset.setEnabled(false);
                }
                else if(listIndex == 1 || listIndex == 2 || listIndex == 3) //Meaning when selected admin all three buttons are valid

                {
                    btnSignup.setEnabled(false);
                    btnSignin.setEnabled(true);
                    btnReset.setEnabled(true);
                }
                if(listIndex == 1) btnSignup.setEnabled(true);
            }
        });
        add(temp);
        return temp;
    }
    private JButton makeButton(String caption,int x,int y,int w,int h) //Make Button function

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
                    Object ob = e.getSource();
                    if(ob==btnSignup)
                    {
                        Toolkit tk = Toolkit.getDefaultToolkit();
                        Image img = tk.getImage("p0.jpg");
                        SignupFrame signFrame = new SignupFrame();
                        signFrame.setIconImage(img);
                        signFrame.setTitle("SIGN UP PANEL...");
                        signFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // the signup window that appear and on the right where is the red cross, this method issues what the program does after the cross is clicked i.e closing the window and returning to the On board login panel
                        signFrame.setResizable(false);
                        signFrame.setSize(500,300);
                        signFrame.setLocationRelativeTo(null);
                        signFrame.getContentPane().setBackground(new Color(250,250,200));
                        signFrame.setLayout(new BorderLayout());
                        signFrame.setModal(true); //After signup panel comes up, the window below i.e "ON BOARD LOGIN" window, it will be unresponsive.
                        signFrame.setUndecorated(true);
                        signFrame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG); //Theme, useless except looks
                        signFrame.setVisible(true);
                    }
                    else if(ob==btnSignin)
                    {
                        if(cmbRole.getSelectedIndex()==0||txtUid.getText().equals("")||txtPwd.getText().equals(""))
                            JOptionPane.showMessageDialog(null, "INCOMPLETE CREDENTIAL SUBMITTED");
                        else
                        {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321"); //Here 'unisys?autoReconnect' - unisys is the db name and '4321' is the pwd for logging in MYSql

                            String sql = "SELECT USERID,PASSWORD,ROLE FROM USER WHERE USERID = ? AND PASSWORD = ? AND ROLE = ?";
                            PreparedStatement pst = con.prepareStatement(sql);
                            pst.setString(1, txtUid.getText());
                            pst.setString(2, txtPwd.getText());
                            pst.setString(3, (String)cmbRole.getSelectedItem());
                            ResultSet rst = pst.executeQuery();
                            if(!rst.next())
                            {
                                JOptionPane.showMessageDialog(null, "CREDENTIAL ERROR");
                                txtUid.setText("");
                                txtPwd.setText("");
                                txtUid.grabFocus();
                            }
                            else // otherwise if found

                            {
                                dispose();
                                Toolkit tk = Toolkit.getDefaultToolkit();
                                Image img = tk.getImage("images.jpg");
                                MainFrame mFrame = new MainFrame();
                                mFrame.setIconImage(img);
                                mFrame.setTitle("STUDENT MANAGEMENT SYSTEM");
                                mFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // the signup window that appear and on the right where is the red cross, this method issues what the program does after the cross is clicked i.e closing the window and returning to the On board login panel
                                mFrame.setResizable(false);
                                mFrame.setSize(800,600);
                                mFrame.setLocationRelativeTo(null);
                                mFrame.getContentPane().setBackground(new Color(250,250,200));
                                mFrame.setLayout(new BorderLayout());
                                mFrame.setUndecorated(true);
                                mFrame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
                                mFrame.setVisible(true);
                                
                                if (cmbRole.getSelectedIndex()==3)
                                {
                                    System.setProperty("student_id", txtUid.getText());  //Storing atomic values like student_id in system properties so that we can use them in the entire project whenever we need.
                                }
                            }
                        }
                    }
                    else if(ob==btnReset)
                    {
                        cmbRole.setSelectedIndex(0);
                        txtUid.setText("");
                        txtPwd.setText("");
                    }
                    else if(ob==btnExit)
                    {
                        System.exit(0);
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
    public LoginFrame()
    {
        Border brdr1 = BorderFactory.createLineBorder(Color.RED, 2);
        Border brdr2 = BorderFactory.createLineBorder(Color.BLUE, 2);
        Border brdr3 = BorderFactory.createCompoundBorder(brdr1,brdr2);
        JLabel caption = new JLabel("ON BOARD LOGIN");
        caption.setFont(new Font("verdana",1,24));
        caption.setHorizontalAlignment(JLabel.CENTER);
        caption.setOpaque(true);
        caption.setBackground(Color.YELLOW);
        caption.setForeground(Color.red);
        caption.setBorder(brdr3);
        caption.setBounds(10,10,470,50); //To resize the On Board Login inside the box.
        super.add(caption);
        
        makeLabel("SELECT ROLE/PRIVILEGE",10,70,250,30);
        cmbRole = makeComboBox(250,70,230,30,role);
        makeLabel("ENTER USER ID",10,110,250,30);
        txtUid = (JTextField)makeTextBox(250,110,230,30,1);
        txtUid.setHorizontalAlignment(JTextField.CENTER);
        makeLabel("ENTER PASSWORD",10,150,250,30);
        txtPwd = (JPasswordField)makeTextBox(250,150,230,30,2);
        txtPwd.setHorizontalAlignment(JPasswordField.CENTER);
        txtPwd.setEchoChar('*'); //For masking in the password field

        
        btnSignup = makeButton("Sign Up",20,190,100,30);
        btnSignup.setEnabled(false);
        btnSignin = makeButton("Sign In",140,190,100,30);
        btnSignin.setEnabled(false);
        btnReset = makeButton("Reset",260,190,100,30);
        btnReset.setEnabled(false);
        btnExit = makeButton("Exit",380,190,100,30);
        btnExit.setEnabled(true);
        
        makeLabel("",100,100,2,2); //Dummy, only needed on frame not needed for panel

        
        
    }
}

public class LoginMain
{
    public static void main(String[] args) //Window Creation Function, also create all database connection code in main class
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver"); //driver upload
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unisys?autoReconnect=true&useSSL=false","root","4321"); //connection establish string, '?autoReconnect=true&useSSL=false' this line is for secure socket connection otherwise it will work but will give a error.
            //JOptionPane.showMessageDialog(null, "connection OK");           //to check whether connection is established);
            DatabaseMetaData metadata = con.getMetaData();
            ResultSet result = metadata.getTables("unisys","root","USER",new String[]{"TABLE"});
            Statement sst = con.createStatement(); //Dialog establishment calss between frontend and backend, there are three such statement class - statement, preparedstatement and callable statement

            if(!result.next()) //means the table named 'USER' was not found
            {
                String sql = "";
                sql = "create table user(userid varchar(20) primary key,password varchar(20),role varchar(9))";
                sst.executeUpdate(sql);
                sql = "insert into user values('admin','admin','Admin')";
                sst.executeUpdate(sql);
                
                sql = "create table student_master(student_id varchar(15) primary key,name varchar(20),father_name varchar(20),gender varchar(6),address varchar(50),dob date,phone varchar(12),email varchar(30),course varchar(5),semester char(1))";
                sst.executeUpdate(sql);
                
                sql = "create table professor_master(professor_id varchar(13) primary key,name varchar(20),address varchar(50),gender varchar(6),phone varchar(12),email varchar(30),dob date,doj date)";
                sst.executeUpdate(sql);
                
                sql = "create table professor_degree(professor_id varchar(13),degree varchar(10),primary key(professor_id,degree),foreign key(professor_id) references professor_master(professor_id) on delete cascade)";
                sst.executeUpdate(sql);
                
                sql = "create table student_grade(student_id varchar(15),semester char(1),grade char(1),primary key(student_id,semester),foreign key(student_id) references student_master(student_id) on delete cascade)";
                sst.executeUpdate(sql);
            }
            con.close(); //after tables were created there is no more need to create tables hence we close the connection, if this line is not given someone else can intercept the connection

            
            Toolkit tk = Toolkit.getDefaultToolkit(); //theme change

            Image img = tk.getImage("images.jpg");
            LoginFrame frame = new LoginFrame(); //Window Creation
            frame.setIconImage(img); //To set the new icon image from source folder

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(510, 270);
            frame.setLocationRelativeTo(null);
            frame.setTitle("SIGN IN PANEL");
            frame.setResizable(false); //Window creation ends

            frame.getContentPane().setBackground(new Color(250,200,200));
            frame.setUndecorated(true);
            frame.getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
            frame.setLayout(new BorderLayout());
            frame.setVisible(true);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
