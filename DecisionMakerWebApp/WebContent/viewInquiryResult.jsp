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
		<h1>${inquiryResultSummary.inquiry.inquiryCode}-
			${inquiryResultSummary.inquiry.name}</h1>

		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th><fmt:message key="label.answered" /></th>
					<th><fmt:message key="label.notAnswered" /></th>
					<th><fmt:message key="label.total" /></th>
					<th><fmt:message key="label.answered.percentage" /></th>
					<th><fmt:message key="label.notAnswered.percentage" /></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${inquiryStatus.numberAnswers}</td>
					<td>${inquiryStatus.numberNotAnswered}</td>
					<td>${inquiryStatus.numberElectors}</td>
					<td>${inquiryStatus.haveAnsweredPercentage}%</td>
					<td>${inquiryStatus.haveNotAnsweredPercentage}%</td>
				</tr>
			</tbody>
		</table>
		<br />
		<c:forEach var="inquiryResultQuestion"
			items="${inquiryResultSummary.inquiryResultQuestions}">
			<h2>${inquiryResultQuestion.question}</h2>
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th><fmt:message key="label.inquiry.answer" /></th>
						<th><fmt:message key="label.inquiry.answer.votes" /></th>
						<%-- 						<th><fmt:message key="label.inquiry.answer.percentage" /></th> --%>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="inquiryResultAnswer"
						items="${inquiryResultQuestion.inquiryResultAnswers}">
						<tr>
							<td>${inquiryResultAnswer.answer}</td>
							<td>${inquiryResultAnswer.votes}</td>
							<%-- 							<td>${inquiryResultAnswer.percentage}</td> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
			<fmt:message key="label.inquiry.answer.votes" />: ${inquiryResultQuestion.votes}
		</c:forEach>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
