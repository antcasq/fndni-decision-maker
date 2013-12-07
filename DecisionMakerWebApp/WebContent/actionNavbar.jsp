<%@page import="pt.cyberRabbit.server.DecisionMakerServlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pt.cyberRabbit.i18n.messages" />

<%
	String selectedAction = request.getParameter("method");
%>

<div id="top">
	<div id="logo"><img src="images/logo-system.png" /></div>
	<div id="user"><fmt:message key="label.user" />: ${userBean.name} (${userBean.role})</div>
</div>
<div id="navtop">
	<ul>
		<li><a href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_LOGOUT%>"><fmt:message key="link.logout" /></a></li>
		
		<%-- Highlight selected action --%>
		<li><a <%=DecisionMakerServlet.M_VIEW_ALL_ELECTORS.equals(selectedAction)?"class='selected'":""%> href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_VIEW_ALL_ELECTORS%>"><fmt:message key="link.viewAllElectors" /></a></li>
		<li><a <%=DecisionMakerServlet.M_VIEW_INQUIRIES.equals(selectedAction)?"class='selected'":""%> href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_VIEW_INQUIRIES%>"><fmt:message key="link.viewInquiries" /></a></li>
		<c:if test="${userBean.hasPendingInquiries}">
			<li><a <%=DecisionMakerServlet.M_ANSWER_INQUIRY.equals(selectedAction)?"class='selected'":""%> href="DecisionMakerServlet?method=<%=DecisionMakerServlet.M_ANSWER_INQUIRY%>"><fmt:message key="link.answerInquiry" /></a></li>
		</c:if>
	</ul>
</div>

<%-- Show error messages --%>
<%
	String message = (String) request.getAttribute("message");
	if (message != null) {
%>
<div class="errorInfo" style="font-size: large;">
	<fmt:message key="<%=message%>" />
</div>
<br/>
<%
	}
%>

