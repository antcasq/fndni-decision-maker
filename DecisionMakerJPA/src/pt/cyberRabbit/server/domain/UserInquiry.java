package pt.cyberRabbit.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.dto.UserInquiryAnswerDTO;
import pt.cyberRabbit.shared.dto.UserInquiryDTO;
import pt.cyberRabbit.shared.util.Pair;

@Entity
@Table(name = "USER_INQUIRY")
public class UserInquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// [start] FK
	private String inquiryCodeFK;
	// [end] FK

	// [start] Relations
	@OneToOne(mappedBy = "userInquiry")
	private UserInquiryRegistry userInquiryRegistry;

	@OneToMany(mappedBy = "userInquiry", fetch = FetchType.LAZY)
	private List<UserInquiryAnswer> userInquiryAnswers;

	// [end] Relations

	public UserInquiry() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInquiryCodeFK() {
		return inquiryCodeFK;
	}

	public void setInquiryCodeFK(String inquiryCodeFK) {
		this.inquiryCodeFK = inquiryCodeFK;
	}

	public UserInquiryRegistry getUserInquiryRegistry() {
		return userInquiryRegistry;
	}

	public void setUserInquiryRegistry(UserInquiryRegistry userInquiryRegistry) {
		this.userInquiryRegistry = userInquiryRegistry;
	}

	public List<UserInquiryAnswer> getUserInquiryAnswers() {
		return userInquiryAnswers;
	}

	public void setUserInquiryAnswers(List<UserInquiryAnswer> userInquiryAnswers) {
		this.userInquiryAnswers = userInquiryAnswers;
	}

	public static UserInquiry create(final String inquiryCodeFK) {
		UserInquiry userInquiry = new UserInquiry();

		userInquiry.setInquiryCodeFK(inquiryCodeFK);

		ServerHelper.getInstance().getEntityManager().persist(userInquiry);
		return userInquiry;
	}

	public static UserInquiry createWithResponse(final String inquiryCodeFK,
			final List<Pair<String, String>> response) {
		UserInquiry userInquiry = new UserInquiry();
		userInquiry.setInquiryCodeFK(inquiryCodeFK);
		ServerHelper.getInstance().getEntityManager().persist(userInquiry);

		// Add answers
		for (Pair<String, String> pair : response) {
			String question = pair.getFirst();
			String answer = pair.getSecond();
			UserInquiryAnswer.create(userInquiry, question, answer);
		}

		return userInquiry;
	}

	public UserInquiryDTO buildDTO() {
		UserInquiryDTO userInquiry = new UserInquiryDTO(getId(),
				getInquiryCodeFK(), null);

		List<UserInquiryAnswerDTO> userInquiryAnswersDTO = new ArrayList<UserInquiryAnswerDTO>();
		for (UserInquiryAnswer userInquiryAnswer : getUserInquiryAnswers()) {
			userInquiryAnswersDTO.add(userInquiryAnswer.buildDTO());
		}
		userInquiry.setUserInquiryAnswers(userInquiryAnswersDTO);

		return userInquiry;
	}

	public static List<UserInquiry> findAllByInquiryCode(
			final String inquiryCode) {
		EntityManager em = ServerHelper.getInstance().getEntityManager();

		Query query = em
				.createQuery("SELECT e FROM UserInquiry e WHERE e.inquiryCodeFK = :inquiryCode");
		query.setParameter("inquiryCode", inquiryCode);

		List<UserInquiry> userInquiries = new ArrayList<UserInquiry>();
		for (Object elem : query.getResultList()) {
			userInquiries.add((UserInquiry) elem);
		}

		return userInquiries;
	}
}
