<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pt.cyberRabbit.i18n.messages" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="${language}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="title.system.name" /></title>
<link href="main.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<jsp:include page="actionNavbar.jsp" />

	<div id="body_content">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th><fmt:message key="label.user.name" /></th>
<%-- 					<th><fmt:message key="label.workingUnit.name" /></th> --%>
<%-- 					<th><fmt:message key="label.workingUnit.acronym" /></th> --%>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${users}">
					<tr>
						<td>${user.name}</td>
<%-- 						<td>${user.workingUnit.name}</td> --%>
<%-- 						<td>${user.workingUnit.acronym}</td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<hr />
		<fmt:message key="message.info.hr.based.data" />
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
