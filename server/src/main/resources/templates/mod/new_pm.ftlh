<#ftl output_format="HTML">
<form action="?/new_PM/${newPm.username}" method="post">
    <input type="hidden" name="token" value="${newPm.token}">
    <table>
        <tr>
            <th><@spring.message "mod.new_pm.To"/></th>
            <#if (mod.hasPermission.editusers)!false>
                <td><a href="?/users/${newPm.id}">${newPm.username}</a></td>
            <#else>
                <td>${newPm.username}</td>
            </#if>
        </tr>
        <tr>
            <th>Message</th>
            <td><textarea name="message" rows="10" cols="40">${newPm.message?no_esc}</textarea></td>
        </tr>
    </table>

    <p style="text-align:center"><input type="submit" value="<@spring.message "mod.new_pm.Send_message"/>"></p>
</form>
