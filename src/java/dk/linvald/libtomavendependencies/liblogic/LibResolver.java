package dk.linvald.libtomavendependencies.liblogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

import dk.linvald.libtomavendependencies.exceptions.ResolveException;
/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class LibResolver {
	
	private ArrayList jars;
	private Locations where;
	
	public LibResolver(Locations loc) {
		this.jars = new ArrayList();
		this.where = loc;
	}

	public void resolve() throws ResolveException{
		File lib = new File(where.getLibLocation());
		if(!lib.isDirectory()) {
			throw new ResolveException("The library specified  [" + where.getLibLocation() + "] dosent seem to be a valid directory..");
		}else {
			collectJars(lib);
		}
	}
	
	private void collectJars(File parentDir) {
		File [] dirs = parentDir.listFiles();
		if(dirs != null && dirs.length>0) {
			for (int i = 0; i < dirs.length; i++) {
				if(dirs[i].isDirectory()) {
					collectJars(dirs[i]);
				}else if(dirs[i].getAbsolutePath().indexOf(".jar")>0){
					jars.add(dirs[i]);
				}
			}
		}
	}
	
	public void clearAll(){
	    this.jars = new ArrayList();
	}
	
	/**
	 * @return Returns the jars.
	 */
	public ArrayList getJars() {
		return jars;
	}
	
	public void printIt() {
		for (Iterator iter = jars.iterator(); iter.hasNext();) {
			File jar = (File) iter.next();
			System.out.println("JAR:" + jar.getName());
		}
	}
	/**
	 * @return Returns the where.
	 */
	public Locations getWhere() {
		return where;
	}
}
