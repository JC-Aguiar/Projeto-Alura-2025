<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Registro do Courso </title>
    
    <link rel="stylesheet" type="text/css" href="/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/login.css">
    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
    <script src="/assets/external-libs/bootstrap/js/bootstrap.bundle.js"></script>
    <style>
        .responsive-text {
            font-size: 1.2rem;
        }
        .error {
            color: red;
            font-size: 0.875rem;
        }
    </style>
</head>

<body>

    <div class="container p-md-3">
        <section class="text-white p-1">
            <div class="highlight frame m-1">
                <div class="light-sphere purple-light inside"></div>
                <div class="d-flex h-100 align-items-center m-0 p-0">
                    <div class="col text-white small">
                        <h1 class="lh-1 m-0 p-2">
                            Cadastrar Novo Curso
                        </h1>
                    </div>
                </div>
            </div>
            
            <c:if test="${not empty error}">
                <div class="fixed-top bagde bagde-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="success bagde bagde-success">${success}</div>
            </c:if>

            <form:form 
                modelAttribute="newCourseFormDTO" 
                cssClass="form-horizontal panel-body" 
                action="/admin/course/save/${id}" 
                method="post"
            >
                <div class="responsive-text h-100 frame rounded mt-3 mx-1 pt-2 px-3 px-lg-4 pb-4">
                    <div class="row form-group pb-5 pb-lg-3">
                        
                        <!-- Field: name -->
                        <div class="col-12 col-lg-6 p-2">
                            <label for="course-name" class="form-label">
                                Nome do Curso
                            </label>
                            <form:input 
                                path="name" 
                                type="text" 
                                id="course-name" 
                                cssClass="form-control" 
                                required="true"
                            />
                            <form:errors path="name" cssClass="error"/>
                        </div>
                        <!-- Field: code -->
                        <div class="col-12 col-lg-6 p-2">
                            <label for="course-code" class="form-label">
                                Código
                            </label>
                            <form:input 
                                path="code" 
                                type="text" 
                                id="course-code" 
                                cssClass="form-control" 
                                required="true"
                            />
                            <form:errors path="code" cssClass="error"/>
                        </div>
                        <!-- Field: description -->
                        <div class="col-12 p-2">
                            <label for="course-description" class="form-label">
                                Descrição
                            </label>
                            <form:textarea 
                                path="description" 
                                id="course-description" 
                                cssClass="form-control" 
                                rows="4"
                            />
                            <form:errors path="description" cssClass="error"/>
                        </div>
                        <!-- Field: instructorEmail -->
                        <div class="col-12 p-2">
                            <label for="course-instructorEmail" class="form-label">
                                Email do Instrutor
                            </label>
                            <form:input 
                                path="instructorEmail" 
                                type="email" 
                                id="course-instructorEmail" 
                                cssClass="form-control" 
                                required="true"
                            />
                            <form:errors path="instructorEmail" cssClass="error"/>
                        </div>
                        <!-- Field: categoryId -->
                        <div class="col-12 col-lg-6 p-2">
                            <label for="course-categoryId" class="form-label">
                                Categoria
                            </label>
                            <form:select 
                                path="categoryId" 
                                id="course-categoryId" 
                                cssClass="form-control"
                                required="true"
                            >
                                <form:option value="" label="-- Selecione uma categoria --"/>
                                <form:options 
                                    items="${listCategoryDTO}" 
                                    itemValue="id" 
                                    itemLabel="name"
                                />
                            </form:select>
                            <form:errors path="categoryId" cssClass="error"/>
                        </div>
                        <!-- Field: status -->
                        <div class="col-12 col-lg-6 p-2">
                            <label for="course-instructorEmail" class="form-label">
                                Status
                            </label>
                            <form:select 
                                path="status" 
                                id="course-status" 
                                cssClass="form-control"
                                required="true"
                            >
                                <form:option value="" label="-- Selecione um Status --"/>
                                <form:options 
                                    items="${courseStatusType}" 
                                    itemValue="name" 
                                    itemLabel="name"
                                />
                            </form:select>
                            <form:errors path="status" cssClass="error"/>
                        </div>
                    </div>
                    <input 
                        type="submit"
                        class="d-block w-100 rounded rounded-pill py-1 submit btn btn-primary" 
                        value="Salvar"
                    />
                </div>
            </form:form>
        </section>
    </div>
</body>