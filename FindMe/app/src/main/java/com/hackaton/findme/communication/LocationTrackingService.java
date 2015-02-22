package com.hackaton.findme.communication;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.DocumentChange;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.replicator.Replication;
import com.hackaton.findme.usermanagement.UserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Phil on 22/02/2015.
 */
public class LocationTrackingService implements Replication.ChangeListener{

    private UserInfo m_currentUser;
    private Map<Long, DocumentWrapper> m_userIdToPositionAsDocument = new HashMap<>();
    private Database m_locationDatabase;

    private static final String LOCATION_DB_NAME = "user_locations";
    private static final String LOCATION_PROPERTY_KEY = "location";
    private static final String LAST_UPDATE_TIME_PROPERTY_KEY = "last_update_date";
    private static final String USER_ID_PROPERTY_KEY = "user_id";

    public LocationTrackingService(Manager manager, String serverPath, UserInfo user) throws CouchbaseLiteException {

        m_locationDatabase = manager.getDatabase(LOCATION_DB_NAME);
        m_currentUser = user;
        URL url = null;
        try {
            url = new URL(serverPath + "\\" + LOCATION_DB_NAME);
        } catch (MalformedURLException e) {

        }



        Replication push = m_locationDatabase.createPushReplication(url);
        push.setContinuous(true);
    }

    private Document getCurrentUserLocationAsDocument() throws CouchbaseLiteException {
        return getAndSaveUserLocationAsDocument(m_currentUser);
    }

    private Document getAndSaveUserLocationAsDocument(UserInfo user) throws CouchbaseLiteException {
        long targetUserId = user.get_id();
        if(!m_userIdToPositionAsDocument.containsKey(targetUserId))
        {
            m_userIdToPositionAsDocument.put( targetUserId, new DocumentWrapper(getUserLocationAsDocument(user)));
        }

        return m_userIdToPositionAsDocument.get(targetUserId).get_document();
    }

    private Document getUserLocationAsDocument(UserInfo m_currentUser) throws CouchbaseLiteException {
        Query query = m_locationDatabase.getView(USER_ID_PROPERTY_KEY).createQuery();
        query.setLimit(1);
        QueryEnumerator enumerator = query.run();
        return enumerator.next().getDocument();
    }

    private boolean checkIfTrackedByOthers()
    {
        return true;
    }

    public void updateCurrentLocation(GeoData geoData)
    {
        Document currentUserPosition = null;
        try {
            currentUserPosition = getCurrentUserLocationAsDocument();
            currentUserPosition.getProperties().put(LOCATION_PROPERTY_KEY, geoData);
            currentUserPosition.getProperties().put(LAST_UPDATE_TIME_PROPERTY_KEY, new Date());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public void startWatchingUserPosition(UserInfo targetUser)
    {
        // Check if we are not watching ourselves
        if(targetUser.get_id() == m_currentUser.get_id())
        {
            return;
        }

        Document doc = null;
        try {
            doc = getAndSaveUserLocationAsDocument(targetUser);
            DocumentWrapper docWrapper = new DocumentWrapper(doc);
            docWrapper.set_changeListener(new Document.ChangeListener() {
                @Override
                public void changed(Document.ChangeEvent event) {
                    DocumentChange docChange = event.getChange();
                    GeoData geoData = getGeoDataFromDocumentProperties(docChange.getAddedRevision().getProperties());
                }
            });

            doc.addChangeListener(docWrapper.get_changeListener());
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    private void stopWatchingUserPosition(UserInfo targetUser)
    {
        if(!m_userIdToPositionAsDocument.containsKey(targetUser.get_id()))
        {
            return;
        }

        DocumentWrapper doc = m_userIdToPositionAsDocument.get(targetUser.get_id());
        doc.removeListener();
    }

    private GeoData getGeoDataFromDocumentProperties(Map<String, Object> properties)
    {
        return new GeoData();
    }

    @Override
    public void changed(Replication.ChangeEvent changeEvent) {

    }
}
