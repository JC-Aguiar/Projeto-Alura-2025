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

    <title>Cadastrar nova Categoria</title>
    
    <link rel="stylesheet" type="text/css" href="/assets/css/main.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/list-catebories.css">
    <link rel="stylesheet" type="text/css" href="/assets/external-libs/bootstrap/css/bootstrap.min.css">
    <script src="/assets/external-libs/bootstrap/js/bootstrap.bundle.js"></script>
    <style>
        .form-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
            gap: 1rem;
        }

        .input-group span {
            width: 90px;
        }
    </style>
</head>

<body>
<div class="container-md content-container p-md-3">

    <div class="light-sphere top-lgiht"></div>
    <div class="light-sphere purple-light"></div>

    <jsp:include page="../../highlight-frame.jsp">
        <jsp:param name="title" value="${id != null ? 'Editar Categoria' : 'Cadastrar Nova Categoria'}"/>
        <jsp:param name="frameCss" value="p-0 m-0"/>
    </jsp:include>
    
    <div class="mt-3">

        <c:choose>
            <c:when test="${empty id}">
                <c:set var="formActionUrl" value="/admin/category/save" />
            </c:when>
            <c:otherwise>
                <c:set var="formActionUrl" value="/admin/category/save/${id}" />
            </c:otherwise>
        </c:choose>

        <form:form 
            modelAttribute="newCategoryFormDTO" 
            cssClass="form-floating" 
            action="${formActionUrl}"
            method="post"
        >
            <div class="responsive-text h-100 frame rounded mt-3 py-4 px-3 px-lg-4">
                <div class="form-container form-group pb-5 pb-lg-3">
                    <div class="row g-3">
                        <!-- Field: name -->
                         <div>
                            <div class="input-group input-group-sm">
                                <span 
                                    id="newCategory-name-label" 
                                    class="input-group-text bg-dark text-white frame"
                                >
                                    Nome
                                </span>
                                <form:input 
                                    path="name" 
                                    id="newCategory-name" 
                                    aria-describedby="newCategory-name"
                                    cssClass="form-control frame bg-dark text-white" 
                                    required="required"
                                />
                            </div>
                            <form:errors path="name" cssClass="error"/>
                        </div>
                        <!-- Field: code -->
                         <div>
                            <div class="input-group input-group-sm">
                                <span 
                                    id="newCategory-code-label" 
                                    class="input-group-text bg-dark text-white frame"
                                >
                                    Código
                                </span>
                                <form:input 
                                    path="code" 
                                    id="newCategory-code" 
                                    aria-describedby="newCategory-code" 
                                    onkeyup="updateCategoryImage()"
                                    cssClass="form-control frame bg-dark text-white" 
                                    required="required"
                                />
                            </div>
                            <form:errors path="code" cssClass="error"/>
                        </div>
                        <!-- Field: order -->
                        <div>
                            <div class="input-group input-group-sm">
                                <span 
                                    id="newCategory-order-label" 
                                    class="input-group-text bg-dark text-white frame"
                                >
                                    Ordem
                                </span>
                                <form:input 
                                    path="order" 
                                    type="number" 
                                    min="1" 
                                    id="newCategory-order" 
                                    aria-describedby="newCategory-order" 
                                    cssClass="form-control" 
                                    required="required"
                                />
                            </div>
                            <form:errors path="order" cssClass="error"/>
                        </div>
                        <!-- Field: color -->
                        <div>
                            <div class="input-group input-group-sm h-100">
                                <span 
                                    id="newCategory-color-label" 
                                    class="input-group-text bg-dark text-white frame "
                                >
                                    Cor
                                </span>
                                <form:input 
                                    path="color" 
                                    type="color"
                                    id="newCategory-color" 
                                    aria-describedby="newCategory-color" 
                                    cssClass="form-control form-control-color bg-transparent frame m-0 p-0 h-100" 
                                    required="required"
                                />
                            </div>
                            <form:errors path="color" cssClass="error"/>
                        </div>
                    </div>

                    <div class="mx-3 p-3 frame rounded-3 bg-dark" style="max-height: 230px;">
                        <!-- Code Icon -->
                        <img 
                            class="w-100 h-100 img-fluid p-3"
                            id="categoryImage" 
                            src="../../assets/svg/ICON-CATEGORY-DEFAULT.svg" 
                            alt="Ícone da Categoria"
                            style="width: 100%; height: 100%;"
                        >
                        <input 
                            type="submit"
                            class="d-block w-100 btn btn-primary py-1 submit" 
                            value="Salvar"
                        />
                    </div>

                </div>
            </div>
        </form:form>
    </div>
</div>

<jsp:include page="../../modal.jsp">
    <jsp:param name="title" value="Problema na requisição!"/>
    <jsp:param name="message" value="${error}"/>
</jsp:include>

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