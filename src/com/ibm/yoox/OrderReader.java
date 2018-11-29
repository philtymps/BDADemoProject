package com.ibm.yoox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class OrderReader {
	public static void main(String[] args) throws NamingException, JMSException, IOException{
		
		String sProvider = "corbaloc::oms.innovationcloud.info:2809";
		String sQueue = "OrderQueue";
		for (int i = 0; i < args.length; i++){
			if (args[i].equals("-provider")){
				sProvider = args[i + 1];
			} else if (args[i].equals("-queue")){
				sQueue = args[i + 1];
			}
		}
		
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
		properties.put(Context.PROVIDER_URL, sProvider);
		InitialContext ctx = new InitialContext(properties);
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("AgentQCF");
		Queue queue = (Queue) ctx.lookup(sQueue);
		
		QueueConnection queueConn = connFactory.createQueueConnection();
		QueueSession queueSession = queueConn.createQueueSession(false,  Session.CLIENT_ACKNOWLEDGE);
		QueueReceiver queueReceiver = queueSession.createReceiver(queue);
		queueConn.start();
		TextMessage message = (TextMessage) queueReceiver.receive();
	
		while (message != null){
			if (message.getStringProperty("FlowName") != null && message.getStringProperty("FlowName").equals("SCWC_createOrderOnSuccess")){
				String sOutput = message.getText();
				sOutput = sOutput.replace("SellerOrganizationCode=\"Aurora\"", "SellerOrganizationCode=\"ARMANI_US\"");
				System.out.println("Posting: " + sOutput);
				/*		CloseableHttpClient httpclient = HttpClients.createDefault();
				
				HttpPost postmethod = new HttpPost("http://private.api.yoox.net/Order.API/1.0/Order/PostOrderXml");
				postmethod.setHeader("Content-Type", "application/xml");
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("orderXml", sOutput));
				UrlEncodedFormEntity tmp = new UrlEncodedFormEntity(params, "UTF-8");
				tmp.setContentType("application/xml");
				postmethod.setEntity(tmp);
				HttpResponse response = httpclient.execute(postmethod);
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
					String res;
					while ((res = in.readLine()) != null) {
						System.out.println(res);
					}
					
					in.close();
				}
				*/
				URL url = new URL("http://private.api.yoox.net/Order.API/1.0/Order/PostOrderXml");
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setRequestMethod("POST");
		        connection.setRequestProperty("Content-Type", "application/xml");
		        connection.setRequestProperty("Content-Length",  String.valueOf(sOutput.length()));
	       

		        // Write data

		        OutputStream os = connection.getOutputStream();
		        os.write(sOutput.getBytes());
		        // Read response
		        StringBuilder responseSB = new StringBuilder();
		        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        String line;
		        while ( (line = br.readLine()) != null)
		            responseSB.append(line);
		        // Close streams
		        br.close();
		        os.close();
				message.acknowledge();
			} else {
				message.acknowledge();
			}
			message = (TextMessage) queueReceiver.receive();
		}
		
		
		queueConn.close();
	}

	
}
