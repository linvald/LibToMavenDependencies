package dk.linvald.libtomavendependencies.liblogic;

import java.io.File;

/**
 * @author Jesper Linvald (jesper@linvald.net)
 * Keeps track of where things are...
 */
public class Locations {
	private String repositoryLocation;
	private String libLocation;
	private File pomLocation;
	/**
	 * @return Returns the libLocation.
	 */
	public String getLibLocation() {
		return libLocation;
	}
	/**
	 * @param libLocation The libLocation to set.
	 */
	public void setLibLocation(String libLocation) {
		this.libLocation = libLocation;
	}
	/**
	 * @return Returns the repositoryLocation.
	 */
	public String getRepositoryLocation() {
		return repositoryLocation;
	}
	/**
	 * @param repositoryLocation The repositoryLocation to set.
	 */
	public void setRepositoryLocation(String repositoryLocation) {
		this.repositoryLocation = repositoryLocation;
	}
    /**
     * @return Returns the pomLocation.
     */
    public File getPomLocation() {
        return pomLocation;
    }
    /**
     * @param pomLocation The pomLocation to set.
     */
    public void setPomLocation(File pomLocation) {
        this.pomLocation = pomLocation;
    }
}
