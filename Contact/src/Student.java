import javax.swing.*;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
//import java.sql.Connection;
//import java.sql.DriverManager;

public class Student {
		JFrame f1;
		JPanel p1,p2,p3;
		JButton add,search,reset;
		JLabel l1,l2,l3,l4,s1,d1,d2,d3,v1,v2,v3;
		JTextField t1,t2,t3,t4;
		JComboBox<String> b1;
		JTabbedPane tp;
		Connection conn;
				
		
		Student(){
			f1= new JFrame("Student Registration Record");
			f1.setSize(400,400);
			f1.setVisible(true);
			f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f1.setResizable(true);
			/*
			f1.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});
			*/
			p1 = new JPanel();
			p2 = new JPanel();
			p3 = new JPanel();
			p1.setLayout(new GridLayout(5,2));
			add = new JButton("Add");
			//delete = new JButton("Delete");
			search = new JButton("Search");
			reset = new JButton("Reset");
			
			l1 = new JLabel("Name");
			l2 = new JLabel("Registration Number");
			l3 = new JLabel("Mobile Number");
			l4 = new JLabel("Gender");
			
			b1 = new JComboBox<String>();
			b1.addItem("Male");
			b1.addItem("Female");
			
			t1=new JTextField(12);
			t2=new JTextField(12);
			t3=new JTextField(12);
			//t4=new JTextField();
			
			p1.add(l1);
			p1.add(t1);
			p1.add(l2);
			p1.add(t2);
			p1.add(l4);
			p1.add(b1);
			p1.add(l3);
			p1.add(t3);
			p1.add(add);
			p1.add(reset);
			
			try{
				conn = getconnection(false);
				// createDatabase(conn);
			}
			catch(Exception exp){
				exp.printStackTrace();
			}
			
			s1= new JLabel("Enter the registration number");
			d1= new JLabel("Name");
			d2= new JLabel("Mobile Number");
			d3 = new JLabel("Gender");
			
			t4 = new JTextField(12);
			v1 =new JLabel();
			v1.setVisible(true);
			v2 =new JLabel();
			v3 =new JLabel();
			
			p2.add(s1);
			p2.add(t4);
			p2.add(d1);
			p2.add(v1);
			p2.add(d2);
			p2.add(v2);
			p2.add(d3);
			p2.add(v3);
			p2.add(p3);
			//p2.add(search);
			
			p2.setLayout(new GridLayout(5,2));
			p3.add(search);
			p3.setLayout(new FlowLayout());
			
			
			
			add.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String n1,r1,m1,g1 ;
					n1=t1.getText();
					r1=t2.getText();
					m1=t3.getText();
					g1=(String)b1.getSelectedItem();
					JOptionPane.showMessageDialog(p1,"Data is successfully entered");
					try{
						
					//Statement st = con.createStatement();
						PreparedStatement pt= conn.prepareStatement("insert into student.STUDENT_TAB(STUDENT_RN,STUDENT_NAME,STUDENT_GEN,STUDENT_PHONE)values(?,?,?,?)");
							pt.setString(1,r1);
							pt.setString(2,n1);
							pt.setString(3,g1);
							pt.setString(4, m1);
							pt.executeUpdate();
					}
//					catch(ClassNotFoundException ae){
//						JOptionPane.showMessageDialog(p1,"Error in adding the record");
//					}
					catch(SQLException es){
						System.out.println("SQL error in adding the record");
//						Logger.getLogger(Student.class.getName());
						es.printStackTrace();
	//					System.exit(0);
					}
				}
				
			});
			
			reset.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					t1.setText("");
					t2.setText("");
					t3.setText("");
				}
			});
//			
			search.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String s1 = t4.getText();
					try{
						
						Statement st = conn.createStatement();
						
						//PreparedStatement prt = con.prepareStatement("select * from STUDENT_TAB where STUDENT_RN=?");
						//prt.setString(1,s1);
						ResultSet rt =st.executeQuery("select * from student.STUDENT_TAB where STUDENT_RN='"+s1 + "'");
						rt.next();
						{
							v1.setText(rt.getString(2));
							v2.setText(rt.getString(4));
							v3.setText(rt.getString(3));
							
						}
					}
					catch (SQLException ep){
						System.out.println("SQL error");
						ep.printStackTrace();
						System.exit(0);
					}
					catch (Exception c){
						System.out.println("Class not found");
						System.exit(0);
					}
				}
			});
			tp =new JTabbedPane();
			f1.getContentPane().add(tp);
			tp.addTab("Add",p1);
			tp.addTab("Search",p2);
			
					
		}
		
		public Connection getconnection(boolean shouldcreate) throws ClassNotFoundException,SQLException{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			Connection con = DriverManager.getConnection("jdbc:derby:student;create=" + String.valueOf(shouldcreate));
			return con;
		}
		
		public void createDatabase(Connection con) throws ClassNotFoundException,SQLException{
//			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//			Connection con = DriverManager.getConnection("jdbc:derby:student;create=true");
			
			String createString = "create table student.STUDENT_TAB(STUDENT_RN VARCHAR(11),\r\n"
					+ "STUDENT_NAME VARCHAR(50),\r\n"
					+ "STUDENT_GEN VARCHAR(50),\r\n"
					+ "STUDENT_PHONE VARCHAR(50))"; // do not put \r\n in the last line of database
			Statement st = con.createStatement();
			st.executeUpdate(createString);
		}
		public static void main(String[] args){
			 new Student();
		}
}