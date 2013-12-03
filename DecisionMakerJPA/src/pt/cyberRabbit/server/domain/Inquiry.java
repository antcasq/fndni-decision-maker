package pt.cyberRabbit.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.domain.exceptions.DomainException;
import pt.cyberRabbit.shared.dto.ElectorDTO;
import pt.cyberRabbit.shared.dto.InquiryDTO;
import pt.cyberRabbit.shared.dto.InquiryQuestionDTO;
import pt.cyberRabbit.shared.dto.InquiryResultSummaryDTO;
import pt.cyberRabbit.shared.dto.UserInquiryDTO;
import pt.cyberRabbit.shared.util.Pair;

@Entity
@Table(name = "INQUIRY")
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String inquiryCode;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	/**
	 * This flag is used allow the answers to the inquiry be anonymous. When
	 * Boolean.TRUE, the submitted inquiry is not connected to the user.
	 */
	private Boolean anonymous;

	/**
	 * This flag is used to prevent someone from answering the inquiry after it
	 * has been officially closed and protect against tempting with the server
	 * system time in order to allow additional answers.
	 */
	private Boolean resultPublished;

	// [start] Relations
	@OneToMany(mappedBy = "inquiry", fetch = FetchType.LAZY)
	private List<InquiryQuestion> inquiryQuestions;

	// [end] Relations

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInquiryCode() {
		return inquiryCode;
	}

	public void setInquiryCode(String inquiryCode) {
		this.inquiryCode = inquiryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}

	public Boolean getResultPublished() {
		return resultPublished;
	}

	public void setResultPublished(Boolean resultPublished) {
		this.resultPublished = resultPublished;
	}

	public List<InquiryQuestion> getInquiryQuestions() {
		return inquiryQuestions;
	}

	public void setInquiryQuestions(List<InquiryQuestion> inquiryQuestions) {
		this.inquiryQuestions = inquiryQuestions;
	}

	// ------------------------------------------------------------------------
	// [start] Business methods
	// ------------------------------------------------------------------------

	public static Inquiry create(final String inquiryCode, final String name,
			final Date beginDate, final Date endDate, final Boolean anonymous,
			final Boolean resultPublished) {
		checkIfDuplicate(inquiryCode);

		Inquiry inquiry = new Inquiry();
		inquiry.setInquiryCode(inquiryCode);
		inquiry.setName(name);
		inquiry.setBeginDate(beginDate);
		inquiry.setEndDate(endDate);
		inquiry.setAnonymous(anonymous);
		inquiry.setResultPublished(resultPublished);

		ServerHelper.getInstance().getEntityManager().persist(inquiry);
		return inquiry;
	}

	public static Inquiry create(final InquiryDTO inquiryDTO) {
		checkIfDuplicate(inquiryDTO.getInquiryCode());

		Inquiry inquiry = new Inquiry();
		inquiry.setInquiryCode(inquiryDTO.getInquiryCode());
		inquiry.setName(inquiryDTO.getName());
		inquiry.setBeginDate(inquiryDTO.getBeginDate());
		inquiry.setEndDate(inquiryDTO.getEndDate());
		inquiry.setAnonymous(inquiryDTO.getAnonymous());
		inquiry.setResultPublished(inquiryDTO.getResultPublished());

		List<InquiryQuestion> inquiryQuestions = new ArrayList<InquiryQuestion>();
		for (InquiryQuestionDTO inquiryQuestionDTO : inquiryDTO
				.getInquiryQuestions()) {
			InquiryQuestion inquiryQuestion = InquiryQuestion.create(inquiry,
					inquiryQuestionDTO);
			inquiryQuestions.add(inquiryQuestion);
		}

		// Set relations
		inquiry.setInquiryQuestions(inquiryQuestions);

		ServerHelper.getInstance().getEntityManager().persist(inquiry);
		return inquiry;
	}

	private static void checkIfDuplicate(final String inquiryCode) {
		if (inquiryCode == null) {
			throw new IllegalArgumentException("missing.inquiry.code");
		}

		Inquiry inquiry = findByInquiryCode(inquiryCode);
		if (inquiry != null) {
			throw new DomainException("error.duplicate.inquiry");
		}
	}

	public static Inquiry findByInquiryCode(String inquiryCode) {
		try {
			EntityManager em = ServerHelper.getInstance().getEntityManager();

			Query query = em
					.createQuery("SELECT e FROM Inquiry e WHERE e.inquiryCode = :inquiryCode");
			query.setParameter("inquiryCode", inquiryCode);

			return (Inquiry) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public InquiryDTO buildDTO() {
		InquiryDTO inquiry = new InquiryDTO(getId(), getName(), getBeginDate(),
				getEndDate(), getAnonymous(), getResultPublished(),
				getInquiryCode(), null);

		List<InquiryQuestionDTO> inquiryQuestionsDTO = new ArrayList<InquiryQuestionDTO>();
		for (InquiryQuestion inquiryQuestion : getInquiryQuestions()) {
			inquiryQuestionsDTO.add(inquiryQuestion.buildDTO());
		}
		inquiry.setInquiryQuestions(inquiryQuestionsDTO);

		return inquiry;
	}

	public boolean hasPossibleAnswer(Long answer) {
		for (InquiryQuestion inquiryQuestion : getInquiryQuestions()) {
			for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : inquiryQuestion
					.getInquiryQuestionPossibleAnswers()) {
				if (inquiryQuestionPossibleAnswer.getId().equals(answer)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Pair<String, String>> buildInquiryResponse(
			final List<Long> answerIds) {
		List<Pair<String, String>> responses = new ArrayList<Pair<String, String>>();

		final List<InquiryQuestionPossibleAnswer> inquiryQuestionPossibleAnswers = findPossibleAnswers(answerIds);
		for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : inquiryQuestionPossibleAnswers) {
			final String question = inquiryQuestionPossibleAnswer
					.getInquiryQuestion().getName();
			final String answer = inquiryQuestionPossibleAnswer.getName();

			responses.add(new Pair<String, String>(question, answer));
		}

		return responses;
	}

	public List<InquiryQuestionPossibleAnswer> findPossibleAnswers(
			final List<Long> answerIds) {
		final List<InquiryQuestionPossibleAnswer> inquiryQuestionPossibleAnswers = new ArrayList<InquiryQuestionPossibleAnswer>();

		for (Long answerId : answerIds) {
			InquiryQuestionPossibleAnswer elem = ServerHelper.getInstance()
					.getEntityManager()
					.find(InquiryQuestionPossibleAnswer.class, answerId);
			inquiryQuestionPossibleAnswers.add(elem);
		}

		return inquiryQuestionPossibleAnswers;
	}

	public void openInquiry() {
		List<User> users = User.getAll();
		for (User user : users) {
			UserInquiryRegistry.create(getInquiryCode(), user);
		}
	}

	public List<ElectorDTO> getElectors() {
		List<UserInquiryRegistry> userInquiryRegistrys = UserInquiryRegistry
				.findAllByInquiryCode(getInquiryCode());

		List<ElectorDTO> electors = new ArrayList<ElectorDTO>(
				userInquiryRegistrys.size());
		for (UserInquiryRegistry userInquiryRegistry : userInquiryRegistrys) {
			electors.add(userInquiryRegistry.buildElectorDTO());
		}

		return electors;
	}

	public boolean isOpen() {
		final Date now = new Date();
		if (now.after(getBeginDate()) && now.before(getEndDate())
				&& !Boolean.TRUE.equals(getResultPublished())) {
			return true;
		}

		return false;
	}

	public InquiryResultSummaryDTO getInquiryResult() {
		// Check if the inquiry response period is open
		if (isOpen()) {
			throw new DomainException(
					"error.inquiry.response.period.still.open");
		}

		List<UserInquiry> userInquiries = UserInquiry
				.findAllByInquiryCode(getInquiryCode());

		List<UserInquiryDTO> userInquiriesDTO = new ArrayList<>(
				userInquiries.size());
		for (UserInquiry userInquiry : userInquiries) {
			userInquiriesDTO.add(userInquiry.buildDTO());
		}

		InquiryResultSummaryDTO result = new InquiryResultSummaryDTO(buildDTO(),
				userInquiriesDTO);
		return result;
	}

	public void publishResult() {
		if (Boolean.TRUE.equals(getResultPublished())) {
			throw new DomainException("error.inquiry.result.already.published");
		}

		// Check if the inquiry response period is open
		if (isOpen()) {
			throw new DomainException(
					"error.inquiry.response.period.still.open");
		}

		// Set flag
		setResultPublished(Boolean.TRUE);

		// Store
		ServerHelper.getInstance().getEntityManager().persist(this);
	}
}
