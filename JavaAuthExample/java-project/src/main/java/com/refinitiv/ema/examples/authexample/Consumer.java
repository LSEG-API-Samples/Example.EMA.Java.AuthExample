///*|----------------------------------------------------------------------------------------------------
// *|            This source code is provided under the Apache 2.0 license      	--
// *|  and is provided AS IS with no warranty or guarantee of fit for purpose.  --
// *|                See the project's LICENSE.md for details.                  					--
// *|           Copyright (C) 2019 Refinitiv. All rights reserved.            		--
///*|----------------------------------------------------------------------------------------------------

package com.refinitiv.ema.examples.authexample;

import com.refinitiv.ema.access.Msg;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.refinitiv.ema.access.AckMsg;
import com.refinitiv.ema.access.GenericMsg;
import com.refinitiv.ema.access.OmmConsumerConfig;
import com.refinitiv.ema.access.RefreshMsg;
import com.refinitiv.ema.access.ReqMsg;
import com.refinitiv.ema.access.StatusMsg;
import com.refinitiv.ema.access.UpdateMsg;
import com.refinitiv.ema.domain.login.Login.LoginRefresh;
import com.refinitiv.ema.domain.login.Login.LoginReq;
import com.refinitiv.ema.rdm.EmaRdm;
import com.refinitiv.ema.access.EmaFactory;
import com.refinitiv.ema.access.OmmConsumer;
import com.refinitiv.ema.access.OmmConsumerClient;
import com.refinitiv.ema.access.OmmConsumerEvent;
import com.refinitiv.ema.access.OmmException;


class AppClient implements OmmConsumerClient
{
	public void onRefreshMsg(RefreshMsg refreshMsg, OmmConsumerEvent event)
	{
		System.out.println("Received Refresh Message\n");
		
		System.out.println( refreshMsg );		
		System.out.println();
	}
	
	public void onUpdateMsg(UpdateMsg updateMsg, OmmConsumerEvent event) 
	{
		System.out.println("Received Update Message\n");
		
		System.out.println( updateMsg );		
		System.out.println();
	}

	public void onStatusMsg(StatusMsg statusMsg, OmmConsumerEvent event) 
	{
		System.out.println("Received Status Message\n");
		
		System.out.println( statusMsg );		
		System.out.println();
	}
	
	public void onAckMsg(AckMsg ackMsg, OmmConsumerEvent event) {}
	public void onGenericMsg(GenericMsg genericMsg, OmmConsumerEvent event){}
	public void onAllMsg(Msg msg, OmmConsumerEvent event){}
}

class AppLoginClient implements OmmConsumerClient
{
	public long handle = 0;
	public long ttReissue = 0;
	
	public void onRefreshMsg(RefreshMsg refreshMsg, OmmConsumerEvent event)
	{
		System.out.println("Received Login Refresh Message\n");
		
		System.out.println( refreshMsg );		
		System.out.println();
		
		/* Get the handle from the event and save it for a future reissue */
		handle = event.handle();
		/* Get the time to reissue from the refresh and save it */
		LoginRefresh loginRefresh = EmaFactory.Domain.createLoginRefresh().message(refreshMsg);
		
		if(loginRefresh.hasAuthenticationTTReissue())
			ttReissue = loginRefresh.authenticationTTReissue();
		
	}
	
	public void onUpdateMsg(UpdateMsg updateMsg, OmmConsumerEvent event) 
	{
		System.out.println("Received Login Update Message\n");
		
		System.out.println( updateMsg );		
		System.out.println();
	}

	public void onStatusMsg(StatusMsg statusMsg, OmmConsumerEvent event) 
	{
		System.out.println("Received Login Status Message\n");
		
		System.out.println( statusMsg );		
		System.out.println();
	}
	
	public void onAckMsg(AckMsg ackMsg, OmmConsumerEvent event) {}
	public void onGenericMsg(GenericMsg genericMsg, OmmConsumerEvent event){}
	public void onAllMsg(Msg msg, OmmConsumerEvent event){}
}
	
public class Consumer 
{
	private static String authenticationToken = "";
	private static String authurl = "";
	private static String username = "";
	private static String password = "";
	private static String service = "";
	private static String item = "IBM.N";
	private static String appId = "256";
	
	public static void parseJson(String jsonString) {
		 JSONObject jsonResponse = new JSONObject(jsonString);
		 if(jsonResponse.getBoolean("success")==true) {
			 authenticationToken = jsonResponse.getString("token");
			 System.out.println("Token: "+authenticationToken);	 
		 }else {			 
			 System.exit(-1);			 
		 }
		 
	}
	
	public static void getToken() {
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(authurl);
	    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("username", username));
	    params.add(new BasicNameValuePair("password", password));
	    try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			
			HttpResponse response = client.execute(httpPost);
			String responseString = new BasicResponseHandler().handleResponse(response);
			System.out.println(responseString);
			
			if(response.getStatusLine().getStatusCode() == 200) {
				
				parseJson(responseString);
               
				
			}else {
				
				client.close();
				System.exit(-1);
			}
			 
			client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	   
	    
	    
	    
	}
	public static void printHelp()
	{
		
		System.out.println("\nOptions:\n"
		+ "  -?                            Shows this usage\n\n"
		+ "  -authurl <authurl>           Required: Authentication URL to get a token (e.g. http://<AuthServer>:8443/getToken)\n"
		+ "  -username <username>         Required: Username\n"
		+ "  -password <password>         Required: Password\n"
		+ "  -service <service>           Required: Service name\n"
		+ "  -item <ric>                  Optional: A RIC\n"
		+ "\n" );
	}
	
	public static void printInvalidOption()
	{
		System.out.println("Detected a missing argument. Please verify command line options [-?]");
	}
	
	public static boolean init(String[] argv)
	{
		int count = argv.length;
		int idx = 0;
		
		while ( idx < count )
		{
			if ( 0 == argv[idx].compareTo("-?") )
			{
				printHelp();
				return false;
			}
			else if ( 0 == argv[idx].compareTo("-authurl") )
			{
				if ( ++idx >= count )
				{
					printInvalidOption();
					return false;
				}
				authurl = argv[idx];
				++idx;
			}
			else if ( 0 == argv[idx].compareTo("-username") )
			{
				if ( ++idx >= count )
				{
					printInvalidOption();
					return false;
				}
				username = argv[idx];
				++idx;
			}
			else if ( 0 == argv[idx].compareTo("-password") )
			{
				if ( ++idx >= count )
				{
					printInvalidOption();
					return false;
				}
				password = argv[idx];
				++idx;
			}else if ( 0 == argv[idx].compareTo("-service") )
			{
				if ( ++idx >= count )
				{
					printInvalidOption();
					return false;
				}
				service = argv[idx];
				++idx;
			}else if ( 0 == argv[idx].compareTo("-item") )
			{
				if ( ++idx >= count )
				{
					printInvalidOption();
					return false;
				}
				item = argv[idx];
				++idx;
			}
			else
			{
				System.out.println( "Unrecognized option. Please see command line help. [-?]");
				return false;
			}
		}
		if(authurl.isBlank()){
			System.out.println("Missing an authentication URL.");
			printHelp();
			return false;
		}
		if(username.isBlank()) {
			System.out.println("Missing a username.");
			printHelp();
			return false;
		}
		if(password.isBlank()) {
			System.out.println("Missing a password.");
			printHelp();
			return false;
		}
		if(service.isBlank()) {
			System.out.println("Missing a service name.");
			printHelp();
			return false;
		}
		return true;
	}
	
	private static void printActiveConfig()
	{
		System.out.println("Following options are selected:");
		
		System.out.println("appId = " + appId);
		System.out.println("authurl = " + authurl);
		System.out.println("username = " + username);
		System.out.println("password = *****");
		System.out.println("service = " + service);
		System.out.println("item = " + item);
		
		
	}
	
	public static void main(String[] args)
	{
		OmmConsumer consumer = null;
		try
		{
			if ( !init(args) ) return;
			AppClient appClient = new AppClient();
			AppLoginClient appLoginClient = new AppLoginClient();
			LoginReq loginReq = EmaFactory.Domain.createLoginReq();
			
			printActiveConfig();
			getToken();
			OmmConsumerConfig config = EmaFactory.createOmmConsumerConfig();
			
			loginReq.clear().name(authenticationToken).nameType(EmaRdm.USER_AUTH_TOKEN).applicationId(appId);
			
			
							
			config.addAdminMsg(loginReq.message());
			consumer = EmaFactory.createOmmConsumer(config, appLoginClient);
			
			ReqMsg reqMsg = EmaFactory.createReqMsg();
		
			consumer.registerClient(reqMsg.clear().serviceName(service).name(item), appClient);

			while(true)
			{						
				if(appLoginClient.ttReissue != 0 && appLoginClient.ttReissue <= (System.currentTimeMillis()/1000))
				{
					getToken();
					loginReq.clear().name(authenticationToken).nameType(EmaRdm.USER_AUTH_TOKEN).applicationId(appId);
					
					
					consumer.reissue(loginReq.message(), appLoginClient.handle);
					appLoginClient.ttReissue = 0;
				}
				
				Thread.sleep(1000);
			}
			
		} 
		catch (InterruptedException | OmmException excp)
		{
			System.out.println(excp.getMessage());
		}
		finally 
		{
			if (consumer != null) consumer.uninitialize();
		}
	}
}
