package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class UserInquiryRegistryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Date submitDate;

	private UserInquiryDTO userInquiry;
	private InquiryDTO inquiry;

	public UserInquiryRegistryDTO() {
		super();
	}

	public UserInquiryRegistryDTO(Long id, Date submitDate,
			UserInquiryDTO userInquiry, InquiryDTO inquiry) {
		this();
		this.id = id;
		this.submitDate = submitDate;
		this.userInquiry = userInquiry;
		this.inquiry = inquiry;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public UserInquiryDTO getUserInquiry() {
		return userInquiry;
	}

	public void setUserInquiry(UserInquiryDTO userInquiry) {
		this.userInquiry = userInquiry;
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public void setInquiry(InquiryDTO inquiry) {
		this.inquiry = inquiry;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("id.registry");
		sb.append("|");
		sb.append(InquiryDTO.getToStringHeader());
		sb.append("|");
		sb.append("submitDate");
		sb.append("|");
		sb.append("answers");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getToStringHeader());
		sb.append(System.lineSeparator());
		sb.append(getId());
		sb.append("|");
		sb.append(getInquiry().toStringSummary());
		sb.append("|");
		sb.append(getSubmitDate());
		sb.append(System.lineSeparator());

		if (getUserInquiry() != null) {
			for (UserInquiryAnswerDTO userInquiryAnswer : getUserInquiry()
					.getUserInquiryAnswers()) {
				sb.append(userInquiryAnswer.toString());
				sb.append("|");
			}

			// Remove trailing "|"
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	// ------------------------------------------------------------------------
	// Business methods
	// ------------------------------------------------------------------------

	public boolean getIsActive() {
		return isActive();
	}

	public boolean getIsSubmited() {
		return isSubmited();
	}

	public boolean isActive() {
		return getInquiry().isActive();
	}

	public boolean isSubmited() {
		if (getSubmitDate() == null) {
			return false;
		}

		return true;
	}
}
