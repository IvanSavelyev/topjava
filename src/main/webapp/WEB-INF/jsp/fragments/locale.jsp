<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>
<ul>
  <li><a href="?sessionLocale=en">EN</a></li>
  <li><a href="?sessionLocale=ru">RU</a></li>
</ul>
<c:if test="${not empty param.cookieLocale}">
  Success
  <button><a href="#">На страницу</a></button>
</c:if>
