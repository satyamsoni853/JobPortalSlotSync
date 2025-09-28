package com.SlotSync.SlotSync.Utility;

import com.SlotSync.SlotSync.Entity.Sequence;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class Utilities {

    private static final String COUNTERS_COLLECTION = "counters"; // collection to store sequences
    private static MongoOperations mongoOperations;

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        Utilities.mongoOperations = mongoOperations;
    }

    public static Long getNextSequence(String key) throws JobPortalException {
        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update().inc("seq", 1L);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        Sequence seq = mongoOperations.findAndModify(query, update, options, Sequence.class);
        if (seq == null) throw new JobPortalException("Unable to get sequence id for key :" + key);
        return seq.getSeq();
    }
}
