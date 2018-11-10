import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;


public class Gui extends JFrame
{
	JButton leftb, rightb, addb, removeb, backb, confirmaddb;
	
	JLabel namel, mobilenuml, idl;
	JLabel addnamel, addmobilenuml;
	JLabel disnamel, dismobilenuml, disidl;
	
	JPanel centerd;
	JPanel addd;
	JPanel gobackpanel;
	JPanel cardholder;
	
	JTextField name, mobilenum;
	String dbname = "CONTACTLIST";
	String tablename = "CONTACTLISTTABLE";
	
	//TABLE COLUMNS
	String tid = "IDNUM";
	String tname = "NAME";
	String tnum = "MOBILENUM";
	Connection conn = null;
	int pk = 1;
	
	//Connection Methods:
	public Connection getconnect(String dbname, boolean should_create)
	{
		System.out.println("Starting app...");
		
		try
		{
			conn = DriverManager.getConnection("jdbc:derby:" + dbname + ";create=" + String.valueOf(should_create));
			conn.setAutoCommit(true);
			
			if(should_create)
			{
				createTable(conn);
			}
			
		}
	
		catch (SQLException se)
		{
			System.out.println(se + "getconnect");
		}
		
		return conn;
	}
	
	
	public void createTable(Connection conn)
	{
		String createcommand = "create table " + dbname + "." + tablename + "("
								+ tid + " integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
								+ tname + " varchar(40) NOT NULL, "
								+ tnum + " integer NOT NULL, "
								+ "PRIMARY KEY (" + tid + "))";
		
		Statement stmt = null;
		try
		{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(createcommand);
		}
		
		catch(SQLException se)
		{
			System.out.println(se + "createtable");
		}
		
	}
	
	
	public void removeContact(Connection conn, String nametoremove)
	{
		String removecommand = "delete from " + dbname + "." + tablename + " where NAME='" + nametoremove + "'";
	
		Statement stmt = null;
		
		try
		{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(removecommand);
		}
		
		catch(SQLException se)
		{
			System.out.println(se);
		}
	
	}
	
	public void addContact(Connection conn, String nametoadd, int numbertoadd)
	{
		String addcommand = "insert into " + dbname + "." + tablename
				+ " (" + tname + ", " + tnum + ") values(\'" + nametoadd + "\', " + numbertoadd + ")";
	
		Statement stmt = null;
		
		try
		{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(addcommand);
		}
		
		catch(SQLException se)
		{
			System.out.println(se);
		}
	
	}
	
	public ArrayList<String> viewContact(Connection conn, int pk)
	{
		String viewcommand = "select " + tname + ", " + tnum + ", " + tid + " from " + dbname + "." + tablename 
								+ " where IDNUM = " + pk;
	
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> contact = null;
		
		try
		{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(viewcommand);
			
			while(rs.next())
			{	
				contact = new ArrayList<String>();
				contact.add(rs.getString(tname));
				contact.add(String.valueOf(rs.getInt(tnum)));
				contact.add(String.valueOf(rs.getInt(tid)));
			}
		}
		
		catch(SQLException se)
		{
			System.out.println(se);
		}
		
		return contact;
	
	}
	
	Gui()
	{	
		
		conn = getconnect(dbname, false);
		
		//cardholder
		CardLayout cl = new CardLayout();
		cardholder = new JPanel(cl);
		
		
		//labels
		namel = new JLabel("Name");
		mobilenuml = new JLabel("Number");
		idl = new JLabel("id");
		addnamel = new JLabel("Name");
		addmobilenuml = new JLabel("NUmber");
		disnamel = new JLabel("Some name");
		dismobilenuml = new JLabel("SOme number");
		disidl = new JLabel("Some id");
		
		
		//textfields
		name = new JTextField(40);
		mobilenum = new JTextField(10);
		
		
		//leftb
		leftb = new JButton("Left");
		leftb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					ArrayList<String> contact = viewContact(conn, pk);
					System.out.println("inside if");
					disnamel.setText(contact.get(0));
					dismobilenuml.setText(contact.get(1));
					disidl.setText(contact.get(2));
					pk -= 1;
				}
				
				catch(Exception x)
				{
					System.out.println("inside if");
				}
			}
		});
		
		//rightb
		rightb = new JButton("Right");
		rightb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					ArrayList<String> contact = viewContact(conn, pk);
				
					disnamel.setText(contact.get(0));
					dismobilenuml.setText(contact.get(1));
					disidl.setText(contact.get(2));
					pk += 1;
				}
				
				catch(Exception es)
				{
					
				}
				
			}
		});
		
		//removeb
		removeb = new JButton("Remove");
		removeb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeContact(conn, disnamel.getText());
			}
		});
		
		//backb
		backb = new JButton("Back");
		backb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(cardholder, "CenterPanel");
			}
		});
		
		//addb
		addb = new JButton("Add");
		addb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(cardholder, "AddPanel");
			}
		});
		
		confirmaddb = new JButton("Confirm");
		confirmaddb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addContact(conn, name.getText(), Integer.parseInt(mobilenum.getText()));
				cl.show(cardholder, "CenterPanel");
			}
		});
		
//		if (viewContact(conn, pk) != null)
//		{
//			ArrayList<String> contact = viewContact(conn, pk);
//		
//			disnamel.setText(contact.get(0));
//			dismobilenuml.setText(contact.get(1));
//		}
		
//		else
//		{
			disnamel.setText("No contact");
			dismobilenuml.setText("No contact");
//		}
		
		//centerd
		centerd = new JPanel();
		centerd.setLayout(new GridLayout(3, 2));
		centerd.add(namel);
		centerd.add(disnamel);
		centerd.add(mobilenuml);
		centerd.add(dismobilenuml);
		centerd.add(idl);
		centerd.add(disidl);
		centerd.setVisible(true);
		
		
		//gobackpanel
		gobackpanel = new JPanel();
		gobackpanel.add(backb);
		gobackpanel.add(removeb);
		
		//addd
		addd = new JPanel();
		addd.setLayout(new GridLayout(3, 2));
		addd.add(addnamel);
		addd.add(name);
		addd.add(addmobilenuml);
		addd.add(mobilenum);
		addd.add(confirmaddb);
		
		
		cardholder.add(centerd, "CenterPanel");
		cardholder.add(addd, "AddPanel");
		
		
		
		setSize(500, 500);
		setLayout(new BorderLayout(0, 100));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(leftb, BorderLayout.WEST);
		add(rightb, BorderLayout.EAST);
		add(addb, BorderLayout.NORTH);
		add(gobackpanel, BorderLayout.SOUTH);
		add(cardholder);
		
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Gui();
	}
}
