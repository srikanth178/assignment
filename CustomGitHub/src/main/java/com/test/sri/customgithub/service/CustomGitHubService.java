/**
 * 
 */
package com.test.sri.customgithub.service;

import java.util.List;

import org.json.JSONArray;

import com.test.sri.customgithub.domain.CustomGitHubBean;
import com.test.sri.customgithub.exception.CustomGitHubException;

/**
 * @author srikanthgummula
 *
 */
public interface CustomGitHubService {
	
	//Git Hub API 
    public static final String SEARCH_BASE_URL = "https://api.github.com/search/code?q=";
	public static final String ORGANIZATION_QUALIFIER = "org:";
	public static final String EXTENSION_QUALIFIER = "extension:";
	
	
	public CustomGitHubBean createFiles(JSONArray orgs, JSONArray extns, String keyspace) throws CustomGitHubException;


	public List<CustomGitHubBean> getFiles(List<String> fileExtensions, List<String> organizations, String keyspace) throws CustomGitHubException;	

}
