package com.purbon.search.solr.lib.fairness;

import com.purbon.search.fair.lib.NotImplementedException;
import com.purbon.search.solr.lib.FairnessTableLookup;
import com.purbon.search.solr.utils.MTableGenerator;

public class GenFairnessTableLookup implements FairnessTableLookup {

    public int fairness(int k, float proportion, float significance) {
        throw new NotImplementedException();
    }

    @Override
    public int[] fairnessAsTable(int k, float p, float a) {
        return new MTableGenerator(k,p, a).getMTable();
    }

}
