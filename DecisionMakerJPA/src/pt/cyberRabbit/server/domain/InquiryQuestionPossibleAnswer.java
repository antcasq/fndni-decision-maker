package pt.cyberRabbit.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.dto.InquiryQuestionPossibleAnswerDTO;

@Entity
@Table(name = "INQUIRY_QUESTION_POSSIBLE_ANSWER")
public class InquiryQuestionPossibleAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String imageUrl;

	// [start] Relations
	@ManyToOne
	private InquiryQuestion inquiryQuestion;

	// [end] Relations

	public InquiryQuestionPossibleAnswer() {
		super();
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

	public InquiryQuestion getInquiryQuestion() {
		return inquiryQuestion;
	}

	public void setInquiryQuestion(InquiryQuestion inquiryQuestion) {
		this.inquiryQuestion = inquiryQuestion;
	}

	public static InquiryQuestionPossibleAnswer create(
			final InquiryQuestion inquiryQuestion,
			final InquiryQuestionPossibleAnswerDTO inquiryQuestionPossibleAnswerDTO) {
		InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer = new InquiryQuestionPossibleAnswer();

		inquiryQuestionPossibleAnswer.setName(inquiryQuestionPossibleAnswerDTO
				.getName());
		inquiryQuestionPossibleAnswer
				.setImageUrl(inquiryQuestionPossibleAnswerDTO.getImageUrl());

		// Set relations
		inquiryQuestionPossibleAnswer.setInquiryQuestion(inquiryQuestion);

		ServerHelper.getInstance().getEntityManager()
				.persist(inquiryQuestionPossibleAnswer);
		return inquiryQuestionPossibleAnswer;
	}

	public InquiryQuestionPossibleAnswerDTO buildDTO() {
		return new InquiryQuestionPossibleAnswerDTO(getId(), getName(),
				getImageUrl());
	}

}
