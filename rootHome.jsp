<!DOCTYPE html">
<!-- errorpage.jsp -->

<%
    String message = (String) session.getAttribute("message");
    if (message == null) message = "";
    String sqlStatement = (String) session.getAttribute("sqlStatement");
    if (sqlStatement == null) sqlStatement = "";
%>
<html lang="en">
    <head>
        <title>Project 4 Root Home</title>
        <meta charset="utf-8"/>
        <style>
            body {
                background-color: black;
                text-align: center;
                align-items: center;
            }

            h1 {
                color: maroon;
            }

            h2 {
                color: yellow;
            }

            p {
                color: white;
            }

            textarea {
                width: 90%;
                height: 65%;
                margin-bottom: 10px;
                resize: none;
            }

            tr:nth-child(odd) { background-color: lightgray;}
            tr:nth-child(even) { background-color: white;}


        </style>

        <script type="text/javascript">
            function eraseText()
            {
                document.getElementById("inputField").innerHTML = "";
            }
        </script>
        <script type="text/javascript">
            function eraseData()
            {
                document.getElementById("results").innerHTML = "";
            }
        </script>
    </head>
    </head>
    <body>
        <h1>Welcome to the Spring 2023 Project 4 Enterprise Database System</h1>
        <h2>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h2>
        <div style="border: 2px solid white; height: 50%; padding: 5px">
            <p>You are connected to the Project 4 Enterprise System database as a <span style="color:red;">root-level</span> user. Please enter any valid SQL query or update command in the box below</p>
            <form action="/Project 4/RootUser" method="post">
                <textarea name="sqlStatement" id="inputField"><%=sqlStatement%></textarea>
                <div>
                    <input type="submit" value="Execute Command"/>
                    <button type="button" onclick="javascript:eraseText();">Reset Form</button>
                    <button type="button" onclick="javascript:eraseData();">Clear Results</button>
                </div>
            </form>
        </div>
        <p>Database Results:</p>
        <div style="display: flex; justify-content: center; flex-direction: column; background-color: red; border: 2px solid yellow;" id="results">
            <%=message%>
        </div>
    </body>
</html>