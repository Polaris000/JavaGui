import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


public class Datatable 
{
	String dbname;
	String tablename;
	String tid = "IDCOL";
	String tname = "NAMECOL";
	String toopmarks = "OOPCOL";
	String tlogicmarks = "LOGICCOL";
	String ttotalmarks = "TOTALMARKSCOL";
	
	Datatable(String dbname, String tablename)
	{
		this.dbname = dbname;
		this.tablename = tablename;
	}
	
	public Connection getconnect(boolean should_create)
	{
		System.out.println("Starting app...");
		Connection conn = null;
		
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
								+ toopmarks + " integer NOT NULL, "
								+ tlogicmarks + " integer NOT NULL, "
								+ ttotalmarks + " integer NOT NULL, "
								+ "PRIMARY KEY (" + tid + "))";
		
		PreparedStatement stmt = null;
		try
		{
			stmt = conn.prepareStatement(createcommand, ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
			stmt.executeUpdate();
		}
		
		catch(SQLException se)
		{
			System.out.println(se + " -> createtable");
		}
		
	}
	
	public void removeStudent(Connection conn, String nametoremove)
	{
		String removecommand = "delete from " + dbname + "." + tablename + " where NAMECOL = ?";
	
		PreparedStatement stmt = null;
		
		try
		{
			stmt = conn.prepareStatement(removecommand, ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_SENSITIVE);
			stmt.setString(1, nametoremove);
			stmt.executeUpdate();
		}
		
		catch(SQLException se)
		{
			System.out.println(se);
		}
	
	}
	
	public void addStudent(Connection conn, String nametoadd, int oopmarks, int logicmarks)
	{
		String addcommand = "insert into " + dbname + "." + tablename
				+ " (" + tname + ", " + toopmarks + ", " + tlogicmarks + ", " + ttotalmarks + ") "
				+ "values(\'" + nametoadd + "\', ?, ?, ?)";
	
		PreparedStatement stmt = null;
		
		try
		{
			stmt = conn.prepareStatement(addcommand, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, oopmarks);
			stmt.setInt(2, logicmarks);
			stmt.setInt(3, oopmarks + logicmarks);
			stmt.executeUpdate();
		}
		
		catch(SQLException se)
		{
			System.out.println(se);
		}
	
	}
	
	public ArrayList<ArrayList<String>> viewTable(Connection conn)
	{
		String viewcommand = "select * from " + dbname + "." + tablename;
	
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> rows = null;
		
		try
		{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(viewcommand);
			
			rows = new ArrayList<ArrayList<String>>();
			
			while(rs.next())
			{	
				ArrayList<String> row = new ArrayList<String>();
				
				row.add(String.valueOf(rs.getInt(tid)));
				row.add(rs.getString(tname));
				row.add(String.valueOf(rs.getInt(toopmarks)));
				row.add(String.valueOf(rs.getInt(tlogicmarks)));
				row.add(String.valueOf(rs.getInt(ttotalmarks)));	
				rows.add(row);
				System.out.println(String.valueOf(rs.getInt(tid)));
				
			}
		}
		
		catch(SQLException se)
		{
			System.out.println(se);
		}
		
		return rows;
	
	}

	public void editStudent(Connection conn, int pk, int oopmarks, int logicmarks)
	{

		String editcommand = "update " + dbname + "." + tablename
				+ " set " + toopmarks + " = ?, " + tlogicmarks + " = ?" 
				+ ", " + ttotalmarks + " = " + (oopmarks + logicmarks)
				+ "where " + tid + " = ?";
	
		PreparedStatement stmt = null;
		
		try
		{
			stmt = conn.prepareStatement(editcommand, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, oopmarks);
			stmt.setInt(2, logicmarks);
			stmt.setInt(3, pk);
			stmt.executeUpdate();
		}
		
		catch(SQLException se)
		{
			System.out.println(se);
		}
	}
}
