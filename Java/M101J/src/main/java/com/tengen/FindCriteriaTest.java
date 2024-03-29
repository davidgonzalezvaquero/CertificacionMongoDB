package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created by David González on 19/01/14.
 */
public class FindCriteriaTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("findCriteriaTest");
        collection.drop();

        //insert 10 documents with a random integer as the value of field "x"
        for(int i = 0; i < 10; i++){
            collection.insert(new BasicDBObject("x", new Random().nextInt(2)).append("y", new Random().nextInt(100)));
        }

        QueryBuilder builder = QueryBuilder.start("x").is(0).and("y").greaterThan(10).lessThan(90);
        DBObject query = new BasicDBObject("x",0)
                .append("y", new BasicDBObject("$gt",10).append("$lt",90));

        System.out.println("Find one:");
        DBObject one = collection.findOne();
        System.out.println(one);

        System.out.println("\nFind all:");
        DBCursor cursor = collection.find(builder.get());
        try{
            while (cursor.hasNext()){
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        }finally{
            cursor.close();
        }

        System.out.println("\nCount:");
        long count = collection.count(builder.get());
        System.out.println(count);
    }
}
