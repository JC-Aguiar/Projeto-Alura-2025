<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Lista de Categorias</title>

    <link rel="stylesheet" type="text/css" href="/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/list-catebories.css">
    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
    <script src="/assets/external-libs/bootstrap/js/bootstrap.bundle.js"></script>
</head>

<body>
<div class="container-md content-container p-md-3">

        <jsp:include page="../../highlight-frame.jsp">
            <jsp:param name="title" value="Categorias Disponíveis"/>
            <jsp:param name="subtitle" value="Clique no card para acessar seu formulário."/>
            <jsp:param name="frameCss" value="p-0 m-0"/>
        </jsp:include>

        <div class="mt-3">

            <c:if test="${empty categories or categories == null}">
                <div class="row align-items-center text-white text-center w-100 p-2">
                    <h4 class="py-3 m-0">
                        Nenhum registro na base disponível
                    </h4>
                </div>
            </c:if>
            <c:if test="${not empty categories and categories != null}">
                <div class="grid p-2">
                    <c:forEach items="${categories}" var="category">
                        <jsp:include page="../../mini-card.jsp">
                            <jsp:param name="subtitle" value="Categoria"/>
                            <jsp:param name="id" value="${category.code()}"/>
                            <jsp:param name="img" value="../../assets/svg/ICON-CATEGORY-${category.code()}.svg"/>
                            <jsp:param name="title" value="${category.name()}"/>
                            <jsp:param name="color" value="${category.color()}"/>
                            <jsp:param name="description" value=" "/>
                            <jsp:param name="onClickUrl" value="/admin/category/edit/${category.id()}"/>
                        </jsp:include>
                    </c:forEach>
                    <jsp:include page="../../mini-card.jsp">
                        <jsp:param name="subtitle" value=""/>
                        <jsp:param name="id" value="CREATE-NEW-ONE"/>
                        <jsp:param name="img" value="../../assets/svg/ICON-CATEGORY-CREATE-NEW-ONE.svg"/>
                        <jsp:param name="title" value="  Criar Nova Categoria"/>
                        <jsp:param name="color" value="#FFFFFF"/>
                        <jsp:param name="description" value=" "/>
                        <jsp:param name="onClickUrl" value="/admin/category/new"/>
                    </jsp:include>
                </div>
            </c:if>
        </div>
    </div>

</body>