package com.hsforum.correlation;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

public class Main {
    public static void main(String[] args) throws Exception {
        Session s = HibernateUtil.getSession();

        FullTextSession fts = Search.getFullTextSession(s);

        fts.createIndexer(Artist.class, Album.class, House.class)
        .batchSizeToLoadObjects(30)
        .threadsForSubsequentFetching(4)
        .threadsToLoadObjects(2)
        .startAndWait();

        System.out.println("done");
    }
}
