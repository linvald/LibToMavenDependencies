package dk.linvald.libtomavendependencies.liblogic;

import java.util.ArrayList;

/**
 * A collection of entries - each know how to copy itself in 
 * the right format to the repository
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class RepoEntryCollection {
	
	private ArrayList entries;
	
	public RepoEntryCollection() {
		this.entries = new ArrayList();
	}
	
	public void addEntry(RepoEntry entry) {
		entries.add(entry);
	}
	
	/**
	 * @return Returns the entries.
	 */
	public ArrayList getEntries() {
		return entries;
	}
}
