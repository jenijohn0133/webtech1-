import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToolsDatabaseServlet extends HttpServlet {

    public ToolsDatabaseServlet() {
    }

    // Handles GET requests to fetch and display data from the 'tools' table
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Connection connection = null;
        Statement statement = null;
        PrintWriter out = response.getWriter();

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");


            // Connect to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/theme_art_tools", "root", "");
            if (connection != null) {
                out.println("<h1> Connection Successful</h1>");
            }

            // Create a statement
            statement = connection.createStatement();
            String query = "SELECT * FROM tools"; // Query to get all tools data
            ResultSet resultSet = statement.executeQuery(query);

            // HTML response
            out.println("<html><body>");
            out.println("<h2>Art Tools List</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Tool ID</th><th>Tool Name</th><th>Power Requirement (W)</th></tr>");

            // Loop through the result set and display the data in a table
            while (resultSet.next()) {
                String toolID = resultSet.getString("ToolID");
                String toolName = resultSet.getString("ToolName");
                int powerRequirement = resultSet.getInt("PowerRequirement");

                out.println("<tr>");
                out.println("<td>" + toolID + "</td>");
                out.println("<td>" + toolName + "</td>");
                out.println("<td>" + powerRequirement + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            out.print("<p>Error connecting to the database: " + e.toString() + "</p>");
        } finally {
            if (out != null) out.close();
        }
    }
}
