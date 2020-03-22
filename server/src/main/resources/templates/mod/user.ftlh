<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- mod.hasPermission.changePassword
  -- mod.hasPermission.editusers
  -- mod.id
  -- userPage.isNew
  -- userPage.token
  -- userPage.user - id
  -- userPage.user - username
  -->
<#if userPage.isNew>
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
                <#if userPage.isNew || (mod.hasPermission.editusers)!false>
                    <input size="20" maxlength="30" type="text" name="username" value="${userPage.user.username}" autocomplete="off">
                <#else>
                    ${userPage.user.username}
                </#if>
            </td>
        </tr>
        <tr>
            <th><@spring.message "mod.user.Password"/><#if !userPage.isNew> <small style="font-weight:normal">(<@spring.message "mod.user.new__optional"/>)</small></#if></th>
            <td>
                <#if userPage.user.isNew || ((mod.hasPermission.editusers)!false || ((mod.hasPermission.changePassword)!false && userPage.user.id == mod.id))>
                    <input size="20" maxlength="30" type="password" name="password" value="" autocomplete="off">
                <#else>
                    -
                </#if>
            </td>
        </tr>
<#-- TODO: -->
        {% if new %}
            <tr>
                <th>{% trans 'Group' %}</th>
                <td>
                    <ul style="padding:5px 8px;list-style:none">
                        {% for group_value, group_name in config.mod.groups if group_name != 'Disabled' %}
                            <li>
                                <input type="radio" name="type" id="group_{{ group_name }}" value="{{ group_value }}">
                                <label for="group_{{ group_name }}">{% trans group_name %}</label>
                            </li>
                        {% endfor %}
                    </ul>
                </td>
            </tr>
        {% endif %}
        <tr>
            <th>{% trans 'Boards' %}</th>
            <td>
                <ul style="padding:0 5px;list-style:none">
                    <li>
                        <input type="checkbox" id="allboards" name="allboards"
                        {% if '*' in user.boards %} checked{% endif %}
                        {% if not mod|hasPermission(config.mod.editusers) %}
                         disabled
                        {% endif %}
                        >
                        <label for="allboards">"*" - {% trans 'All boards' %}</label>
                    </li>
                    {% for board in boards %}
                        <li>
                            <input type="checkbox" id="board_{{ board.uri }}" name="board_{{ board.uri }}"
                                {% if board.uri in user.boards %} checked{% endif %}
                                {% if not mod|hasPermission(config.mod.editusers) %}
                                 disabled
                                {% endif %}
                                >
                            <label for="board_{{ board.uri }}">
                                {{ config.board_abbreviation|sprintf(board.uri) }}
                                 -
                                {{ board.title|e }}
                            </label>
                        </li>
                    {% endfor %}
                </ul>
            </td>
        </tr>
    </table>

    <ul style="padding:0;text-align:center;list-style:none">
        {% if new %}
            <li><input type="submit" value="{% trans 'Create user' %}"></li>
        {% else %}
            <li><input type="submit" value="{% trans 'Save changes' %}"></li>
            {% if mod|hasPermission(config.mod.deleteusers) %}
                <li><input name="delete" onclick="return confirm('Are you sure you want to permanently delete this user?');" type="submit" value="{% trans 'Delete user' %}"></li>
            {% endif %}
        {% endif %}
    </ul>
</form>

{% if logs and logs|count > 0  %}
    <table class="modlog" style="width:600px">
        <tr>
            <th>{% trans 'IP address' %}</th>
            <th>{% trans 'Time' %}</th>
            <th>{% trans 'Board' %}</th>
            <th>{% trans 'Action' %}</th>
        </tr>
        {% for log in logs %}
            <tr>
                <td class="minimal">
                    <a href="?/IP/{{ log.ip }}">{{ log.ip }}</a>
                </td>
                <td class="minimal">
                    <span title="{{ log.time|date(config.post_date) }}">{{ log.time|ago }}</span>
                </td>
                <td class="minimal">
                    {% if log.board %}
                        <a href="?/{{ config.board_path|sprintf(log.board) }}{{ config.file_index }}">{{ config.board_abbreviation|sprintf(log.board) }}</a>
                    {% else %}
                        -
                    {% endif %}
                </td>
                <td>
                    {{ log.text }}
                </td>
            </tr>
        {% endfor %}
    </table>
    <p style="text-align:center" class="unimportant">
        <a href="?/log:{{ user.username|e }}">{% trans 'View more logs for this user.' %}</a>
    </p>
{% endif %}

