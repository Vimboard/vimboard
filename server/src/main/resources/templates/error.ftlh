<#ftl output_format="HTML">
<div style="text-align:center">
    <h2 style="margin:20px 0">${error.message}</h2>
    <#if (error.board)??>
        <p>
            <a href="{{ config.root }}{% if mod %}{{ config.file_mod }}?/{% endif %}{{ board.dir }}{{ config.file_index }}">
                {% trans 'Go back' %}
            </a>
        </p>
    </#if>
</div>
<#if debug?? && (config.debug)??>
    <hr>
    <h3>{% trans 'Error information' %}</h3>
    <pre style="white-space:pre-wrap;font-size: 10px;">
        {{ debug }}
    </pre>
    <hr>
</#if>