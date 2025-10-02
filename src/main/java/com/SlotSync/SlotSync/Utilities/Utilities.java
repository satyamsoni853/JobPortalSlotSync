package com.SlotSync.SlotSync.Utilities;

import com.SlotSync.SlotSync.Entity.Sequence;
import com.SlotSync.SlotSync.Exception.JobPortalException;

import java.security.SecureRandom;

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
    private final MongoOperations mongoOperations;

    @Autowired
    public Utilities(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Long getNextSequence(String key) throws JobPortalException {
        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update().inc("seq", 1L);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        Sequence seq = mongoOperations.findAndModify(query, update, options, Sequence.class);
        if (seq == null) throw new JobPortalException("Unable to get sequence id for key :" + key);
        return seq.getSeq();
    }

    public static String generateOtp() {
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
     
    }

