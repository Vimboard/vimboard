<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- login.error
  -->
<#if login.error??><h2 style="text-align:center">${login.error}</h2></#if>
<form action="" method="post">
    <table style="margin-top:25px;">
        <tr>
            <th>
                <@spring.message "mod.login.Username"/>
            </th>
            <td>
                <input type="text" name="username" size="20" maxlength="30" value="{{ username|e }}">
            </td>
        </tr>
        <tr>
            <th>
                {% trans 'Password' %}
            </th>
            <td>
                <input type="password" name="password" size="20" maxlength="30" value="">
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" name="login" value="{% trans %}Continue{% endtrans %}" />
            </td>
    </table>
</form>
