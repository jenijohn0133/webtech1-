import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToolsDatabaseServlet3 extends HttpServlet {

    public ToolsDatabaseServlet3() {
    }

    // Handles POST requests to add a new art tool
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection connection = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/theme_art_tools", "root", "");

            // SQL statement for inserting a new tool into the tools table
            String insertQuery = "INSERT INTO tools (ToolID, ToolName, PowerRequirement) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Setting parameters from the request
            preparedStatement.setString(1, request.getParameter("toolid"));
            preparedStatement.setString(2, request.getParameter("toolname"));
            preparedStatement.setInt(3, Integer.parseInt(request.getParameter("powerrequirement")));

            // Execute the update
            preparedStatement.executeUpdate();
            out.println("<html><body><p>Database Updated: New tool added successfully!</p>");

            // Display all tools after the update
            displayAllTools(connection, out);

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.toString() + "</p>");
        }
    }

    // Handles GET requests to display existing art tools
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Connection connection = null;
        PrintWriter out = response.getWriter();

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");


            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/theme_art_tools", "root", "");

            // Display all tools
            displayAllTools(connection, out);

            connection.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.toString() + "</p>");
        }
    }

    // Helper method to display all art tools
    private void displayAllTools(Connection connection, PrintWriter out) throws Exception {
        String query = "SELECT * FROM tools";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        out.println("<html><body><h2>Existing Art Tools</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>Tool ID</th><th>Tool Name</th><th>Power Requirement (W)</th></tr>");

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
        preparedStatement.close();
    }
}
