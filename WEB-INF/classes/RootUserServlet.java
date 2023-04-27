/* Name: Robert Farrell
Course: CNT 4714 – Spring 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 23, 2023
*/



import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.Scanner;
import java.util.Properties;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.JOptionPane;

public class RootUserServlet extends HttpServlet{

    private Statement statement;
    private Connection connection;

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException  
    {
        String sqlStatement = request.getParameter("sqlStatement");
        String message = "";

        if (!getDBConnection())
        {
            return;
        }

        ResultSet resultSet;
        ResultSetMetaData metaData;

        try
        {
            String [] ss = sqlStatement.split("\\W+", 0);
            if (ss.length > 0 && (ss[0].toLowerCase()).equals("select"))
            {
                resultSet = statement.executeQuery( sqlStatement );
                metaData = resultSet.getMetaData(); 
            }
            else
            {
                int res = 0;
                int res2 = 0;
                boolean trigger = false;

                res = statement.executeUpdate( sqlStatement );
                for (int j = 0; j < ss.length; j++)
                {
                    if ((ss[j].toLowerCase()).equals("shipments"))
                    {
                        trigger = true;
                        res2 = statement.executeUpdate("update suppliers set status = (status + 5) where snum in (select distinct snum from shipments where quantity >= 100);");
                        break;
                    }
                }

                message += "<p style=\"color: white;\">" + "Successful Update... " + res + " rows updated" + "</p>";
                if (trigger)
                {
                    message += "<p>Business Logic Detected! - Updating Supplier Status</p>";
                    message += "<p>Business Logic updated " + res2 + " supplier status marks</p>";
                }
                else
                {
                    message += "<p>Business Logic Not Detected!</p>";
                }
                
                HttpSession session = request.getSession();
                session.setAttribute("message", message);
                session.setAttribute("sqlStatement", sqlStatement);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
                dispatcher.forward(request, response);

                destroy();
                return;
            }
        }
        catch(SQLException sqlException)
        {
            message = "<p style=\"color: white;\">" + sqlException + "</p>";

            HttpSession session = request.getSession();
            session.setAttribute("message", message);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
            dispatcher.forward(request, response);

            destroy();
            return;
        }

        message += "<table>";
        try
        {
            message += "<tr style=\"background-color:red\">";
            for (int i = 1; i <= metaData.getColumnCount(); i++)
            {
                    message += "<td>";
                    message += metaData.getColumnName(i);
                    message += "</td>";
            }
            message += "</tr>";

            while (resultSet.next())
            {
                message += "<tr>";
                for (int i = 1; i <= metaData.getColumnCount(); i++)
                {
                    message += "<td>";
                    message += resultSet.getString(i);
                    message += "</td>";
                }
                message += "</tr>";
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
            destroy();
            return;
        }

        message += "</table>";

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("sqlStatement", sqlStatement);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
        dispatcher.forward(request, response);

        destroy();
        
    }

    private boolean getDBConnection()
    {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        try 
        {
            filein = new FileInputStream("/Library/Tomcat1017/webapps/Project 4/WEB-INF/lib/root.properties");
            properties.load(filein);
            
            dataSource = new MysqlDataSource();
            dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD")); 	
            
            connection = dataSource.getConnection();
            statement = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
        }
        catch ( SQLException sqlException ) 
        {
            JOptionPane.showMessageDialog(null, sqlException, "ERROR", JOptionPane.ERROR_MESSAGE);
            destroy();
            return false;
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: IOException", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }  

        return true;
    }  

    public void destroy()
    {
        try
        {
            statement.close();
            connection.close();
        }
        catch (SQLException sqlException)
        {

        }
    }
}