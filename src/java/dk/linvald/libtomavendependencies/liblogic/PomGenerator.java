package dk.linvald.libtomavendependencies.liblogic;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import java.io.*;
import java.util.ArrayList;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author Jesper Linvald This class takes care of running templates given a
 *         Model
 */
public class PomGenerator {

    private File                  _outFile;

    private VelocityEngine        _vEngine;

    private VelocityContext       _context;

    private FileWriter _writer;

    private ArrayList             _dependencies;

    public PomGenerator(File outFile, ArrayList dependencies) {
        this._outFile = outFile;
        this._dependencies = dependencies;
        //outFile.mkdirs();
        try {
            outFile.createNewFile();
        } catch (IOException e) {
            System.err.println("IOException:" +e);
        }
        velocityInit(outFile);
    }
    
    public void run(){
        try {
            runTemplate();
        } catch (IOException e) {
            System.err.println("IOException:" +e);
        } catch (Exception e) {
            System.err.println("Exception:" +e);
        }
    }
    private void velocityInit(File outFile) {
        try {
            _vEngine = new VelocityEngine();
            _vEngine.init();
            _vEngine.addProperty("resource.loader", "class");
            _vEngine.addProperty("class.resource.loader.class ","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            //tells where the macros are
            _context = new VelocityContext();
            _context.put("dependencies", _dependencies);
            //we need a StringHelper to aid us
            _writer = new FileWriter(outFile);
            _context.put("writer", _writer);
        } catch (ResourceNotFoundException rnfe) {
            System.out.println("Example : error : cannot find template ");
        } catch (ParseErrorException pee) {
            System.out.println("Example : Syntax error in template " + ":"
                    + pee);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }

    private void runTemplate()
            throws IOException, Exception {
        try {
            Template t = _vEngine.getTemplate("templates/pom.vm");
            //Write to stream
            FileWriter writer = new FileWriter(this._outFile);
            t.merge(_context, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}