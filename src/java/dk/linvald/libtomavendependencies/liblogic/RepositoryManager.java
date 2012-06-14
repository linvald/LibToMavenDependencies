package dk.linvald.libtomavendependencies.liblogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import dk.linvald.libtomavendependencies.exceptions.ResolveException;

/**
 * Given a list of jars:
 * 	version the jars properly
 * 	create folders in repository
 *  deletegate job of creating dependency pom entries 
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class RepositoryManager {
	
	private ArrayList versionedJars;
	private ArrayList unversionedJars;
	private LibResolver resolver;
	private RepoEntryCollection repoCollection;
	private ArrayList unversionedEntries;
	
	public RepositoryManager(LibResolver resolver) {
		this.resolver = resolver;
		versionedJars = new ArrayList();
		unversionedJars = new ArrayList();
		unversionedEntries = new ArrayList();
		repoCollection = new RepoEntryCollection();
		sortJars();
	}
	
	public void sortJars() {
		for (Iterator iter = resolver.getJars().iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			if(isVersioned(file)) {
				versionedJars.add(file);
			}else {
				unversionedJars.add(file);
			}
		}
		createRepoCollection();
		createUnversionedCollection();
	}
	
	private void createRepoCollection() {
		//first solve the versioned jars
		for (Iterator iter = versionedJars.iterator(); iter.hasNext();) {
			File jar = (File) iter.next();
			RepoEntry entry = new RepoEntry(new File(this.resolver.getWhere().getRepositoryLocation()),jar.getAbsolutePath(),true);
			repoCollection.addEntry(entry);
			//go
			System.out.println("Adding entry for:" + entry);
		}
		
	}
	
	private void createUnversionedCollection(){
	    //	  then solve the unversioned
		for (Iterator iter = unversionedJars.iterator(); iter.hasNext();) {
            File noVersion = (File) iter.next();
            RepoEntry uEntry = new RepoEntry(new File(this.resolver.getWhere().getRepositoryLocation()),noVersion.getAbsolutePath(),false);
            unversionedEntries.add(uEntry);
        }
	}
	
	private void addVersionedToRepo(File f){
		File repo = new File(resolver.getWhere().getRepositoryLocation());
		if(!repo.exists()){
			try {
				throw new ResolveException("It appears that the repository doesnt exist...");
			} catch (ResolveException e) {
				System.err.println("ResolveException:" +e);
			}
		}else{
			//ask user to automatic or interactive
		}
	}

	
	private boolean isVersioned(File f) {
		String fileName = f.getAbsolutePath();
		boolean is = fileName.matches(".*\\p{Punct}[0-9].*.jar");
		if(is) {
			System.out.println(fileName);
		}
		return is;
	}
	
	/**
	 * @return Returns the unversionedJars.
	 */
	public ArrayList getUnversionedJars() {
		return unversionedEntries;
	}
	/**
	 * @return Returns the versionedJars.
	 */
	public ArrayList getVersionedJars() {
		return versionedJars;
	}
	/**
	 * @param entry
	 */
	public void addEntry(RepoEntry entry) {
		repoCollection.addEntry(entry);
	}
	/**
	 * @return
	 */
	public ArrayList getEntries() {
		return repoCollection.getEntries();
	}
}
