package dk.linvald.libtomavendependencies;

import java.io.File;

import dk.linvald.libtomavendependencies.liblogic.Copier;
import junit.framework.TestCase;


/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class CopierTest extends TestCase {
    
    private File in, out;
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        out = new File("./hello.you");
        in = File.createTempFile("./delete.txt", "me");
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        out.delete();
        in.delete();
    }

    public void testCopyFile() {
        try {
            Copier.copyFile(in, out );
            assertNotNull(in);
            assertNotNull(out);
        } catch (Exception e) {
            fail();
            System.err.println("Exception:" +e);
        }
    }

}
