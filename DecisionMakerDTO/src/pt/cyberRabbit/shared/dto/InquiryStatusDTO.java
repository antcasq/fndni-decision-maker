package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class InquiryStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	private InquiryDTO inquiry;

	private List<WorkingUnitAnswerStatusDTO> workingUnitsAnswerStatus;
	private List<ElectorDTO> electors;

	private Integer numberElectors;
	private Integer numberAnswers;
	private BigDecimal haveAnsweredPercentage;
	private BigDecimal haveNotAnsweredPercentage;

	public InquiryStatusDTO() {
		super();
	}

	public InquiryStatusDTO(final InquiryDTO inquiry,
			final List<ElectorDTO> electors) {
		this();
		this.inquiry = inquiry;
		this.electors = electors;
		this.numberElectors = electors.size();

		// Count the number of answers
		int votes = 0;
		for (ElectorDTO electorDTO : electors) {
			if (electorDTO.getHasVoted()) {
				votes++;
			}
		}
		this.numberAnswers = votes;

		// Calculate the percentages
		this.haveAnsweredPercentage = new BigDecimal(numberAnswers)
				.multiply(ONE_HUNDRED)
				.divide(new BigDecimal(numberElectors), 2, RoundingMode.HALF_UP)
				.setScale(2, RoundingMode.HALF_UP);

		this.haveNotAnsweredPercentage = ONE_HUNDRED.subtract(
				getHaveAnsweredPercentage()).setScale(2, RoundingMode.HALF_UP);

		// Build the WorkingUnitsAnswerStatusDTOs
		Map<String, List<ElectorDTO>> mapElectors = new HashMap<>();
		for (ElectorDTO electorDTO : electors) {
			String key = electorDTO.getUser().getWorkingUnit().getAcronym();
			ElectorDTO value = electorDTO;
			List<ElectorDTO> workingUnitElectors = mapElectors.get(key);
			if (workingUnitElectors == null) {
				workingUnitElectors = new ArrayList<ElectorDTO>();
				mapElectors.put(key, workingUnitElectors);
			}
			workingUnitElectors.add(value);
		}

		this.workingUnitsAnswerStatus = new ArrayList<>();
		for (String workinkUnitAcronym : new TreeSet<>(mapElectors.keySet())) {
			List<ElectorDTO> workingUnitElectors = mapElectors
					.get(workinkUnitAcronym);
			workingUnitsAnswerStatus.add(new WorkingUnitAnswerStatusDTO(
					workingUnitElectors));
		}
	}

	public InquiryDTO getInquiry() {
		return inquiry;
	}

	public List<WorkingUnitAnswerStatusDTO> getWorkingUnitsAnswerStatus() {
		return workingUnitsAnswerStatus;
	}

	public List<ElectorDTO> getElectors() {
		return electors;
	}

	public Integer getNumberElectors() {
		return numberElectors;
	}

	public Integer getNumberAnswers() {
		return numberAnswers;
	}

	public Integer getNumberNotAnswered() {
		return numberElectors - numberAnswers;
	}

	public BigDecimal getHaveAnsweredPercentage() {
		return haveAnsweredPercentage;
	}

	public BigDecimal getHaveNotAnsweredPercentage() {
		return haveNotAnsweredPercentage;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("numberElectors");
		sb.append("|");
		sb.append("numberAnswers");
		sb.append("|");
		sb.append("answered / not answered");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(inquiry.toString());
		sb.append(System.lineSeparator());

		sb.append("[Global status]");
		sb.append(InquiryStatusDTO.getToStringHeader());
		sb.append(getNumberElectors());
		sb.append("|");
		sb.append(getNumberAnswers());
		sb.append("|");
		sb.append(getHaveAnsweredPercentage());
		sb.append("%");
		sb.append(" / ");
		sb.append(getHaveNotAnsweredPercentage());
		sb.append("%");
		sb.append(System.lineSeparator());

		sb.append("[Working units status]");
		sb.append(WorkingUnitAnswerStatusDTO.getToStringHeader());
		sb.append(System.lineSeparator());
		for (WorkingUnitAnswerStatusDTO workingUnitAnswerStatus : getWorkingUnitsAnswerStatus()) {
			sb.append(workingUnitAnswerStatus.toString());
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

}
