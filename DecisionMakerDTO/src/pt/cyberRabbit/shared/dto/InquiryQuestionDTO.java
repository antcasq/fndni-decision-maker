package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InquiryQuestionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String imageUrl;

	private List<InquiryQuestionPossibleAnswerDTO> inquiryQuestionPossibleAnswers;

	public InquiryQuestionDTO() {
		super();
	}

	public InquiryQuestionDTO(
			Long id,
			String name,
			String imageUrl,
			List<InquiryQuestionPossibleAnswerDTO> inquiryQuestionPossibleAnswers) {
		this();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.inquiryQuestionPossibleAnswers = inquiryQuestionPossibleAnswers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<InquiryQuestionPossibleAnswerDTO> getInquiryQuestionPossibleAnswers() {
		return inquiryQuestionPossibleAnswers;
	}

	public void setInquiryQuestionPossibleAnswers(
			List<InquiryQuestionPossibleAnswerDTO> inquiryQuestionPossibleAnswers) {
		this.inquiryQuestionPossibleAnswers = inquiryQuestionPossibleAnswers;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("id");
		sb.append("|");
		sb.append("name");
		sb.append("|");
		sb.append("imageUrl");

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getId());
		sb.append("|");
		sb.append(getName());
		sb.append("|");
		sb.append(getImageUrl());

		return sb.toString();
	}

	public List<InquiryQuestionPossibleAnswerDTO> getInquiryQuestionPossibleAnswersSorted() {
		if (inquiryQuestionPossibleAnswers == null) {
			return new ArrayList<InquiryQuestionPossibleAnswerDTO>();
		}
		Collections.sort(inquiryQuestionPossibleAnswers);

		return inquiryQuestionPossibleAnswers;
	}

}
