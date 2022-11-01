<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="error.title"/></title>
    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/predictDiagnosisPageMarkup.css">
    <link rel="stylesheet" href="/css/forms.css">

</head>
<body>
<%@ include file="reusable/navbar.jspf" %>
<h1><spring:message code="error.header"/></h1>
</body>
</html>
