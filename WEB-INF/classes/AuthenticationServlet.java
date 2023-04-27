/* Name: Robert Farrell
Course: CNT 4714 – Spring 2023 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: April 23, 2023
*/

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class AuthenticationServlet extends HttpServlet{
    @Override
   protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException  
   {
        boolean userCredentialsOK = false;
        String user = "";

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        File credentialsFile = new File("/Library/Tomcat1017/webapps/Project 4/WEB-INF/lib/credentials.txt");

        try
        {
            Scanner scanner = new Scanner(credentialsFile);
            String line = "";

            while (scanner.hasNextLine() && !userCredentialsOK)
            {
                line = scanner.nextLine();
                String[] creds = line.split(",");

                if (username.equals(creds[0].trim()) && password.equals(creds[1].trim()))
                {
                    userCredentialsOK = true;
                    user = creds[0].trim();
                }
            }
            scanner.close();
        }
        catch(FileNotFoundException fileNotFoundException)
        {
            JOptionPane.showMessageDialog(null, "Error: file not found", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        if (userCredentialsOK)
        {
            if (user.equals("root"))
            {
                response.sendRedirect("http://localhost:8082/Project 4/rootHome.jsp");
            }
            else if (user.equals("client"))
            {
                response.sendRedirect("http://localhost:8082/Project 4/clientHome.jsp");
            }
            else if (user.equals("dataentryuser"))
            {
                response.sendRedirect("http://localhost:8082/Project 4/dataentryHome.jsp");
            }
            else
            {
                response.sendRedirect("http://localhost:8082/Project 4/errorpage.html");
            }  
        }
        else
        {
            response.sendRedirect("http://localhost:8082/Project 4/errorpage.html");
        }
   }
}
