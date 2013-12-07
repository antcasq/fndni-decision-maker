<%@page import="com.danter.google.auth.GoogleAuthHelper"%>
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
<style>
body {
	font-family: Verdana, Arial, Helvetica, sans-serif;
}

.oauthDemo a {
	font-family:Verdana, Arial, Helvetica, sans-serif;
	background-color:#83ae5c;
	font-weight:bold; 
	font-size:11px; 
	color:#333 !important; 
	cursor: pointer;
	text-decoration:none;
	padding:4px;
	border-bottom: 2px solid #8c8a80 !important;
	border-right: 2px solid #8c8a80;
	border-top: 2px solid #f8f7f0;
	border-left: 2px solid #f8f7f0;
	margin: 10px;
}

.oauthDemo:focus:hover {
	font-family:Verdana, Arial, Helvetica, sans-serif;
	background-color:#83ae5c;
	font-weight:bold; 
	font-size:11px; 
	color:#333 !important; 
	cursor: pointer;
	text-decoration:none;
	padding:4px;
	border-bottom: 2px solid #f8f7f0 !important;
	border-right: 2px solid #f8f7f0;
	border-top: 2px solid #8c8a80;
	border-left: 2px solid #8c8a80;
	margin: 10px;
}

.oauthDemo pre {
	background: #ccc;
}
#container {
	margin: 100px auto 0 auto;
	background: #ffffff;
	width: 40%;
	border: 1px solid #113344;
	padding: 0px 0px 20px 0px;
}

.oauthDemo a:active {
	border-color: #666 #aaa #bbb #888;
	border-width: 2px 1px 1px 2px;
	color: #000;
}

.readme {
	padding: .5em;
	background-color: #F9AD81;
	color: #333;
	margin: 10px
}
#top {
	height: 78px;
	position: relative;
	background-color: #424242;
}

#top #logo {
	padding-top: 5px;
	padding-left: 25px;
	margin: 0;
}

</style>
</head>
<body>
<div id="container">

<div id="top">
	<div id="logo"><img src="images/logo-system.png" /></div>
</div>
	<%-- Show error messages --%>
	<%
		String message = request.getParameter("message");
		if (message != null) {
	%>
	<div class="readme" style="font-size: large;">
		<fmt:message key="<%=message%>" />
	</div>
	<%
		}
	%>

	<%-- If authenticated, send to Inquiry view JSP --%>
	<%
		String username = (String) session.getAttribute("authUser");
		if (username != null) {
			response.sendRedirect("DecisionMakerServlet?method=answerInquiry");
			return;
		}
	%>

	<div class="oauthDemo">
		<%
			/*
			 * The GoogleAuthHelper handles all the heavy lifting, and contains all "secrets"
			 * required for constructing a google login url.
			 */
			final GoogleAuthHelper helper = new GoogleAuthHelper();

			if (request.getParameter("code") == null
					|| request.getParameter("state") == null) {
				// initial visit to the page
		%>
		<br /><br /> <div align="center"><a href="<%=helper.buildLoginUrl()%>"> <fmt:message
				key="link.login" /></a></div><br />
		<%
			/*
				 * set the secure state token in session to be able to track what we sent to google
				 */
				session.setAttribute("state", helper.getStateToken());

			} else if (request.getParameter("code") != null
					&& request.getParameter("state") != null
					&& request.getParameter("state").equals(
							session.getAttribute("state"))) {

				session.removeAttribute("state");

				out.println("<pre>");
				/*
				 * Executes after google redirects to the callback url.
				 * Please note that the state request parameter is for convenience to differentiate
				 * between authentication methods (ex. facebook oauth, google oauth, twitter, in-house).
				 * 
				 * GoogleAuthHelper()#getUserInfoJson(String) method returns a String containing
				 * the json representation of the authenticated user's information. 
				 * At this point you should parse and persist the info.
				 */

				String userInfoJson = helper.getUserInfoJson(request
						.getParameter("code"));
				out.println(userInfoJson);
				out.println("</pre>");

				session.setAttribute("authUser",
						GoogleAuthHelper.parseUsername(userInfoJson));
				response.sendRedirect("DecisionMakerServlet?method=answerInquiry");
			}
		%>
	</div>
</div>
</body>
</html>
