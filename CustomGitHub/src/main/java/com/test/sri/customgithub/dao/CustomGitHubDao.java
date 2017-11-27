/**
 * 
 */
package com.test.sri.customgithub.dao;

import java.util.List;

import com.test.sri.customgithub.domain.CustomGitHubBean;

/**
 * @author srikanthgummula
 *
 */
public interface CustomGitHubDao {
	
	public void insert(List<CustomGitHubBean> customGitHubBeanList);

	public List<CustomGitHubBean> getFiles(List<String> fileExtensions, List<String> orgNames);

}
