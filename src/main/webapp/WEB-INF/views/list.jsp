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

    <div class="light-sphere top-lgiht"></div>
    <div class="light-sphere purple-light"></div>

        <jsp:include page="../../highlight-frame.jsp">
            <jsp:param name="title" value="Categorias Disponíveis"/>
            <jsp:param name="subtitle" value="Clique no card para acessar seu formulário."/>
            <jsp:param name="frameCss" value="p-0 m-0 text-center"/>
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

<script>
    const CONTEXT_PATH = '${pageContext.request.contextPath}';

    document.addEventListener('DOMContentLoaded', function() {
        updateCategoryImage(); 
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

    async function updateCategoryImage() {
        const defaultImgPath = CONTEXT_PATH  + '/assets/svg/ICON-CATEGORY-DEFAULT.svg';
        const inputElement = document.getElementById('newCategory-code');
        const code = inputElement ? inputElement.value : ''; 
        console.log('code: ' + code);
        
        const imgElement = document.getElementById('categoryImage');
        const newImageUrl = CONTEXT_PATH  + '/assets/svg/ICON-CATEGORY-' + code + '.svg';
        console.log(newImageUrl);

        try {
            const response = await fetch(newImageUrl, { 
                method: 'HEAD' 
            });
            if (response.ok) imgElement.src = newImageUrl;
            else imgElement.src = defaultImgPath;
            imgElement.onerror = null;
        } 
        catch (error) {
            console.error("Erro ao verificar o recurso de imagem:", error);
            imgElement.src = defaultImgPath;
        }
    }

    function applyColorEffect() {
        const colorInputElement = document.getElementById('newCategory-color');
        if (!colorInputElement) {
            console.warn("Ellement #newCategory-color not found.");
            return;
        }
        const hexColor = colorInputElement.value;
        const imgElement = document.getElementById('newCategory-name');
        const hexPattern = /^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})$/;

        if (hexPattern.test(hexColor)) {
            imgElement.style.color = hexColor;
        } 
        else {
            console.warn('Invalid HEX value: ' +  hexColor);
        }
    }
</script>