<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="${param.title}" />
<c:set var="subtitle" value="${param.subtitle}" />
<c:set var="buttonText" value="${param.buttonText}" />
<c:set var="url" value="${param.onClickUrl}" />
<c:set var="frameCss" value="${param.frameCss}" />

<style>
    .highlight {
        /* 
        Figma values:
            width: 500;
            height: 250;
            top: 63px;
            left: 123px;
            angle: 0 deg;
            opacity: 1;
            border-radius: 5px;
            border-width: 0.5px;
        */
        border-radius: 5px;
        border-width: 0.5px;
        padding: 16px 8px 16px 8px;
        color: white;
        position: relative;
        display: block;
        text-align: center;
        overflow: hidden;
        background: var(--color-figma-frame-dark) !important;
    }

    .highlight-title {
        font-weight: 700;
        font-size: clamp(16px, 1rem, 24px);
        line-height: 100%;
        letter-spacing: 0.5px;
        text-align: center;
    }

    .highlight-subtitle {
        font-weight: 500;
        font-size: 12px;
        line-height: 100%;
        letter-spacing: 0.3px;
        text-align: center;
    }
    
    .highlight-button {
        min-width: 40px;
        width: 100%;
        max-width: 500px;
        height: clamp(28px, 2em, 46px);
        opacity: 1;
        border: none;
        font-weight: 900;
        font-size: clamp(12px, 1vh, 14px);;
        border-radius: 24px;
        letter-spacing: 1.42px;
        text-transform: uppercase;
        color: var(--color-figma-bg-dark);
        background-color: var(--color-figma-btn-light);
    }

    @media (max-width: 768px) {
        .highlight {
            width: calc(100vw - 16px);
            position: fixed !important;
            margin: 8px;
            padding: 0 8px 0 8px;
            height: var(--banner-height) !important;
            max-height: var(--banner-height) !important;
            top: 0;
            left: 0;
        }
    }
</style>

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