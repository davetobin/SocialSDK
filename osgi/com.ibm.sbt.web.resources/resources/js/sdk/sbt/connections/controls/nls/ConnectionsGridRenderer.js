/*
 * © Copyright IBM Corp. 2013
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
 
dojo.provide("sbt.connections.controls.nls.ConnectionsGridRenderer");

// NLS_CHARSET=UTF-8
define('sbt/connections/controls/nls/ConnectionsGridRenderer',[],{
  root: {
      empty : "Empty",
      loading : "Loading...",
      previous : "Previous",
      previousPage : "Previous Page",
      next : "Next",
      nextPage : "Next Page",
      pagingResults : "${start}-${end} of ${totalCount}",
      sortBy : "Sort by:",
      msgNoData : "Please wait...",
      show10Items : "Show 10 items",
      show25Items : "Show 25 items",
      show50Items : "Show 50 items",
      show100Items : "Show 100 items",
      items : "items",
      feed : "${nls.feed}"
  }
});

