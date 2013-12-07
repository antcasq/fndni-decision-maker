package pt.cyberRabbit.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.cyberRabbit.shared.domain.exceptions.DomainException;
import pt.cyberRabbit.shared.dto.InquiryResultSummaryDTO;
import pt.cyberRabbit.shared.dto.InquiryStatusDTO;
import pt.cyberRabbit.shared.dto.UserDTO;

/**
 * Servlet implementation class DecisionMakerServlet
 */
@WebServlet("/DecisionMakerServlet")
public class DecisionMakerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String M_LOGOUT = "logout";
	public static final String M_ANSWER_INQUIRY = "answerInquiry";
	public static final String M_PUBLISH_INQUIRY_RESULT = "publishInquiryResult";
	public static final String M_VIEW_ALL_ELECTORS = "viewAllElectors";
	public static final String M_VIEW_INQUIRIES = "viewInquiries";
	public static final String M_VIEW_INQUIRY_RESULT = "viewInquiryResult";
	public static final String M_VIEW_INQUIRY_STATUS = "viewInquiryStatus";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DecisionMakerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		doWork(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		doWork(request, response);
	}

	private void printParams(HttpServletRequest request) {
		for (String parameterName : request.getParameterMap().keySet()) {
			System.out.println(parameterName + ":"
					+ request.getParameter(parameterName));
		}
	}

	private void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		printParams(request);

		final String method = request.getParameter("method");
		if (M_LOGOUT.equals(method)) {
			logout(request);
			response.sendRedirect("index.jsp?message=message.info.logout.done.please.close.all.browser.windows");
			return;
		}

		if (!isAuthenticated(request)) {
			response.sendRedirect("index.jsp");
			return;
		}

		refreshUserData(request);

		if (!isUserValid(request)) {
			logout(request);
			response.sendRedirect("index.jsp?message=message.error.invalid.user");
			return;
		}

		if (M_ANSWER_INQUIRY.equals(method)) {
			answerInquiry(request, response);
			return;
		}

		if (M_VIEW_INQUIRIES.equals(method)) {
			viewInquiries(request, response);
			return;
		}

		if (M_VIEW_INQUIRY_RESULT.equals(method)) {
			viewInquiryResult(request, response);
			return;
		}

		if (M_VIEW_INQUIRY_STATUS.equals(method)) {
			viewInquiryStatus(request, response);
			return;
		}

		if (M_VIEW_ALL_ELECTORS.equals(method)) {
			viewAllElectors(request, response);
			return;
		}

		if (M_PUBLISH_INQUIRY_RESULT.equals(method)) {
			publishInquiryResult(request, response);
			return;
		}

		throw new UnsupportedOperationException("Unexpected method: " + method);
	}

	private void publishInquiryResult(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String username = getAuthenticatedUser(request);
		final String inquiryCode = request.getParameter("inquiryCode");

		try {
			DecisionMakerFacade.publishInquiryResult(username, inquiryCode);

			// Refresh data in userBean in request scope,
			// otherwise the system thinks the inquiry
			// has not been published yet.
			refreshUserData(request);

			request.getRequestDispatcher(M_VIEW_INQUIRIES + ".jsp").forward(
					request, response);
		} catch (DomainException e) {
			request.setAttribute("message", e.getMessage());
			forwardToDefaultView(request, response);
		}
	}

	private void forwardToDefaultView(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(M_ANSWER_INQUIRY + ".jsp").forward(
				request, response);
	}

	private void viewInquiryResult(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String username = getAuthenticatedUser(request);
		final String inquiryCode = request.getParameter("inquiryCode");

		try {
			final InquiryStatusDTO inquiryStatus = DecisionMakerFacade
					.getInquiryStatus(username, inquiryCode);
			request.setAttribute("inquiryStatus", inquiryStatus);

			final InquiryResultSummaryDTO inquiryResultSummary = DecisionMakerFacade
					.getInquiryResult(username, inquiryCode);
			request.setAttribute("inquiryResultSummary", inquiryResultSummary);

			request.getRequestDispatcher(M_VIEW_INQUIRY_RESULT + ".jsp")
					.forward(request, response);
		} catch (DomainException e) {
			request.setAttribute("message", e.getMessage());
			forwardToDefaultView(request, response);
		}
	}

	private void viewInquiryStatus(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String username = getAuthenticatedUser(request);
		final String inquiryCode = request.getParameter("inquiryCode");

		try {
			final InquiryStatusDTO inquiryStatus = DecisionMakerFacade
					.getInquiryStatus(username, inquiryCode);
			request.setAttribute("inquiryStatus", inquiryStatus);

			request.getRequestDispatcher(M_VIEW_INQUIRY_STATUS + ".jsp")
					.forward(request, response);
		} catch (DomainException e) {
			request.setAttribute("message", e.getMessage());
			forwardToDefaultView(request, response);
		}
	}

	private void viewInquiries(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(M_VIEW_INQUIRIES + ".jsp").forward(
				request, response);
	}

	private void viewAllElectors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String username = getAuthenticatedUser(request);
		try {
			final List<UserDTO> users = DecisionMakerFacade
					.getAllUsers(username);
			request.setAttribute("users", users);

			request.getRequestDispatcher(M_VIEW_ALL_ELECTORS + ".jsp").forward(
					request, response);
		} catch (DomainException e) {
			request.setAttribute("message", e.getMessage());
			forwardToDefaultView(request, response);
		}
	}

	private void answerInquiry(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final List<Long> answers = buildAnswers(request);
		try {
			if (answers != null && !answers.isEmpty()) {
				final String username = getAuthenticatedUser(request);
				final String inquiryCode = request.getParameter("inquiryCode");
				DecisionMakerFacade.answerInquiry(username, inquiryCode,
						answers);

				// Refresh data in userBean in request scope,
				// otherwise the system thinks the inquiry
				// has not been answered yet.
				refreshUserData(request);
			}
			forwardToDefaultView(request, response);
		} catch (DomainException e) {
			request.setAttribute("message", e.getMessage());
			forwardToDefaultView(request, response);
		}
	}

	private List<Long> buildAnswers(HttpServletRequest request) {
		final List<Long> answers = new ArrayList<>();

		for (String key : request.getParameterMap().keySet()) {
			if (key.startsWith("answer")) {
				String[] value = request.getParameterMap().get(key);
				answers.add(Long.valueOf(value[0]));
			}
		}

		return answers;
	}

	private boolean isUserValid(HttpServletRequest request) {
		if (request.getAttribute("userBean") == null) {
			return false;
		}
		return true;
	}

	private void refreshUserData(HttpServletRequest request) {
		final String username = getAuthenticatedUser(request);
		try {
			final UserDTO user = DecisionMakerFacade
					.findUserByUsername(username);
			request.setAttribute("userBean", user);
		} catch (DomainException e) {
			request.setAttribute("message", e.getMessage());
		}
	}

	private String getAuthenticatedUser(HttpServletRequest request) {
		final String username = (String) request.getSession().getAttribute(
				"authUser");
		return username;
	}

	private boolean isAuthenticated(HttpServletRequest request) {
		if (getAuthenticatedUser(request) == null) {
			return false;
		}
		return true;
	}

	private void logout(HttpServletRequest request) {
		request.getSession().removeAttribute("authUser");
		request.getSession().invalidate();
	}
}
