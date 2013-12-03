package pt.cyberRabbit.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.dto.UserInquiryAnswerDTO;

@Entity
@Table(name = "USER_INQUIRY_ANSWER")
public class UserInquiryAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String question;

	private String answer;

	// [start] Relations
	@ManyToOne
	private UserInquiry userInquiry;

	// [end] Relations

	public UserInquiryAnswer() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public UserInquiry getUserInquiry() {
		return userInquiry;
	}

	public void setUserInquiry(UserInquiry userInquiry) {
		this.userInquiry = userInquiry;
	}

	static UserInquiryAnswer create(final String question, final String answer) {
		UserInquiryAnswer inquiryAnswer = new UserInquiryAnswer();

		inquiryAnswer.setQuestion(question);
		inquiryAnswer.setAnswer(answer);

		ServerHelper.getInstance().getEntityManager().persist(inquiryAnswer);
		return inquiryAnswer;
	}

	static UserInquiryAnswer create(final UserInquiry userInquiry,
			final String question, final String answer) {
		UserInquiryAnswer inquiryAnswer = new UserInquiryAnswer();

		inquiryAnswer.setQuestion(question);
		inquiryAnswer.setAnswer(answer);
		inquiryAnswer.setUserInquiry(userInquiry);

		ServerHelper.getInstance().getEntityManager().persist(inquiryAnswer);
		return inquiryAnswer;
	}

	public UserInquiryAnswerDTO buildDTO() {
		return new UserInquiryAnswerDTO(getId(), getQuestion(), getAnswer());
	}

}
