<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- request.cookies
  -- request.properties
  -->
<table class="modlog" style="width:auto">
    <caption><h2>Common properties</h2></caption>
    <tr>
        <th>Key</th>
        <th>Value</th>
    </tr>
    <#list request.properties as key, value>
        <tr>
            <td>${key}</td>
            <td>${value!""}</td>
        </tr>
    </#list>
</table>
<br>
<table class="modlog" style="width:auto">
    <caption><h2>Headers</h2></caption>
    <tr>
        <th>Key</th>
        <th>Value</th>
    </tr>
    <#list request.headers as key, value>
        <tr>
            <td>${key}</td>
            <td>${value!""}</td>
        </tr>
    </#list>
</table>
<br>
<table class="modlog" style="width:auto">
    <caption><h2>Parameters</h2></caption>
    <tr>
        <th>Key</th>
        <th>Value</th>
    </tr>
    <#list request.parameters as key, value>
        <tr>
            <td>${key}</td>
            <td>${value!""}</td>
        </tr>
    </#list>
</table>
<br>
<table class="modlog" style="width:auto">
    <caption><h2>Cookies</h2></caption>
    <#list request.cookies>
        <tr>
            <#list request.cookies?first as key, value>
                <th>${key}</th>
            </#list>
        </tr>
        <#items as cookie>
            <tr>
                <#list cookie as key, value>
                    <td>${value!""}</td>
                </#list>
            </tr>
        </#items>
    </#list>
</table>
