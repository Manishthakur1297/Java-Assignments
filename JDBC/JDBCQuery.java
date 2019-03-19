import java.sql.*;
import java.io.*;
public class JDBCQuery
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password-1");
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println("Enter table name : ");
			String table = br.readLine();
			ResultSet rs = stmt.executeQuery("select * from "+table);
			ResultSetMetaData rsmd = rs.getMetaData();

			int cols = rsmd.getColumnCount();

			while(rs.next())
			{
				for(int i=1;i<=cols;i++)
				{
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i));
				}
				System.out.println();
			}

			rs.absolute(5);
			{
				System.out.println("Name : "+rs.getString(1));
				System.out.println("Age : "+rs.getString(2));
			}
			//System.out.println(rsmd.getColumnName(1)+" : "+rs.getString(2));
			rs.relative(1);
			{
				System.out.println("Name : "+rs.getString(1));
				System.out.println("Age : "+rs.getString(2));
			}
			//System.out.println(rsmd.getColumnName(1)+" : "+rs.getString(2));
			rs.first();
			{
				System.out.println("Name : "+rs.getString(1));
				System.out.println("Age : "+rs.getString(2));
			}
			//System.out.println(rsmd.getColumnName(1)+" : "+rs.getString(2));
			rs.last();
			{
				System.out.println("Name : "+rs.getString(1));
				System.out.println("Age : "+rs.getString(2));
			}
			//System.out.println(rsmd.getColumnName(1)+" : "+rs.getString(2));
			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception occured....");
		}
	}

}