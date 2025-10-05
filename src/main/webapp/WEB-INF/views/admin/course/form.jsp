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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
        rel="stylesheet" 
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" 
        crossorigin="anonymous"
    >
    <style>
        .responsive-text {
            font-size: 1.2rem;
        }
        .erro {
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

            <form:form 
                modelAttribute="newCourseFormDTO" 
                cssClass="form-horizontal panel-body" 
                action="/admin/course/save" 
                method="post"
            >
                <div class="responsive-text h-100 frame rounded mt-3 mx-1 pt-2 px-3 px-lg-4 pb-4">
                    <div class="row form-group pb-5 pb-lg-3">
                        <!-- Campo: name -->
                        <div class="col-12 col-lg-6 p-2">
                            <label for="newCourse-name" class="form-label">
                                Nome do Curso
                            </label>
                            <form:input 
                                path="name" 
                                type="text" 
                                id="newCourse-name" 
                                cssClass="form-control" 
                                required="true"
                            />
                            <form:errors path="name" cssClass="erro"/>
                        </div>
                        <!-- Campo: code -->
                        <div class="col-12 col-lg-6 p-2">
                            <label for="newCourse-code" class="form-label">
                                Código
                            </label>
                            <form:input 
                                path="code" 
                                type="text" 
                                id="newCourse-code" 
                                cssClass="form-control" 
                                required="true"
                            />
                            <form:errors path="code" cssClass="erro"/>
                        </div>
                        <!-- Campo: description -->
                        <div class="col-12 p-2">
                            <label for="newCourse-description" class="form-label">
                                Descrição
                            </label>
                            <form:textarea 
                                path="description" 
                                id="newCourse-description" 
                                cssClass="form-control" 
                                rows="4"
                            />
                            <form:errors path="description" cssClass="erro"/>
                        </div>
                        <!-- Campo: instructorEmail -->
                        <div class="col-12 p-2">
                            <label for="newCourse-instructorEmail" class="form-label">
                                Email do Instrutor
                            </label>
                            <form:input 
                                path="instructorEmail" 
                                type="email" 
                                id="newCourse-instructorEmail" 
                                cssClass="form-control" 
                                required="true"
                            />
                            <form:errors path="instructorEmail" cssClass="erro"/>
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