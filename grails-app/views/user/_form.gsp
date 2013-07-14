<%@ page import="yuhui.huang.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'fullName', 'error')} ">
	<label for="fullName">
		<g:message code="user.fullName.label" default="Full Name" />
		
	</label>
	<g:textField name="fullName" value="${userInstance?.fullName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'userName', 'error')} ">
	<label for="userName">
		<g:message code="user.userName.label" default="User Name" />
		
	</label>
	<g:textField name="userName" value="${userInstance?.userName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} ">
	<label for="password">
		<g:message code="user.password.label" default="Password" />
		
	</label>
	<g:field type="password" name="password" value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'docs', 'error')} ">
	<label for="docs">
		<g:message code="user.docs.label" default="Docs" />
		
	</label>
	<g:select name="docs" from="${yuhui.huang.Doc.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.docs*.id}" class="many-to-many"/>
</div>

