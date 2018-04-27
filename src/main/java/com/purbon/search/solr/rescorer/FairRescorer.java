package com.purbon.search.solr.rescorer;

import com.purbon.search.solr.lib.FairTopK;
import com.purbon.search.solr.lib.FairTopKImpl;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;


public class FairRescorer extends Rescorer {

    private FairTopK topK = new FairTopKImpl();

    // final TopDocs firstPassTopDocs - the top n results from the first pass

    // final int topN - the actual N in top N

    @Override

    public TopDocs rescore(final IndexSearcher searcher, final TopDocs firstPassTopDocs, final int topN) throws IOException {


        // in case you need the main query:

        // Query query = SolrRequestInfo.getRequestInfo().getResponseBuilder().getQuery();

        final ScoreDoc[] origScoreDocs = firstPassTopDocs.scoreDocs;

        // your new scores, initialised with the original scores
        final ScoreDoc[] rescoredDocs = new ScoreDoc[origScoreDocs.length];
        System.arraycopy(origScoreDocs, 0, rescoredDocs, 0, rescoredDocs.length);

        // now change the scores in rescoredDocs:

        int max = Math.min(firstPassTopDocs.scoreDocs.length, topN);

        List<ScoreDoc> npQueue = new ArrayList<>();
        List<ScoreDoc> pQueue = new ArrayList<>();

        for(int i=0; i < max; i++) {
            ScoreDoc scoreDoc = rescoredDocs[i];
            Document doc = searcher.doc(scoreDoc.doc);
            if (isProtected(doc)) {
                pQueue.add(scoreDoc);
            } else {
                npQueue.add(scoreDoc);
            }
        }
        assert npQueue.size() + pQueue.size() == max;


        float significance = 0.1f;
        float proportion = 0.6f;
        int protectedElementsCount = Math.round(proportion * rescoredDocs.length);

        // …

        // finally sort the re-scored docs:
        /*Arrays.sort(rescoredDocs, SCORE_DOC_COMPARATOR);

        if (topN < rescoredDocs.length) { // not sure if this can ever happen
            final ScoreDoc[] shortened = new ScoreDoc[topN];
            System.arraycopy(rescoredDocs, 0, shortened, 0, topN);
            return new TopDocs(firstPassTopDocs.totalHits, shortened, shortened[0].score);
        } else {
            return new TopDocs(firstPassTopDocs.totalHits, rescoredDocs, rescoredDocs[0].score);
        }*/
        System.out.println(npQueue.size()+" "+pQueue.size());
        return topK.fairTopK(npQueue, pQueue, protectedElementsCount, proportion, significance);

    }

    @Override
    public Explanation explain(IndexSearcher indexSearcher, Explanation explanation, int i) throws IOException {
        return  Explanation.match(10.0f, "fair-rescoring", asList(explanation));
    }

    // Comparator for sorting by score desc

    private static final Comparator<ScoreDoc> SCORE_DOC_COMPARATOR = (o1, o2) -> {
        int c = Float.compare(o2.score, o1.score);
        return (c == 0) ? Integer.compare(o1.doc, o2.doc) : c;
    };

    private boolean isProtected(Document doc) {
        try {
            return doc.get("gender").equals("f");
        } catch (Exception ex) {
            throw new Error("Gender should be an stored value for this plugin to work properly.");
        }
    }

    /*
    private boolean isProtected(Document doc, FairSearchConfig config) {
        try {
            return doc.get(config.getProtectedKey()).equals(config.getProtectedValue());
        } catch (Exception ex) {
            throw new ElasticsearchException(config.getProtectedKey()+" should be an stored value for this plugin to work properly.");
        }
    }*/



}

