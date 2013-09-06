<!-- /*
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
 */-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.ibm.sbt.services.client.connections.forums.ForumTopic"%>
<%@page import="com.ibm.sbt.services.client.connections.forums.ForumService"%>
<%@page import="com.ibm.sbt.services.client.connections.forums.TopicList"%>
<%@page import="com.ibm.sbt.services.client.connections.forums.Forum"%>
<%@page import="java.util.Collection"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.ibm.commons.runtime.Context"%>
<%@page import="com.ibm.sbt.services.client.connections.forums.ForumList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page 
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>SBT JAVA Sample - Create Forum</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body>	
	<h4>Update Topic</h4>
	<div id="content">
	<%
	try {
		ForumService service = new ForumService();
		TopicList topicList = service.getMyForumTopics();
		ForumTopic topic = (ForumTopic)topicList.iterator().next();
		
		topic = service.getForumTopic(topic.getTopicUuid());
		topic.setTitle("Updated Topic Title123" + System.currentTimeMillis());
		topic = topic.save();  
		out.println("Forum updated with title : " + topic.getTitle());
		
	} catch (Exception e) {
	e.printStackTrace();
		out.println("<pre>");
		out.println(e.getMessage());
		out.println("</pre>");
	}
	%>
	</div>
</body>
</html>