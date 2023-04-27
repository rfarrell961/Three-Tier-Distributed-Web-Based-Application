<!DOCTYPE html">
<!-- errorpage.html -->

<%
    String message = (String) session.getAttribute("message");
    if (message == null) message = "";

    String snum = (String) session.getAttribute("snum");
    if (snum == null) snum = "";
    String sname = (String) session.getAttribute("sname");
    if (sname == null) sname = "";
    String status = (String) session.getAttribute("status");
    if (status == null) status = "";
    String city = (String) session.getAttribute("city");
    if (city == null) city = "";

    String pnum = (String) session.getAttribute("pnum");
    if (pnum == null) pnum = "";
    String pname = (String) session.getAttribute("pname");
    if (pname == null) pname = "";
    String color = (String) session.getAttribute("color");
    if (color == null) color = "";
    String weight = (String) session.getAttribute("weight");
    if (weight == null) weight = "";
    String city1 = (String) session.getAttribute("city1");
    if (city1 == null) city1 = "";

    String jnum = (String) session.getAttribute("jnum");
    if (jnum == null) jnum = "";
    String jname = (String) session.getAttribute("jname");
    if (jname == null) jname = "";
    String numworkers = (String) session.getAttribute("numworkers");
    if (numworkers == null) numworkers = "";
    String city2 = (String) session.getAttribute("city2");
    if (city2 == null) city2 = "";

    String snum1 = (String) session.getAttribute("snum1");
    if (snum1 == null) snum1 = "";
    String pnum1 = (String) session.getAttribute("pnum1");
    if (pnum1 == null) pnum1 = "";
    String jnum1 = (String) session.getAttribute("jnum1");
    if (jnum1 == null) jnum1 = "";
    String quantity = (String) session.getAttribute("quantity");
    if (quantity == null) quantity = "";
%>

<html lang="en">
    <head>
        <title>Project 4 Data Entry Home</title>
        <meta charset="utf-8"/>
        <style>
            body {
                background-color: black;
                /* text-align: center; */
            }

            h1 {
                color: maroon;
                text-align: center;
            }

            h2 {
                color: yellow;
                text-align: center;
            }

            p {
                color: white;
                text-align: center;
            }

            table {
                margin-bottom: 5px;
            }

            td {
                border: 1px solid white;
                color:white;
            }

            fieldset {
                display: flex;
                align-items: center;
                flex-direction: column;
                margin-top: 10px;
                
            }

            input {
                width: fit-content;
            }



        </style>
        <script type="text/javascript">
            function eraseAll()
            {
                document.getElementById("sname").value = "";
                document.getElementById("snum").value = "";
                document.getElementById("status").value = "";
                document.getElementById("city").value = "";
                document.getElementById("pnum").value = "";
                document.getElementById("pname").value = "";
                document.getElementById("color").value = "";
                document.getElementById("weight").value = "";
                document.getElementById("city1").value = "";
                document.getElementById("jnum").value = "";
                document.getElementById("jname").value = "";
                document.getElementById("numworkers").value = "";
                document.getElementById("city2").value = "";
                document.getElementById("snum1").value = "";
                document.getElementById("pnum1").value = "";
                document.getElementById("jnum1").value = "";
                document.getElementById("quantity").value = "";

                document.getElementById("results").innerHTML = "";
            }
        </script>
    </head>
    <body>
        <h1>Welcome to the Spring 2023 Project 4 Enterprise Database System</h1>
        <h2>Data Entry Application</h2>

        <div style="border: 2px solid white; padding: 5px;">
            <p>You are connected to the Project 4 Enterprise System database as a <span style="color:red;">data-entry-level</span> user. Please enter any valid SQL query or update command in the box below</p>
        </div>

        <form action="SuppliersInsert" method="post">
            <fieldset style="width:90%;">
                <legend style="color: white;">
                    Suppliers Record Insert
                </legend> <br>
                <table style="border: 1px solid white">
                    <tr>
                        <td>snum</td>
                        <td>sname</td>
                        <td>status</td>
                        <td>city</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="snum" id="snum" value="<%=snum%>"/></td>
                        <td><input type="text" name="sname" id="sname" value="<%=sname%>"/></td>
                        <td><input type="text" name="status" id="status" value="<%=status%>"/></td>
                        <td><input type="text" name="city" id="city" value="<%=city%>"/></td>
                    </tr>
                </table>
                <div>
                    <input type="submit" value="Enter Supplier Record Into Database"/>
                    <button type="button" onclick="javascript:eraseAll();">Clear Data and Results</button>
                </div>
            </fieldset>
        </form>

        <form action="PartsInsert" method="post">
            <fieldset style="width:90%;">
                <legend style="color: white;">
                    Parts Record Insert
                </legend> <br>
                <table style="border: 1px solid white">
                    <tr>
                        <td>pnum</td>
                        <td>pname</td>
                        <td>color</td>
                        <td>weight</td>
                        <td>city</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="pnum" id="pnum" value='<%=pnum%>'/></td>
                        <td><input type="text" name="pname" id="pname" value='<%=pname%>'/></td>
                        <td><input type="text" name="color" id="color" value='<%=color%>'/></td>
                        <td><input type="text" name="weight" id="weight" value="<%=weight%>"/></td>
                        <td><input type="text" name="city" id="city1" value="<%=city1%>"/></td>
                    </tr>
                </table>
                <div>
                    <input type="submit" value="Enter Parts Record Into Database"/>
                    <button type="button" onclick="javascript:eraseAll();">Clear Data and Results</button>
                </div>
            </fieldset>
        </form>

        <form action="JobsInsert" method="post">
            <fieldset style="width:90%;">
                <legend style="color: white;">
                    Jobs Record Insert
                </legend> <br>
                <table style="border: 1px solid white">
                    <tr>
                        <td>jnum</td>
                        <td>jname</td>
                        <td>numworkers</td>
                        <td>city</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="jnum" id="jnum" value="<%=jnum%>"/></td>
                        <td><input type="text" name="jname" id="jname" value="<%=jname%>"/></td>
                        <td><input type="text" name="numworkers" id="numworkers" value="<%=numworkers%>"/></td>
                        <td><input type="text" name="city" id="city2" value="<%=city2%>"/></td>
                    </tr>
                </table>
                <div>
                    <input type="submit" value="Enter Jobs Record Into Database"/>
                    <button type="button" onclick="javascript:eraseAll();">Clear Data and Results</button>
                </div>
            </fieldset>
        </form>

        <form action="ShipmentsInsert" method="post">
            <fieldset style="width:90%;">
                <legend style="color: white;">
                    Shipments Record Insert
                </legend> <br>
                <table style="border: 1px solid white">
                    <tr>
                        <td>snum</td>
                        <td>pnum</td>
                        <td>jnum</td>
                        <td>quantity</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="snum"id="snum1" value="<%=snum1%>"/></td>
                        <td><input type="text" name="pnum"id="pnum1" value="<%=pnum1%>"/></td>
                        <td><input type="text" name="jnum"id="jnum1" value="<%=jnum1%>"/></td>
                        <td><input type="text" name="quantity"id="quantity" value="<%=quantity%>"/></td>
                    </tr>
                </table>
                <div>
                    <input type="submit" value="Enter Shipments Record Into Database"/>
                    <button type="button" onclick="javascript:eraseAll();">Clear Data and Results</button>
                </div>
            </fieldset>
        </form>

        <p>Database Results:</p>
        <div id="results" style="display: flex; justify-content: center; background-color: red; border: 2px solid yellow">
            <%=message%>
        </div>
    </body>
</html>