package pt.cyberRabbit.shared.dto;

import java.io.Serializable;

public class WorkingUnitDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String acronym;

	private String name;

	private String hierarchy;

	public WorkingUnitDTO() {
		super();
	}

	public WorkingUnitDTO(Long id, String acronym, String name, String hierarchy) {
		this();
		this.id = id;
		this.acronym = acronym;
		this.name = name;
		this.hierarchy = hierarchy;
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

}
