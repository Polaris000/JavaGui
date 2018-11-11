import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Properties;


public class Gui 
{
	JFrame jframe = new JFrame("Student registration app");
	JPanel addtab, edittab;
	JTabbedPane cardholder;
	JTextField namefield, marksfield, idfield, editnamefield, editmarksfield;
	JLabel namefieldl, marksfieldl, idfieldl;
	JButton addbutton, editbutton, resetbutton1, resetbutton2;
	
	Gui()
	{
		// addtab panel
		addtab = new JPanel();
		addtab.setLayout(new GridLayout(4, 2));
		
		// addtab labels
		idfieldl = new JLabel("Id");
		namefieldl = new JLabel("Name");
		marksfieldl = new JLabel("Marks");
		
		//add tab textboxes
		idfield = new JTextField();
		namefield = new JTextField("hgjfj");
		marksfield = new JTextField();
		
		Student s = new Student();
		Connection conn = s.startconnect("STUDENT");
		
		//addbutton
		addbutton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				
				try
				{	
					s.addStudent(conn, "STUDENT", namefield.getText(), Integer.parseInt(idfield.getText()), Integer.parseInt(marksfield.getText()));
					s.viewTable(conn, "STUDENT");
				
					
					System.out.println("Table created!");
					
					idfield.setText("");
					namefield.setText("");
					marksfield.setText("");
				}
				
				catch(SQLException e)
				{
					System.out.println(e);
				}
			}
		});
				
				
		resetbutton1 = new JButton("Reset");
		resetbutton1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{		
				idfield.setText("");
				namefield.setText("");
				marksfield.setText("");
			}
		});
		
		
		addtab.add(idfieldl);
		addtab.add(idfield);
		addtab.add(namefieldl);
		addtab.add(namefield);
		addtab.add(marksfieldl);
		addtab.add(marksfield);
		addtab.add(addbutton);
		addtab.add(resetbutton1);
		
		
	// ************************************************************		
	// edittab panel
		
		//edit tab labels
		namefieldl = new JLabel("Name");
		marksfieldl = new JLabel("Marks");
		
		//edit tab textboxes
		editnamefield = new JTextField();
		editmarksfield = new JTextField();
		
		edittab = new JPanel();
		edittab.setLayout(new GridLayout(3, 2));	
	
		//editbutton
		editbutton = new JButton("Edit");
		editbutton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				System.out.println(namefield.getText());
				try
				{					
					s.modifyMarks(conn, "STUDENT", editnamefield.getText(), Integer.parseInt(editmarksfield.getText()));
					s.viewTable(conn, "STUDENT");

					System.out.println("Marks modified...");
					
					idfield.setText("");
					namefield.setText("");
					marksfield.setText("");
				}
				
				catch(SQLException e)
				{
					System.out.println(e);
				}
				
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		});
		
		resetbutton2 = new JButton("Reset");
		resetbutton2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{		
				editnamefield.setText("");
				editmarksfield.setText("");
			}
		});		
		
		edittab.add(namefieldl);
		edittab.add(editnamefield);
		edittab.add(marksfieldl);
		edittab.add(editmarksfield);
		edittab.add(editbutton);
		edittab.add(resetbutton2);
		
		cardholder = new JTabbedPane();
		cardholder.addTab("Add", addtab);
		cardholder.addTab("Edit", edittab);
		

		//jframe
		jframe.setLayout(new CardLayout());
		jframe.setSize(800, 400);
		jframe.add(cardholder);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setVisible(true);
	}
	
	
	public static void main(String[] args) throws SQLException
	{
		System.out.println("Starting...");
		new Gui();
	}
}
