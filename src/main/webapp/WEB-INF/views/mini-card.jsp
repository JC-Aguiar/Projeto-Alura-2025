<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="subtitle" value="${param.subtitle}" />
<c:set var="id" value="${param.id}" />
<c:set var="img" value="${param.img}" />
<c:set var="name" value="${param.name}" />
<c:set var="color" value="${param.color}" />
<c:set var="courses" value="${param.courses}" />
<c:set var="url" value="${param.onClickUrl}" />

<div class="d-flex justify-content-center">
    <c:if test="${url != null}">
        <a href="${url}" style="text-decoration: none">
    </c:if>
            
            <div class="mini-card" id="${id}" data-category-code="${id}">
                <img class="mb-2" src="${img}">
                <div style="color: ${color}">
                    <p class="mini-card-subtitle"><small>${subtitle}</small></p>
                    <p class="mini-card-category">${name}</p>
                </div>
                <p class="mini-card-courses">${courses}</p>
            </div>

    <c:if test="${url != null}">
        </a>
    </c:if>
</div>