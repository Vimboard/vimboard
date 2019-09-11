<!DOCTYPE html>
<html>
<head>
    <title>City</title>
    <meta charset="UTF-8">
    <!--    <meta name="viewport" content="width=device-width, initial-scale=1.0">-->
    <!--    <link rel="stylesheet" href="css/style.css">-->
</head>
<body>
<h2>City</h2>


<h3>Test board config</h3>
<p>
    Board 1:
    <#if board1??>
        <#if board1.title?has_content>${board1.title}<#else>title = null</#if>
    <#else>
        board = null
    </#if>
    <br>
    Board 1a: <#if (board1.title)?has_content>${board1.title}<#else>title = null</#if>
</p>

<p>
    Board 2:
    <#if board2??>
        <#if board2.title?has_content>${board2.title}<#else>title = null</#if>
    <#else>
        board = null
    </#if>
    <br>
    Board 2a: <#if (board2.title)?has_content>${board2.title}<#else>title = null</#if>
</p>

<p>
    Board 3:
    <#if board3??>
        <#if board3.title?has_content>${board3.title}<#else>title = null</#if>
    <#else>
        board = null
    </#if>
    <br>
    Board 3a: <#if (board3.title)?has_content>${board3.title}<#else>title = null</#if>
</p>


<h3>Test freemarker</h3>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Population</th>
    </tr>

    <#list cities as city>
        <tr>
            <td>${city.id}</td>
            <td>${city.name}</td>
            <td>${city.population}</td>
        </tr>
    </#list>
</table>

</body>
</html>
