<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Lista de Cursos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/list-courses.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
        rel="stylesheet" 
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" 
        crossorigin="anonymous"
    >
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
                        href="/admin/course/new"
                    >
                        CADASTRAR NOVA 
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
                    href="/admin/category/new"
                >
                    CADASTRAR NOVA 
                </button>
            </div>
        </div>

        <div class="frame rounded mt-3">
            <c:if test="${empty courses or courses == null}">
                <div class="row align-items-center text-white text-center w-100 p-2">
                    <h4 class="py-3 m-0">
                        Nenhum registro na abse disponível
                    </h4>
                </div>
            </c:if>
            <c:if test="${not empty courses and courses != null}">
                <table class="table table-transparent">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Código</th>
                            <th>Descrição</th>
                            <th>Email do Instrutor</th>
                            <th>Status</th>
                            <th>Data Inativação</th>
                            <th width="50"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${courses}" var="course">
                            <tr>
                                <td>${course.name()}</td>
                                <td>${course.code()}</td>
                                <td>${course.description()}</td>
                                <td>${course.instructorEmail()}</td>
                                <td>${course.status()}</td>
                                <td>${course.inactivationDate()}</td>
                                <td>
                                    <a class="btn btn-outline-primary bg-transparent m-0 p-0 border-0 d-flex justify-content-center" 
                                        href="/admin/course/edit/${course.id()}"
                                    >
                                        <i class="bi bi-pencil-square"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
<body>