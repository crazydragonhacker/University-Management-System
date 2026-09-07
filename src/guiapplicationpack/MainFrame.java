
// Creating the Menu Structure from 2nd Image
// From Menu we will go inside the options and then use JDBC

package guiapplicationpack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame
{
    private Font fnt = new Font("Verdana", 1, 12);

    private JMenuBar menuBar;   // Creation of menubar

    // Creation of three menus in the menubar
    private JMenu menuAdmin, menuProfessor, menuStudent;

    // Creation of options/items in the menus
    private JMenuItem[] adminItems = new JMenuItem[7];
    private JMenuItem[] profItems = new JMenuItem[4];
    private JMenuItem[] stuItems = new JMenuItem[3];

    // Options inside Administration menu
    private String[] adminItemCap = {
        "Add Student",
        "Add Professor",
        "Edit Student",
        "Edit Professor",
        "Delete Student",
        "Delete Professor",
        "Exit"
    };

    // Options inside Professor menu
    private String[] profItemCap = {
        "Assign Grade",
        "Edit Grade",
        "View Student",
        "Exit"
    };

    // Options inside Student menu
    private String[] stuItemCap = {
        "View Detail",
        "View Grade",
        "Exit"
    };

    // Method to create a JMenu
    private JMenu makeMenu(String caption)
    {
        JMenu temp = new JMenu(caption);

        temp.setFont(fnt);

        menuBar.add(temp);

        return temp;
    }

    // Method to create a JMenuItem
    private JMenuItem makeMenuItem(String caption, JMenu menu, String image)
    {
        JMenuItem temp = new JMenuItem(
            caption,
            new ImageIcon(image)
        );

        temp.setFont(fnt);

        menu.add(temp);

        // Adding click event to the menu item
        temp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Get the menu item which was clicked
                Object ob = e.getSource();

                // -----------------------------------------
                // ADMINISTRATION MENU
                // -----------------------------------------

                if(ob == adminItems[0])       // Add New Student
                {
                    StudentAddFrame frame = new StudentAddFrame();

                    frame.setTitle("ADD NEW STUDENT");

                    frame.setDefaultCloseOperation(
                        JDialog.DISPOSE_ON_CLOSE
                    );

                    frame.setResizable(false);

                    frame.setSize(530, 550);

                    frame.getContentPane().setBackground(
                        new Color(250, 200, 150)
                    );

                    frame.setModal(true);

                    frame.setLayout(new BorderLayout());

                    frame.setVisible(true);
                }

                else if(ob == adminItems[1])  // Add New Professor
                {
                    ProfessorAddFrame frame = new ProfessorAddFrame();

                    frame.setTitle("ADD NEW PROFESSOR");

                    frame.setDefaultCloseOperation(
                        JDialog.DISPOSE_ON_CLOSE
                    );

                    frame.setResizable(false);

                    frame.setSize(530, 550);

                    frame.getContentPane().setBackground(
                        new Color(250, 200, 150)
                    );

                    frame.setModal(true);

                    frame.setLayout(new BorderLayout());

                    frame.setVisible(true);
                }

                else if(ob == adminItems[2]) // Edit Existing Student
                {
                    StudentEditFrame frame = new StudentEditFrame();

                    frame.setTitle("EDIT EXISTING STUDENT");

                    frame.setDefaultCloseOperation(
                        JDialog.DISPOSE_ON_CLOSE
                    );

                    frame.setResizable(false);

                    frame.setSize(530, 550);

                    frame.setLocationRelativeTo(null);

                    frame.getContentPane().setBackground(
                        new Color(250, 200, 150)
                    );

                    frame.setModal(true);

                    frame.setLayout(new BorderLayout());

                    frame.setVisible(true);
                }

                else if(ob == adminItems[3])  // Edit Existing Professor
                {
                    // Code for editing professor will come here
                    ProfessorEditFrame frame = new ProfessorEditFrame();

                    frame.setTitle("EDIT EXISTING PROFESSOR");

                    frame.setDefaultCloseOperation(
                        JDialog.DISPOSE_ON_CLOSE
                    );

                    frame.setResizable(false);

                    frame.setSize(530, 500);

                    frame.setLocationRelativeTo(null);

                    frame.getContentPane().setBackground(
                        new Color(250, 200, 150)
                    );

                    frame.setModal(true);

                    frame.setLayout(new BorderLayout());

                    frame.setVisible(true);
                }

                else if(ob == adminItems[4])  // Delete Existing Student
                {
                    // Code for deleting student will come here
                    StudentDeleteFrame frame = new StudentDeleteFrame();
                    frame.setTitle("DELETE EXISTING STUDENT");
                    frame.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(530, 550);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(new Color(250, 200, 150));
                    frame.setModal(true);
                    frame.setLayout(new BorderLayout());
                    frame.setVisible(true);
                }

                else if(ob == adminItems[5])  // Delete Existing Professor
                {
                    // Code for deleting professor will come here
                    ProfessorDeleteFrame frame = new ProfessorDeleteFrame();
                    frame.setTitle("Delete EXISTING Professor");
                    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(530, 550);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(new Color(250, 200, 150));
                    frame.setModal(true);
                    frame.setLayout(new BorderLayout());
                    frame.setVisible(true);
                }

                else if(ob == adminItems[6])  // Exit
                {
                    System.exit(0);
                }

                // -----------------------------------------
                // PROFESSOR MENU
                // -----------------------------------------

                else if(ob == profItems[0])  // Assign Grade
                {
                    // Code for assigning grade will come here
                    System.setProperty("gradeassign", "1"); ////For both menus of AssignGrade and EditGrade the same window will appear, thus we use the global/system property setting.
                    GradeAssign frame = new GradeAssign();
                    frame.setTitle("ASSIGNING OF GRADE");
                    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(540, 270);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(new Color(250, 200, 150));
                    frame.setModal(true);
                    frame.setLayout(new BorderLayout());
                    frame.setVisible(true);
                }

                else if(ob == profItems[1])  // Edit Grade
                {
                    // Code for editing grade will come here
                    
                    System.setProperty("gradeassign", "2"); //For both menus of AssignGrade and EditGrade the same window will appear, thus we use the global/system property setting.
                    GradeAssign frame = new GradeAssign();
                    frame.setTitle("ASSIGNING OF GRADE");
                    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(540, 270);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(new Color(250, 200, 150));
                    frame.setModal(true);
                    frame.setLayout(new BorderLayout());
                    frame.setVisible(true);
                }

                else if(ob == profItems[2])  // View Student
                {
                    // Code for viewing student will come here
                    StudentSnapshot frame = new StudentSnapshot();
                    frame.setTitle("STUDENTS' SEMESTER WISE GRADE SNAPSHOT");
                    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(900, 400);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(new Color(250, 200, 150));
                    frame.setModal(true);
                    frame.setLayout(new BorderLayout());
                    frame.setVisible(true);
                }

                else if(ob == profItems[3])  // Exit
                {
                    System.exit(0);
                }

                // -----------------------------------------
                // STUDENT MENU
                // -----------------------------------------

                else if(ob == stuItems[0])   // View Detail
                {
                    // Code for viewing student details will come here
                    ViewDetailByStudent frame = new ViewDetailByStudent();
                    frame.setTitle("PERSONAL DETAIL");
                    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(530, 550);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(Color.WHITE);
                    frame.setModal(true);
                    frame.setLayout(new BorderLayout());
                    frame.setVisible(true);
                }

                else if(ob == stuItems[1])   // View Grade
                {
                    // Code for viewing student grade will come here
                    ViewGradeByStudent frame = new ViewGradeByStudent();
                    frame.setTitle("GRADE DETAIL");
                    frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(530, 500);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(Color.WHITE);
                    frame.setModal(true);
                    frame.setLayout(new BorderLayout());
                    frame.setVisible(true);
                }

                else if(ob == stuItems[2])   // Exit
                {
                    System.exit(0);
                }
            }
        });

        return temp;
    }

    // Constructor
    public MainFrame()
    {
        // Create menubar
        menuBar = new JMenuBar();

        // Attach menubar to JFrame
        super.setJMenuBar(menuBar);

        // Create three menus
        menuAdmin = makeMenu("Administration");
        menuProfessor = makeMenu("Professor");
        menuStudent = makeMenu("Student");

        // -----------------------------------------
        // ADMINISTRATION MENU ITEMS
        // -----------------------------------------

        for(int i = 0; i < 7; i++)
        {
            // Add separator before:
            // Edit Student and Delete Student
            // because i = 2 and i = 4
            if(i != 0 && i % 2 == 0)
            {
                menuAdmin.addSeparator();
            }

            adminItems[i] = makeMenuItem(
                adminItemCap[i],
                menuAdmin,
                "right.gif"
            );
        }

        // -----------------------------------------
        // PROFESSOR MENU ITEMS
        // -----------------------------------------

        for(int i = 0; i < 4; i++)
        {
            // Add separator before Exit
            if(i == 3)
            {
                menuProfessor.addSeparator();
            }

            profItems[i] = makeMenuItem(
                profItemCap[i],
                menuProfessor,
                "right.gif"
            );
        }

        // -----------------------------------------
        // STUDENT MENU ITEMS
        // -----------------------------------------

        for(int i = 0; i < 3; i++)
        {
            // Add separator before Exit
            if(i == 2)
            {
                menuStudent.addSeparator();
            }

            stuItems[i] = makeMenuItem(
                stuItemCap[i],
                menuStudent,
                "right.gif"
            );
        }
    }
}


