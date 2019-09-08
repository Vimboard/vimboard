<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
    <meta charset="UTF-8">
    <!--    <meta name="viewport" content="width=device-width, initial-scale=1.0">-->
    <!--    <link rel="stylesheet" href="css/style.css">-->
</head>
<body>
<h2>Index</h2>


<h3>Test board config</h3>
<p>Log dir: ${boardConfig.log}</p>
<p>Www dir: ${boardConfig.www}</p>

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
