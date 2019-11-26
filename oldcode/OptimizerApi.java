package com.extension.inbalance;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.AbstractRequest;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class OptimizerApi extends DefaultApi20 {

	protected OptimizerApi() {
    }

    private static class InstanceHolder {
        private static final OptimizerApi INSTANCE = new OptimizerApi();
    }

    public static OptimizerApi instance() {
        return InstanceHolder.INSTANCE;
    }
	    
	@Override
	public String getAccessTokenEndpoint() {
		return "https://omfulfillment.com:9444/oauth2/endpoint/OTMZOAuthProvider/token";
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		return "https://omfulfillment.com:9444/oauth2/endpoint/OTMZOAuthProvider/authorize";
	}
	
	public Verb getAccessTokenVerb(){
		return Verb.POST;
	}
	
	public OAuth20Service createService(OAuthConfig config){
		return new OptimizerOAuth2Service(this, config);
	}

	 private class OptimizerOAuth2Service extends OAuth20Service {

	        private DefaultApi20 api;
	        private OAuthConfig config;

	        public OptimizerOAuth2Service(DefaultApi20 api, OAuthConfig config) {
	            super(api, config);
	            this.api = api;
	            this.config = config;
	        }
	        
	        @Override
	        protected <T extends AbstractRequest> T createAccessTokenRequest(String code, T request) {
	        	javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	        			new javax.net.ssl.HostnameVerifier(){

	        			    public boolean verify(String hostname,
	        			            javax.net.ssl.SSLSession sslSession) {
	        			        return hostname.equals("omfulfillment.com");
	        			    }
	        			});
	            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
	            request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
	            request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
	            final String scope = config.getScope();
	            if (scope != null) {
	                request.addParameter(OAuthConstants.SCOPE, scope);
	            }
	            request.addBodyParameter(OAuthConstants.GRANT_TYPE, "client_credentials");
	            return request;
	        }
	       
	    }
}
