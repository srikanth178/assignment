/**
 * 
 */
package com.test.sri.customgithub.exception;

/**
 * @author srikanthgummula
 *
 */
public class CustomGitHubException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CustomGitHubException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomGitHubException(String message) {
		super(message);
	}

}
