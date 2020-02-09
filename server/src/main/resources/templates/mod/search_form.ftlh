<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- searchForm.searchQuery
  -- searchForm.searchType
  -->
<form style="display:inline" action="?/search" method="post">
    <label style="display:inline" for="search"><@spring.message "searchForm.Phrase"/>:</label>
    <input id="search" name="query" type="text" size="60" value="${(searchForm.searchQuery)!""}">
    <select name="type">
        <option value="posts"<#if (searchForm.searchType)!"" = "posts"> selected</#if>><@spring.message "searchForm.Posts"/></option>
        {% if mod|hasPermission(config.mod.view_notes) and mod|hasPermission(config.mod.show_ip) %}
            <option value="IP_notes"<#if (searchForm.searchType)!"" = "IP_notes"> selected</#if>><@spring.message "searchForm.IP_address_notes"/></option>
        {%  endif %}
        {% if mod|hasPermission(config.mod.view_banlist) %}
            <option value="bans"<#if (searchForm.searchType)!"" = "bans"> selected</#if>><@spring.message "searchForm.Bans"/></option>
        {%  endif %}
        {% if mod|hasPermission(config.mod.modlog) %}
            <option value="log"<#if (searchForm.searchType)!"" = "log"> selected</#if>><@spring.message "searchForm.Moderation_log"/></option>
        {%  endif %}
    </select>
    <input type="submit" value="<@spring.message "searchForm.Search"/>">
</form>
<p class="unimportant"><@spring.message "searchForm.Search_help"/></p>