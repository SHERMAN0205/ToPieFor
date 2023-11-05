<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Error Page</title>
    </head>
    <body>
        <h1>Opps...</h1>
        <p>Sorry, an error occurred.</p>
        <p>Here is the exception stack trace: </p>
        <pre><% exception.printStackTrace(response.getWriter());%></pre>
    </body>
</html>
