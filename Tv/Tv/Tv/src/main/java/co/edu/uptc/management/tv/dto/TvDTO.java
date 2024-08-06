package co.edu.uptc.management.tv.dto;

import java.io.Serializable;

public class TvDTO implements Serializable {

	private String serialNumber;
	private String resolution;
	private String sizeDisplay;
	private String technologyDisplay;
	private String systemOperational;

	/** constructor vacio */

	public TvDTO() {
	}

	public TvDTO(String serialNumber, String resolution, String sizeDisplay, String technologyDisplay, String systemOperational) {
        this.serialNumber = serialNumber;
        this.resolution = resolution;
        this.sizeDisplay = sizeDisplay;
        this.technologyDisplay = technologyDisplay;
        this.systemOperational = systemOperational;
    }

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getSizeDisplay() {
		return sizeDisplay;
	}

	public void setSizeDisplay(String sizeDisplay) {
		this.sizeDisplay = sizeDisplay;
	}

	public String getTechnologyDisplay() {
		return technologyDisplay;
	}

	public void setTechnologyDisplay(String technologyDisplay) {
		this.technologyDisplay = technologyDisplay;
	}

	public String getSystemOperational() {
		return systemOperational;
	}

	public void setSystemOperational(String systemOperational) {
		this.systemOperational = systemOperational;
	}

	@Override
	public String toString() {
		return "Tv [serialNumber=" + serialNumber + ", resolution=" + resolution + ", sizeDisplay=" + sizeDisplay
				+ ", technologyDisplay=" + technologyDisplay + ", systemOperational=" + systemOperational + "]";
	}

}
