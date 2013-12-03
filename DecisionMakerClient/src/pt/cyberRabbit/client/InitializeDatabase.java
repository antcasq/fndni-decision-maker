package pt.cyberRabbit.client;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.io.FileUtils;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.server.domain.Inquiry;
import pt.cyberRabbit.server.domain.InquiryQuestion;
import pt.cyberRabbit.server.domain.InquiryQuestionPossibleAnswer;
import pt.cyberRabbit.server.domain.User;
import pt.cyberRabbit.server.domain.UserInquiry;
import pt.cyberRabbit.server.domain.UserInquiryAnswer;
import pt.cyberRabbit.server.domain.UserInquiryRegistry;
import pt.cyberRabbit.server.domain.WorkingUnit;
import pt.cyberRabbit.shared.domain.enums.Role;
import pt.cyberRabbit.shared.dto.InquiryDTO;
import pt.cyberRabbit.shared.dto.InquiryQuestionDTO;
import pt.cyberRabbit.shared.dto.InquiryQuestionPossibleAnswerDTO;

public class InitializeDatabase {
	// private static final SimpleDateFormat sdf = new SimpleDateFormat(
	// "yyyy-MM-dd");

	private static final SimpleDateFormat sdfHMS = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	public static void initDatabase() {
		delete();
		insertParam();
		// insertAnswers();
		// select();

		// closeOpenInquiries();
	}

	private static void closeOpenInquiries() {
		try {
			ServerHelper.init(true);

			Inquiry inquiry = Inquiry.findByInquiryCode("0001");
			inquiry.setEndDate(new Date());
			ServerHelper.getInstance().getEntityManager().persist(inquiry);

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
	}

	private static void select() {
		try {
			ServerHelper.init(true);

			showUsers();
			showAnonymousAnswers();

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
	}

	private static void showAnonymousAnswers() {
		System.out.println("showAnonymousAnswers()");
		EntityManager em = ServerHelper.getInstance().getEntityManager();
		TypedQuery<UserInquiry> query = em.createQuery(
				"SELECT e FROM UserInquiry e", UserInquiry.class);
		for (UserInquiry userInquiry : query.getResultList()) {
			if (userInquiry.getUserInquiryRegistry() == null) {
				System.out.print(userInquiry.buildDTO());
				System.out.println();
				System.out.println();

			}
		}
	}

	private static void showUsers() {
		System.out.println("showUsers()");
		EntityManager em = ServerHelper.getInstance().getEntityManager();
		TypedQuery<User> query = em.createQuery("SELECT e FROM User e",
				User.class);
		for (User user : query.getResultList()) {
			System.out.print(user.buildDTO());
			System.out.println();
			System.out.println();
		}
	}

	private static void insertParam() {
		try {
			ServerHelper.init(true);

			insertWorkingUnit();
			insertUser();

			insertInquiryTemplateFull();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
	}

	private static void insertAnswers() {
		try {
			ServerHelper.init(true);

			insertUserInquiryFull();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
	}

	private static void insertUserInquiryFull() {
		insertUserInquiryFullNotAnonymous();
		insertUserInquiryFullAnonymous();
	}

	private static void insertUserInquiryFullNotAnonymous() {
		{
			final String inquiryCode = "0001";
			final String answer = "Mickey";

			User user = User.findByUsername("ajsco@iscte.pt");

			Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			List<Long> answerIds = new ArrayList<Long>();
			for (InquiryQuestion inquiryQuestion : inquiry
					.getInquiryQuestions()) {
				for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : inquiryQuestion
						.getInquiryQuestionPossibleAnswers()) {
					if (answer.equals(inquiryQuestionPossibleAnswer.getName())) {
						answerIds.add(inquiryQuestionPossibleAnswer.getId());
					}
				}
			}

			user.answerInquiry(inquiryCode, answerIds);
		}

		{
			// User user = User.findByUsername("hmcb@iscte.pt");

			// Hasn't responded yet
		}

		{
			final String inquiryCode = "0001";
			final String answer = "Minnie";

			User user = User.findByUsername("rgbsa@iscte.pt");

			Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			List<Long> answerIds = new ArrayList<Long>();
			for (InquiryQuestion inquiryQuestion : inquiry
					.getInquiryQuestions()) {
				for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : inquiryQuestion
						.getInquiryQuestionPossibleAnswers()) {
					if (answer.equals(inquiryQuestionPossibleAnswer.getName())) {
						answerIds.add(inquiryQuestionPossibleAnswer.getId());
					}
				}
			}

			user.answerInquiry(inquiryCode, answerIds);
		}
	}

	private static void insertUserInquiryFullAnonymous() {
		{
			final String inquiryCode = "0002";
			final String answer = "Voto branco";

			User user = User.findByUsername("ajsco@iscte.pt");

			Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			List<Long> answerIds = new ArrayList<Long>();
			for (InquiryQuestion inquiryQuestion : inquiry
					.getInquiryQuestions()) {
				for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : inquiryQuestion
						.getInquiryQuestionPossibleAnswers()) {
					if (answer.equals(inquiryQuestionPossibleAnswer.getName())) {
						answerIds.add(inquiryQuestionPossibleAnswer.getId());
					}
				}
			}

			user.answerInquiry(inquiryCode, answerIds);
		}

		{
			final String inquiryCode = "0002";
			final String answer = "Voto nulo";

			User user = User.findByUsername("hmcb@iscte.pt");

			Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
			List<Long> answerIds = new ArrayList<Long>();
			for (InquiryQuestion inquiryQuestion : inquiry
					.getInquiryQuestions()) {
				for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : inquiryQuestion
						.getInquiryQuestionPossibleAnswers()) {
					if (answer.equals(inquiryQuestionPossibleAnswer.getName())) {
						answerIds.add(inquiryQuestionPossibleAnswer.getId());
					}
				}
			}

			user.answerInquiry(inquiryCode, answerIds);
		}

		{
			// User user = User.findByUsername("rgbsa@iscte.pt");

			// Hasn't responded yet
		}
	}

	private static void insertInquiryTemplateFull() throws IOException,
			ParseException {
		List<String> lines = FileUtils.readLines(new File("inquiry.txt"));

		InquiryDTO inquiryDTO = null;
		List<InquiryQuestionDTO> inquiryQuestions = null;
		List<InquiryQuestionPossibleAnswerDTO> inquiryQuestionPossibleAnswers = null;
		for (String line : lines) {
			String[] parts = line.split(",");
			if ("I".equals(parts[0])) {
				if (inquiryDTO != null) {
					Inquiry inquiry = Inquiry.create(inquiryDTO);
					inquiry.openInquiry();
				}

				int i = 1;
				String inquiryCode = parts[i++];
				String name = parts[i++];
				Date beginDate = sdfHMS.parse(parts[i++]);
				Date endDate = sdfHMS.parse(parts[i++]);
				Boolean anonymous = Boolean.parseBoolean(parts[i++]);
				Boolean resultPublished = Boolean.parseBoolean(parts[i++]);

				inquiryQuestions = new ArrayList<>();
				inquiryDTO = new InquiryDTO(null, name, beginDate, endDate,
						anonymous, resultPublished, inquiryCode,
						inquiryQuestions);
			}

			if ("Q".equals(parts[0])) {
				inquiryQuestionPossibleAnswers = new ArrayList<>();
				int i = 1;
				String name = parts[i++];
				String imageUrl = parts[i++];
				inquiryQuestions.add(new InquiryQuestionDTO(null, name,
						imageUrl, inquiryQuestionPossibleAnswers));
			}

			if ("A".equals(parts[0])) {
				int i = 1;
				String name = parts[i++];
				String imageUrl = parts[i++];
				inquiryQuestionPossibleAnswers
						.add(new InquiryQuestionPossibleAnswerDTO(null, name,
								imageUrl));
			}
		}

		Inquiry inquiry = Inquiry.create(inquiryDTO);
		inquiry.openInquiry();
	}

	private static void insertWorkingUnit() {
		WorkingUnit.create("SI", "Serviços Informática", "SI");
		WorkingUnit.create("UD", "Unidade Desenvolvimento", "SI/UD");
		WorkingUnit.create("NAU", "Nucleo de Apoio ao Utilizador", "SI/NAU");
	}

	private static void insertUser() throws IOException {
		// User.create("ajsco@iscte.pt", "António Casqueiro",
		// "Antonio.Casqueiro@iscte.pt", Role.ADMIN, "UD");
		// User.create("hmcb@iscte.pt", "Henrique Borges",
		// "Henrique.Borges@iscte.pt", Role.ADMIN, "NAU");
		// User.create("rgbsa@iscte.pt", "Rita Sousa", "Rita.Sousa@iscte.pt",
		// Role.USER, "UD");

		List<String> lines = FileUtils.readLines(new File("users.txt"));
		for (String line : lines) {
			String[] parts = line.split(",");

			int i = 0;
			String username = parts[i++];
			String name = parts[i++];
			String email = parts[i++];
			Role role = Role.valueOf(parts[i++]);
			String workUnitAcronymFK = parts[i++];
			User.create(username, name, email, role, workUnitAcronymFK);
		}
	}

	private static void delete() {
		try {
			ServerHelper.init(true);

			deleteUserInquiryFull();
			deleteInquiryTemplateFull();

			deleteUser();
			deleteWorkingUnit();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			ServerHelper.getInstance().releaseResources();
		}
	}

	private static void deleteUserInquiryFull() {
		EntityManager em = ServerHelper.getInstance().getEntityManager();
		{
			TypedQuery<UserInquiryAnswer> query = em.createQuery(
					"SELECT e FROM UserInquiryAnswer e",
					UserInquiryAnswer.class);
			for (UserInquiryAnswer userInquiryAnswer : query.getResultList()) {
				ServerHelper.getInstance().getEntityManager()
						.remove(userInquiryAnswer);
			}
		}

		{
			TypedQuery<UserInquiry> query = em.createQuery(
					"SELECT e FROM UserInquiry e", UserInquiry.class);
			for (UserInquiry userInquiry : query.getResultList()) {
				// Remove reference from UserInquiryRegistry if exists
				if (userInquiry.getUserInquiryRegistry() != null) {
					userInquiry.getUserInquiryRegistry().setUserInquiry(null);
				}

				ServerHelper.getInstance().getEntityManager()
						.remove(userInquiry);
			}
		}

		{
			TypedQuery<UserInquiryRegistry> query = em.createQuery(
					"SELECT e FROM UserInquiryRegistry e",
					UserInquiryRegistry.class);
			for (UserInquiryRegistry userInquiryRegistry : query
					.getResultList()) {
				// Remove reference from User if exists
				if (userInquiryRegistry.getUser() != null) {
					userInquiryRegistry.setUser(null);
				}
				ServerHelper.getInstance().getEntityManager()
						.remove(userInquiryRegistry);
			}
		}

	}

	private static void deleteInquiryTemplateFull() {
		EntityManager em = ServerHelper.getInstance().getEntityManager();
		TypedQuery<Inquiry> query = em.createQuery("SELECT e FROM Inquiry e",
				Inquiry.class);
		for (Inquiry inquiry : query.getResultList()) {
			List<InquiryQuestion> inquiryQuestions = new ArrayList<InquiryQuestion>(
					inquiry.getInquiryQuestions());

			// Remove questions
			for (InquiryQuestion inquiryQuestion : inquiryQuestions) {
				List<InquiryQuestionPossibleAnswer> inquiryQuestionPossibleAnswers = new ArrayList<InquiryQuestionPossibleAnswer>(
						inquiryQuestion.getInquiryQuestionPossibleAnswers());

				// Remove possible answers
				for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : inquiryQuestionPossibleAnswers) {
					inquiryQuestion.getInquiryQuestionPossibleAnswers().remove(
							inquiryQuestionPossibleAnswer);
					inquiryQuestionPossibleAnswer.setInquiryQuestion(null);
					ServerHelper.getInstance().getEntityManager()
							.remove(inquiryQuestionPossibleAnswer);
				}

				inquiry.getInquiryQuestions().remove(inquiryQuestion);
				inquiryQuestion.setInquiry(null);
				ServerHelper.getInstance().getEntityManager()
						.remove(inquiryQuestion);
			}

			ServerHelper.getInstance().getEntityManager().remove(inquiry);
		}
	}

	private static void deleteWorkingUnit() {
		EntityManager em = ServerHelper.getInstance().getEntityManager();
		TypedQuery<WorkingUnit> query = em.createQuery(
				"SELECT e FROM WorkingUnit e", WorkingUnit.class);
		for (WorkingUnit workingUnit : query.getResultList()) {
			ServerHelper.getInstance().getEntityManager().remove(workingUnit);
		}
	}

	private static void deleteUser() {
		EntityManager em = ServerHelper.getInstance().getEntityManager();
		TypedQuery<User> query = em.createQuery("SELECT e FROM User e",
				User.class);
		for (User user : query.getResultList()) {
			ServerHelper.getInstance().getEntityManager().remove(user);
		}
	}

}
