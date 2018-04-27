package com.purbon.search.solr.rescorer;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.solr.search.AbstractReRankQuery;

public class FairReRankQuery extends AbstractReRankQuery {

    private static Query DEFAULT_QUERY = new MatchAllDocsQuery();


    private final int reRankDocs;

    public FairReRankQuery(final int reRankDocs) {

        // I’m using a MatchAllQuery as the main query here - the super class does not use it

        // See MyRescorer below for an easier way to access the main query (= the parsed value of q=…)

        super(DEFAULT_QUERY, reRankDocs, new FairRescorer(/* pass whatever you need for rescoring*/));

        this.reRankDocs = reRankDocs;

    }

    @Override
    protected Query rewrite(final Query rewrittenMainQuery) {
        // you'll probably never get here, just for the sake of the contract
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