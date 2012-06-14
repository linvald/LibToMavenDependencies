package dk.linvald.libtomavendependencies;

import java.io.File;
import java.io.IOException;

import dk.linvald.libtomavendependencies.liblogic.Locations;
import junit.framework.TestCase;


/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class LocationsTest extends TestCase {
    
    private Locations l;
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        l = new Locations();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        l = null;
    }

    public void testGetLibLocation() {
        l.setLibLocation("here");
        assertEquals("here", l.getLibLocation());
    }

    public void testSetLibLocation() {
        l.setLibLocation("here");
        assertEquals("here", l.getLibLocation());
    }

    public void testGetRepositoryLocation() {
        l.setRepositoryLocation("here");
        assertEquals("here", l.getRepositoryLocation());
    }

    public void testSetRepositoryLocation() {
        l.setRepositoryLocation("here");
        assertEquals("here", l.getRepositoryLocation());
    }

    public void testGetPomLocation() {
        File f;
        try {
            f = File.createTempFile("some", "file");
            l.setPomLocation(f);
            assertEquals(f , l.getPomLocation());
            f = null;
        } catch (IOException e) {
            fail();
            System.err.println("IOException:" +e);
        }
    }

    public void testSetPomLocation() {
        File f;
        try {
            f = File.createTempFile("some", "file");
            l.setPomLocation(f);
            assertEquals(f , l.getPomLocation());
            f = null;
        } catch (IOException e) {
            fail();
            System.err.println("IOException:" +e);
        }

    }

}
