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
	</div>

	<hr />
	<hr />
	<hr />

	<div id="body_content">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th><fmt:message key="label.workingUnit.acronym" /></th>
					<th><fmt:message key="label.workingUnit.name" /></th>
					<th><fmt:message key="label.answered" /></th>
					<th><fmt:message key="label.notAnswered" /></th>
					<th><fmt:message key="label.total" /></th>
					<th><fmt:message key="label.answered.percentage" /></th>
					<th><fmt:message key="label.notAnswered.percentage" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="workingUnitAnswerStatus"
					items="${inquiryStatus.workingUnitsAnswerStatus}">
					<tr>
						<td>${workingUnitAnswerStatus.workingUnit.acronym}</td>
						<td>${workingUnitAnswerStatus.workingUnit.name}</td>
						<td>${workingUnitAnswerStatus.numberAnswers}</td>
						<td>${workingUnitAnswerStatus.numberNotAnswered}</td>
						<td>${workingUnitAnswerStatus.numberElectors}</td>
						<td>${workingUnitAnswerStatus.haveAnsweredPercentage}%</td>
						<td>${workingUnitAnswerStatus.haveNotAnsweredPercentage}%</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<hr />
	<hr />
	<hr />

	<div id="body_content">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th><fmt:message key="label.user.name" /></th>
					<th><fmt:message key="label.workingUnit.name" /></th>
					<th><fmt:message key="label.inquiry.submited" /></th>
					<th><fmt:message key="label.inquiry.submitDate" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="elector" items="${inquiryStatus.electors}">
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
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
