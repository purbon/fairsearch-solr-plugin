package com.purbon.search.solr.rescorer;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.solr.search.AbstractReRankQuery;

public class FairReRankQuery extends AbstractReRankQuery {

    private static Query DEFAULT_QUERY = new MatchAllDocsQuery();

    private final int reRankDocs;
    private final String protectedField;
    private final String protectedValue;

    public FairReRankQuery(final int reRankDocs, String protectedField, String protectedValue) {
        super(DEFAULT_QUERY, reRankDocs, new FairRescorer(protectedField, protectedValue));
        this.reRankDocs = reRankDocs;
        this.protectedField = protectedField;
        this.protectedValue = protectedValue;

    }

    @Override
    protected Query rewrite(final Query rewrittenMainQuery) {
        return new FairReRankQuery(reRankDocs, protectedField, protectedValue).wrap(rewrittenMainQuery);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}