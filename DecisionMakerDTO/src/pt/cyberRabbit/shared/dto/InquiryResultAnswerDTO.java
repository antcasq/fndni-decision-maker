package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InquiryResultAnswerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String answer;
	private Integer votes;
	private BigDecimal percentage;

	public InquiryResultAnswerDTO() {
		super();
	}

	public InquiryResultAnswerDTO(String answer, Integer votes,
			BigDecimal percentage) {
		this();
		this.answer = answer;
		this.votes = votes;
		this.percentage = percentage;
	}

	public String getAnswer() {
		return answer;
	}

	public Integer getVotes() {
		return votes;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("answer");
		sb.append("|");
		sb.append("votes");
		sb.append("|");
		sb.append("percentage");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getAnswer());
		sb.append("|");
		sb.append(getVotes());
		sb.append("|");
		sb.append(getPercentage());

		return sb.toString();
	}

}
