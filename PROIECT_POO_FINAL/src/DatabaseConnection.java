import java.sql.*;

public class DatabaseConnection {
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/my_library",
                    "root",
                    "20denisa03");
    }
}
