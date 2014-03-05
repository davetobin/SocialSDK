/*
 * � Copyright IBM Corp. 2014
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.ibm.sbt.sample.app;

import com.ibm.commons.util.StringUtil;
import com.ibm.sbt.services.client.base.JsonEntity;
import com.ibm.sbt.services.client.base.datahandlers.EntityList;


/**
 * @author mwallace
 *
 */
public class AddSubscribersApp extends BaseBssApp {
	
	public AddSubscribersApp(String url, String user, String password) {
		super(url, user, password);
	}
	
	/**
	 * Demo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 6) {
			System.out.println("Usage: java com.ibm.sbt.sample.app.AddSubscribersBlog <url> <user> <password> <customerId> <domain> <subscriptions>");
			return;
		}
		
		String url = args[0];
		String user = args[1];
		String password = args[2];
		String customerId = args[3];
		String domain = args[4];
		String[] subscriptionIds = StringUtil.splitString(args[5], ',');
		
		AddSubscribersApp sa = null;
		try {
			sa = new AddSubscribersApp(url, user, password);
			
			for (int i=0; i<1; i++) {
				String subscriberId = sa.addSubscriber(customerId, sa.getUniqueEmail(domain));
				
				sa.activateSubscriber(subscriberId);
				
				String loginName = sa.getLoginName(subscriberId);
				
				sa.setOneTimePassword(loginName, "one-time-123");
				
				sa.changePassword(loginName, "one-time-123", "password1");
				
				for (String subscriptionId : subscriptionIds) {
					sa.entitleSubscriber(subscriberId, subscriptionId, true);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
