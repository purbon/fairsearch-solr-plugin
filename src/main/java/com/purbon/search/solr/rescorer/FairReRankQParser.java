package com.purbon.search.solr.rescorer;

import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;

public class FairReRankQParser extends QParser {

    public FairReRankQParser(final String qstr,
                             final SolrParams localParams,
                             final SolrParams params,
                             final SolrQueryRequest req) {

        super(qstr, localParams, params, req); // need to call super(..) constructor

        // You can init member variables etc. here for later use in parse()

        // There will be one MyReRankQParser object per request - so it’s safe to

        // hold request specific references, but avoid keeping a reference from the query to the request itself (the query could become

        // a cache key and the request holds a reference to the SolrCore - big & fat, ...

    }


    @Override
    public Query parse() throws SyntaxError {

        int reRankDocs = 100; // rerank the top 100 docs. Alternatively, you can pass this in as a request param, which you can access in the

        // constructor

        return new FairReRankQuery(reRankDocs);

    }
}


