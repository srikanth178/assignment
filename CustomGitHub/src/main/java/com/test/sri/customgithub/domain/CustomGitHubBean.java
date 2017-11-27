/**
 * 
 */
package com.test.sri.customgithub.domain;

/**
 * @author srikanthgummula
 *
 */
public class CustomGitHubBean {
	
	private String organization;
	private String extension;
	private String fileName;
	private String fileRef;
	
	public CustomGitHubBean(String org, String fileExtn, String fileName, String ref) {
		this.organization = org;
		this.extension = fileExtn;
		this.fileName = fileName;
		this.fileRef = ref;
	}
	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the fileRef
	 */
	public String getFileRef() {
		return fileRef;
	}
	/**
	 * @param fileRef the fileRef to set
	 */
	public void setFileRef(String fileRef) {
		this.fileRef = fileRef;
	}
	
	
	
	

}
