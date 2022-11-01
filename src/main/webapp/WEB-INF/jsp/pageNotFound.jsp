<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundles/messages"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="pageNotfound.title"/></title>
    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/predictDiagnosisPageMarkup.css">
    <link rel="stylesheet" href="/css/forms.css">

</head>
<%@ include file="reusable/navbar.jspf" %>
<body>
<h1><spring:message code="pageNotfound.header"/></h1>
</body>
</html>
