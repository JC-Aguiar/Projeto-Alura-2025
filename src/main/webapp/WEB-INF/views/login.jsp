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
    <link rel="stylesheet" type="text/css" href="/assets/css/background.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/highlight-frame.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/mini-card.css">
    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
    <script src="/assets/external-libs/bootstrap/js/bootstrap.bundle.js"></script>
    >
</head>
<body>
    
    <!-- Background -->
    <jsp:include page="background.jsp" />

    <div class="container-md content-container p-md-3 pt-md-5">
        <div class="row g-2 g-lg-5">
            
            <div class="col-12 col-lg-5">
                <jsp:include page="highlight-frame.jsp">
                    <jsp:param name="title" value="Já estuda com a gente?"/>
                    <jsp:param name="subtitle" value="Faça seu login e boa aula!"/>
                    <jsp:param name="buttonText" value="ENTRAR"/>
                    <jsp:param name="onClickUrl" value="/admin/categories"/>
                </jsp:include>
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
                    <c:if test="${not empty data and data != null}">
                        <jsp:include page="mini-card-gallery.jsp">
                            <jsp:param name="categoryListAttributeName" value="data"/>
                        </jsp:include>
                    </c:if>
                </div>
            </div> 

        </div>
    </div>

</body>
</html>
