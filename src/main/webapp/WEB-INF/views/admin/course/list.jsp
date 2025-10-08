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
    <link rel="stylesheet" type="text/css" href="/assets/css/background.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/highlight-frame.css">
    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
    <script src="/assets/external-libs/bootstrap/js/bootstrap.bundle.js"></script>
</head>

<body>

    <!-- Background -->
    <jsp:include page="../../background.jsp" />

    <!-- Main Content -->
    <div class="container-md content-container p-md-3">

        <!-- Content Header -->
        <jsp:include page="../../highlight-frame.jsp">
            <jsp:param name="title" value="Cursos Disponíveis"/>
            <jsp:param name="subtitle" value="Clique no card para acessar seu formulário."/>
            <jsp:param name="frameCss" value="p-0 text-center"/>
        </jsp:include>
        
        <!-- Categories Result -->  
        <div class="frame rounded mt-3">

            <!-- Not Found Message -->
            <c:if test="${empty courses or courses == null}">
                <div class="row align-items-center text-white text-center w-100 p-2">
                    <h4 class="py-3 m-0">
                        Nenhum registro na base disponível
                    </h4>
                </div>
            </c:if>

            <!-- Courses Table -->
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
                                <td>
                                    <!-- Edition Icon -->
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

    <jsp:include page="../../toast.jsp">
        <jsp:param name="title" value="Problema na requisição!"/>
        <jsp:param name="message" value="${error}"/>
    </jsp:include>

<body>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        toastFeedback();
    });

    function toastFeedback() {
        var toastEl = document.getElementById('liveToast');
        if (toastEl) {
            var toast = new bootstrap.Toast(toastEl, {
                delay: 7000
            });
            toast.show();
        }
    }
</script>