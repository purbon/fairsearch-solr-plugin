package com.purbon.search.solr.rescorer;

import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;

import static com.purbon.search.solr.FairReRankQParserPlugin.PROTECTED_FIELD;
import static com.purbon.search.solr.FairReRankQParserPlugin.PROTECTED_VALUE;
import static com.purbon.search.solr.FairReRankQParserPlugin.RERANK_DOCS;
import static com.purbon.search.solr.FairReRankQParserPlugin.RERANK_DOCS_DEFAULT;

public class FairReRankQParser extends QParser {

    public static final String RERANK_QUERY = "fairReRankQuery";
    private final int reRankDocs;
    private final String protectedField;
    private final String protectedValue;

    public FairReRankQParser(final String qstr,
                             final SolrParams localParams,
                             final SolrParams params,
                             final SolrQueryRequest req) {

        super(qstr, localParams, params, req); // need to call super(..) constructor

        // You can init member variables etc. here for later use in parse()

        // There will be one MyReRankQParser object per request - so it’s safe to

        // hold request specific references, but avoid keeping a reference from the query to the request itself (the query could become

        // a cache key and the request holds a reference to the SolrCore - big & fat, ...

        reRankDocs  = localParams.getInt(RERANK_DOCS, RERANK_DOCS_DEFAULT);
        protectedField = localParams.get(PROTECTED_FIELD);
        protectedValue = localParams.get(PROTECTED_VALUE);

    }


    @Override
    public Query parse() throws SyntaxError {
        return new FairReRankQuery(reRankDocs, protectedField, protectedValue);

    }
}


