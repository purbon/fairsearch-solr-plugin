package com.purbon.search.solr;

import com.purbon.search.solr.rescorer.FairReRankQParser;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;

public class FairReRankQParserPlugin extends QParserPlugin {


    public static final String NAME = "fairrerank";
    public static final String RERANK_DOCS = "reRankDocs";


    public static final String PROTECTED_FIELD = "protectedField";
    public static final String PROTECTED_VALUE = "protectedValue";

    @Override
    public QParser createParser(final String qstr, final SolrParams localParams, final SolrParams params,
                                final SolrQueryRequest req) {
        assert localParams != null;
        return new FairReRankQParser(qstr, localParams, params, req);
    }


    @Override
    public void init( NamedList args ) {
        // use this for initialisation/loading
        // args holds the config from solrconfig.xml
    }


}

