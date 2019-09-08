<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script type="text/javascript">
        var active_page = "page";
    </script>
    {% include 'header.html' %}
    <title>${title}</title>
</head>
<body class="8chan vichan {% if mod %}is-moderator{% else %}is-not-moderator{% endif %} active-page" data-stylesheet="{% if config.default_stylesheet.1 != '' %}{{ config.default_stylesheet.1 }}{% else %}default{% endif %}">
{{ boardlist.top }}

{% if pm %}<div class="top_notice">You have <a href="?/PM/{{ pm.id }}">an unread PM</a>{% if pm.waiting > 0 %}, plus {{ pm.waiting }} more waiting{% endif %}.</div><hr>{% endif %}
<header>
    <h1>{{ title }}</h1>
    <div class="subtitle">
        <#if subtitle?has_content>${subtitle}</#if>
        {% if mod and not hide_dashboard_link %}<p><a href="?/">{% trans %}Return to dashboard{% endtrans %}</a></p>{% endif %}
    </div>
</header>
{{ body }}
<hr>
<footer>
    <p class="unimportant" style="margin-top:20px;text-align:center;">- Tinyboard +
        <a href="https://engine.vichan.net/">vichan</a> ${config.version} -
        <br>Tinyboard Copyright &copy; 2010-2014 Tinyboard Development Group
        <br><a href="https://engine.vichan.net/">vichan</a> Copyright &copy; 2012-2018 vichan-devel</p>
</footer>
</body>
</html>
