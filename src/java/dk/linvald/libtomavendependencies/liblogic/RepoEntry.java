package dk.linvald.libtomavendependencies.liblogic;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;


/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class RepoEntry {
	
	private File theArtifact;
	private File repoLocation;
	private String groupId;
	private String artifactName;
	private String versionId;
	private boolean isVersioned;
	private boolean isCopied;
	private boolean toBeCopied;
	
	
	public RepoEntry(File repoLocation, String filePath, boolean isVersioned) {
		this.theArtifact = new File(filePath);
		this.repoLocation = repoLocation;
		this.isVersioned = isVersioned;
		resolveFileName();
	}
	
	public File getRepoFile(){
	    return new File(repoLocation + "//" + groupId + "//jars//"+ artifactName + "-" + versionId + ".jar");
	}
	public void copyToRepository() {
		File destFile = new File(repoLocation + "//" + groupId + "//jars//"+ artifactName + "-" + versionId + ".jar");	
		try {
            Copier.copyFile(this.getTheArtifact(), destFile);
            if(destFile.exists()){
                this.isCopied = true;
            }
        } catch (Exception e) {
            System.err.println("Exception while copying:" +e);
        }
		
	}
	
	
	private void resolveFileName() {
        //	(".*\\p{Punct}[0-9].*.jar")
        String name = theArtifact.getName();
        String manifestVersion = "";
        if (this.isVersioned) {
            // i.e. junit-3.7.jar
            try {
                String firstPart = name.substring(0, name.lastIndexOf("-"));
                this.artifactName = firstPart;
                //groupid dosent strip of numbering
                this.groupId = stripVersionInfo(artifactName);
                String lastPart = name.substring(name.lastIndexOf("-") + 1, name.length());

                String version = lastPart.substring(0, lastPart
                        .lastIndexOf("."));
                this.versionId = version;
                this.setToBeCopied(true);
            } catch (java.lang.StringIndexOutOfBoundsException e) {
                System.out.println("Failing with::-->" + name);
            }
        } else {
            this.groupId =  name.substring(0,name.lastIndexOf(".jar"));

            try {
                //test if jar has a manifest...
                JarFile jar = new JarFile(theArtifact);
                Attributes att = jar.getManifest().getMainAttributes();//or is it Implementation-Version
                if(att!=null) {
                    manifestVersion = att.getValue(Attributes.Name.SPECIFICATION_VERSION);
                    if(manifestVersion!= null && manifestVersion.length()==0) {
                        manifestVersion = att.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
                    }
                }
            } catch (IOException e) {
                //ignore - no manifest available
            }
            if(manifestVersion!= null && manifestVersion.length()>0) {
                this.versionId=manifestVersion;
                this.setToBeCopied(true);
                this.setVersioned(true);
                System.out.println("Resolved version from manifest:" + manifestVersion);
            }else {
                this.versionId = "1.0";
                this.setToBeCopied(false);
            }
           
            this.artifactName = theArtifact.getName().substring(0,theArtifact.getName().indexOf(".jar"));   
        }
    }
	
	private String stripVersionInfo(String name){
	    //we may have a name: velocity-1.2.4
	    if(name.indexOf("-")>0)
	        return name.substring(0,name.indexOf("-"));
	    return name;
	}

	
	/**
	 * @return Returns the artifactName.
	 */
	public String getArtifactName() {
		return artifactName;
	}
	/**
	 * @param artifactName
	 *            The artifactName to set.
	 */
	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}
	/**
	 * @return Returns the groupId.
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return Returns the repoLocation.
	 */
	public File getRepoLocation() {
		return repoLocation;
	}
	/**
	 * @param repoLocation The repoLocation to set.
	 */
	public void setRepoLocation(File repoLocation) {
		this.repoLocation = repoLocation;
	}
	/**
	 * @return Returns the versionId.
	 */
	public String getVersionId() {
		return versionId;
	}
	/**
	 * @param versionId The versionId to set.
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer().append("Versioned"+ this.isVersioned + " repoLocation:" + this.repoLocation.getAbsolutePath()).append(
			" artifactName:" +			this.artifactName).append(" groupId:" + this.groupId).append(" versionId:" + this.versionId)
			.toString();
	}
	/**
	 * @return Returns the theArtifact.
	 */
	public File getTheArtifact() {
		return theArtifact;
	}
    /**
     * @return Returns the isVersioned.
     */
    public boolean isVersioned() {
        return isVersioned;
    }
    /**
     * @param isVersioned The isVersioned to set.
     */
    public void setVersioned(boolean isVersioned) {
        this.isVersioned = isVersioned;
    }
    /**
     * @return Returns the isCopied.
     */
    public boolean isCopied() {
        return isCopied;
    }
    /**
     * @param isCopied The isCopied to set.
     */
    public void setCopied(boolean isCopied) {
        this.isCopied = isCopied;
    }
    /**
     * @return Returns the toBeCopied.
     */
    public boolean isToBeCopied() {
        return toBeCopied;
    }
    /**
     * @param toBeCopied The toBeCopied to set.
     */
    public void setToBeCopied(boolean toBeCopied) {
        this.toBeCopied = toBeCopied;
    }
}
