package com.purbon.search.solr.rescorer;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.solr.search.AbstractReRankQuery;

public class FairReRankQuery extends AbstractReRankQuery {

    private static Query DEFAULT_QUERY = new MatchAllDocsQuery();

    private final int reRankDocs;

    public FairReRankQuery(final int reRankDocs) {
        super(DEFAULT_QUERY, reRankDocs, new FairRescorer());
        this.reRankDocs = reRankDocs;

    }

    @Override
    protected Query rewrite(final Query rewrittenMainQuery) {
        return new FairReRankQuery(reRankDocs).wrap(rewrittenMainQuery);
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