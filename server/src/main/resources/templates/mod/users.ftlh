<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- config.boardAbbreviation
  -- mod.id
  -- mod.hasPermission.modlog
  -- mod.hasPermission.promoteusers
  -- users.list - action
  -- users.list - boards
  -- users.list - canBeDemoted
  -- users.list - canBePromoted
  -- users.list - demoteToken
  -- users.list - id
  -- users.list - last
  -- users.list - promoteToken
  -- users.list - type
  -- users.list - username
  -->
<table class="modlog" style="width:auto">
    <tr>
        <th><@spring.message "mod.users.ID"/></th>
        <th><@spring.message "mod.users.Username"/></th>
        <th><@spring.message "mod.users.Type"/></th>
        <th><@spring.message "mod.users.Boards"/></th>
        <#if (mod.hasPermission.modlog)!false>
            <th><@spring.message "mod.users.Last_action"/></th>
        </#if>
        <th>&hellip;</th>
    </tr>

    <#list users.list as user>
        <tr>
            <td><small>${user.id}</small></td>
            <td>${user.username}</td>
            <td>
                <#switch user.type>
                    <#case "JANITOR">
                        <@spring.message "mod.users.Janitor"/>
                        <#break>
                    <#case "MOD">
                        <@spring.message "mod.users.Mod"/>
                        <#break>
                    <#case "ADMIN">
                        <@spring.message "mod.users.Admin"/>
                        <#break>
                    <#default>
                        <em><@spring.message "mod.users.Unknown"/></em> (${user.type})
                </#switch>
            </td>
            <td>
                <#if user.boards?size == 0>
                    <em><@spring.message "mod.users.none"/></em>
                <#elseif user.boards?size == 1 && user.boards[0] == "*">
                    <em><@spring.message "mod.users.all_boards"/></em>
                <#else>
                    <#-- This is really messy, but IMO it beats doing it in Java. -->
                    <#assign boards = []>
                    <#list user.boards?sort as board>
                        <#if board == "*">
                            <#assign boards = boards + [ board ]>
                        <#else>
                            <#assign boards = boards + [ config.boardAbbreviation?replace("{uri}", board) ]>
                        </#if>
                    </#list>
                    ${boards?join(", ")}
                </#if>
            </td>
            <#if (mod.hasPermission.modlog)!false>
                <td>
                    <#if user.last??>
                        <span title="${user.action}">${user.last}</span> <#-- TODO last|ago -->
                    <#else>
                        <em><@spring.message "mod.users.never"/></em>
                    </#if>
                </td>
            </#if>
            <td>
                <#if ((mod.hasPermission.promoteusers)!false) && user.canBePromoted>
                    <a style="float:left;text-decoration:none" href="?/users/${user.id}/promote/${user.promoteToken}" title="<@spring.message "mod.users.Promote"/>">&#9650;</a>
                </#if>
                <#if ((mod.hasPermission.promoteusers)!false) && user.canBeDemoted>
                    <a style="float:left;text-decoration:none" href="?/users/${user.id}/demote/${user.demoteToken}" title="<@spring.message "mod.users.Demote"/>"<#if mod.id == user.id> onclick="return confirm('<@spring.message "mod.users.Are_you_sure_you_want_to_demote_yourself_"/>')"</#if>>&#9660;</a>
                </#if>
                <#if (mod.hasPermission.modlog)!false>
                    <a class="unimportant" style="margin-left:5px;float:right" href="?/log:${user.username}">[<@spring.message "mod.users.log"/>]</a>
                </#if>
                {% if mod|hasPermission(config.mod.editusers) or (mod|hasPermission(config.mod.change_password) and mod.id == user.id) %}
                    <a class="unimportant" style="margin-left:5px;float:right" href="?/users/{{ user.id }}">[{% trans 'edit' %}]</a>
                {% endif %}
                {% if mod|hasPermission(config.mod.create_pm) %}
                    <a class="unimportant" style="margin-left:5px;float:right" href="?/new_PM/{{ user.username|e }}">[{% trans 'PM' %}]</a>
                {% endif %}
            </td>
        </tr>
    </#list>
</table>

{% if mod|hasPermission(config.mod.createusers) %}
    <p style="text-align:center">
        <a href="?/users/new">Create a new user</a>
    </p>
{% endif %}

