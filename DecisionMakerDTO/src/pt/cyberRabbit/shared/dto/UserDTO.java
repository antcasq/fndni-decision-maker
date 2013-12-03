package pt.cyberRabbit.shared.dto;

import java.io.Serializable;
import java.util.List;

import pt.cyberRabbit.shared.domain.enums.Role;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String name;

	private String email;

	private Role role;

	private WorkingUnitDTO workingUnit;

	private List<UserInquiryRegistryDTO> userInquiryRegistrys;

	public UserDTO() {
		super();
	}

	public UserDTO(Long id, String username, String name, String email,
			Role role, WorkingUnitDTO workingUnit,
			List<UserInquiryRegistryDTO> userInquiryRegistrys) {
		this();
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.role = role;
		this.workingUnit = workingUnit;
		this.userInquiryRegistrys = userInquiryRegistrys;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public WorkingUnitDTO getWorkingUnit() {
		return workingUnit;
	}

	public void setWorkingUnit(WorkingUnitDTO workingUnit) {
		this.workingUnit = workingUnit;
	}

	public List<UserInquiryRegistryDTO> getUserInquiryRegistrys() {
		return userInquiryRegistrys;
	}

	public void setUserInquiryRegistrys(
			List<UserInquiryRegistryDTO> userInquiryRegistrys) {
		this.userInquiryRegistrys = userInquiryRegistrys;
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
		sb.append(getId());
		sb.append("|");
		sb.append(getUsername());
		sb.append("|");
		sb.append(getName());
		sb.append("|");
		sb.append(getEmail());
		sb.append("|");
		sb.append(getRole());
		sb.append("|");
		sb.append(getWorkingUnit().getAcronym());
		sb.append(System.lineSeparator());

		if (!getUserInquiryRegistrys().isEmpty()) {
			for (UserInquiryRegistryDTO userInquiryRegistry : getUserInquiryRegistrys()) {
				sb.append(userInquiryRegistry.toString());
				sb.append(System.lineSeparator());
			}
		}

		return sb.toString();
	}

	// ------------------------------------------------------------------------
	// Business methods
	// ------------------------------------------------------------------------
	public boolean getHasAnyInquiries() {
		if (getUserInquiryRegistrys() != null
				&& !getUserInquiryRegistrys().isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean getHasPendingInquiries() {
		return hasPendingInquiries();
	}

	public boolean hasPendingInquiries() {
		if (getUserInquiryRegistrys() != null
				&& !getUserInquiryRegistrys().isEmpty()) {
			for (UserInquiryRegistryDTO userInquiryRegistry : getUserInquiryRegistrys()) {
				if (userInquiryRegistry.isActive()
						&& !userInquiryRegistry.isSubmited()) {
					return true;
				}
			}
		}

		return false;
	}

	public UserInquiryRegistryDTO getOldestPendingInquiry() {
		if (hasPendingInquiries()) {
			UserInquiryRegistryDTO oldest = null;

			for (UserInquiryRegistryDTO userInquiryRegistry : getUserInquiryRegistrys()) {
				if (userInquiryRegistry.isActive()
						&& !userInquiryRegistry.isSubmited()) {
					if (oldest == null
							|| oldest
									.getInquiry()
									.getBeginDate()
									.after(userInquiryRegistry.getInquiry()
											.getBeginDate())) {
						oldest = userInquiryRegistry;
					}
				}
			}

			return oldest;
		}

		return null;
	}

	public boolean getIsAdmin() {
		return Role.ADMIN.equals(role);
	}

}
