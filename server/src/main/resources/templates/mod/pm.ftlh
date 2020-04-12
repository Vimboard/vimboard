<#ftl output_format="HTML">
<form action="" method="post">
    <table>
        <tr>
            <th><@spring.message "mod.pm.From"/></th>
            <#if pm.username?has_content>
                <td><a href="?/new_PM/${pm.username}">${pm.username}</a></td>
            <#else>
                <td><em><@spring.message "mod.pm.deleted_"/></em></td>
            </#if>
        </tr>
        <#if pm.to != mod.id>
            <tr>
                <th><@spring.message "mod.pm.To"/></th>
                <#if pm.toUsername>
                    <td><a href="?/new_PM/${pm.toUsername}">${pm.toUsername}</a></td>
                <#else>
                    <td><em><@spring.message "mod.pm.deleted_"/></em></td>
                </#if>
            </tr>
        </#if>
        <tr>
            <th><@spring.message "mod.pm.Date"/></th>
            <td>${pm.time?string[config.postDate]} <small>(${pm.last})</small></td>

        </tr>
        <tr>
            <th><@spring.message "mod.pm.Message"/></th>
            <td>${pm.message?no_esc}</td>
        </tr>
    </table>

    <ul style="list-style:none;text-align:center;padding:0">
        <li style="padding:5px 0">
            <input type="submit" name="delete" value="<@spring.message "mod.pm.Delete_forever"/>">
        </li>
        <#if (mod.hasPermission.createPm)!false>
            <li style="padding:5px 0">
                <a href="?/PM/${pm.id}/reply">
                    <@spring.message "mod.pm.Reply_with_quote"/>
                </a>
            </li>
        </#if>
    </ul>
</form>
