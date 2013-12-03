package pt.cyberRabbit.server.domain;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import pt.cyberRabbit.server.ServerHelper;
import pt.cyberRabbit.shared.dto.WorkingUnitDTO;

@Entity
@Table(name = "WORKING_UNIT")
public class WorkingUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String acronym;

	private String name;

	private String hierarchy;

	public WorkingUnit() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public static WorkingUnit create(final String acronym, final String name,
			final String hierarchy) {
		WorkingUnit workingUnit = new WorkingUnit();
		workingUnit.setAcronym(acronym);
		workingUnit.setName(name);
		workingUnit.setHierarchy(hierarchy);

		ServerHelper.getInstance().getEntityManager().persist(workingUnit);
		return workingUnit;
	}

	public static WorkingUnit findByAcronym(String acronym) {
		EntityManager em = ServerHelper.getInstance().getEntityManager();

		Query query = em
				.createQuery("SELECT e FROM WorkingUnit e WHERE e.acronym = :acronym");
		query.setParameter("acronym", acronym);

		return (WorkingUnit) query.getSingleResult();
	}

	public WorkingUnitDTO buildDTO() {
		WorkingUnitDTO workingUnit = new WorkingUnitDTO(getId(), getAcronym(), getName(), getHierarchy());
		return workingUnit;
	}
}
