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
    <link rel="stylesheet" type="text/css" href="/assets/css/adminForms.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/background.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/highlight-frame.css">
    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
    <script src="/assets/external-libs/bootstrap/js/bootstrap.bundle.js"></script>
</head>

<body>

    <!-- Background -->
    <jsp:include page="../../background.jsp" />

    <!-- Main Content -->
    <div class="container-md content-container p-0 p-md-3">

        <!-- Content Header -->
        <jsp:include page="../../highlight-frame.jsp">
            <jsp:param name="title" value="${id != null ? 'Editar Categoria' : 'Cadastrar Nova Categoria'}"/>
            <jsp:param name="frameCss" value="p-0"/>
        </jsp:include>

        <section class="mt-3">

            <!-- Defining Endpoint URI -->
            <c:choose>
                <c:when test="${empty id}">
                    <c:set var="formActionUrl" value="/admin/course/save" />
                </c:when>
                <c:otherwise>
                    <c:set var="formActionUrl" value="/admin/course/save/${id}" />
                </c:otherwise>
            </c:choose>

            <!-- Form -->
            <form:form 
                modelAttribute="newCourseFormDTO" 
                cssClass="form-horizontal panel-body" 
                action="${formActionUrl}" 
                method="post"
            >
                <div class="h-100 frame rounded mt-3 py-2 py-md-4 px-1 px-md-3 px-lg-4">
                    <div class="form-container form-group pb-0 pb-lg-3">
                        <!-- Left Side of the Form -->
                        <div class="row g-3 responsive-font">
                            <!-- Field: name -->
                            <div >
                                <div class="input-group input-group-sm">
                                    <span 
                                        id="course-name-label" 
                                        class="input-group-text bg-dark text-white frame"
                                    >
                                        Nome
                                    </span>
                                    <form:input 
                                        path="name" 
                                        type="text" 
                                        id="course-name" 
                                        cssClass="form-control frame bg-dark text-white" 
                                        required="true"
                                    />
                                </div>
                                <form:errors path="name" cssClass="error"/>
                            </div>
                            <!-- Field: code -->
                            <div>
                                <div class="input-group input-group-sm">
                                    <span 
                                        id="course-code-label" 
                                        class="input-group-text bg-dark text-white frame"
                                    >
                                        Código
                                    </span>
                                    <form:input 
                                        path="code" 
                                        type="text" 
                                        id="course-code" 
                                        cssClass="form-control frame bg-dark text-white" 
                                        required="true"
                                    />
                                </div>
                                <form:errors path="code" cssClass="error"/>
                            </div>
                            <!-- Field: instructorEmail -->
                            <div>
                                <div class="input-group input-group-sm">
                                    <span 
                                        id="course-instructorEmail-label" 
                                        class="input-group-text bg-dark text-white frame"
                                    >
                                        Email do</br> Instrutor
                                    </span>
                                    <form:input 
                                        path="instructorEmail" 
                                        type="email" 
                                        id="course-instructorEmail" 
                                        cssClass="form-control frame bg-dark text-white" 
                                        required="true"
                                    />
                                </div>
                                <form:errors path="instructorEmail" cssClass="error"/>
                            </div>
                            <!-- Field: categoryId -->
                            <div>
                                <div class="input-group input-group-sm">
                                    <span 
                                        id="course-categoryId-label" 
                                        class="input-group-text bg-dark text-white frame"
                                    >
                                      Categoria
                                    </span>
                                    <form:select 
                                        path="categoryId" 
                                        id="course-categoryId" 
                                        cssClass="form-control bg-dark text-white frame"
                                        required="true"
                                    >
                                        <form:option value="" label="Selecione uma categoria"/>
                                        <form:options 
                                            items="${listCategoryDTO}" 
                                            itemValue="id" 
                                            itemLabel="name"
                                        />
                                    </form:select>
                                </div>
                                <form:errors path="categoryId" cssClass="error"/>
                            </div>
                            <!-- Field: status -->
                             <c:if test="${id != null}">
                                <div>
                                    <div class="input-group input-group-sm">
                                        <span 
                                            id="course-status-label" 
                                            class="input-group-text bg-dark text-white frame"
                                        >
                                            Status
                                        </span>
                                        <form:select 
                                            path="status" 
                                            id="course-status" 
                                            cssClass="form-control bg-dark text-white frame"
                                            required="true"
                                        >
                                            <form:option value="" label="Selecione um status"/>
                                            <form:options 
                                                items="${courseStatusType}" 
                                                itemValue="name" 
                                                itemLabel="name"
                                            />
                                        </form:select>
                                    </div>
                                    <form:errors path="status" cssClass="error"/>
                                </div>
                            </c:if>
                        </div>

                        <!-- Rright Side of the Form -->
                        <div>
                            <!-- Field: description -->
                             <div class="mb-3 responsive-font">
                                <div class="input-group-sm">
                                    <span 
                                        id="course-description-label" 
                                        class="input-group-text bg-dark text-white frame"
                                        style="border-radius: 8px 8px 0px 0px !important;"
                                    >
                                        Descrição
                                    </span>
                                    <form:textarea 
                                        path="description" 
                                        id="course-description" 
                                        cssClass="form-control bg-dark text-white frame pb-0 pb-md-2" 
                                        rows="7"
                                        style="border-radius: 0px 0px 8px 8px !important;"
                                    />
                                </div>
                                <form:errors path="description" cssClass="error"/>
                            </div>
                            <div class="row row-cols-1 row-cols-md-2 g-2">
                                <div class="col">
                                    <input 
                                        type="submit"
                                        class="col w-100 btn btn-primary rounded-pill py-1 submit" 
                                        value="Salvar"
                                    />
                                </div>
                                <div class="col">
                                    <button 
                                        type="button"
                                        class="col w-100 btn btn-outline-secondary rounded-pill py-1"
                                        onclick="window.location.href='/admin/courses'" 
                                    >
                                        Voltar
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </section>
    </div>
    
    <!-- Info Message Toast -->
    <jsp:include page="../../toast.jsp">
        <jsp:param name="title" value="Problema na requisição!"/>
        <jsp:param name="message" value="${error}"/>
    </jsp:include>

</body>

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