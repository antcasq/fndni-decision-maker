package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InquiryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String inquiryCode;

	private String name;

	private Date beginDate;

	private Date endDate;

	private Boolean anonymous;

	private Boolean resultPublished;

	private List<InquiryQuestionDTO> inquiryQuestions;

	public InquiryDTO() {
		super();
	}

	public InquiryDTO(Long id, String name, Date beginDate, Date endDate,
			Boolean anonymous, Boolean resultPublished, String inquiryCode,
			List<InquiryQuestionDTO> inquiryQuestions) {
		this();
		this.id = id;
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.anonymous = anonymous;
		this.resultPublished = resultPublished;
		this.inquiryCode = inquiryCode;
		this.inquiryQuestions = inquiryQuestions;
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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}

	public Boolean getResultPublished() {
		return resultPublished;
	}

	public void setResultPublished(Boolean resultPublished) {
		this.resultPublished = resultPublished;
	}

	public String getInquiryCode() {
		return inquiryCode;
	}

	public void setInquiryCode(String inquiryCode) {
		this.inquiryCode = inquiryCode;
	}

	public List<InquiryQuestionDTO> getInquiryQuestions() {
		return inquiryQuestions;
	}

	public void setInquiryQuestions(List<InquiryQuestionDTO> inquiryQuestions) {
		this.inquiryQuestions = inquiryQuestions;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("id");
		sb.append("|");
		sb.append("inquiryCode");
		sb.append("|");
		sb.append("name");
		sb.append("|");
		sb.append("beginDate");
		sb.append("|");
		sb.append("endDate");
		sb.append("|");
		sb.append("anonymous");
		sb.append("|");
		sb.append("resultPublished");

		return sb.toString();
	}

	public String toStringSummary() {
		StringBuilder sb = new StringBuilder();

		sb.append(getId());
		sb.append("|");
		sb.append(getInquiryCode());
		sb.append("|");
		sb.append(getName());
		sb.append("|");
		sb.append(getBeginDate());
		sb.append("|");
		sb.append(getEndDate());
		sb.append("|");
		sb.append(getAnonymous());
		sb.append("|");
		sb.append(getResultPublished());

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getToStringHeader());
		sb.append(System.lineSeparator());
		sb.append(toStringSummary());
		sb.append(System.lineSeparator());

		sb.append(InquiryQuestionDTO.getToStringHeader());
		for (InquiryQuestionDTO inquiryQuestion : getInquiryQuestions()) {
			sb.append(inquiryQuestion.toString());
			sb.append(System.lineSeparator());
		}
		sb.append(System.lineSeparator());

		sb.append(InquiryQuestionPossibleAnswerDTO.getToStringHeader());
		for (InquiryQuestionDTO inquiryQuestion : getInquiryQuestions()) {
			for (InquiryQuestionPossibleAnswerDTO inquiryQuestionPossibleAnswer : inquiryQuestion
					.getInquiryQuestionPossibleAnswers()) {
				sb.append(inquiryQuestionPossibleAnswer.toString());
				sb.append(System.lineSeparator());
			}
		}

		return sb.toString();
	}

	// ------------------------------------------------------------------------
	// Business methods
	// ------------------------------------------------------------------------
	public boolean isActive() {
		return isActive(new Date());
	}

	public boolean isActive(final Date when) {
		if (when.before(getBeginDate()) || when.after(getEndDate())
				|| Boolean.TRUE.equals(getResultPublished())) {
			return false;
		}

		return true;
	}

	public boolean getCanViewStatus() {
		final Date now = new Date();
		if (now.before(getBeginDate())) {
			return false;
		}

		return true;
	}

	public boolean getCanViewResult() {
		return Boolean.TRUE.equals(getResultPublished());
	}

	public boolean getCanPublishResult() {
		if (Boolean.TRUE.equals(getResultPublished())) {
			return false;
		}
		if (isActive()) {
			return false;
		}

		// The inquiry happen.
		final Date now = new Date();
		if (now.before(getBeginDate())) {
			return false;
		}

		return true;
	}
}
