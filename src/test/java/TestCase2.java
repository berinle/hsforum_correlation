import com.hsforum.correlation.Album;
import com.hsforum.correlation.Artist;
import com.hsforum.correlation.HibernateUtil;
import com.hsforum.correlation.House;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * @author berinle
 */
public class TestCase2 {

    Session session;
    FullTextSession fts;

    @BeforeClass
    public void indexDocuments(){
        try {
            session = HibernateUtil.getSession();
            fts = Search.getFullTextSession(session);
            fts.beginTransaction();
            fts.createIndexer(Artist.class, Album.class, House.class)
            .batchSizeToLoadObjects(30)
            .threadsForSubsequentFetching(4)
            .threadsToLoadObjects(2)
            .startAndWait();

//            fts.getTransaction().commit();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @BeforeTest
    public void before(){
        session = HibernateUtil.getSession();
        fts = Search.getFullTextSession(session);
        fts.beginTransaction();
    }

    @Test
    public void test_correlation_search_using_child_relations_simple(){
        //all artist with a gospel album in 2011
        String qry = "+genre:gospel +year:2011";
        try {
            QueryParser parser = new QueryParser(Version.LUCENE_30, "name", new StandardAnalyzer(Version.LUCENE_30));
            Query luceneQuery = parser.parse(qry);
            List list = fts.createFullTextQuery(luceneQuery, Artist.class, Album.class, House.class).list();

            assertTrue("No artists should match!", list.isEmpty());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_correlation_search_using_child_relations_harder(){
        //all artist with inspirational albums in 2007, who have a house in DC with zip of 20002
        String qry = "+genre:inspirational +year:2007";
        String qry2 = "+city:dc +zip:20002";
//        String qry2 = "+city:dc +zip:20554";
        try {
            QueryParser parser = new QueryParser(Version.LUCENE_30, "name", new StandardAnalyzer(Version.LUCENE_30));
            Query luceneQuery = parser.parse(qry);
            List list = fts.createFullTextQuery(luceneQuery, Artist.class, Album.class, House.class).list();

            assertEquals(1, list.size());

            List<Long> matchingIds = new ArrayList<Long>();
            for (Object o : list) {
                matchingIds.add(((Album) o).getArtist().getId());
            }

            List list1 = fts.createFullTextQuery(parser.parse(qry2), Artist.class, Album.class, House.class).list();

            assertTrue(list1.isEmpty());

            List<Long> resultIds = new ArrayList<Long>();

            //build final intersection list
            for(Long id: matchingIds){
                for(Object o: list1){
                    Long tempId = ((House) o).getArtist().getId();
                    if(id == tempId){
                        resultIds.add(id);
                    }
                }
            }

            assertTrue(resultIds.isEmpty());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_correlation_search_using_child_relations_matching(){
        //all artist with inspirational albums in 2007, who have a house in DC with zip of 20002
        String qry = "+genre:inspirational +year:2007";
        String qry2 = "+city:dc +zip:20554";
        try {
            QueryParser parser = new QueryParser(Version.LUCENE_30, "name", new StandardAnalyzer(Version.LUCENE_30));
            Query luceneQuery = parser.parse(qry);
            List list = fts.createFullTextQuery(luceneQuery, Artist.class, Album.class, House.class).list();

            assertEquals(1, list.size());

            List<Long> matchingIds = new ArrayList<Long>();
            for (Object o : list) {
                matchingIds.add(((Album) o).getArtist().getId());
            }

            List list1 = fts.createFullTextQuery(parser.parse(qry2), Artist.class, Album.class, House.class).list();

            assertTrue(!list1.isEmpty());

            List<Long> resultIds = new ArrayList<Long>();

            //build final intersection list
            for(Long id: matchingIds){
                for(Object o: list1){
                    Long tempId = ((House) o).getArtist().getId();
                    if(id == tempId){
                        resultIds.add(id);
                    }
                }
            }

            assertEquals(1, resultIds.size());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void after(){
        fts.getTransaction().commit();
    }
}
