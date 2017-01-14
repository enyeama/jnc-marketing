package com.sap.jnc.marketing.cluster.session.manager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

public interface SessionEventManager {
    void sessionCreatedEvent(HttpSession paramHttpSession);

    void sessionDestroyedEvent(HttpSession paramHttpSession);

    void unbindAttribute(HttpSession paramHttpSession,
                         String paramString, Object paramObject);

    void bindAttribute(HttpSession paramHttpSession,
                       String paramString, Object paramObject);

    void setAttribute(HttpSession paramHttpSession,
                      String paramString, Object paramObject);

    void removeAttribute(HttpSession paramHttpSession,
                         String paramString, Object paramObject);

    void replaceAttribute(HttpSession paramHttpSession,
                          String paramString, Object paramObject1, Object paramObject2);


    HttpSessionListener[] getSessionListeners();

    void setSessionListeners(HttpSessionListener[] sessionListeners);


    HttpSessionAttributeListener[] getSessionAttributeListeners();


    void setSessionAttributeListeners(
            HttpSessionAttributeListener[] sessionAttributeListeners);
}
