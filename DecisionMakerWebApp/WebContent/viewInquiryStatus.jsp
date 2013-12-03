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
</head>
<body>
	<jsp:include page="actionNavbar.jsp" />

	<table border="1">
		<thead>
			<tr>
				<th><fmt:message key="label.user.name" /></th>
				<th><fmt:message key="label.workingUnit.name" /></th>
				<th><fmt:message key="label.inquiry.submited" /></th>
				<th><fmt:message key="label.inquiry.submitDate" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="elector" items="${electors}">
				<tr>
					<td>${elector.user.name}</td>
					<td>${elector.user.workingUnit.name}</td>
					<td>${elector.userInquiryRegistry.isSubmited}</td>
					<td><fmt:formatDate
							value="${elector.userInquiryRegistry.submitDate}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
