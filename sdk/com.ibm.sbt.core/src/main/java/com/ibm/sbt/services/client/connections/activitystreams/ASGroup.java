/*
 * � Copyright IBM Corp. 2013
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


package com.ibm.sbt.services.client.connections.activitystreams;

import com.ibm.sbt.services.client.base.NamedUrlPart;

/**
 * Activity streams ASGroup class, allows user to choose Group type
 * @author Manish Kataria
 * @author Carlos Manias
 */
public enum ASGroup {
	
	//// Possible values : @all,@following,@friends,@self,@involved
	
	ALL("@all"),
	FOLLOWING("@following"),
	FRIENDS("@friends"),
	SELF("@self"),
	INVOLVED("@involved"),
	SAVED("@saved"),
	ACTION("@actions"),
	RESPONSES("@responses"),
	NOTESFORME ("@notesforme"),
	NOTESFROMME("@notesfromme");

	
	String groupType;
	
	private ASGroup(String groupType) {
		this.groupType = groupType;
	}
	
	public NamedUrlPart get(){
		return new NamedUrlPart("group", groupType);
	}

	public static NamedUrlPart getByName(String name){
		return valueOf(name).get();
	}

	public static NamedUrlPart get(String userId){
		return new NamedUrlPart("user", userId);
	}

}
