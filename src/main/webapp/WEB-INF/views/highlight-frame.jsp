<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="${param.title}" />
<c:set var="subtitle" value="${param.subtitle}" />
<c:set var="buttonText" value="${param.buttonText}" />
<c:set var="url" value="${param.onClickUrl}" />
<c:set var="frameCss" value="${param.frameCss}" />

<div class="highlight frame ${frameCss}">
    <div class="light-sphere purple-light inside"></div>

    <div class="py-0 py-lg-4 px-0 px-lg-3 h-100">
        <div class="row justify-content-center h-100 align-items-center m-0 p-0">
            
            <c:if test="${buttonText != null}">
                <div class="col-12 col-sm-6 col-md-12 row align-items-center text-white small">
            </c:if>
                    <div class="pt-1 pt-sm-2">
                        <c:if test="${title != null}">
                            <h2 class="highlight-title mb-1 mb-sm-2">
                                ${title}
                            </h2>
                        </c:if>

                        <c:if test="${subtitle != null}">
                            <p class="d-none d-sm-block highlight-subtitle">
                                ${subtitle}
                            </p>
                        </c:if>
                    </div>
            <c:if test="${buttonText != null}">
                </div>
            </c:if>

            <div class="col d-flex align-items-center px-0 px-sm-2 mt-0 mt-sm-1">
                <c:if test="${buttonText != null}">
                    <div class="container mt-0 mt-md-2">
                        <button 
                            type="button" 
                            class="highlight-button" 
                            onclick="window.location.href='${url}'"
                        >
                            ${buttonText} 
                        </button>
                    </div>
                </c:if>
            </div>

        </div>
    </div>

</div>