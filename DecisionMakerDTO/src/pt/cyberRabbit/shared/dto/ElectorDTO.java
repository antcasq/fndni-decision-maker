package pt.cyberRabbit.shared.dto;

import java.io.Serializable;

public class ElectorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserDTO user;
	private UserInquiryRegistryDTO userInquiryRegistry;

	public ElectorDTO() {
		super();
	}

	public ElectorDTO(UserDTO user, UserInquiryRegistryDTO userInquiryRegistry) {
		this();
		this.user = user;
		this.userInquiryRegistry = userInquiryRegistry;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public UserInquiryRegistryDTO getUserInquiryRegistry() {
		return userInquiryRegistry;
	}

	public void setUserInquiryRegistry(
			UserInquiryRegistryDTO userInquiryRegistry) {
		this.userInquiryRegistry = userInquiryRegistry;
	}

	public static String getToStringHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("id");
		sb.append("|");
		sb.append("username");
		sb.append("|");
		sb.append("name");
		sb.append("|");
		sb.append("email");
		sb.append("|");
		sb.append("role");
		sb.append("|");
		sb.append("workingUnit");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getToStringHeader());
		sb.append(System.lineSeparator());
		sb.append(user.getId());
		sb.append("|");
		sb.append(user.getUsername());
		sb.append("|");
		sb.append(user.getName());
		sb.append("|");
		sb.append(user.getWorkingUnit().getAcronym());
		sb.append(System.lineSeparator());

		if (userInquiryRegistry != null) {
			sb.append(userInquiryRegistry.toString());
		}

		return sb.toString();
	}

	// ------------------------------------------------------------------------
	// Business methods
	// ------------------------------------------------------------------------
	public boolean getHasVoted() {
		return userInquiryRegistry.isSubmited();
	}

}
