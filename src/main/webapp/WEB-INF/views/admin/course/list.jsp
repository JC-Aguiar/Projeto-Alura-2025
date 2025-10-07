<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.OffsetDateTime, java.util.Date" %>

<fmt:formatDate value="${convertedDate}" pattern="dd/MM/yyyy HH:mm:ss"/>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Lista de Cursos</title>
    
    <link rel="stylesheet" type="text/css" href="/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/list-courses.css">
    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
    <script src="/assets/external-libs/bootstrap/js/bootstrap.bundle.js"></script>
</head>

<body>
    <div class="light-sphere top-lgiht"></div>
    <div class="light-sphere purple-light"></div>

    <c:if test="${not empty erro}">
        <div class="fixed-top bagde bagde-danger">${erro}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="success bagde bagde-success">${success}</div>
    </c:if>

    <div class="fixed-top d-md-none m-0 p-0">
        <div class="highlight frame p-1 m-1 banner">
            <div class="light-sphere purple-light inside"></div>
            
            <div class="row row-cols-1 row-cols-sm-2 h-100 align-items-center m-0 p-0">
                <div class="col text-white small">
                    <h5 class="lh-1 m-0 p-0">
                        Cursos
                    </h5>
                </div>
                <div class="col d-flex align-items-center px-5 px-sm-2 mt-1 mt-sm-0">
                    <button type="button" 
                        class="rounded rounded-pill py-0 py-sm-2" 
                        onclick="window.location.href='/admin/course/new'"
                    >
                        CADASTRAR NOVO
                    </button>
                </div>
            </div>

        </div>
    </div>


    <div class="container-md content-container p-md-3">
        
        <div class="card highlight frame d-none d-md-block">
            <div class="light-sphere purple-light inside"></div>
            <div class="card-body px-center">
                <div class="highlight-body text-white">
                    <h1 class="mb-0">
                        Cursos
                    </h1>
                </div>
                <button type="button" 
                    class="rounded rounded-pill py-2 px-5" 
                    onclick="window.location.href='/admin/course/new'"
                >
                    CADASTRAR NOVO
                </button>
            </div>
        </div>

        <div class="frame rounded mt-3">

            <c:if test="${empty courses or courses == null}">
                <div class="row align-items-center text-white text-center w-100 p-2">
                    <h4 class="py-3 m-0">
                        Nenhum registro na base disponível
                    </h4>
                </div>
            </c:if>
            <c:if test="${not empty courses and courses != null}">
                <table class="table table-transparent">
                    <thead>
                        <tr class="align-items-center">
                            <th width="40"></th>
                            <th>Nome</th>
                            <th>Código</th>
                            <th>Descrição</th>
                            <th>Email do Instrutor</th>
                            <th>Status</th>
                            <th>Inativação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${courses}" var="course">
                            <tr class="small">
                                <!-- Edition Icon -->
                                <td>
                                    <a class="btn btn-outline-primary bg-transparent m-0 p-0 border-0 d-flex justify-content-center" 
                                        href="/admin/course/edit/${course.id()}"
                                    >
                                        <i class="bi bi-pencil-square"></i>
                                    </a>
                                </td>
                                <td>${course.name()}</td>
                                <td>${course.code()}</td>
                                <td>${course.description()}</td>
                                <td>${course.instructorEmail()}</td>
                                <td>
                                    <div class="dropdown">
                                        <div class="btn btn-outline-primary btn-sm small dropdown-toggle ${course.status() == 'ACTIVE' ? '' : 'disabled'}"
                                            data-bs-toggle="dropdown" 
                                            aria-expanded="false"
                                        >
                                            ${course.status()}
                                        </div>
                                        <ul class="dropdown-menu bg-dark">
                                            <li class="small">
                                                <form 
                                                    class="dropdown-item bg-dark"
                                                    action="/admin/course/${course.code()}/inactive" 
                                                    method="post"
                                                >
                                                    <button 
                                                        type="submit" 
                                                        class="w-100 btn btn-outline-danger btn-sm border-0"
                                                    >
                                                        Desativar curso
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                                <td>
                                    <%
                                        Object course = pageContext.getAttribute("course");
                                        OffsetDateTime offsetDate = (OffsetDateTime) course.getClass().getMethod("inactivationDate").invoke(course);
                                        Date date = offsetDate == null ? null : Date.from(offsetDate.toInstant());
                                        pageContext.setAttribute("date", date);
                                    %>
                                    <fmt:formatDate value="<%= date %>" pattern="dd/MM/yyyy"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
<body>