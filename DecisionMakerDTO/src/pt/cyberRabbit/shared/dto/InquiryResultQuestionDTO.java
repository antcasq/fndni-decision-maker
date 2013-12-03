package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class InquiryResultQuestionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String question;
	private Integer votes;
	private List<InquiryResultAnswerDTO> inquiryResultAnswers;

	public InquiryResultQuestionDTO() {
		super();
	}

	public InquiryResultQuestionDTO(String question, List<String> answers) {
		this();
		this.question = question;
		this.votes = answers.size();
		initAnswers(answers);
	}

	private void initAnswers(List<String> answers) {
		Map<String, Integer> mapAnswers = new HashMap<String, Integer>();
		for (String answer : answers) {
			Integer value = mapAnswers.get(answer);
			if (value == null) {
				value = new Integer(0);
			}
			value++;
			mapAnswers.put(answer, value);
		}

		int count = 1;
		final int size = mapAnswers.size();
		inquiryResultAnswers = new ArrayList<InquiryResultAnswerDTO>(size);
		BigDecimal ONE_HUNDRED = new BigDecimal(100);
		BigDecimal VOTES = new BigDecimal(votes);
		BigDecimal sumPercentage = BigDecimal.ZERO;

		// Sort answers to ensure always the same order.
		// it's important to ensure consistent percentages.
		for (String answer : new TreeSet<String>(mapAnswers.keySet())) {
			Integer answerVotes = mapAnswers.get(answer);

			BigDecimal answerPercentage;
			if (size == 1) {
				answerPercentage = ONE_HUNDRED;
			} else {
				if (count != size) {
					answerPercentage = new BigDecimal(answerVotes)
							.multiply(ONE_HUNDRED)
							.divide(VOTES, 2, RoundingMode.HALF_UP)
							.setScale(2, RoundingMode.HALF_UP);
					sumPercentage = sumPercentage.add(answerPercentage);
				} else {
					// To ensure that the sum is always 100%
					// Last percentage = (100 - Sum of previous percentages)
					answerPercentage = ONE_HUNDRED.subtract(sumPercentage)
							.setScale(2, RoundingMode.HALF_UP);
				}
				count++;
			}

			inquiryResultAnswers.add(new InquiryResultAnswerDTO(answer,
					answerVotes, answerPercentage));
		}
	}

	public String getQuestion() {
		return question;
	}

	public Integer getVotes() {
		return votes;
	}

	public List<InquiryResultAnswerDTO> getInquiryResultAnswers() {
		return inquiryResultAnswers;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("question");
		sb.append("|");
		sb.append("votes");

		return sb.toString();
	}

	StringBuilder sb = new StringBuilder();

	public String toStringSummary() {
		StringBuilder sb = new StringBuilder();

		sb.append(getQuestion());
		sb.append("|");
		sb.append(getVotes());

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getToStringHeader());
		sb.append(System.lineSeparator());
		sb.append(toStringSummary());
		sb.append(System.lineSeparator());

		for (InquiryResultAnswerDTO inquiryResultAnswer : getInquiryResultAnswers()) {
			sb.append(inquiryResultAnswer.toString());
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

}
