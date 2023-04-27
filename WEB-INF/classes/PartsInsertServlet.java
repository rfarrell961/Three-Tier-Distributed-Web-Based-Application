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
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.JOptionPane;

public class PartsInsertServlet extends HttpServlet{

    private PreparedStatement statement;
    private Connection connection;

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException  
    {
        String pnum = request.getParameter("pnum");
        String pname = request.getParameter("pname");
        String color = request.getParameter("color");
        int weight = Integer.parseInt(request.getParameter("weight"));
        String city = request.getParameter("city");

        String message = "";

        if (!getDBConnection())
        {
            return;
        }

        int res;

        try
        {
            statement = connection.prepareStatement("insert into parts values (?, ?, ?, ?, ?)");
            statement.setString(1, pnum);
            statement.setString(2, pname);
            statement.setString(3, color);
            statement.setInt(4, weight);
            statement.setString(5, city);

            res = statement.executeUpdate();
            message = "<p style=\"color: white;\">";
            message += "New parts record: (" + pnum + ", " + pname + ", " + color + ", " + weight + ", " + city + ")";
            message += " - successfully entered into database";
            message += "</p>";

        }
        catch(SQLException sqlException)
        {
            message = "<p style=\"color: white;\">" + sqlException + "</p>";
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("snum", pnum);
        session.setAttribute("sname", pname);
        session.setAttribute("weight", String.valueOf(weight));
        session.setAttribute("city1", city);
        session.setAttribute("color", color);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
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
            filein = new FileInputStream("/Library/Tomcat1017/webapps/Project 4/WEB-INF/lib/dataentryuser.properties");
            properties.load(filein);
            
            dataSource = new MysqlDataSource();
            dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD")); 	
            
            connection = dataSource.getConnection();
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
