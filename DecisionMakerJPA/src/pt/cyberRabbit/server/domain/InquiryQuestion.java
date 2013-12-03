package pt.cyberRabbit.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.dto.InquiryQuestionDTO;
import pt.cyberRabbit.shared.dto.InquiryQuestionPossibleAnswerDTO;

@Entity
@Table(name = "INQUIRY_QUESTION")
public class InquiryQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String imageUrl;

	// [start] Relations
	@ManyToOne
	private Inquiry inquiry;

	@OneToMany(mappedBy = "inquiryQuestion", fetch = FetchType.LAZY)
	private List<InquiryQuestionPossibleAnswer> inquiryQuestionPossibleAnswers;

	// [end] Relations

	public InquiryQuestion() {
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

	public Inquiry getInquiry() {
		return inquiry;
	}

	public void setInquiry(Inquiry inquiry) {
		this.inquiry = inquiry;
	}

	public List<InquiryQuestionPossibleAnswer> getInquiryQuestionPossibleAnswers() {
		return inquiryQuestionPossibleAnswers;
	}

	public void setInquiryQuestionPossibleAnswers(
			List<InquiryQuestionPossibleAnswer> inquiryQuestionPossibleAnswers) {
		this.inquiryQuestionPossibleAnswers = inquiryQuestionPossibleAnswers;
	}

	public static InquiryQuestion create(final Inquiry inquiry,
			final InquiryQuestionDTO inquiryQuestionDTO) {
		InquiryQuestion inquiryQuestion = new InquiryQuestion();
		inquiryQuestion.setName(inquiryQuestionDTO.getName());
		inquiryQuestion.setImageUrl(inquiryQuestionDTO.getImageUrl());

		List<InquiryQuestionPossibleAnswer> inquiryQuestionPossibleAnswers = new ArrayList<InquiryQuestionPossibleAnswer>();
		for (InquiryQuestionPossibleAnswerDTO inquiryQuestionPossibleAnswerDTO : inquiryQuestionDTO
				.getInquiryQuestionPossibleAnswers()) {
			InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer = InquiryQuestionPossibleAnswer
					.create(inquiryQuestion, inquiryQuestionPossibleAnswerDTO);
			inquiryQuestionPossibleAnswers.add(inquiryQuestionPossibleAnswer);
		}

		// Set relations
		inquiryQuestion
				.setInquiryQuestionPossibleAnswers(inquiryQuestionPossibleAnswers);
		inquiryQuestion.setInquiry(inquiry);

		ServerHelper.getInstance().getEntityManager().persist(inquiryQuestion);
		return inquiryQuestion;
	}

	public InquiryQuestionDTO buildDTO() {
		InquiryQuestionDTO inquiryQuestion = new InquiryQuestionDTO(getId(),
				getName(), getImageUrl(), null);

		List<InquiryQuestionPossibleAnswerDTO> inquiryQuestionPossibleAnswersDTO = new ArrayList<InquiryQuestionPossibleAnswerDTO>();
		for (InquiryQuestionPossibleAnswer inquiryQuestionPossibleAnswer : getInquiryQuestionPossibleAnswers()) {
			inquiryQuestionPossibleAnswersDTO.add(inquiryQuestionPossibleAnswer
					.buildDTO());
		}
		inquiryQuestion
				.setInquiryQuestionPossibleAnswers(inquiryQuestionPossibleAnswersDTO);

		return inquiryQuestion;
	}

}
