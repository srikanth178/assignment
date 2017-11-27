/**
 * 
 */
package com.test.sri.customgithub.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.test.sri.customgithub.dao.CustomGitHubDao;
import com.test.sri.customgithub.dao.CustomGitHubDaoImpl;
import com.test.sri.customgithub.domain.CustomGitHubBean;
import com.test.sri.customgithub.exception.CustomGitHubException;
import com.test.sri.customgithub.exception.CustomGitHubMessages;

/**
 * @author srikanthgummula
 *
 */
public class CustomGitHubServiceImpl implements CustomGitHubService {
	
	private static final Logger LOGGER = Logger.getLogger(CustomGitHubServiceImpl.class.getName());

	public CustomGitHubBean createFiles(JSONArray orgs, JSONArray extns, String keyspace) throws CustomGitHubException {

		try {
			
			StringBuilder sb = new StringBuilder(SEARCH_BASE_URL);

			for(Object org:orgs) {
				if(org != null) {
					sb.append(ORGANIZATION_QUALIFIER);
					String orgStr = (String)org;
					if(orgStr.startsWith("https://")) {
						String orgName = orgStr.substring(orgStr.lastIndexOf("/")+1);
						sb.append(orgName);
					}
					else 
						sb.append(org);
					sb.append(" ");
				}		
			}
			
			for(Object extn:extns) {
				sb.append(EXTENSION_QUALIFIER);
				sb.append(extn);
				sb.append(" ");
			}			
			
			URL url = new URL(URLEncoder.encode(sb.toString(),"UTF-8"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
			
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String inline;
			while((inline = br.readLine()) != null) {
				sb.append(inline);
			}
			
			JSONObject jsonout = new JSONObject(sb.toString());
			JSONArray items = jsonout.getJSONArray("items");
			
			List<CustomGitHubBean> customGitHubBeanList = new ArrayList<CustomGitHubBean>();
			for(Object item:items) {
				if(item != null) {
					JSONObject obj = new JSONObject(item);					
					String org = obj.getJSONObject("repository").getJSONObject("owner").getString("login");
					String fileName = obj.getString("name");
					String fileExtn = fileName.substring(fileName.lastIndexOf(".")+1);
					String ref = obj.getString("html_url");
					CustomGitHubBean customGitHubBean = new CustomGitHubBean(org,fileExtn,fileName,ref);
					customGitHubBeanList.add(customGitHubBean);
				}				
			}
			
			CustomGitHubDao customGitHubDao = new CustomGitHubDaoImpl(keyspace);
			customGitHubDao.insert(customGitHubBeanList);
			
		} catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE,CustomGitHubMessages.PROCESSING_ERROR, e);
			throw new CustomGitHubException(CustomGitHubMessages.PROCESSING_ERROR);
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.SEVERE,CustomGitHubMessages.PROCESSING_ERROR, e);
			throw new CustomGitHubException(CustomGitHubMessages.PROCESSING_ERROR);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,CustomGitHubMessages.PROCESSING_ERROR, e);
			throw new CustomGitHubException(CustomGitHubMessages.PROCESSING_ERROR);
		}
		
		return null;
	}

	public List<CustomGitHubBean> getFiles(List<String> fileExtensions, List<String> organizations, String keyspace) throws CustomGitHubException{
		List<CustomGitHubBean> customGitHubBeanList = null;
		try {
			List<String> orgNames = new ArrayList<String>();
			for(String org: organizations) {
				if(org != null && !org.isEmpty()) {
					String orgName = org;
					if(org.startsWith("https://") || org.startsWith("http://")) {
						orgName = org.lastIndexOf("/")+1 < org.length() ? org.substring(org.lastIndexOf("/") +1) : null;
					}
					if(orgName != null && !orgName.isEmpty()) 
						orgNames.add(orgName);
				}
			}
			CustomGitHubDao customGitHubDao = new CustomGitHubDaoImpl(keyspace);
			customGitHubBeanList = customGitHubDao.getFiles(fileExtensions,orgNames);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,CustomGitHubMessages.PROCESSING_ERROR, e);
			throw new CustomGitHubException(CustomGitHubMessages.PROCESSING_ERROR);
		}
		return customGitHubBeanList;
	}

	
}
