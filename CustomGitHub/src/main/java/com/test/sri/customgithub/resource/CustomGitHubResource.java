package com.test.sri.customgithub.resource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.test.sri.customgithub.domain.CustomGitHubBean;
import com.test.sri.customgithub.exception.CustomGitHubException;
import com.test.sri.customgithub.exception.CustomGitHubMessages;
import com.test.sri.customgithub.service.CustomGitHubService;
import com.test.sri.customgithub.service.CustomGitHubServiceImpl;

@Path("/customgithub")
public class CustomGitHubResource {
	
	private static final Logger LOGGER = Logger.getLogger(CustomGitHubResource.class.getName());
	
	private static final String FILE_EXTENSION = "fileExtension";
	private static final String ORGANIZATION = "organizaion";
	private static final String KEYSPACE = "keyspace";
	private static final String KEYSPACE_NAME = "custom_github_files";
	
	
	@GET
	@Path("/files")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFiles(@QueryParam(FILE_EXTENSION) List<String> fileExtensions,
							 @QueryParam(ORGANIZATION) List<String> organizations,
							 @QueryParam(KEYSPACE) String keyspace) {
		try {
			validateSearchInputParams(fileExtensions,keyspace);
		} catch (CustomGitHubException e) {
			LOGGER.log(Level.SEVERE,e.getMessage(), e);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
		}
		List<CustomGitHubBean> customGitHubBeanList;
		try {
			CustomGitHubService customGitHubService = new CustomGitHubServiceImpl();
			customGitHubBeanList = customGitHubService.getFiles(fileExtensions, organizations, keyspace);
		} catch (CustomGitHubException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(e.getMessage()).build();
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE,e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).build();
		}	
		return Response.status(200).entity(customGitHubBeanList).build();
	}
	
	private void validateSearchInputParams(List<String> fileExtensions, String keyspace) throws CustomGitHubException {
		StringBuilder sb = new StringBuilder();		
		if(fileExtensions == null || fileExtensions.size() == 0) {
			sb.append(CustomGitHubMessages.INVALID_EXTENSIONS);
			sb.append("\n");
		}
		if(!KEYSPACE_NAME.equals(keyspace)) {
			sb.append(CustomGitHubMessages.INVALID_KEYSPACE);
		}
		
		if(!sb.toString().isEmpty()) {
			throw new CustomGitHubException(sb.toString());
		}
	}
	
	@POST
	@Path("/extensions")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createFiles(String arg) {
		
		JSONArray orgs;
		JSONArray extns;
		String keyspace;
		try {
			JSONObject jsonObject = new JSONObject(arg);
			orgs = jsonObject.getJSONArray("orgs");
			extns = jsonObject.getJSONArray("extns");
			keyspace = jsonObject.getString("keyspace");
			
			validateCreateInputParams(orgs,extns,keyspace);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,e.getMessage(), e);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
		}
		
		CustomGitHubBean customGitHubBean;
		
		try {
			CustomGitHubService customGitHubService = new CustomGitHubServiceImpl();
			customGitHubBean = customGitHubService.createFiles(orgs,extns,keyspace);
		} catch (CustomGitHubException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(e.getMessage()).build();
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE,e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).build();
		}		
		
		return Response.status(Response.Status.CREATED).entity(customGitHubBean).build();	
	}
	
	private void validateCreateInputParams(JSONArray orgs, JSONArray extns, String keyspace) throws CustomGitHubException {
		StringBuilder sb = new StringBuilder();
		if(orgs == null || orgs.length() == 0) {
			sb.append(CustomGitHubMessages.INVALID_ORG);
			sb.append("\n");
		}
		if(extns == null || extns.length() == 0) {
			sb.append(CustomGitHubMessages.INVALID_EXTENSIONS);
			sb.append("\n");
		}
		if(!KEYSPACE_NAME.equals(keyspace)) {
			sb.append(CustomGitHubMessages.INVALID_KEYSPACE);
		}
		
		if(!sb.toString().isEmpty()) {
			throw new CustomGitHubException(sb.toString());
		}
	}

}
