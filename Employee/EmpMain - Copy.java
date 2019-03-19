import assignment.Clerk;
import assignment.Manager;
import assignment.Programmer;
import assignment.Emp; 

public class EmpMain{
	public static void main(String args[]){
		Emp e[] = new Emp[100];
		
		in ch1=0,ch2=0;
		do
		{
1. Create
2. display
3. raise
4. exit
enter choice
switch
case 1
switch
		}
	
		Emp e1=new Clerk();
		Emp e2=new Programmer();
		Emp e3=new Manager();
		Emp.display(e1);
		Emp.display(e2);
		Emp.display(e3);
		e1.raiseSalary();
		e2.raiseSalary();
		e3.raiseSalary();
		System.out.println("------------AFTER RAISING SALARY--------------");
		Emp.display(e1);
		Emp.display(e2);
		Emp.display(e3);
		System.out.println("----------------------------------------------");
		System.out.println("Employee Count------>"+Emp.employeeCount);
	}
}