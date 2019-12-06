<#ftl output_format="HTML">
    <link rel="stylesheet" media="screen" href="{{ config.url_stylesheet }}">
    {% if config.url_favicon %}<link rel="shortcut icon" href="{{ config.url_favicon }}">{% endif %}
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">
    {% if config.meta_keywords %}<meta name="keywords" content="{{ config.meta_keywords }}">{% endif %}
    {% if config.default_stylesheet.1 != '' %}<link rel="stylesheet" type="text/css" id="stylesheet" href="{{ config.uri_stylesheets }}{{ config.default_stylesheet.1 }}">{% endif %}
    {% if config.font_awesome %}<link rel="stylesheet" href="{{ config.root }}{{ config.font_awesome_css }}">{% endif %}
    {% if config.country_flags_condensed %}<link rel="stylesheet" href="{{ config.root }}{{ config.country_flags_condensed_css }}">{% endif %}
    <script type="text/javascript">
        var configRoot="{{ config.root }}";
        var inMod = {% if mod %}true{% else %}false{% endif %};
        var modRoot="{{ config.root }}"+(inMod ? "mod.php?/" : "");
    </script>
    {% if not nojavascript %}
        <script type="text/javascript" src="{{ config.url_javascript }}"></script>
        {% if not config.additional_javascript_compile %}
        {% for javascript in config.additional_javascript %}<script type="text/javascript" src="{{ config.additional_javascript_url }}{{ javascript }}"></script>{% endfor %}
        {% endif %}
    {% endif %}
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
