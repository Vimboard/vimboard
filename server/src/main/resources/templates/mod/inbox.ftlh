<#ftl output_format="HTML">
<#if inbox.messages?size == 0>
    <p style="text-align:center" class="unimportant">(<@spring.message "mod.inbox.No_private_messages_for_you_"/>)</p>
<#else>
    <table class="modlog">
        <tr>
            <th><@spring.message "mod.inbox.ID"/></th>
            <th><@spring.message "mod.inbox.From"/></th>
            <th><@spring.message "mod.inbox.Date"/></th>
            <th><@spring.message "mod.inbox.Message_snippet"/></th>
        </tr>
        <#list inbox.messages as message>
            <tr<#if message.unread> style="font-weight:bold"</#if>>
                <td class="minimal">
                    <a href="?/PM/${message.id}">
                        ${message.id}
                    </a>
                </td>
                <td class="minimal">
                    <#if message.username?has_content>
                        <a href="?/new_PM/${message.username}">${message.username}</a>
                    <#else>
                        <em><@spring.message "mod.inbox.deleted_"/></em>
                    </#if>
                </td>
                <td class="minimal">
                    ${message.time?string[config.postDate]}
                </td>
                <td>
                    <a href="?/PM/${message.id}">
                        ${message.snippet?no_esc}
                    </a>
                </td>
            </tr>
        </#list>
    </table>
</#if>
