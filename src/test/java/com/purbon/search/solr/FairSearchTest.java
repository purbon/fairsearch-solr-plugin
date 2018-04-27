package com.purbon.search.solr;

import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QueryParsing;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FairSearchTest  extends SolrTestCaseJ4 {

     public void index() throws Exception {

         assertU(adoc("id", "1",
                  "body", "hello hello hello hello hello hello hello hello hello hello", "gender", "m"));
         assertU(adoc("id", "2",
                 "body", "hello hello hello hello hello bye bye bye bye bye", "gender", "f"));
         assertU(adoc("id", "3",
                 "body", "hello hello hello hello hello hello hello hello hello bye", "gender", "m"));
         assertU(adoc("id", "4",
                 "body", "hello hello hello hello bye bye bye bye bye bye", "gender", "f"));
         assertU(adoc("id", "5",
                 "body", "hello hello hello hello hello hello hello hello bye bye", "gender", "m"));
         assertU(adoc("id", "6",
                 "body", "hello hello hello bye bye bye bye bye bye bye", "gender", "f"));
         assertU(adoc("id", "7",
                 "body", "hello hello hello hello hello hello hello bye bye bye", "gender", "m"));
         assertU(adoc("id", "8",
                 "body", "hello hello bye bye bye bye bye bye bye bye", "gender", "f"));

          assertU(commit());
     }

     @BeforeClass
     public static void beforeTests() throws Exception {
          initCore("solrconfig-commonrules.xml", "schema.xml");
     }

     @Override
     @Before
     public void setUp() throws Exception {
          super.setUp();
          clearIndex();
          index();
     }

     @Test
     public void testThatFairSearchRun() throws Exception {
          String q = "hello";

          SolrQueryRequest req = req("q", q,
                  QueryParsing.OP, "OR",
                  "rq", "{!fairrerank reRankDocs=8}",
                  "debugQuery", "true"
          );

          assertNotNull(req);

             assertQ(req, "*[count(//doc)=0]"/*, "//result/doc[1]/str[@name='id'][.='1']" */);

              //assertQ("Default fair search", req, "//result[@name='response'][@numFound='8']");
          req.close();

     }

}
