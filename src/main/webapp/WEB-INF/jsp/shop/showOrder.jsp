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


        .fill-order-form form {
            color: #9ba5a8;
        }

        .fill-order-form h2 {
            color: #4d4d4d;
            font-weight: bold;
            margin-top: 0;
        }

        .fill-order-form label {
            font-weight: normal;
            font-size: 20px;
            float: left;
            color: #3f3838;
        }

        .fill-order-form .btn {
            font-size: 16px;
            font-weight: bold;
            background: #49b293;
            border: none;
            min-width: 140px;
        }

        .fill-order-form .btn:hover, .signup-form .btn:focus {
            background: #3d9f81;
        }


        .fill-order-form a:hover {
            text-decoration: none;
        }

        .check-box-container {
            display: flex;
            padding-top: 0.3em;
            padding-bottom: 0.3em;
        }

        .check-box {
            margin-left: 0.5em;
            margin-top: auto;
            margin-bottom: auto;
        }

        .submit-button {
            width: 100%;
        }

        .order-data {
            font-weight: normal;
            font-size: 20px;
            display: inline;
            color: #4f4848;
        }

        .dropdown-data {
            display: inline;
        }

        label {
            font-size: 20px;
        }

        h2 {
            text-align: center;
            font-weight: bold;
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
                <div class="table-title">
                    <h2><spring:message code="shop.orderPage.reviewOfOrder"/></h2>
                </div>

                <h4><spring:message code="shop.buyPage.orderedProducts"/></h4>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th class="col-md-8 text-column"><spring:message code="shop.buyPage.productNameLabel"/></th>
                        <th class="col-md-2 number-column"><spring:message code="shop.buyPage.productPriceLabel"/></th>
                        <th class="col-md-1 number-column" style="width: 20%"><spring:message
                                code="shop.buyPage.productCount"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${order.products}" var="product">
                        <tr>
                            <th>${product.product.name}</th>
                            <th><fmt:formatNumber type="number"
                                                  maxFractionDigits="0"
                                                  value="${product.product.price/100}"/>.<c:out
                                    value="${product.product.price%100}"/> <spring:message
                                    code="diagnosisPrediction.shop.priceHrivna"/></th>
                            <th>${product.count}</th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <h4><spring:message code="shop.buyPage.totalPrice"/> <fmt:formatNumber type="number"
                                                                                       maxFractionDigits="0"
                                                                                       value="${totalPrice/100}"/>.<c:out
                        value="${totalPrice%100}"/> <spring:message
                        code="diagnosisPrediction.shop.priceHrivna"/></h4>


                <div class="form-group">
                    <label><spring:message code="shop.orderPage.name"/></label>
                    <div class="order-data">
                        ${order.name}
                    </div>
                </div>


                <div class="form-group">
                    <label><spring:message code="shop.orderPage.phoneNumber"/></label>
                    <div class="order-data">
                        ${order.phoneNumber}
                    </div>
                </div>

                <div class="form-group">
                    <label><spring:message code="shop.orderPage.email"/></label>
                    <div class="order-data">
                        ${order.email}
                    </div>
                </div>

                <div class="form-group">
                    <label><spring:message code="shop.orderPage.address"/></label>
                    <div class="order-data">
                        ${order.address}
                    </div>
                </div>

                <div class="form-group">
                    <label><spring:message code="shop.orderPage.paymentMethod"/></label>
                    <div class="order-data">
                        <spring:message code="shop.orderPage.paymentMethod.${order.paymentMethod}"/>
                    </div>
                </div>


                <div class="form-group">
                    <label><spring:message code="shop.orderPage.deliveryMethod"/></label>
                    <div class="order-data">
                        <spring:message code="shop.orderPage.deliveryMethod.${order.deliveryMethod}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label><spring:message code="shop.orderPage.comment"/></label>
                    <div class="order-data">
                        ${order.comment}
                    </div>
                </div>


                <div class="form-group">
                    <label><spring:message code="shop.orderPage.notReceiveCall"/></label>
                    <div class="order-data">
                        <spring:message code="shop.orderPage.notRecieveCall.${order.notReceiveCall}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label><spring:message code="shop.orderPage.dateCreted"/></label>
                    <div class="order-data">
                        ${order.dateCreated.format(foramter)}
                    </div>
                </div>

                <div class="form-group">
                    <label><spring:message code="shop.orderPage.status"/></label>
                    <sec:authorize access="!hasRole('SHOP_WORKER')">
                        <div class="order-data">
                            <spring:message code="shop.orderPage.status.${order.currentStatus}"/>
                        </div>
                    </sec:authorize>
                    <sec:authorize access="hasRole('SHOP_WORKER')">
                        <li class="nav-item dropdown order-data dropdown-data">
                            <a class="nav-link dropdown-toggle dropdown-data" id="dropdown" data-toggle="dropdown" aria-haspopup="true"
                               aria-expanded="false"><spring:message code="shop.orderPage.status.${order.currentStatus}"/></a>
                            <div class="dropdown-menu" aria-labelledby="dropdown">
                                <a class="dropdown-item"
                                   href="/order/${order.orderId}/update?status=CREATED"><spring:message code="shop.orderPage.status.CREATED"/></a>
                                <a class="dropdown-item"
                                   href="/order/${order.orderId}/update?status=IN_PROCESSING"><spring:message code="shop.orderPage.status.IN_PROCESSING"/></a>
                                <a class="dropdown-item"
                                   href="/order/${order.orderId}/update?status=IN_DELIVERY"><spring:message code="shop.orderPage.status.IN_DELIVERY"/></a>
                                <a class="dropdown-item"
                                   href="/order/${order.orderId}/update?status=WAITING_IN_PLACE_OF_GIVING"><spring:message code="shop.orderPage.status.WAITING_IN_PLACE_OF_GIVING"/></a>
                                <a class="dropdown-item"
                                   href="/order/${order.orderId}/update?status=DONE"><spring:message code="shop.orderPage.status.DONE"/></a>
                                <a class="dropdown-item"
                                   href="/order/${order.orderId}/update?status=CANCELED"><spring:message code="shop.orderPage.status.CANCELED"/></a>

                            </div>
                        </li>
                    </sec:authorize>

                </div>

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