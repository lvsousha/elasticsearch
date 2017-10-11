<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<% 	
	String context=request.getContextPath();
	String rand=new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	String resource=context+"/static";
	request.setAttribute("context",context);
	request.setAttribute("resource",resource);
	request.setAttribute("rand",rand);
%>
	<script>
	var context="<%=context%>";
	var resource = "<%=resource%>";
	</script>
	
    <!-- Bootstrap -->
    <link href="${resource}/bootstrap/dist/css/bootstrap.min.css?rand=${rand}" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${resource}/font-awesome/css/font-awesome.min.css?rand=${rand}" rel="stylesheet">
    <!-- NProgress -->
    <link href="${resource}/nprogress/nprogress.css?rand=${rand}" rel="stylesheet">
    <!-- jQuery custom content scroller -->
    <link href="${resource}/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css?rand=${rand}" rel="stylesheet"/>

    <!-- Custom Theme Style -->
    <link href="${resource}/app/custom.min.css?rand=${rand}" rel="stylesheet">
</head>
</html>

