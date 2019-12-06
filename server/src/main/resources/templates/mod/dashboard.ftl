<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- dashboard.boards
  -- dashboard.boards - board.title
  -- dashboard.boards - board.uri
  -- config.boardAbbreviation
  -- config.boardPath
  -- config.fileIndex
  -->
<fieldset>
    <legend><@spring.message "dashboard.Boards"/></legend>

    <ul>
        <#list dashboard.boards as board>
        <li>
            <a href="?/${config.boardPath?replace("%{uri}", board.uri)}${config.fileIndex}">${config.boardAbbreviation?replace("%{uri}", board.uri)}</a>
            -
            ${board.title}
            {% if board.subtitle %}
            <small>&mdash;
                {% if config.allow_subtitle_html %}
                {{ board.subtitle }}
                {% else %}
                {{ board.subtitle|e }}
                {% endif %}
            </small>
            {% endif %}
            {% if mod|hasPermission(config.mod.manageboards) %}
            <a href="?/edit/{{ board.uri }}"><small>[{% trans 'edit' %}]</small></a>
            {% endif %}
            {% if mod|hasPermission(config.mod.edit_pages) %}
            <a href="?/edit_pages/{{ board.uri }}"><small>[{% trans 'pages' %}]</small></a>
            {% endif %}
        </li>
        </#list>

        {% if mod|hasPermission(config.mod.newboard) %}
        <li style="margin-top:15px"><a href="?/new-board"><strong>{% trans 'Create new board' %}</strong></a></li>
        {% endif %}
    </ul>
</fieldset>

<fieldset>
    <legend>{% trans 'Messages' %}</legend>
    <ul>
        {% if mod|hasPermission(config.mod.noticeboard) %}
        {% if noticeboard|count > 0 %}
        <li>
            {% trans 'Noticeboard' %}:
            <ul>
                {% for post in noticeboard %}
                <li>
                    <a href="?/noticeboard#{{ post.id }}">
                        {% if post.subject %}
                        {{ post.subject|e }}
                        {% else %}
                        <em>{% trans 'no subject' %}</em>
                        {% endif %}
                    </a>
                    <small class="unimportant">
                        &mdash; by
                        {% if post.username %}
                        {{ post.username|e }}
                        {% else %}
                        <em>deleted?</em>
                        {% endif %}
                        at
                        {{ post.time|date(config.post_date) }}
                    </small>
                </li>
                {% endfor %}
            </ul>
        </li>
        {% endif %}
        <li><a href="?/noticeboard">{% trans 'View all noticeboard entries' %}</a></li>
        {% endif %}
        <li><a href="?/edit_news">{% trans 'News' %}</a></li>
        <li>
            <a href="?/inbox">
                {% trans 'PM inbox' %}
                {% if unread_pms > 0 %}<strong>{%endif %}({{ unread_pms }} unread){% if unread_pms > 0 %}</strong>{%endif %}
            </a>
        </li>
    </ul>
</fieldset>

<fieldset>
    <legend>{% trans 'Administration' %}</legend>

    <ul>
        {% if mod|hasPermission(config.mod.reports) %}
        <li>
            {% if reports > 0 %}<strong>{% endif %}
                <a href="?/reports">{% trans 'Report queue' %} ({{ reports }})</a>
                {% if reports > 0 %}</strong>{% endif %}
        </li>
        {% endif %}
        {% if mod|hasPermission(config.mod.view_banlist) %}
        <li><a href="?/bans">{% trans 'Ban list' %}</a></li>
        {% endif %}
        {% if config.ban_appeals and mod|hasPermission(config.mod.view_ban_appeals) %}
        <li><a href="?/ban-appeals">{% trans 'Ban appeals' %}</a></li>
        {% endif %}
        {% if mod|hasPermission(config.mod.manageusers) %}
        <li><a href="?/users">{% trans 'Manage users' %}</a></li>
        {% elseif mod|hasPermission(config.mod.change_password) %}
        <li><a href="?/users/{{ mod.id }}">{% trans 'Change password' %}</a></li>
        {% endif %}
        {% if mod|hasPermission(config.mod.themes) %}
        <li><a href="?/themes">{% trans 'Manage themes' %}</a></li>
        {% endif %}
        {% if mod|hasPermission(config.mod.modlog) %}
        <li><a href="?/log">{% trans 'Moderation log' %}</a></li>
        {% endif %}
        {% if mod|hasPermission(config.mod.edit_pages) %}
        <li><a href="?/edit_pages">{% trans 'Global static pages' %}</a></li>
        {% endif %}
        {% if mod|hasPermission(config.mod.recent) %}
        <li><a href="?/recent/25">{% trans 'Recent posts' %}</a></li>
        {% endif %}
        {% if mod|hasPermission(config.mod.rebuild) %}
        <li><a href="?/rebuild">{% trans 'Rebuild' %}</a></li>
        {% endif %}
        {% if mod|hasPermission(config.mod.edit_config) %}
        <li><a href="?/config">{% trans 'Configuration' %}</a></li>
        {% endif %}

    </ul>
</fieldset>

{% if mod|hasPermission(config.mod.search) %}
<fieldset>
    <legend>{% trans 'Search' %}</legend>

    <ul>
        <li>
            {% include 'mod/search_form.html' %}
        </li>
    </ul>
</fieldset>
{%  endif %}

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
        {% if mod|hasPermission(config.mod.debug_sql) %}
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

