<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- body
  -- page.boardlist.top
  -- page.dataStylesheet
  -- page.hideDashboardLink
  -- page.mod
  -- page.pm
  -- page.subtitle
  -- page.title
  -- page.version
  -->
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="<@spring.message "page.lang"/>">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript">
        var active_page = "page";
    </script>
    <#include "header.ftlh">
    <title>${page.title}</title>
</head>
<body class="8chan vichan <#if page.mod??>is-moderator<#else>is-not-moderator</#if> active-page" data-stylesheet="${page.dataStylesheet}">
${page.boardlist.top?no_esc}

<#if page.pm??><div class="top_notice"><@spring.message "page.You_have"/> <a href="?/PM/${page.pm.id?string["0"]}"><@spring.message "page.An_unread_PM"/></a><#if page.pm.waiting gt 0 ><@spring.messageArgs "page.Plus_{count}_more_waiting", [ page.pm.waiting ]/></#if>.</div><hr></#if>
<header>
    <h1>${page.title}</h1>
    <div class="subtitle">
        <#if page.subtitle?has_content>${page.subtitle}</#if>
        <#if page.mod?? && !page.hideDashboardLink><p><a href="?/"><@spring.message "page.Return_to_dashboard"/></a></p></#if>
    </div>
</header>
<#include body>
<hr>
<footer>
    <p class="unimportant" style="margin-top:20px;text-align:center;">- Tinyboard +
        <a href="https://engine.vichan.net/">vichan</a> ${page.version} -
        <br>Tinyboard Copyright &copy; 2010-2014 Tinyboard Development Group
        <br><a href="https://engine.vichan.net/">vichan</a> Copyright &copy; 2012-2018 vichan-devel</p>
</footer>
</body>
</html>
