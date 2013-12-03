package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.util.List;

public class UserInquiryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String inquiryCode;

	private List<UserInquiryAnswerDTO> userInquiryAnswers;

	public UserInquiryDTO() {
		super();
	}

	public UserInquiryDTO(Long id, String inquiryCode,
			List<UserInquiryAnswerDTO> userInquiryAnswers) {
		this();
		this.id = id;
		this.inquiryCode = inquiryCode;
		this.userInquiryAnswers = userInquiryAnswers;
	}

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

	public List<UserInquiryAnswerDTO> getUserInquiryAnswers() {
		return userInquiryAnswers;
	}

	public void setUserInquiryAnswers(
			List<UserInquiryAnswerDTO> userInquiryAnswers) {
		this.userInquiryAnswers = userInquiryAnswers;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("id");
		sb.append("|");
		sb.append("inquiryCode");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getToStringHeader());
		sb.append(System.lineSeparator());
		sb.append(getId());
		sb.append("|");
		sb.append(getInquiryCode());
		sb.append(System.lineSeparator());

		for (UserInquiryAnswerDTO userInquiryAnswer : getUserInquiryAnswers()) {
			sb.append(userInquiryAnswer.toString());
			sb.append("|");
		}

		// Remove trailing "|"
		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}
}
