<#ftl output_format="HTML">
<#if userPage.new>
    <#assign action = "?/users/new">
<#else>
    <#assign action = "?/users/" + userPage.user.id>
</#if>

<form action="${action}" method="post">
    <input type="hidden" name="token" value="${userPage.token}">
    <table>
        <tr>
            <th><@spring.message "mod.user.Username"/></th>
            <td>
                <#if userPage.new || (mod.hasPermission.editusers)!false>
                    <input size="20" maxlength="30" type="text" name="username" value="${(userPage.user.username)!""}" autocomplete="off">
                <#else>
                    ${userPage.user.username}
                </#if>
            </td>
        </tr>
        <tr>
            <th><@spring.message "mod.user.Password"/><#if !userPage.new> <small style="font-weight:normal">(<@spring.message "mod.user.new__optional"/>)</small></#if></th>
            <td>
                <#if userPage.new || ((mod.hasPermission.editusers)!false || ((mod.hasPermission.changePassword)!false && userPage.user.id == mod.id))>
                    <input size="20" maxlength="30" type="password" name="password" value="" autocomplete="off">
                <#else>
                    -
                </#if>
            </td>
        </tr>
        <#if userPage.new>
            <tr>
                <th><@spring.message "mod.user.Group"/></th>
                <td>
                    <ul style="padding:5px 8px;list-style:none">
                        <#list userPage.groups as group>
                            <#if group != "DISABLED">
                                <li>
                                    <input type="radio" name="type" id="group_${group}" value="${group}">
                                    <label for="group_${group}">
                                        <#switch group>
                                            <#case "JANITOR">
                                                <@spring.message "group.Janitor"/>
                                                <#break>
                                            <#case "MOD">
                                                <@spring.message "group.Mod"/>
                                                <#break>
                                            <#case "ADMIN">
                                                <@spring.message "group.Admin"/>
                                                <#break>
                                            <#default>
                                                <em><@spring.message "group.Unknown"/></em> (${group})
                                        </#switch>
                                    </label>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </td>
            </tr>
        </#if>
        <tr>
            <th><@spring.message "mod.user.Boards"/></th>
            <td>
                <ul style="padding:0 5px;list-style:none">
                    <li>
                        <input type="checkbox" id="allboards" name="allboards"
                        <#if (userPage.user.boards?seq_contains("*"))!false> checked</#if>
                        <#if !(mod.hasPermission.editusers)!false> disabled</#if>
                        >
                        <label for="allboards">"*" - <@spring.message "mod.user.All_boards"/></label>
                    </li>
                    <#list userPage.boards as board>
                        <li>
                            <input type="checkbox" id="board_${board.uri}" name="board_${board.uri}"
                                <#if (userPage.user.boards?seq_contains(board.uri))!false> checked</#if>
                                <#if !(mod.hasPermission.editusers)!false> disabled</#if>
                                >
                            <label for="board_${board.uri}">
                                ${config.boardAbbreviation?replace("{uri}", board.uri)}
                                 -
                                ${board.title}
                            </label>
                        </li>
                    </#list>
                </ul>
            </td>
        </tr>
    </table>

    <ul style="padding:0;text-align:center;list-style:none">
        <#if userPage.new>
            <li><input type="submit" value="<@spring.message "mod.user.Create_user"/>"></li>
        <#else>
            <li><input type="submit" value="<@spring.message "mod.user.Save_changes"/>"></li>
            <#if (mod.hasPermission.deleteusers)!false>
                <li><input name="delete" onclick="return confirm('<@spring.message "mod.user.Are_you_sure_you_want_to_permanently_delete_this_user_"/>');" type="submit" value="<@spring.message "mod.user.Delete_user"/>"></li>
            </#if>
        </#if>
    </ul>
</form>

<#if userPage.logs?? && userPage.logs?size gt 0>
    <table class="modlog" style="width:600px">
        <tr>
            <th><@spring.message "mod.user.IP_address"/></th>
            <th><@spring.message "mod.user.Time"/></th>
            <th><@spring.message "mod.user.Board"/></th>
            <th><@spring.message "mod.user.Action"/></th>
        </tr>
        <#list userPage.logs as log>
            <tr>
                <td class="minimal">
                    <a href="?/IP/${log.ip}">${log.ip}</a>
                </td>
                <td class="minimal">
                    <span title="${log.time?string[config.postDate]}">${log.last}</span>
                </td>
                <td class="minimal">
                    <#if log.board??>
                        <a href="?/${config.boardPath?replace("{uri}", log.board)}${config.fileIndex}">${config.boardAbbreviation?replace("{uri}", log.board)}</a>
                    <#else>
                        -
                    </#if>
                </td>
                <td>
                    ${log.text}
                </td>
            </tr>
        </#list>
    </table>
    <p style="text-align:center" class="unimportant">
        <a href="?/log:${userPage.user.username}"><@spring.message "mod.user.View_more_logs_for_this_user_"/></a>
    </p>
</#if>

