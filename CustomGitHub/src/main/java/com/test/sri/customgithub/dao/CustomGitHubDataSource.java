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
public interface CustomGitHubDataSource {
	
	public void insert(List<CustomGitHubBean> customGitHubBeanList);

}
