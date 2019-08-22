//JAVA 8 grouping related program

/*
1. for queries such as grouping of input data, use the Collector.groupingBy() method.
	in this query, we use Collectors.groupingBy() method which takes 2 arguments.
	we will pass first argument as input on which we need to group
	and 2nd argument as Collectors.counting or average etc to perform that operation on the group

2. use DISTINCT method after calling map on the stream.

3. Collectors.maxBy() returns maximum element wrapped in an optional object based on the supplier.

4. for filtering we have to use stream.filter method which filters input elements according to supplied Predicate.

5. we can use Stream.filter() to apply any filter and can use Stream.min() for any operation on that filter

6. sorted() can be used for sorting purpose

7. Collectors.summarizingDouble() will return statistics of the class like min,max,average,total etc.

8. to separate data based on supplied predicate we will be suing Collectors.partitioningBy()


 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Client {
	static List<Employee> employeeList = new ArrayList<>();

	public static void main(String[] args) {
		employeeList = empList();
		Client client = new Client();

		client.noOfMaleAndFemale();
		client.getAllDepartments();
		client.getAverageAgeOfMaleAndFemale();
		client.getEmployeeWithMaxSalary();
		client.getEmployeeJoinedAfter();
		client.noOfEmployeeInEachDept();
		client.getAverageSalaryOfDepartment();
		client.getYoungestMaleInPDdept();
		client.getMostExpEmp();
		client.getCountSalesMarketing();
		client.getAverageSalOfMaleAndFemale();
		client.AllEmpPerDept();
		client.getSummary();
		client.partitionOfEmpByAge();
		client.getOldestEmp();
		client.getAvgSalOfMandFGroupByDept();

	}


	private void getAvgSalOfMandFGroupByDept() {
		System.out.println("========");
		Map<String, Map<String, Double>> avg=employeeList.stream()
				.collect(Collectors.groupingBy(
						Employee::getDepartment,Collectors.
						groupingBy(Employee::getGender,Collectors.averagingDouble(Employee::getSalary)
								)));
		//System.out.println(avg);
		for (Map.Entry map : avg.entrySet()) {
			System.out.println(map.getKey()+"-"+map.getValue());
		}
	}
	

	private void getOldestEmp() {
		Optional<Employee> oldestOpt = employeeList.stream()
				.max(Comparator.comparingInt(Employee::getAge));
		Employee oldestEmp = oldestOpt.get();
		System.out.println("DETAILS OF OLDEST EMP");
		System.out.println(oldestEmp.getAge());
		System.out.println(oldestEmp.getDepartment());
		System.out.println(oldestEmp.getName());

	}


	private void partitionOfEmpByAge() {
		System.out.println("PARTITION BY AGE");
		Map<Boolean, List<Employee>> partitionByAge= employeeList.stream()
				.collect(Collectors.partitioningBy(e->e.getAge()>25));
		Set<Entry<Boolean, List<Employee>>> entrySet= partitionByAge.entrySet();
		for (Entry<Boolean, List<Employee>> entry : entrySet) {

			if(entry.getKey()){
				System.out.println("Employees older than 25 years");
			}else{
				System.out.println("Employees less than or equal to 25");
			}
			List<Employee> list = entry.getValue();
			for (Employee e : list) {
				System.out.println(e.getName());
			}
		}

	}


	private void getSummary() {
		DoubleSummaryStatistics empSalaryStat = employeeList.stream()
				.collect(Collectors.summarizingDouble(Employee::getSalary));

		System.out.println("Average salary:"+empSalaryStat.getAverage());
		System.out.println("Total salary:" +empSalaryStat.getSum());
	}


	private void AllEmpPerDept() {
		Map<String, List<Employee>> empListByDept=employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment));

		Set<Entry<String, List<Employee>>> empList= empListByDept.entrySet();

		for (Entry<String, List<Employee>> entry : empList) {
			System.out.println("EMPLOYEES IN "+entry.getKey());
			List<Employee> list = entry.getValue();
			for (Employee e : list) {
				System.out.println(e.getName());
			}
		}
	}



	private void getAverageSalOfMaleAndFemale() {
		System.out.println("GETTING AVERAGE SALARY OF MALE AND FEMALE EMPLOYEE");

		Map<String, Double> avgSal =employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));

		System.out.println(avgSal);

	}


	private void getCountSalesMarketing() {
		System.out.println("MALE AND FEMALE IN SALES AND MARKETING");
		Map<String, Long> countMaleAndFemale= employeeList.stream()
				.filter(e->e.getDepartment()=="Sales And Marketing")
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
		System.out.println(countMaleAndFemale);

	}





	private void getMostExpEmp() {
		Optional<Employee> mostExpEmp = employeeList.stream()
				.sorted(Comparator.comparingInt(Employee::getYearOfJoining)).findFirst();
		Employee mostExp = mostExpEmp.get();

		System.out.println("DETAILS OF SENIOR EMPLOYEE IN PRODUCT DEVELOPMENT");

		System.out.println(mostExp.getId());
		System.out.println(mostExp.getAge());
		System.out.println(mostExp.getGender());
		System.out.println(mostExp.getName());
		System.out.println(mostExp.getSalary());
		System.out.println(mostExp.getYearOfJoining());
	}


	private void getYoungestMaleInPDdept() {
		Optional<Employee> youngestMaleInPDDept = employeeList.stream()
				.filter(e ->e.getGender()=="Male" && e.getDepartment()=="Product Development")
				.min(Comparator.comparingInt(Employee::getAge));

		Employee ym = youngestMaleInPDDept.get();
		System.out.println("DETAILS OF YOUNGEST EMPLOYEE IN PRODUCT DEVELOPMENT");

		System.out.println(ym.getId());
		System.out.println(ym.getAge());
		System.out.println(ym.getGender());
		System.out.println(ym.getName());
		System.out.println(ym.getSalary());
		System.out.println(ym.getYearOfJoining());
	}


	private void getAverageSalaryOfDepartment() {
		System.out.println("AVERAGE SALARY OF EACH DEPARTMENT");
		Map<String , Double> avgSal= employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
		System.out.println(avgSal);
	}


	private void noOfEmployeeInEachDept() {
		System.out.println("NO. OF EMPLOYEE OF EACH DEPARTMENT");
		Map<String, Long> empCountByDept=
				employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
		System.out.println(empCountByDept);

	}


	private void getEmployeeJoinedAfter() {
		System.out.println("EMPLOYEES JOINED AFTER 2015");
		employeeList.stream().filter(e->e.getYearOfJoining()>2015).map(Employee::getName).forEach(System.out::println);
	}


	private void getEmployeeWithMaxSalary() {
		Optional<Employee> highestPaid=
				employeeList.stream().collect(Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)));

		Employee highestPaidEmp=highestPaid.get();
		System.out.println("DETAILS OF HIGHEST PAID EMPLOYEE");
		System.out.println(highestPaidEmp.getId());
		System.out.println(highestPaidEmp.getName());
		System.out.println(highestPaidEmp.getAge());
		System.out.println(highestPaidEmp.getDepartment());
		System.out.println(highestPaidEmp.getGender());
		System.out.println(highestPaidEmp.getSalary());
		System.out.println(highestPaidEmp.getYearOfJoining());


	}


	private void getAverageAgeOfMaleAndFemale(){
		System.out.println("PRINTING AVERAGE AGE OF AMLE AND FEMALE EMPLOYEE");
		Map<String, Double> avgAge=employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));


		System.out.println(avgAge);
	}


	private void getAllDepartments(){
		System.out.println("DISTINCT DEPARTMENTS");
		employeeList.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);

	}


	private void noOfMaleAndFemale(){
		Map<String, Long> noOfMF = new HashMap<>();
		noOfMF = employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
		System.out.println("NO OF MALE AND FEMALE EMPLOYEES");
		System.out.println(noOfMF);
	}


	private static List<Employee> empList(){
		List<Employee> list = new ArrayList<>();

		list.add(new Employee(111, "Jiya Brein", 32, "Female", "HR", 2011, 25000.0));
		list.add(new Employee(122, "Paul Niksui", 25, "Male", "Sales And Marketing", 2015, 13500.0));
		list.add(new Employee(133, "Martin Theron", 29, "Male", "Infrastructure", 2012, 18000.0));
		list.add(new Employee(144, "Murali Gowda", 28, "Male", "Product Development", 2014, 32500.0));
		list.add(new Employee(155, "Nima Roy", 27, "Female", "HR", 2013, 22700.0));
		list.add(new Employee(166, "Iqbal Hussain", 43, "Male", "Security And Transport", 2016, 10500.0));
		list.add(new Employee(177, "Manu Sharma", 35, "Male", "Account And Finance", 2010, 27000.0));
		list.add(new Employee(188, "Wang Liu", 31, "Male", "Product Development", 2015, 34500.0));
		list.add(new Employee(199, "Amelia Zoe", 24, "Female", "Sales And Marketing", 2016, 11500.0));
		list.add(new Employee(200, "Jaden Dough", 38, "Male", "Security And Transport", 2015, 11000.5));
		list.add(new Employee(211, "Jasna Kaur", 27, "Female", "Infrastructure", 2014, 15700.0));
		list.add(new Employee(222, "Nitin Joshi", 25, "Male", "Product Development", 2016, 28200.0));
		list.add(new Employee(233, "Jyothi Reddy", 27, "Female", "Account And Finance", 2013, 21300.0));
		list.add(new Employee(244, "Nicolus Den", 24, "Male", "Sales And Marketing", 2017, 10700.5));
		list.add(new Employee(255, "Ali Baig", 23, "Male", "Infrastructure", 2018, 12700.0));
		list.add(new Employee(266, "Sanvi Pandey", 26, "Female", "Product Development", 2015, 28900.0));
		list.add(new Employee(277, "Anuj Chettiar", 31, "Male", "Product Development", 2012, 35700.0));

		return list;

	}
}

class Employee {
	private int id;
	private String name;
	private int age;
	private String gender;
	private String department;
	private int yearOfJoining;
	private double salary;

	public Employee(int id, String name, int age, String gender, String department, int yearOfJoining, double salary) 
	{
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.department = department;
		this.yearOfJoining = yearOfJoining;
		this.salary = salary;
	}

	public int getId() 
	{
		return id;
	}

	public String getName() 
	{
		return name;
	}

	public int getAge() 
	{
		return age;
	}

	public String getGender() 
	{
		return gender;
	}

	public String getDepartment() 
	{
		return department;
	}

	public int getYearOfJoining() 
	{
		return yearOfJoining;
	}

	public double getSalary() 
	{
		return salary;
	}

	@Override
	public String toString() 
	{
		return "Id : "+id
				+", Name : "+name
				+", age : "+age
				+", Gender : "+gender
				+", Department : "+department
				+", Year Of Joining : "+yearOfJoining
				+", Salary : "+salary;
	}
}
