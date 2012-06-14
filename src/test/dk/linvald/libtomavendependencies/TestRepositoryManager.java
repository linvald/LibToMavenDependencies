package dk.linvald.libtomavendependencies;

import dk.linvald.libtomavendependencies.exceptions.ResolveException;
import dk.linvald.libtomavendependencies.liblogic.LibResolver;
import dk.linvald.libtomavendependencies.liblogic.Locations;
import dk.linvald.libtomavendependencies.liblogic.RepositoryManager;
import junit.framework.TestCase;

/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class TestRepositoryManager extends TestCase {
	
	private final String LIB = "C:/eclipse_work/Reap/lib";
	
	public void testManager() {
		Locations loc = new Locations();
		loc.setLibLocation(LIB);
		loc.setRepositoryLocation("somewhere");
		
		LibResolver resolver = new LibResolver(loc);
		try {
			resolver.resolve();
			assertTrue(resolver.getJars().size()>0);
		} catch (ResolveException e) {
			System.err.println("ResolveException:" +e);
		}
		RepositoryManager manager = new RepositoryManager(resolver);
		manager.sortJars();
	}
	public static void main(String[] args) {
		new TestRepositoryManager().testManager();
	}
}
