package pt.cyberRabbit.shared.dto;

import java.io.Serializable;

public class UserInquiryAnswerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String question;

	private String answer;

	public UserInquiryAnswerDTO() {
		super();
	}

	public UserInquiryAnswerDTO(Long id, String question, String answer) {
		this();
		this.id = id;
		this.question = question;
		this.answer = answer;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getId());
		sb.append("#");
		sb.append(getQuestion());
		sb.append("#");
		sb.append(getAnswer());

		return sb.toString();
	}

}
