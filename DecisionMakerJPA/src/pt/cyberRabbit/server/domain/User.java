package pt.cyberRabbit.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.domain.enums.Role;
import pt.cyberRabbit.shared.domain.exceptions.DomainException;
import pt.cyberRabbit.shared.dto.UserDTO;
import pt.cyberRabbit.shared.dto.UserInquiryRegistryDTO;
import pt.cyberRabbit.shared.dto.WorkingUnitDTO;
import pt.cyberRabbit.shared.util.Pair;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String name;

	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	// [start] FK
	private String workUnitAcronymFK;
	// [end] FK

	// [start] Relations
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserInquiryRegistry> userInquiryRegistrys;

	// [end] Relations

	public User() {
		super();
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

	public List<UserInquiryRegistry> getUserInquiryRegistrys() {
		return userInquiryRegistrys;
	}

	public void setUserInquiryRegistrys(
			List<UserInquiryRegistry> userInquiryRegistrys) {
		this.userInquiryRegistrys = userInquiryRegistrys;
	}

	public String getWorkUnitAcronymFK() {
		return workUnitAcronymFK;
	}

	public void setWorkUnitAcronymFK(String workUnitAcronymFK) {
		this.workUnitAcronymFK = workUnitAcronymFK;
	}

	// ------------------------------------------------------------------------
	// [start] Business methods
	// ------------------------------------------------------------------------

	public static User create(final String username, final String name,
			final String email, final Role role, final String workUnitAcronymFK) {
		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setEmail(email);
		user.setRole(role);
		user.setWorkUnitAcronymFK(workUnitAcronymFK);

		ServerHelper.getInstance().getEntityManager().persist(user);
		return user;
	}

	public static User findByUsername(String username) {
		EntityManager em = ServerHelper.getInstance().getEntityManager();
		// TypedQuery<User> query = em.createQuery(
		// "SELECT e FROM User e WHERE e.username = :username",
		// User.class);
		// query.setParameter("username", username);

		Query query = em
				.createQuery("SELECT e FROM User e WHERE e.username = :username");
		query.setParameter("username", username);

		return (User) query.getSingleResult();
	}

	public static User findByEmail(String email) {
		EntityManager em = ServerHelper.getInstance().getEntityManager();

		Query query = em
				.createQuery("SELECT e FROM User e WHERE e.email = :email");
		query.setParameter("email", email);

		return (User) query.getSingleResult();
	}

	public UserDTO buildDTO() {
		UserDTO user = buildWithoutRegistriesDTO();

		List<UserInquiryRegistryDTO> userInquiryRegistrysDTO = new ArrayList<UserInquiryRegistryDTO>();
		if (getUserInquiryRegistrys() != null) {
			for (UserInquiryRegistry userInquiryRegistry : getUserInquiryRegistrys()) {
				userInquiryRegistrysDTO.add(userInquiryRegistry.buildDTO());
			}
		}
		user.setUserInquiryRegistrys(userInquiryRegistrysDTO);

		return user;
	}

	public UserDTO buildWithoutRegistriesDTO() {
		UserDTO user = new UserDTO(getId(), getUsername(), getName(),
				getEmail(), getRole(), null, null);

		WorkingUnit workingUnit = WorkingUnit.findByAcronym(workUnitAcronymFK);
		WorkingUnitDTO workingUnitDTO = workingUnit.buildDTO();
		user.setWorkingUnit(workingUnitDTO);

		return user;
	}

	public void answerInquiry(final String inquiryCode,
			final List<Long> answerIds) throws DomainException {
		final Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);

		// Check if the inquiry code exists
		if (inquiry == null) {
			throw new DomainException("error.invalid.inquiry.code");
		}

		// Check if the inquiry response period is open
		if (!inquiry.isOpen()) {
			throw new DomainException("error.inquiry.response.period.closed");
		}

		// Check if the answers belong to the inquiry
		for (Long answerId : answerIds) {
			if (!inquiry.hasPossibleAnswer(answerId)) {
				throw new DomainException("error.invalid.answer.for.inquiry");
			}
		}

		// Check if the inquiry registry exist (it should...)
		UserInquiryRegistry userInquiryRegistry = findUserInquiryRegistry(inquiryCode);
		if (userInquiryRegistry == null) {
			throw new DomainException("error.userInquiryRegistry.not.found");
		}

		// Check if the inquiry hasn't been answered yet
		if (userInquiryRegistry.getSubmitDate() != null) {
			throw new DomainException("error.inquiry.already.answered");
		}

		// Store response
		final List<Pair<String, String>> inquiryResponse = inquiry
				.buildInquiryResponse(answerIds);

		UserInquiry userInquiry = UserInquiry.createWithResponse(inquiryCode,
				inquiryResponse);

		// Check if the inquiry is anonymous and submit it
		if (Boolean.TRUE.equals(inquiry.getAnonymous())) {
			userInquiryRegistry.submit();
		} else {
			// Connect the user response to the registry,
			// in order to know what the user answered.
			userInquiryRegistry.submit(userInquiry);
		}
	}

	private UserInquiryRegistry findUserInquiryRegistry(final String inquiryCode) {
		for (UserInquiryRegistry userInquiryRegistry : getUserInquiryRegistrys()) {
			if (inquiryCode.equals(userInquiryRegistry.getInquiryCodeFK())) {
				return userInquiryRegistry;
			}
		}
		return null;
	}

	public boolean hasInquiryRegistry(String inquiryCode) {
		return findUserInquiryRegistry(inquiryCode) != null;
	}

	public static List<User> getAll() {
		EntityManager em = ServerHelper.getInstance().getEntityManager();

		Query query = em.createQuery("SELECT e FROM User e");

		List<User> users = new ArrayList<User>();
		for (Object elem : query.getResultList()) {
			users.add((User) elem);
		}

		return users;
	}

	public static List<UserDTO> getAllWithoutRegistriesDTO() {
		return getAllDTO(false);
	}

	public static List<UserDTO> getAllWithRegistriesDTO() {
		return getAllDTO(true);
	}

	private static List<UserDTO> getAllDTO(boolean includeRegistries) {
		List<User> users = getAll();

		List<UserDTO> usersDTO = new ArrayList<UserDTO>(users.size());
		for (User user : users) {
			if (includeRegistries) {
				usersDTO.add(user.buildDTO());
			} else {
				usersDTO.add(user.buildWithoutRegistriesDTO());
			}
		}

		return usersDTO;
	}

	public boolean isAdmin() {
		return Role.ADMIN.equals(role);
	}
}
