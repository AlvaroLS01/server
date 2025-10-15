package com.comerzzia.pos.services.articulos;

import java.util.List;

public class DesglosesDTO {

	private String codart;
	private List<String> desgloses1;
	private List<String> desgloses2;

	public String getCodart() {
		return codart;
	}

	public void setCodart(String codart) {
		this.codart = codart;
	}

	public List<String> getDesgloses1() {
		return desgloses1;
	}

	public void setDesgloses1(List<String> desgloses1) {
		this.desgloses1 = desgloses1;
	}

	public List<String> getDesgloses2() {
		return desgloses2;
	}

	public void setDesgloses2(List<String> desgloses2) {
		this.desgloses2 = desgloses2;
	}

}
