<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <sec:csrfMetaTags/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"--%>
    <%--          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">--%>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/predictDiagnosisPageMarkup.css">
    <link rel="stylesheet" href="/css/forms.css">

    <title><spring:message code="diagnosisPrediction.prediction.title"/></title>
    <spring:message var="dateFormat" code="dateFormat"/>
    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>

    <style>
        .number-column {
            width: 20%;
        !important;
        }

        .text-column {
            width: 100%;
        }

        .submit-button {
            display: block;
            width: 98%;
            margin: 0.5em;
        }
        .table-title {
            padding-right: 0;
        }

        .title {
            display: inline;
            width: max-content;
            text-align: left;
            float: left;
            flex-grow: 10;

        }

        .show-entries {
            width: fit-content;
            float: right;
            margin-right: 15px;
            padding-right: 0;

        }

        h2 {
            text-align: center;
        }

        h4 {
            text-align: center;
        }
    </style>
</head>
<body>
<%@ include file="../reusable/navbar.jspf" %>
<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav"></div>

        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">

                <c:set var="refreshLink" value="/${pageName}/{page}?recordsPerPage={records}"/>
                <c:set var = "refWithPage" value = "${fn:replace(refreshLink, '{page}', page.number)}"/>
                <c:set var ="fullSelfLink" value = "${fn:replace(refWithPage, '{records}', page.size)}"/>

                <div class="table-title row">
                    <h2 class="title"><spring:message code="shop.ordersList.title"/></h2>
                    <div class="show-entries">
                    <span><spring:message code="pagination.show"/></span>
                    <div class="dropdown">
                        <button class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                type="button" data-toggle="dropdown">${page.size}</button>
                        <ul class="dropdown-menu ">
                            <c:set var = "refWithPage" value = "${fn:replace(refreshLink, '{page}', page.number)}"/>

                            <li><a class="dropdown-item"
                                   href="<c:out value="${fn:replace(refWithPage, '{records}', '5')}"/>">5</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="<c:out value="${fn:replace(refWithPage, '{records}', '10')}"/>">10</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="<c:out value="${fn:replace(refWithPage, '{records}', '15')}"/>">15</a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="<c:out value="${fn:replace(refWithPage, '{records}', '20')}"/>">20</a>
                            </li>
                        </ul>
                    </div>
                    <span> <spring:message code="pagination.entries"/></span>
                </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th class="col-md-8 text-column"><spring:message code="shop.ordersList.id"/></th>
                        <th class="col-md-8 text-column"><spring:message code="shop.ordersList.shortList"/></th>
                        <th class="col-md-2 number-column"><spring:message code="shop.buyPage.productPriceLabel"/></th>
                        <th class="col-md-2 number-column"><spring:message code="shop.ordersList.name"/></th>
                        <th class="col-md-2 number-column"><spring:message code="shop.ordersList.address"/></th>
                        <th class="col-md-1 number-column" style="width: 20%"><spring:message
                                code="shop.ordersList.currentStatus"/></th>
                        <th class="col-md-2 number-column"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.content}" var="order">
                        <tr>
                            <th>${order.orderId}</th>
                            <th>${order.shortListOfProducts}</th>
                            <th><fmt:formatNumber type="number"
                                                  maxFractionDigits="0"
                                                  value="${order.totalPrice/100}"/>.<c:out
                                    value="${order.totalPrice%100}"/> <spring:message
                                    code="diagnosisPrediction.shop.priceHrivna"/></th>
                            <th>${order.name}</th>
                            <th>${order.address}</th>
                            <th><spring:message code="shop.orderPage.status.${order.currentStatus}"/></th>
                            <th><a class="btn btn-primary" href="/order/${order.orderId}" role="button">
                                <spring:message code="doctor.page.patientsList.open"/></a>
                            </th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <%@ include file="../reusable/pageableTableFooter.jspf"%>
            </div>

        </div>
        <div class="col-sm-2 sidenav"></div>
    </div>
</div>

<footer class="container-fluid text-center">
</footer>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>