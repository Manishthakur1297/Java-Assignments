package assignment; 
import java.util.*;
import java.io.*;
import java.sql.*;
public abstract class Emp{
	public int id;
	public String name;
	public int age;
	public int salary;
	public String designation;
	public static int employeeCount=0;
	public static Connection conn=null;
	public Emp(int salary, String designation)
	{
		Scanner in=new Scanner(System.in);
		try
		{
			System.out.println("Enter details of "+designation+":-");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			java.sql.PreparedStatement ptmt = conn.prepareStatement("insert into Employee(Id,Name,Age,Salary,Designation) values(?,?,?,?,?)");
			System.out.print("\nEnter ID for "+designation+" : ");
			id = Integer.parseInt(br.readLine());
			System.out.print("\nEnter Name of "+designation+" : ");
			name = br.readLine();
			try
			{
				System.out.print("Enter Age of "+designation+" : ");
				age = Integer.parseInt(br.readLine());

				if(age<21 || age>60)
				{
					throw new InvalidAgeException("\nAge must be in range[21-60].... Please Enter Valid Age\n");
				}
			}
			catch(InputMismatchException|InvalidAgeException e)
			{	
				age = new InvalidAgeException("Please Enter number between [21-60] only").readEmpAge();
			
			}
			this.salary = salary;
			this.designation = designation;
			ptmt.setInt(1,id);
			ptmt.setString(2,name);
			ptmt.setInt(3,age);
			ptmt.setInt(4,salary);
			ptmt.setString(5,designation);
			ptmt.execute();
			ptmt.close();
			// conn.commit();
			//   conn.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception e : " + e);
		}

		employeeCount++;
	}

	
	
		
	
	@Override
	public String toString(){
		//String res = "";
		//res+="\nName : "+name+"\nAge : "+age+"\nDesignation : "+designation+"\nSalary : "+salary+"\n";
		//return res;

		System.out.println("\nName : "+name);
		System.out.println("Age : "+age);
		System.out.println("Designation : "+designation);
		System.out.println("Salary : "+salary+"\n");
		return "";
		
	}

	public static void deleteRecord()
	{
	try
	{
		System.out.println("Enter Employee ID to delete : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int ID = Integer.parseInt(br.readLine());
		final String queryCheck = "SELECT * from Employee  WHERE Id = ?";
		PreparedStatement ps = conn.prepareStatement(queryCheck);
		ps.setInt(1, ID);
		ResultSet resultSet = ps.executeQuery();
		if(resultSet.next()) 
		{
    			final int count = resultSet.getInt(1);
			System.out.println("Count = "+count);
			System.out.println("Do you really want to delete record with following details: ");
			ResultSetMetaData rsmd = resultSet.getMetaData();

			int cols = rsmd.getColumnCount();
			for(int i=1;i<=cols;i++)
			{
				System.out.println(rsmd.getColumnName(i)+" : "+resultSet.getString(i));
			}
			System.out.println();
			System.out.println("Type yes/no : ");
			String choice = br.readLine();
			if(choice.equalsIgnoreCase("yes"))
			{
				final String query = "DELETE from Employee  WHERE Id = ?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, ID);
				ps.executeQuery();
				System.out.println("Record Deleted Successfully.........");
			}
			else
			{
				return;
			}
			
		}
		else
		{
			System.out.println("Sorry! No record Exists....");

		}
	}
		catch(Exception e)
		{

			System.out.println("Exception occured...." + e);
		}
		
	}
	
	public static void raiseSalary()
	{
	try
	{
		System.out.println("Enter designation for rasie Salary : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		String desig = br.readLine();
		String query = "Select * from Employee where Designation = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, desig);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			if(desig.equals("Clerk"))
			{
				String q = "UPDATE Employee SET Salary = Salary + 2000 where Designation = 'Clerk'";
				ps = conn.prepareStatement(q);
				ps.executeQuery();
			}
			else if(desig.equals("Programmer"))
			{
				String q = "UPDATE Employee SET Salary = Salary + 5000 where Designation = 'Programmer'";
				ps = conn.prepareStatement(q);
				ps.executeQuery();
			}
			else
			{
				String q = "UPDATE Employee SET Salary = Salary + 8000 where Designation = 'Manager'";
				ps = conn.prepareStatement(q);
				ps.executeQuery();
			}
		}
	}
	catch(Exception e)
	{
		System.out.println("Exception e");
	}

	}

	public static void display()
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//System.out.println("Enter table name : ");
			//String table = br.readLine();
			ResultSet rs = stmt.executeQuery("select * from Employee");
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

			rs.close();
			stmt.close();
		
		}
		catch(Exception e)
		{

			System.out.println("Exception occured...." + e);
		}

	}

static{
	try
	{	
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","password-1");
		//conn.setAutoCommit(false);
	}
	catch(Exception e)
	{
		System.out.println("Error");
	}
      }

}
