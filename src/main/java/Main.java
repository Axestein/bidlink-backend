import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventify_db", "root", "W7301@jqir#");
            System.out.println("Connection successful: " + con);
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
