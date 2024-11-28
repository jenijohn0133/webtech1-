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

public class ToolsDatabaseServlet2 extends HttpServlet {

    public ToolsDatabaseServlet2() {
    }

    // This method handles GET requests to retrieve art tool data based on the tool name
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String toolName = request.getParameter("tool");  // Getting tool name from the request

        if (toolName == null || toolName.isEmpty() || toolName.equals("empty")) {
            out.println("<p>Error: Tool name is required</p>");
            return;
        }

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");


            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/theme_art_tools", "root", "");

            // SQL query to fetch tools with the given tool name
            String query = "SELECT * FROM tools WHERE ToolName = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, toolName);  // Set the tool name in the query

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if any tools are found
            if (!resultSet.next()) {
                out.println("<p>No tools found for the name: " + toolName + "</p>");
            } else {
                out.println("<html><body>");
                out.println("<h2>Art Tool Details for: " + toolName + "</h2>");
                out.println("<table border='1'>");
                out.println("<tr><th>Tool ID</th><th>Tool Name</th><th>Power Requirement (W)</th></tr>");

                // Iterate over the results and display tool details
                do {
                    String toolID = resultSet.getString("ToolID");
                    String toolNameResult = resultSet.getString("ToolName");
                    int powerRequirement = resultSet.getInt("PowerRequirement");

                    out.println("<tr>");
                    out.println("<td>" + toolID + "</td>");
                    out.println("<td>" + toolNameResult + "</td>");
                    out.println("<td>" + powerRequirement + "</td>");
                    out.println("</tr>");

                } while (resultSet.next());

                out.println("</table>");
                out.println("</body></html>");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            out.println("<p>Error connecting to the database: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}
