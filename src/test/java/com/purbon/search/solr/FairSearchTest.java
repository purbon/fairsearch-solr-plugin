package com.purbon.search.solr;

import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QueryParsing;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FairSearchTest  extends SolrTestCaseJ4 {

     public void index() {

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
         assertU(adoc("id", "9",
                 "body", "hello hello hello hello hello hello bye bye bye bye", "gender", "m"));
         assertU(adoc("id", "10",
                 "body", "hello bye bye bye bye bye bye bye bye bye", "gender", "f"));

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
     public void testThatFairSearchRun() {
         String q = "body:hello";

         StringBuilder queryParams = new StringBuilder();
         queryParams.append(FairReRankQParserPlugin.RERANK_DOCS);
         queryParams.append("=10 ");
         queryParams.append(FairReRankQParserPlugin.PROTECTED_FIELD);
         queryParams.append("=gender ");
         queryParams.append(FairReRankQParserPlugin.PROTECTED_VALUE);
         queryParams.append("=f ");

         ModifiableSolrParams params = new ModifiableSolrParams();
         params.add("rq", "{!"+FairReRankQParserPlugin.NAME+" "+queryParams.toString()+"}");
         params.add("q", q);
         params.add("start", "0");
         params.add("df", "fairrerank");

         assertNotNull(params);

         assertQ(req(params), "*[count(//doc)=10]",
                 "//result/doc[1]/str[@name='id'][.='1']",
                 "//result/doc[2]/str[@name='id'][.='3']",
                 "//result/doc[3]/str[@name='id'][.='5']",
                 "//result/doc[4]/str[@name='id'][.='2']",
                 "//result/doc[5]/str[@name='id'][.='7']"
         );

     }

}
