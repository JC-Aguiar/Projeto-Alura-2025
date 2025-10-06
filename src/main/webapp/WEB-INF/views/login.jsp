<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
        rel="stylesheet" 
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" 
        crossorigin="anonymous"
    >
</head>
<body>
    <div class="light-sphere top-lgiht"></div>
    <div class="light-sphere purple-light"></div>

    <div class="fixed-top d-md-none m-0 p-0">
        <div class="highlight frame p-1 m-1 banner">
            <div class="light-sphere purple-light inside"></div>
            
            <div class="row row-cols-1 row-cols-sm-2 h-100 align-items-center m-0 p-0">
                <div class="col text-white small">
                    <h5 class="lh-1 m-0 p-0">
                        Já estuda com a gente?
                    </h5>
                    <small class="d-none d-sm-block">Faça seu login e boa aula!</small>
                </div>
                <div class="col d-flex align-items-center px-5 px-sm-2 mt-1 mt-sm-0">
                    <button type="button" 
                        class="rounded rounded-pill py-0 py-sm-2" 
                        href="/admin/categories"
                    >
                        ENTRAR 
                    </button>
                </div>
            </div>

        </div>
    </div>

    <div class="container-md content-container p-md-3 pt-md-5">
        <div class="row g-2 g-lg-5">
            
            <div class="col-12 col-lg-5 d-none d-md-block">
                <div class="highlight frame">
                    <div class="light-sphere purple-light inside"></div>

                    <div class="container w-100 h-100 p-4">
                        <div class="row justify-content-end align-items-center h-100">
                            <h2 class="login-title">
                                Já estuda com a gente?
                            </h2>
                            <p class="login-text">
                                Faça seu login e boa aula!
                            </p>
                            <div class="container mt-2">
                                <button type="button" 
                                    class="highlight-button" 
                                    href="/admin/categories"
                                >
                                    ENTRAR 
                                </button>
                                <!--<a href="/admin/categories" class="btn-login">ENTRAR</a>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div> 

            <div class="col-12 col-lg-7">
                <div class="courses text-white">
                    <h5 class="m-0 mt-3 mt-lg-0 p-0">
                        <b>
                            <span class="fw-bolder">
                                Ainda não estuda com a gente?
                            </span>
                        </b>
                    </h5>
                    <p class="fw-medium">
                        São mais de mil cursos nas seguintes áreas:
                    </p>
                    <%--    TODO: Implementar a Questão 3 (As informações devem vir do seu banco de dados)  --%>
                    <div class="grid">
                        <c:forEach items="${data}" var="category">
                            <div class="mini-card">
                                <img
                                    class="mb-2" 
                                    src="../../assets/svg/ICON-CATEGORY-${category.code()}.svg"
                                >
                                <div style="color: ${category.color()}">
                                    <p class="mini-card-subtitle">
                                        <small>Escola_</small>
                                    </p>
                                    <p class="mini-card-category">
                                        ${category.name()}
                                    </p>
                                </div>
                                <p class="mini-card-courses">
                                    ${category.stringifyCourses()}
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div> 

        </div>
    </div>

    </body>
</html>
