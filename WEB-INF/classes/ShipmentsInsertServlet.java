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

import javax.lang.model.util.ElementScanner14;
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

public class ShipmentsInsertServlet extends HttpServlet{

    private PreparedStatement statement;
    private PreparedStatement blStatement;
    private Connection connection;

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException  
    {
        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String jnum = request.getParameter("jnum");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String message = "";

        if (!getDBConnection())
        {
            return;
        }

        int res = 0;
        int res2 = 0;

        try
        {
            statement = connection.prepareStatement("insert into shipments values (?, ?, ?, ?)");
            statement.setString(1, snum);
            statement.setString(2, pnum);
            statement.setString(3, jnum);
            statement.setInt(4, quantity);

            res = statement.executeUpdate();
            message = "<p style=\"color: white;\">";
            message += "New shipments record: (" + snum + ", " + pnum + ", " + jnum + ", " + quantity + ")";
            message += " - successfully entered into database";
            

            blStatement = connection.prepareStatement("update suppliers set status = status + 5 where snum in (select distinct snum from shipments where quantity >= 100)");

            // Business Logic
            if (res != 0 && quantity >= 100)
            {
                res2 = blStatement.executeUpdate();
                if (res2 != 0) {
                    message += ". Business logic triggered";
                }
            }
            else if (res != 0 && quantity < 100)
            {
                message += ". Business logic not triggered";
            }
            message += "</p>";
        }
        catch(SQLException sqlException)
        {
            message = "<p style=\"color: white;\">" + sqlException + "</p>";
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("snum1", snum);
        session.setAttribute("pnum1", pnum);
        session.setAttribute("quantity", String.valueOf(quantity));
        session.setAttribute("jnum1", jnum);
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

