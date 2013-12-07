package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class WorkingUnitAnswerStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	private WorkingUnitDTO workingUnit;

	private Integer numberElectors;
	private Integer numberAnswers;
	private BigDecimal haveAnsweredPercentage;
	private BigDecimal haveNotAnsweredPercentage;

	public WorkingUnitAnswerStatusDTO() {
		super();
	}

	public WorkingUnitAnswerStatusDTO(final List<ElectorDTO> electors) {
		this();
		if (electors != null && !electors.isEmpty()) {
			this.workingUnit = electors.get(0).getUser().getWorkingUnit();
			this.numberElectors = electors.size();

			// Count the number of answers
			int votes = 0;
			for (ElectorDTO electorDTO : electors) {
				if (!electorDTO.getUser().getWorkingUnit().getAcronym()
						.equals(workingUnit.getAcronym())) {
					throw new IllegalArgumentException(
							"The electors must all belong to the same working unit.");
				}

				if (electorDTO.getHasVoted()) {
					votes++;
				}
			}
			this.numberAnswers = votes;

			// Calculate the percentages
			this.haveAnsweredPercentage = new BigDecimal(numberAnswers)
					.multiply(ONE_HUNDRED)
					.divide(new BigDecimal(numberElectors), 2,
							RoundingMode.HALF_UP)
					.setScale(2, RoundingMode.HALF_UP);

			this.haveNotAnsweredPercentage = ONE_HUNDRED.subtract(
					getHaveAnsweredPercentage()).setScale(2,
					RoundingMode.HALF_UP);
		}
	}

	public WorkingUnitDTO getWorkingUnit() {
		return workingUnit;
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

		sb.append("acronym");
		sb.append("|");
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

		sb.append(getWorkingUnit().getAcronym());
		sb.append("|");
		sb.append(getNumberElectors());
		sb.append("|");
		sb.append(getNumberAnswers());
		sb.append("|");
		sb.append(getHaveAnsweredPercentage());
		sb.append("%");
		sb.append(" / ");
		sb.append(getHaveNotAnsweredPercentage());
		sb.append("%");

		return sb.toString();
	}

}
