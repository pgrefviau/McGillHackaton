package com.hackaton.findme.com.hackaton.findme.communication;

import com.couchbase.lite.Document;
import com.couchbase.lite.replicator.Replication;

/**
 * Created by Phil on 22/02/2015.
 */
public class DocumentWrapper {

    private Document m_document;

    private Document.ChangeListener m_changeListener;

    public DocumentWrapper(Document m_document) {
        this.m_document = m_document;
    }

    public Document get_document() {
        return m_document;
    }

    public Document.ChangeListener get_changeListener() {
        return m_changeListener;
    }

    public void set_changeListener(Document.ChangeListener changeListener) {
        this.m_changeListener = changeListener;
    }

    public void removeListener()
    {
        this.m_document.removeChangeListener(this.get_changeListener());
    }
}
