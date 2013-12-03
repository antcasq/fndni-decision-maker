<%@page import="pt.cyberRabbit.server.DecisionMakerServlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pt.cyberRabbit.i18n.messages" />

<table style="width: 100%">
	<tbody>
		<tr>
			<td><fmt:message key="label.user" />: ${userBean.name}
				(${userBean.role})</td>
			<td><a
				href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_LOGOUT%>"><fmt:message
						key="link.logout" /></a></td>
		</tr>
	</tbody>
</table>
<table>
	<tbody>
		<tr>
			<td><a
				href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_VIEW_ALL_ELECTORS%>">
					<fmt:message key="link.viewAllElectors" />
			</a></td>
			<td><a
				href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_VIEW_INQUIRIES%>">
					<fmt:message key="link.viewInquiries" />
			</a></td>
			<c:if test="${userBean.hasPendingInquiries}">
				<td><a
					href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_ANSWER_INQUIRY%>">
						<fmt:message key="link.answerInquiry" />
				</a></td>
			</c:if>
		</tr>
	</tbody>
</table>
<br />


<%-- Show error messages --%>
<%
	String message = (String) request.getAttribute("message");
	if (message != null) {
%>
<style>
.errorInfo {
	padding: .5em;
	background-color: red;
	color: white;
}
</style>
<div class="errorInfo" style="font-size: large;">
	<fmt:message key="<%=message%>" />
</div>
<br/>
<%
	}
%>

