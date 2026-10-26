import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Demonstrates deleting data from a MySQL database using JDBC's
 * PreparedStatement with a WHERE condition.
 */
public class JDBCDeleteExample {

    // Step 2: Constants for JDBC URL, username and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password"; // change to your MySQL password

    public static void main(String[] args) {

        // Step 4: DELETE query with a placeholder
        String deleteSql = "DELETE FROM users WHERE id = ?";
        int userId = 2; // hardcoded user ID

        // Step 3 & 5: try-with-resources for connection and PreparedStatement
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setInt(1, userId);

            // Step 6: Execute and store the number of rows affected
            int rowsAffected = preparedStatement.executeUpdate();

            // Step 7: Display result
            if (rowsAffected > 0) {
                System.out.println("Row deleted successfully.");
            } else {
                System.out.println("No rows were deleted.");
            }

        } catch (SQLException e) {
            // Step 8: Handle SQLException
            System.err.println("Database error occurred!");
            System.err.println("Message   : " + e.getMessage());
            System.err.println("SQL State : " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
        }
        // Step 9: Resources are closed automatically
    }
}

/*
 * ---------------------------------------------------------------
 * DATABASE SETUP (run in MySQL before executing the program)
 * ---------------------------------------------------------------
 * USE mydatabase;
 *
 * -- If the table does not exist yet:
 * CREATE TABLE IF NOT EXISTS users (
 *     id INT PRIMARY KEY AUTO_INCREMENT,
 *     name VARCHAR(50) NOT NULL,
 *     email VARCHAR(100) NOT NULL
 * );
 *
 * INSERT INTO users (name, email) VALUES
 *     ('John Doe', 'john.doe@example.com'),
 *     ('Jane Doe', 'jane.doe@example.com'),
 *     ('Alice',    'alice@example.com');
 *
 * ---------------------------------------------------------------
 * COMPILE & RUN
 * ---------------------------------------------------------------
 * javac JDBCDeleteExample.java
 * java -cp ".;mysql-connector-java-8.0.33.jar" JDBCDeleteExample   (Windows)
 * java -cp ".:mysql-connector-java-8.0.33.jar" JDBCDeleteExample   (Linux/Mac)
 *
 * ---------------------------------------------------------------
 * EXPECTED OUTPUT
 * ---------------------------------------------------------------
 * First run (user with id = 2 exists):
 *     Row deleted successfully.
 *
 * Second run (row already deleted) or a non-existent id such as 99:
 *     No rows were deleted.
 *
 * ---------------------------------------------------------------
 * VERIFY IN MYSQL
 * ---------------------------------------------------------------
 * SELECT * FROM users;
 */
