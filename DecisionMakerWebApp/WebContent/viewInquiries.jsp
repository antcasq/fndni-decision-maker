<%@page import="pt.cyberRabbit.server.DecisionMakerServlet"%>
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
				<th><fmt:message key="label.inquiry.inquiryCode" /></th>
				<th><fmt:message key="label.inquiry.name" /></th>
				<th><fmt:message key="label.inquiry.hasAnswered" /></th>
				<th><fmt:message key="label.inquiry.isOpen" /></th>
				<th><fmt:message key="label.inquiry.beginDate" /></th>
				<th><fmt:message key="label.inquiry.endDate" /></th>
				<th><fmt:message key="label.inquiry.anonymous" /></th>
				<th><fmt:message key="label.inquiry.resultPublished" /></th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="userInquiryRegistry"
				items="${userBean.userInquiryRegistrys}">
				<tr>
					<td>${userInquiryRegistry.inquiry.inquiryCode}</td>
					<td>${userInquiryRegistry.inquiry.name}</td>
					<td>${userInquiryRegistry.isSubmited}</td>
					<td>${userInquiryRegistry.isActive}</td>
					<td><fmt:formatDate
							value="${userInquiryRegistry.inquiry.beginDate}"
							pattern="yyyy-MM-dd HH:mm" /></td>
					<td><fmt:formatDate
							value="${userInquiryRegistry.inquiry.endDate}"
							pattern="yyyy-MM-dd HH:mm" /></td>
					<td>${userInquiryRegistry.inquiry.anonymous}</td>
					<td>${userInquiryRegistry.inquiry.resultPublished}</td>
					<td>&nbsp; <c:if
							test="${userBean.isAdmin && userInquiryRegistry.inquiry.canViewStatus}">
							<a
								href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_VIEW_INQUIRY_STATUS%>
								&inquiryCode=${userInquiryRegistry.inquiry.inquiryCode}">
								<fmt:message key="link.viewStatus" />
							</a>
								&nbsp; 
						</c:if> <c:if test="${userInquiryRegistry.inquiry.canViewResult}">
							<a
								href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_VIEW_INQUIRY_RESULT%>
								&inquiryCode=${userInquiryRegistry.inquiry.inquiryCode}">
								<fmt:message key="link.viewResult" />
							</a>
								&nbsp; 
						</c:if> <c:if
							test="${userBean.isAdmin && userInquiryRegistry.inquiry.canPublishResult}">
							<a
								href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_PUBLISH_INQUIRY_RESULT%>
								&inquiryCode=${userInquiryRegistry.inquiry.inquiryCode}">
								<fmt:message key="link.publishResult" />
							</a>
								&nbsp; 
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
