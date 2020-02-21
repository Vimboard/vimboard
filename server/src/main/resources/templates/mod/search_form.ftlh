<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- mod.hasPermission.modlog
  -- mod.hasPermission.showIp
  -- mod.hasPermission.viewBanlist
  -- mod.hasPermission.viewNotes
  -- searchForm.searchQuery
  -- searchForm.searchType
  -->
<form style="display:inline" action="?/search" method="post">
    <label style="display:inline" for="search"><@spring.message "mod.searchForm.Phrase"/>:</label>
    <input id="search" name="query" type="text" size="60" value="${(searchForm.searchQuery)!""}">
    <select name="type">
        <option value="posts"<#if (searchForm.searchType)!"" = "posts"> selected</#if>><@spring.message "mod.searchForm.Posts"/></option>
        <#if (mod.hasPermission.viewNotes)!false && (mod.hasPermission.showIp)!false>
            <option value="IP_notes"<#if (searchForm.searchType)!"" = "IP_notes"> selected</#if>><@spring.message "mod.searchForm.IP_address_notes"/></option>
        </#if>
        <#if (mod.hasPermission.viewBanlist)!false>
            <option value="bans"<#if (searchForm.searchType)!"" = "bans"> selected</#if>><@spring.message "mod.searchForm.Bans"/></option>
        </#if>
        <#if (mod.hasPermission.modlog)!false>
            <option value="log"<#if (searchForm.searchType)!"" = "log"> selected</#if>><@spring.message "mod.searchForm.Moderation_log"/></option>
        </#if>
    </select>
    <input type="submit" value="<@spring.message "mod.searchForm.Search"/>">
</form>
<p class="unimportant"><@spring.message "mod.searchForm.Search_help"/></p>