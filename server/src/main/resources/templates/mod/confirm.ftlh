<#ftl output_format="HTML">
<#--
  -- Template attributes:
  --
  -- confirm.request
  -- confirm.token
  -->
<p style="text-align:center;font-size:1.1em">
    <@spring.message "mod.confirm.Are_you_sure_you_want_to_do_that_"/> <a href="?${confirm.request}/${confirm.token}"><@spring.message "mod.confirm.Click_to_proceed_to"/> ?/${confirm.request}</a>.
</p>
<p class="unimportant" style="text-align:center">
    <@spring.message "mod.confirm.Confirm_help"/>
</p>

