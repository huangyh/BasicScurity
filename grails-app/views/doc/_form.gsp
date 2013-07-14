<%@ page import="yuhui.huang.Doc" %>



<div class="fieldcontain ${hasErrors(bean: docInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="doc.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${docInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: docInstance, field: 'fileName', 'error')} ">
	<label for="fileName">
		<g:message code="doc.fileName.label" default="File Name" />
		
	</label>
	<g:textField name="fileName" value="${docInstance?.fileName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: docInstance, field: 'filedata', 'error')} required">
	<label for="filedata">
		<g:message code="doc.filedata.label" default="Filedata" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="filedata" name="filedata" />
</div>

