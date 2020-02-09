<#ftl output_format="HTML">
<#--
  -- Template attributes:

todo  noticeboard#${post.id}

  --
  -- dashboard.boards
  -- dashboard.boards - board.title
  -- dashboard.boards - board.uri
  -- dashboard.noticeboard
  -- dashboard.noticeboard - id
  -- dashboard.noticeboard - subject
  -- dashboard.noticeboard - time
  -- dashboard.noticeboard - username
  -- dashboard.reports
  -- dashboard.unreadPms
  -- config.allowSubtitleHtml
  -- config.banAppeals
  -- config.boardAbbreviation
  -- config.boardPath
  -- config.fileIndex
  -- config.postDate
  -- mod.hasPermission.changePassword
  -- mod.hasPermission.debugSql
  -- mod.hasPermission.editConfig
  -- mod.hasPermission.editPages
  -- mod.hasPermission.manageboards
  -- mod.hasPermission.manageusers
  -- mod.hasPermission.modlog
  -- mod.hasPermission.newboard
  -- mod.hasPermission.noticeboard
  -- mod.hasPermission.rebuild
  -- mod.hasPermission.recent
  -- mod.hasPermission.reports
  -- mod.hasPermission.search
  -- mod.hasPermission.themes
  -- mod.hasPermission.viewBanAppeals
  -- mod.hasPermission.viewBanlist
  -- mod.id
  -->
<fieldset>
    <legend><@spring.message "dashboard.Boards"/></legend>

    <ul>
        <#list dashboard.boards as board>
            <li>
                <a href="?/${config.boardPath?replace("{uri}", board.uri)}${config.fileIndex}">${config.boardAbbreviation?replace("{uri}", board.uri)}</a>
                    -
                ${board.title}
                <#if board.subtitle?has_content>
                    <small>&mdash;
                        <#if config.allowSubtitleHtml>
                            ${board.subtitle?no_esc}
                        <#else>
                            ${board.subtitle}
                        </#if>
                    </small>
                </#if>
                <#if (mod.hasPermission.manageboards)!false>
                    <a href="?/edit/${board.uri}"><small>[<@spring.message "dashboard.edit"/>]</small></a>
                </#if>
                <#if (mod.hasPermission.editPages)!false>
                    <a href="?/edit_pages/${board.uri}"><small>[<@spring.message "dashboard.pages"/>]</small></a>
                </#if>
            </li>
        </#list>

        <#if (mod.hasPermission.newboard)!false>
            <li style="margin-top:15px"><a href="?/new-board"><strong><@spring.message "dashboard.Create_new_board"/></strong></a></li>
        </#if>
    </ul>
</fieldset>

<fieldset>
    <legend><@spring.message "dashboard.Messages"/></legend>
    <ul>
        <#if (mod.hasPermission.noticeboard)!false>
            <#if dashboard.noticeboard?size gt 0>
                <li>
                    <@spring.message "dashboard.Noticeboard"/>:
                    <ul>
                        <#list dashboard.noticeboard as post>
                            <li>
                                <a href="?/noticeboard#${post.id}">
                                    <#if post.subject?has_content>
                                        ${post.subject}
                                    <#else>
                                        <em><@spring.message "dashboard.no_subject"/></em>
                                    </#if>
                                </a>
                                <small class="unimportant">
                                    &mdash; <@spring.message "dashboard.by"/>
                                    <#if post.username?has_content>
                                        ${post.username}
                                    <#else>
                                        <em><@spring.message "dashboard.deleted"/>?</em>
                                    </#if>
                                    <@spring.message "dashboard.at"/>
                                    ${post.time?string[config.postDate]}
                                </small>
                            </li>
                        </#list>
                    </ul>
                </li>
            </#if>
            <li><a href="?/noticeboard"><@spring.message "dashboard.View_all_noticeboard_entries"/></a></li>
        </#if>
        <li><a href="?/edit_news"><@spring.message "dashboard.News"/></a></li>
        <li>
            <a href="?/inbox">
                <@spring.message "dashboard.PM_inbox"/>
                <#if dashboard.unreadPms gt 0><strong></#if>(${dashboard.unreadPms} <@spring.message "dashboard.unread"/>)<#if dashboard.unreadPms gt 0></strong></#if>
            </a>
        </li>
    </ul>
</fieldset>

<fieldset>
    <legend><@spring.message "dashboard.Administration"/></legend>

    <ul>
        <#if (mod.hasPermission.reports)!false>
            <li>
                <#if dashboard.reports gt 0><strong></#if>
                    <a href="?/reports"><@spring.message "dashboard.Report_queue"/> (${dashboard.reports})</a>
                <#if dashboard.reports gt 0></strong></#if>
            </li>
        </#if>
        <#if (mod.hasPermission.viewBanlist)!false>
            <li><a href="?/bans"><@spring.message "dashboard.Ban_list"/></a></li>
        </#if>
        <#if config.banAppeals && (mod.hasPermission.viewBanAppeals)!false>
            <li><a href="?/ban-appeals"><@spring.message "dashboard.Ban_appeals"/></a></li>
        </#if>
        <#if (mod.hasPermission.manageusers)!false>
            <li><a href="?/users"><@spring.message "dashboard.Manage_users"/></a></li>
        <#elseif (mod.hasPermission.changePassword)!false>
            <li><a href="?/users/${mod.id}"><@spring.message "dashboard.Change_password"/></a></li>
        </#if>
        <#if (mod.hasPermission.themes)!false>
            <li><a href="?/themes"><@spring.message "dashboard.Manage_themes"/></a></li>
        </#if>
        <#if (mod.hasPermission.modlog)!false>
            <li><a href="?/log"><@spring.message "dashboard.Moderation_log"/></a></li>
        </#if>
        <#if (mod.hasPermission.editPages)!false>
            <li><a href="?/edit_pages"><@spring.message "dashboard.Global_static_pages"/></a></li>
        </#if>
        <#if (mod.hasPermission.recent)!false>
            <li><a href="?/recent/25"><@spring.message "dashboard.Recent_posts"/></a></li>
        </#if>
        <#if (mod.hasPermission.rebuild)!false>
            <li><a href="?/rebuild"><@spring.message "dashboard.Rebuild"/></a></li>
        </#if>
        <#if (mod.hasPermission.editConfig)!false>
            <li><a href="?/config"><@spring.message "dashboard.Configuration"/></a></li>
        </#if>

    </ul>
</fieldset>

<#if (mod.hasPermission.search)!false>
    <fieldset>
        <legend><@spring.message "dashboard.Search"/></legend>

        <ul>
            <li>
                <#include "search_form.ftlh">
            </li>
        </ul>
    </fieldset>
</#if>

{% if config.mod.dashboard_links and config.mod.dashboard_links|count %}
<fieldset>
    <legend>{% trans 'Other' %}</legend>

    <ul>
        {% for label,link in config.mod.dashboard_links %}
        <li><a href="{{ link }}">{{ label }}</a></li>
        {% endfor %}
    </ul>
</fieldset>
{% endif %}

{% if config.debug %}
<fieldset>
    <legend>{% trans 'Debug' %}</legend>
    <ul>
        <li><a href="?/debug/antispam">{% trans 'Anti-spam' %}</a></li>
        <li><a href="?/debug/recent">{% trans 'Recent posts' %}</a></li>
        {% if (mod.hasPermission.debugSql)!false %}
        <li><a href="?/debug/sql">{% trans 'SQL' %}</a></li>
        {% endif %}
    </ul>
</fieldset>
{% endif %}

{% if newer_release %}
<fieldset>
    <legend>Update</legend>
    <ul>
        <li>
            A newer version of vichan
            (<strong>v{{ newer_release.massive }}.{{ newer_release.major }}.{{ newer_release.minor }}</strong>) is available!
            See <a href="https://engine.vichan.net">https://engine.vichan.net/</a> for upgrade instructions.
        </li>
    </ul>
</fieldset>
{% endif %}

<fieldset>
    <legend>{% trans 'User account' %}</legend>

    <ul>
        <li><a href="?/logout/{{ logout_token }}">{% trans 'Logout' %}</a></li>
    </ul>
</fieldset>

