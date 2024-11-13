package com.rental.ezcars.dto;

import java.util.List;

public class MakeModelDTO {
	 private List<String> makes;
	    private List<String> models;
	    
	    public MakeModelDTO(List<String> makes, List<String> models) {
	        this.makes = makes;
	        this.models = models;
	    }
	    
	    
		public List<String> getMakes() {
			return makes;
		}
		public void setMakes(List<String> makes) {
			this.makes = makes;
		}
		public List<String> getModels() {
			return models;
		}
		public void setModels(List<String> models) {
			this.models = models;
		}
	    
	    
}
