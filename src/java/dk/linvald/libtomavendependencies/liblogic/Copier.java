package dk.linvald.libtomavendependencies.liblogic;

import java.nio.channels.*;
import java.io.*;

public class Copier{

 private Copier(){}
    
  public static void copyFile(File in, File out) throws Exception {
     if(!in.exists()){
         throw new CopyException("File " + in + " does not exist...");
     }
     if(!out.getParentFile().exists()){
         boolean madeEm = out.getParentFile().mkdirs();
         if(!madeEm){
             throw new CopyException("Couldnt create dir:" + out.getParentFile().getAbsolutePath());
         }
     }
     //Copy the file
     FileChannel sourceChannel = new
          FileInputStream(in).getChannel();
     FileChannel destinationChannel = new
          FileOutputStream(out).getChannel();
     sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
     sourceChannel.close();
     destinationChannel.close();
     }
  
  static class CopyException extends Exception{
      public CopyException(String msg){
          super(msg);
      }
  }
}
