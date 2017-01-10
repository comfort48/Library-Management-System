import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Console;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

class Person{
	final String ANSI_CLS = "\u001b[2J";
    final String ANSI_HOME = "\u001b[H";       
	private String username;
	private String password;
	private String name;
	private String branch;
	public void update() throws Exception{
		Scanner s = new Scanner(System.in);
		Connection con = null;
        PreparedStatement pst = null;
        boolean rs = true;
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String password = "asdf";
        con = DriverManager.getConnection(url, user, password);
		System.out.print("Enter the new password : ");
		String d = s.nextLine();
		System.out.println("Do you want to change your password to "+d+" ?");
		char c = s.nextLine().charAt(0);
		if(c == 'Y' || c == 'y'){
			pst = con.prepareStatement("UPDATE login set password = ? where reg_no = ?");
			pst.setString(1,d);
			pst.setString(2,username);
            rs = pst.execute();
            System.out.println("The password is updated successfully.");
		}
		else{
			System.out.println("Thank you.\nHave a good day.");
		}
	}
	public void register() throws Exception{
		System.out.print(ANSI_HOME+ANSI_CLS);
		Scanner s = new Scanner(System.in);
		System.out.print("Enter your name : ");
		name = s.nextLine();
		System.out.print("Enter your branch : CSE / ECE / MEC / EEE : ");
		branch = s.nextLine(); 
		System.out.print("Enter your roll number : ");
		username = s.nextLine();
		System.out.print("Enter your password : ");
		password = s.nextLine();
        Connection con = null;
        PreparedStatement pst = null;
        String url = "jdbc:postgresql://localhost/postgres";
  		String user = "postgres";
        String passwor = "asdf";
        con = DriverManager.getConnection(url, user, passwor);
        String si = "insert into login(reg_no,password) values(?,?)";
        pst = con.prepareStatement(si);
        pst.setString(1, username);
        pst.setString(2, password);  
        pst.executeUpdate();
        char ch;
        System.out.println("Registration successful\nDo you want to login ? (Y/N)");
        ch = s.nextLine().charAt(0);
            if(ch == 'Y' || ch == 'y')
                	login();
                else
                	System.out.println("Thank you registering.\nHave a good day.");
	}
        public void login() throws Exception, IOException{
        	Console console=System.console();
        	Scanner s = new Scanner(System.in);
        	System.out.print(ANSI_HOME+ANSI_CLS);
        	System.out.println("Are you a student or a faculty ? (S/F)");
        	char g = s.nextLine().charAt(0);
        	if(g == 'S' || g == 's'){
            System.out.println("Enter your username");
            username = s.nextLine();
	    	System.out.println("Enter your password");
	    	char[] pass = console.readPassword();
	    	password=new String(pass);
            Connection con = null;
            PreparedStatement pst = null;
            String url = "jdbc:postgresql://localhost/postgres";
        	String user = "postgres";
        	String passwor = "asdf";
        	con = DriverManager.getConnection(url, user, passwor);
            pst = con.prepareStatement("SELECT password FROM login WHERE reg_no = ?");
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
            	if(password.equals(rs.getString(1))){
            	System.out.println("Do you wish to change your password ? (Y/N)");
				char t = s.nextLine().charAt(0);
				if(t == 'Y')
				update();
                Lib l = new Lib();
                l.show();
            }
             else{
            	System.out.println("Incorrect credentials");
            }
        }
            else{
            	System.out.println("Incorrect credentials");
            }
        }
        else{
        	System.out.println("Enter your username");
            username = s.nextLine();
	    	System.out.println("Enter your password");
	    	char[] pass = console.readPassword();
	    	password=new String(pass);
	    	if(username.equals("admin") && password.equals("pass")){
	    		System.out.println("Do you wish to view the borrowers list ? (Y/N)");
	    		char ch = s.nextLine().charAt(0);
	    		if(ch == 'Y' || ch == 'y'){
	    			Lib l = new Lib();
	    			l.show_b();
	    		}
	    		System.out.println("Do you wish to view the book copies list ? (Y/N)");
	    		ch = s.nextLine().charAt(0);
	    		if(ch == 'Y' || ch == 'y'){
	    			Lib l = new Lib();
	    			l.copies();
	    		}
	    	}
        }
	}
}
   public class Lib {
  	final static String ANSI_CLS = "\u001b[2J";
    final static String ANSI_HOME = "\u001b[H";       
    public static void main(String[] args) throws Exception {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String password = "asdf";
        con = DriverManager.getConnection(url, user, password);
        System.out.print(ANSI_HOME+ANSI_CLS);
        System.out.println("\t\t\t\t\t\t\t  Welcome to Library Management System");
			System.out.println("\n\nDo you have an account ? (Y/N)");
			char ch;
			Scanner s = new Scanner(System.in);
			ch = s.nextLine().charAt(0);
			if(ch == 'Y' || ch == 'y'){
				Person p = new Person();
				p.login();
			}
			else{
				Person p = new Person();
				p.register();
			}
	}
	public void show_b() throws SQLException,IOException {
        	Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String password = "asdf";
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement("SELECT * FROM borrowers");
            rs = pst.executeQuery();
             while (rs.next()) {
                System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
        }
    public void copies() throws Exception {
        	Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String passwor = "asdf";
            con = DriverManager.getConnection(url, user, passwor);
            pst = con.prepareStatement("SELECT * FROM book_copies");
            rs = pst.executeQuery();
             while (rs.next()) {
                System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
            select();
        }
	public void show() throws SQLException, Exception{
        Scanner s = new Scanner(System.in);
        System.out.println("Do you wish to view the available books based on :\n1. Author Name  2. Publisher Name  3. Title  4. Branch wise");
        System.out.print("Your choice : ");
        int c = s.nextInt();
        if(c == 1){
        	Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String passwor = "asdf";
            con = DriverManager.getConnection(url, user, passwor);
            pst = con.prepareStatement("SELECT * FROM book_author");
            rs = pst.executeQuery();
             while (rs.next()) {
                System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
            select();
        }
        else if(c == 2){
            Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String passwor = "asdf";
            con = DriverManager.getConnection(url, user, passwor);
            pst = con.prepareStatement("SELECT book.book_id,title,publisher_name.name FROM book,publisher_name WHERE book.book_id = publisher_name.book_id");
            rs = pst.executeQuery();
            while (rs.next()) {
            	System.out.print(rs.getString(1)+"\t\t");
                System.out.print(rs.getString(2)+"\t\t");
                System.out.println(rs.getString(3));
            }	
            select();
        }
        else if(c == 3){
        	Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String passwor = "asdf";
            con = DriverManager.getConnection(url, user, passwor);
            pst = con.prepareStatement("SELECT book_id,title FROM book");
            rs = pst.executeQuery();
            while (rs.next()) {
            	System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
            select();
        }
        else{
        	System.out.print("Enter the required branch (CSE / ECE/ EEE/ MEC) : ");
            Scanner sy = new Scanner(System.in);
        	String d = sy.nextLine();
        	Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String passwor = "asdf";
            con = DriverManager.getConnection(url, user, passwor);
            if(d.equals("CSE")){
            pst = con.prepareStatement("SELECT book_id,title FROM book WHERE book_id like 'CSE%'");
            rs = pst.executeQuery();
             while (rs.next()) {
            	System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
            }else if(d.equals("ECE"))
            {pst = con.prepareStatement("SELECT book_id,title FROM book WHERE book_id like 'ECE%'");
            rs = pst.executeQuery();
             while (rs.next()) {
            	System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
            }else if(d.equals("MEC"))
            {pst = con.prepareStatement("SELECT book_id,title FROM book WHERE book_id like 'MEC%'");
            rs = pst.executeQuery();
             while (rs.next()) {
            	System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
            }else
            {pst = con.prepareStatement("SELECT * FROM book WHERE book_id like 'EEE%'");
            rs = pst.executeQuery();
             while (rs.next()) {
            	System.out.print(rs.getString(1)+"\t");
                System.out.println(rs.getString(2));
            }
            }
            
            select();
        }
	}
	public void select() throws IOException, SQLException, Exception{
		char ch,b='a';
		do{
		System.out.println("\nDo you wish to borrow any book ? (Y/N)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String id="",rno="";
		ch = br.readLine().charAt(0);
		if(ch == 'Y' || ch == 'y'){
		//	show();
			System.out.println("Enter the book id");
			id = br.readLine();
			verify(id);
		}
		else{
			System.out.println("Do you wish to return any book ? (Y/N)");
			b = br.readLine().charAt(0);
			if(b == 'Y' || b == 'y'){
				System.out.println("Enter the book id");
				id = br.readLine();
				System.out.println("Enter your Registration number : ");
				rno = br.readLine();
                check(id,rno);
			}
			else
				System.out.println("Thank you.\nHave a good day.");
		} 
		if(((ch=='N')||(ch=='n'))&&((b=='N')||(b=='n')))
			break;
	}while(true);
	}
    public void check(String id, String rno) throws Exception{
        Connection con = null;
        PreparedStatement pst = null;
        int c=0;
        ResultSet rs = null;
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String passwor = "asdf";

        con = DriverManager.getConnection(url, user, passwor);
        
        pst = con.prepareStatement("SELECT no_of_copies FROM book_copies where book_id = ?");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            if(rs.next()){
        	c = rs.getInt(1);
        	if(c == 1){
        		System.out.println("The book already exists in the Library.");
        		return;
        	}
        	c++;
        	pst = con.prepareStatement("update book_copies set no_of_copies = ? where book_id = ?");
        	pst.setInt(1,c);
        	pst.setString(2,id);
        	pst.executeUpdate();
        if(c >= 1){
        pst = con.prepareStatement("update book_loans set return = 'Y' where book_id = ?");
        pst.setString(1,id);
        pst.executeUpdate();
    	}
    	fine(rno);
        System.out.println("Book succssfully returned");
    }}
    public boolean fine(String rno) throws Exception{
    	Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String passwor = "asdf";
        con = DriverManager.getConnection(url, user, passwor);
        pst = con.prepareStatement("SELECT * FROM book_loans where reg_no = ?");
        pst.setString(1,rno);
        rs = pst.executeQuery();
        String pattern = "yyyy-MM-dd";
        java.util.Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        if(rs.next()){
        d = rs.getDate(4);
    }
    System.out.println(d);
        String due = sdf.format(d);
        String d1 = "2016-11-30";
        if(d1.compareTo(due) < 0){
        	System.out.println("Due date is "+due+".\nThank you for returning beforehand.");
                    return false;
        }
        else if(due.equals(d1)){
        	System.out.println("Due date is today.\nThank you returning the book.");
                               return true;
        }
        else{
        	System.out.println("Due date expired.\nPlease check your fine with the librarian.\nThank you.");
             return true;
        }
    }
    public void verify(String id) throws Exception{
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;int c=3;
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String password = "asdf";
        con = DriverManager.getConnection(url, user, password);
        pst = con.prepareStatement("SELECT return FROM book_loans where book_loans.book_id = ?");
        pst.setString(1,id);
        rs = pst.executeQuery();
        if(rs.next()){
        if((rs.getString(1).equals("Y"))||(rs.getString(1).equals("y"))){
        	pst = con.prepareStatement("SELECT no_of_copies FROM book_copies where book_copies.book_id = ?");
            pst.setString(1,id);
            rs = pst.executeQuery();
            if(rs.next())
            c = rs.getInt(1);
        	c--;
        	pst = con.prepareStatement("update book_copies set no_of_copies = ? where book_id = ?");
        	pst.setInt(1,c);
        	pst.setString(2,id);
        	pst.executeUpdate();
        if(c == 0){
        pst = con.prepareStatement("update book_loans set return = 'N' where book_id = ?");
        pst.setString(1,id);
        pst.executeUpdate();
    	}
    		  System.out.print(ANSI_HOME+ANSI_CLS);
            System.out.println("Thankyou for Borrowing Book ");
          

        }
        else{
        	System.out.print(ANSI_HOME+ANSI_CLS);
            System.out.println("Sorry, this book is currently unavailable.");
            
        }
    }
}
}