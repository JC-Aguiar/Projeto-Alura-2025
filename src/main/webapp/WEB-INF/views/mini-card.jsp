<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="subtitle" value="${param.subtitle}" />
<c:set var="id" value="${param.id}" />
<c:set var="img" value="${param.img}" />
<c:set var="title" value="${param.title}" />
<c:set var="color" value="${param.color}" />
<c:set var="description" value="${param.description}" />
<c:set var="url" value="${param.onClickUrl}" />

<c:set var="categoryCodeValue" value="${fn:escapeXml(empty code ? '' : code)}" />

<div class="d-flex justify-content-center">
    <c:if test="${url != null}">
        <a href="${url}" style="text-decoration: none">
    </c:if>
            
            <div   
                class="mini-card" 
                id="mini-card-${id}" 
                data-category-code="${id}" 
                data-category-code-value="${categoryCodeValue}"
             >
                <img class="mb-2" id="categoryImage-${id}" src="${fn:escapeXml(img)}" 
                     onerror="this.src='${pageContext.request.contextPath}/assets/svg/ICON-CATEGORY-DEFAULT.svg'" 
                     alt="Category Image">
                <div style="color: ${fn:escapeXml(color)}">
                    <p class="mini-card-subtitle"><small>${fn:escapeXml(subtitle)}</small></p>
                    <p class="mini-card-title">${fn:escapeXml(title)}</p>
                </div>
                <p class="mini-card-description">${fn:escapeXml(description)}</p>
            </div>

    <c:if test="${url != null}">
        </a>
    </c:if>
</div>

<script>
    // Function to handle image loading and fallback to default
    function updateImageSrc(code, imgElementId) {
        const defaultImgPath = '${pageContext.request.contextPath}/assets/svg/ICON-CATEGORY-DEFAULT.svg';
        const imgElement = document.getElementById(imgElementId);
        
        if (!imgElement) {
            console.error(`Image element with ID ${imgElementId} not found`);
            return;
        }

        // If no image source is provided or image fails to load, use default
        if (!imgElement.src || imgElement.src === window.location.href) {
            imgElement.src = defaultImgPath;
            return;
        }

        // Check if the image exists
        const testImage = new Image();
        testImage.onerror = () => {
            console.warn(`Image ${imgElement.src} not found, using default image`);
            imgElement.src = defaultImgPath;
        };
        testImage.src = imgElement.src;
    }
    
    // Call the function immediately with the correct parameters
    updateImageSrc('${fn:escapeXml(categoryCodeValue)}', 'categoryImage-${fn:escapeXml(id)}');
</script>