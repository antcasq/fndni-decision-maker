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
<div id="body_content" style="padding-left: 100px;">
	<c:if test="${userBean.hasPendingInquiries}">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td style="padding: 10px;"><img src="images/logo-iscte.png" /></td>
				<td style="padding: 10px;"><fmt:message key='label.inquiry.instructions' /></td>
			</tr>
		</table>
		<br />
		
		<c:set var="userInquiryRegistry"
			value="${userBean.oldestPendingInquiry}" scope="page" />
<%-- 				${userInquiryRegistry.inquiry.inquiryCode} --%>
<!-- 				- -->
<%-- 				${userInquiryRegistry.inquiry.name} --%>
		<form action="DecisionMakerServlet" method="post">
			<input type="hidden" name="method" value="answerInquiry"> <input
				type="hidden" name="inquiryCode"
				value="${userInquiryRegistry.inquiry.inquiryCode}">

			<c:forEach var="inquiryQuestion"
				items="${userInquiryRegistry.inquiry.inquiryQuestions}">
<%-- 				<img src="images/${inquiryQuestion.imageUrl}" /> --%>
				<h2>${inquiryQuestion.name}</h2>
				
				<table cellpadding="0" cellspacing="0">
				<c:forEach var="inquiryQuestionPossibleAnswer"
					items="${inquiryQuestion.inquiryQuestionPossibleAnswersSorted}">
					<tr>
						<td>
						<input type="radio" name="answer${inquiryQuestion.id}"
							id="id${inquiryQuestionPossibleAnswer.id}"
							value="${inquiryQuestionPossibleAnswer.id}" />
						</td>
						<td>
						<label for="id${inquiryQuestionPossibleAnswer.id}"><img
							src="images/${inquiryQuestionPossibleAnswer.imageUrl}">
							${inquiryQuestionPossibleAnswer.name}</label>
						</td>
					</tr>
				</c:forEach>
				</table>
			</c:forEach>
			<br />
			<%-- 			<input type="reset" --%>
			<%-- 				value="<fmt:message key="button.form.reset" />" />&nbsp; --%>
			<input type="submit" value="<fmt:message key='button.form.submit' />" />
		</form>
	</c:if>

	<c:if test="${!userBean.hasPendingInquiries}">
		<fmt:message key='message.info.no.pending.inquiries' />
	</c:if>
</div>
<jsp:include page="footer.jsp" />
</html>
