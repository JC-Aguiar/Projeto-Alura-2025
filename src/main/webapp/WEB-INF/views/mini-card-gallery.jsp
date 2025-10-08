<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="listName" value="${param.categoryListAttributeName}" />
<c:set var="categories" value="${requestScope[listName]}" />

<div class="grid">
    <c:forEach items="${categories}" var="category">
        <jsp:include page="mini-card.jsp">
            <jsp:param name="subtitle" value="Escola_"/>
            <jsp:param name="id" value="${category.code()}"/>
            <jsp:param name="img" value="../../assets/svg/ICON-CATEGORY-${category.code()}.svg"/>
            <jsp:param name="title" value="${category.name()}"/>
            <jsp:param name="color" value="${category.color()}"/>
            <jsp:param name="description" value="${category.stringifyCourses()}"/>
        </jsp:include>
    </c:forEach>
</div>