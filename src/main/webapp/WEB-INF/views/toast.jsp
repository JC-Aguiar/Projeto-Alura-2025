<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${param.title}" />
<c:set var="message" value="${param.message}" />
<c:set var="icon" value="${param.icon}" />

<c:if test="${not empty message and not empty title}">
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="liveToast" class="toast bg-dark" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header bg-dark frame">
                <i class="${icon}"></i>
                <strong class="me-auto text-white">
                    ${title}
                </strong>
                <button type="button" class="btn-close-primary" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body frame text-white small">
                ${message}
            </div>
        </div>
    </div>
</c:if>