package pt.cyberRabbit.shared.dto;

import java.io.Serializable;

public class InquiryQuestionPossibleAnswerDTO implements Serializable,
		Comparable<InquiryQuestionPossibleAnswerDTO> {

	private static final long serialVersionUID = 1L;

	public static final String BLANK_VOTE_IMAGE_NAME = "blankVote.png";
	public static final String INVALID_VOTE_IMAGE_NAME = "invalidVote.png";

	private Long id;

	private String name;

	private String imageUrl;

	public InquiryQuestionPossibleAnswerDTO() {
		super();
	}

	public InquiryQuestionPossibleAnswerDTO(Long id, String name,
			String imageUrl) {
		this();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
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

	@Override
	public int compareTo(InquiryQuestionPossibleAnswerDTO o) {
		if (BLANK_VOTE_IMAGE_NAME.equals(this.getImageUrl())) {
			if (InquiryQuestionPossibleAnswerDTO.INVALID_VOTE_IMAGE_NAME
					.equals(o.getImageUrl())) {
				return 1;
			} else {
				return 1;
			}
		}
		if (INVALID_VOTE_IMAGE_NAME.equals(this.getImageUrl())) {
			if (InquiryQuestionPossibleAnswerDTO.BLANK_VOTE_IMAGE_NAME.equals(o
					.getImageUrl())) {
				return -1;
			} else {
				return 1;
			}
		}
		return this.getName().compareTo(o.getName());
	}

}
