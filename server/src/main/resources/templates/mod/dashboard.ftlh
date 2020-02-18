<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- dashboard.boards
  -- dashboard.boards - board.title
  -- dashboard.boards - board.uri
  -- dashboard.logoutToken
  -- dashboard.newerRelease
  -- dashboard.newerRelease - massive
  -- dashboard.newerRelease - major
  -- dashboard.newerRelease - minor
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
  -- config.debug
  -- config.fileIndex
  -- config.mod.dashboardLinks
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

<#if ((config.mod.dashboardLinks?keys?size)!0) gt 0>
    <fieldset>
        <legend><@spring.message "dashboard.Other"/></legend>

        <ul>
            <#list config.mod.dashboardLinks as label, link>
                <li><a href="${link}">${label}</a></li>
            </#list>
        </ul>
    </fieldset>
</#if>

<#if config.debug>
    <fieldset>
        <legend><@spring.message "dashboard.Debug"/></legend>
        <ul>
            <li><a href="?/debug/antispam"><@spring.message "dashboard.Anti_spam"/></a></li>
            <li><a href="?/debug/recent"><@spring.message "dashboard.Recent_posts"/></a></li>
            <#if (mod.hasPermission.debugSql)!false>
                <li><a href="?/debug/sql"><@spring.message "dashboard.SQL"/></a></li>
            </#if>
        </ul>
    </fieldset>
</#if>

<#if dashboard.newerRelease??>
    <fieldset>
        <legend><@spring.message "dashboard.Update"/></legend>
        <ul>
            <li>
                <@spring.messageArgs "dashboard.A_newer_version_of_vichan_{version}_is_available",
                        ["<strong>v${dashboard.newerRelease.massive}.${dashboard.newerRelease.major}.${dashboard.newerRelease.minor}</strong>"]/>

                <@spring.messageArgs "dashboard.See_{link}_for_upgrade_instructions",
                        ["<a href=\"https://engine.vichan.net\">https://engine.vichan.net/</a>"]/>
            </li>
        </ul>
    </fieldset>
</#if>

<fieldset>
    <legend><@spring.message "dashboard.User_account"/></legend>

    <ul>
        <li><a href="?/logout/${dashboard.logoutToken}"><@spring.message "dashboard.Logout"/></a></li>
    </ul>
</fieldset>

