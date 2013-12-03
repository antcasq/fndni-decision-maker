package pt.cyberRabbit.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.domain.exceptions.DomainException;
import pt.cyberRabbit.shared.dto.ElectorDTO;
import pt.cyberRabbit.shared.dto.InquiryDTO;
import pt.cyberRabbit.shared.dto.UserDTO;
import pt.cyberRabbit.shared.dto.UserInquiryDTO;
import pt.cyberRabbit.shared.dto.UserInquiryRegistryDTO;

@Entity
@Table(name = "USER_INQUIRY_REGISTRY")
public class UserInquiryRegistry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date submitDate;

	// [start] FK
	private String inquiryCodeFK;
	// [end] FK

	// [start] Relations
	@ManyToOne
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	private UserInquiry userInquiry;

	// [end] Relations

	public UserInquiryRegistry() {
		super();
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

	public String getInquiryCodeFK() {
		return inquiryCodeFK;
	}

	public void setInquiryCodeFK(String inquiryCodeFK) {
		this.inquiryCodeFK = inquiryCodeFK;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserInquiry getUserInquiry() {
		return userInquiry;
	}

	public void setUserInquiry(UserInquiry userInquiry) {
		this.userInquiry = userInquiry;
	}

	public static UserInquiryRegistry create(final String inquiryCodeFK,
			final User user) {
		if (user.hasInquiryRegistry(inquiryCodeFK)) {
			throw new DomainException(
					"error.userInquiryRegistry.already.exists");
		}

		UserInquiryRegistry userInquiry = new UserInquiryRegistry();

		userInquiry.setInquiryCodeFK(inquiryCodeFK);
		userInquiry.setUser(user);

		ServerHelper.getInstance().getEntityManager().persist(userInquiry);
		return userInquiry;
	}

	public static List<UserInquiryRegistry> findAllByInquiryCode(
			final String inquiryCode) {
		EntityManager em = ServerHelper.getInstance().getEntityManager();

		Query query = em
				.createQuery("SELECT e FROM UserInquiryRegistry e WHERE e.inquiryCodeFK = :inquiryCode");
		query.setParameter("inquiryCode", inquiryCode);

		List<UserInquiryRegistry> userInquiryRegistrys = new ArrayList<UserInquiryRegistry>();
		for (Object elem : query.getResultList()) {
			userInquiryRegistrys.add((UserInquiryRegistry) elem);
		}

		return userInquiryRegistrys;
	}

	public UserInquiryRegistryDTO buildDTO() {
		UserInquiryRegistryDTO userInquiryRegistry = new UserInquiryRegistryDTO(
				getId(), getSubmitDate(), null, null);

		final String inquiryCode = getInquiryCodeFK();
		Inquiry inquiry = Inquiry.findByInquiryCode(inquiryCode);
		InquiryDTO inquiryDTO = inquiry.buildDTO();
		userInquiryRegistry.setInquiry(inquiryDTO);

		// Check if the registry is connected to the answer
		if (getUserInquiry() != null) {
			UserInquiryDTO userInquiryDTO = getUserInquiry().buildDTO();
			userInquiryRegistry.setUserInquiry(userInquiryDTO);
		}

		return userInquiryRegistry;
	}

	public ElectorDTO buildElectorDTO() {
		UserDTO userDTO = getUser().buildWithoutRegistriesDTO();
		UserInquiryRegistryDTO userInquiryRegistryDTO = new UserInquiryRegistryDTO(
				getId(), getSubmitDate(), null, null);

		ElectorDTO elector = new ElectorDTO(userDTO, userInquiryRegistryDTO);
		return elector;
	}

	public void submit() {
		submit(null);
	}

	public void submit(UserInquiry userInquiry) {
		setSubmitDate(new Date());
		setUserInquiry(userInquiry);

		ServerHelper.getInstance().getEntityManager().persist(this);
	}

}
