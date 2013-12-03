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

	<c:if test="${userBean.hasPendingInquiries}">
		<h1>
			<fmt:message key="title.what.do.you.think" />
		</h1>
		<c:set var="userInquiryRegistry"
			value="${userBean.oldestPendingInquiry}" scope="page" />
				${userInquiryRegistry.inquiry.inquiryCode}
				-
				${userInquiryRegistry.inquiry.name}
		<form action="DecisionMakerServlet" method="post">
			<input type="hidden" name="method" value="answerInquiry"> <input
				type="hidden" name="inquiryCode"
				value="${userInquiryRegistry.inquiry.inquiryCode}">

			<c:forEach var="inquiryQuestion"
				items="${userInquiryRegistry.inquiry.inquiryQuestions}">
				<img src="images/${inquiryQuestion.imageUrl}" />
				<h2>${inquiryQuestion.name}</h2>
				<c:forEach var="inquiryQuestionPossibleAnswer"
					items="${inquiryQuestion.inquiryQuestionPossibleAnswersSorted}">
					<input type="radio" name="answer${inquiryQuestion.id}"
						id="id${inquiryQuestionPossibleAnswer.id}"
						value="${inquiryQuestionPossibleAnswer.id}" />
					<label for="id${inquiryQuestionPossibleAnswer.id}"><img
						src="images/${inquiryQuestionPossibleAnswer.imageUrl}">
						${inquiryQuestionPossibleAnswer.name}</label>
					<br />
				</c:forEach>
			</c:forEach>
			<br />
			<%-- 			<input type="reset" --%>
			<%-- 				value="<fmt:message key="button.form.reset" />" />&nbsp; --%>
			<input type="submit" value="<fmt:message key='button.form.submit' />" />
		</form>
	</c:if>

	<c:if test="${!userBean.hasPendingInquiries}">
		Actualmente não possui inquéritos por responder.
	</c:if>
</html>
