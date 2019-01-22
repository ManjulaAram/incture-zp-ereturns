package com.incture.zp.ereturns.dto;

import java.util.List;

public class DuplicateMaterialDto {

	private List<String> materials;
	
	private boolean duplicate;

	public List<String> getMaterials() {
		return materials;
	}

	public void setMaterials(List<String> materials) {
		this.materials = materials;
	}

	public boolean isDuplicate() {
		return duplicate;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}
	
}
