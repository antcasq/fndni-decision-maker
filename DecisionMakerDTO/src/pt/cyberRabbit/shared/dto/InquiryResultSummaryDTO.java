package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class InquiryResultSummaryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private InquiryDTO inquiry;
	private List<InquiryResultQuestionDTO> inquiryResultQuestions;

	public InquiryResultSummaryDTO() {
		super();
	}

	public InquiryResultSummaryDTO(final InquiryDTO inquiry,
			final List<UserInquiryDTO> userInquiries) {
		this();
		this.inquiry = inquiry;

		Map<String, List<String>> mapQuestionsAndAnswers = new HashMap<String, List<String>>();
		for (UserInquiryDTO userInquiryDTO : userInquiries) {
			for (UserInquiryAnswerDTO userInquiryAnswerDTO : userInquiryDTO
					.getUserInquiryAnswers()) {
				String key = userInquiryAnswerDTO.getQuestion();
				String value = userInquiryAnswerDTO.getAnswer();
				List<String> answers = mapQuestionsAndAnswers.get(key);
				if (answers == null) {
					answers = new ArrayList<String>();
					mapQuestionsAndAnswers.put(key, answers);
				}
				answers.add(value);
			}
		}

		this.inquiryResultQuestions = new ArrayList<>();
		for (String question : new TreeSet<>(mapQuestionsAndAnswers.keySet())) {
			List<String> answers = mapQuestionsAndAnswers.get(question);
			inquiryResultQuestions.add(new InquiryResultQuestionDTO(question,
					answers));
		}
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public List<InquiryResultQuestionDTO> getInquiryResultQuestions() {
		return inquiryResultQuestions;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(inquiry.toString());
		sb.append(System.lineSeparator());

		for (InquiryResultQuestionDTO inquiryResultQuestion : getInquiryResultQuestions()) {
			sb.append(inquiryResultQuestion.toString());
		}

		return sb.toString();
	}

}
