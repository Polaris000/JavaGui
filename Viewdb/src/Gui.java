import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;



public class Gui 
{
	JFrame jframe;
	
	JButton addb, removeb, backb, confirmaddb, editb, confirmeditb;
	
	JTable dbtable;
	
	JLabel addnamel, addoopmarksl, addlogicmarksl;
	JLabel editnamel, editoopmarksl, editlogicmarksl, editidl;
	
	DefaultTableModel tmodel;
	
	JPanel centerd;
	JPanel addd;
	JPanel editd;
	JPanel cardholder;
	
	JScrollPane scrolld;
	
	JTextField anamef, aoopmarksf, alogicmarksf;
	JTextField enamef, eoopmarksf, elogicmarksf, eidf;
	String dbname = "STUDENTMARKSDB";
	String tablename = "STUDENTMARKSTB";
	
	JOptionPane messagebox;
	
	//TABLE COLUMNS
	String idc = "ID";
	String namec = "NAME";
	String oopmarksc = "OOPMARKS";
	String logicmarksc = "LOGICMARKS";
	String totalmarksc = "TOTALMARKS";
	
	Object[] tablecols = {idc, namec, oopmarksc, logicmarksc, totalmarksc};
	
	Connection conn = null;
	Datatable d;
	ArrayList<ArrayList<String>> aldata;
	
	
	void populatetable(Datatable d) 
	{
		
		try
		{
			Connection connpop = d.getconnect(false);
			tmodel = new DefaultTableModel(tablecols, 0);
			aldata = d.viewTable(connpop);
			
			for(ArrayList<String> row: aldata)
			{
				tmodel.addRow(row.toArray(new Object[5]));
			}
			connpop.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e + " -> populatetable");
		}
		
//		//JTabel
//		dbtable = new JTable(tmodel);
//		//scrolld
//		scrolld = new JScrollPane(dbtable);
	}
	
	Gui()
	{
		
		messagebox =  new JOptionPane();
		messagebox.setVisible(false);
		
		Datatable d = new Datatable(dbname, tablename);
		conn = d.getconnect(false);
		populatetable(d);
		dbtable = new JTable(tmodel);
		scrolld = new JScrollPane(dbtable);
		
		//cardholder
		CardLayout cl = new CardLayout();
		cardholder = new JPanel(cl);
			
			
			//labels
			
			addnamel = new JLabel("Name");
			addoopmarksl = new JLabel("oop marks");
			addlogicmarksl = new JLabel("logic marks");
			editnamel = new JLabel("Name");
			editoopmarksl = new JLabel("oop marks");
			editlogicmarksl = new JLabel("logic marks");
			editidl = new JLabel("id");
			
			
			//textfields
			anamef = new JTextField(40);
			aoopmarksf = new JTextField(10);
			alogicmarksf = new JTextField(10);
			
			enamef = new JTextField(40);
			eoopmarksf = new JTextField(10);
			elogicmarksf = new JTextField(10);
			eidf = new JTextField(10);
			
			
			
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
			
			//editb
			editb = new JButton("Edit");
			editb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					cl.show(cardholder, "EditPanel");
				}
			});
			
			confirmaddb = new JButton("Confirm");
			confirmaddb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					
					d.addStudent(conn, anamef.getText(), Integer.parseInt(aoopmarksf.getText()), Integer.parseInt(alogicmarksf.getText()));
					populatetable(d);
					dbtable.invalidate();
					cl.show(cardholder, "CenterPanel");
					messagebox.setVisible(true);
					JOptionPane.showMessageDialog(jframe, "Successfully Added");
				}
			});
			
			confirmeditb = new JButton("Confirm");
			confirmeditb.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					d.editStudent(conn,  Integer.parseInt(eidf.getText()), Integer.parseInt(eoopmarksf.getText()), Integer.parseInt(elogicmarksf.getText()));
					populatetable(d);
					dbtable.repaint();

					cl.show(cardholder, "CenterPanel");
				}
			});
			
			
			//centerd
			centerd = new JPanel();
			centerd.setLayout(new GridLayout(2, 0));
			centerd.add(scrolld);
			centerd.add(addb);
			centerd.add(editb);
			centerd.setVisible(true);
			
			
			
			//addd
			addd = new JPanel();
			addd.setLayout(new GridLayout(4, 2));
			addd.add(addnamel);
			addd.add(anamef);
			addd.add(addoopmarksl);
			addd.add(aoopmarksf);
			addd.add(addlogicmarksl);
			addd.add(alogicmarksf);
			addd.add(confirmaddb);
			addd.add(backb);
			
			//editd
			editd = new JPanel();
			editd.setLayout(new GridLayout(4, 2));
			editd.add(editidl);
			editd.add(eidf);
			editd.add(editoopmarksl);
			editd.add(eoopmarksf);
			editd.add(editlogicmarksl);
			editd.add(elogicmarksf);
			editd.add(confirmeditb);
			editd.add(backb);
			
			
			cardholder.add(centerd, "CenterPanel");
			cardholder.add(addd, "AddPanel");
			cardholder.add(editd, "EditPanel");
			
			
			jframe = new JFrame("student marks");
			jframe.add(messagebox);
			jframe.setSize(500, 500);
			jframe.setLayout(new BorderLayout(0, 100));
			jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			jframe.add(cardholder);
			jframe.setVisible(true);
			
			jframe.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowIconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeiconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeactivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowClosing(WindowEvent arg0) {
					{  
					    int a=JOptionPane.showConfirmDialog(jframe,"Are you sure?");  
					
					    if(a==JOptionPane.YES_OPTION){  
					    	try
					    	{conn.close();}
					    	catch(Exception e)
					    	{
					    		System.out.println(e + " -> windowclose");
					    	}
					    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
					}  
					}  
					
				}
				
				@Override
				public void windowClosed(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowActivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
	}
	
	public static void main(String[] args) {
		new Gui();
	}
}
