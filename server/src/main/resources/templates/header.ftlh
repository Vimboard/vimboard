<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- config.additionalJavascript
  -- config.additionalJavascriptCompile
  -- config.additionalJavascriptUrl
  -- config.countryFlagsCondensed
  -- config.countryFlagsCondensedCss
  -- config.default_stylesheet[1]
  -- config.fontAwesome
  -- config.fontAwesomeCss
  -- config.metaKeywords
  -- config.root
  -- config.uriStylesheets
  -- config.urlFavicon
  -- config.urlJavascript
  -- config.urlStylesheet
  -- page.mod
  -- page.nojavascript
  -->
    <link rel="stylesheet" media="screen" href="${config.urlStylesheet}">
    <#if config.urlFavicon?has_content><link rel="shortcut icon" href="${config.urlFavicon}"></#if>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">
    <#if config.metaKeywords?has_content><meta name="keywords" content="${config.metaKeywords}"></#if>
    <#if config.defaultStylesheet[1]?has_content><link rel="stylesheet" type="text/css" id="stylesheet" href="${config.uriStylesheets}${config.defaultStylesheet[1]}"></#if>
    <#if config.fontAwesome><link rel="stylesheet" href="${config.root}${config.fontAwesomeCss}"></#if>
    <#if config.countryFlagsCondensed><link rel="stylesheet" href="${config.root}${config.countryFlagsCondensedCss}"></#if>
    <script type="text/javascript">
        var configRoot="${config.root}";
        var inMod = <#if page.mod??>true<#else>false</#if>;
        var modRoot="${config.root}"+(inMod ? "mod.php?/" : "");
    </script>
    <#if !page.nojavascript>
        <script type="text/javascript" src="${config.urlJavascript}"></script>
        <#if !config.additionalJavascriptCompile>
            <#list config.additionalJavascript as javascript>
                <script type="text/javascript" src="${config.additionalJavascriptUrl}${javascript}"></script>
            </#list>
        </#if>
    </#if>
    {% if config.recaptcha %}<script src="//www.google.com/recaptcha/api.js"></script>
    <style type="text/css">{% raw %}
        #recaptcha_area {
            float: none !important;
            padding: 0 !important;
        }
        #recaptcha_logo, #recaptcha_privacy {
            display: none;
        }
        #recaptcha_table {
            border: none !important;
        }
        #recaptcha_table tr:first-child {
            height: auto;
        }
        .recaptchatable img {
            float: none !important;
        }
        #recaptcha_response_field {
            font-size: 10pt !important;
            border: 1px solid #a9a9a9 !important;
            padding: 1px !important;
        }
        td.recaptcha_image_cell {
            background: transparent !important;
        }
        .recaptchatable, #recaptcha_area tr, #recaptcha_area td, #recaptcha_area th {
            padding: 0 !important;
        }
    {% endraw %}</style>{% endif %}
