package cn.asens.shiro.filter;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by lenovo on 2015/12/31.
 */
public class ConcurrencyFilter extends AccessControlFilter {
    private SessionManager sessionManager;
    private  boolean kickoutAfter;
    private int maxSession;
    private  String kickoutUrl;
    private Cache<String, Deque<Serializable>> cache;
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject=getSubject(request,response);
        if(!subject.isAuthenticated()&&!subject.isRemembered())
        {
            return true;
        }

        Session session=subject.getSession();
        String username=(String)subject.getPrincipal();
        Serializable sessionId=session.getId();
        Deque<Serializable> deque=cache.get(username);
        if(deque==null)
        {
            deque=new LinkedList<Serializable>();
            cache.put(username,deque);
        }
        if(!deque.contains(sessionId)&&session.getAttribute("kickout")==null)
        {
            deque.push(sessionId);
        }
        //System.out.println(deque.size());
        while(deque.size()>maxSession)
        {
            Serializable kickoutSessionId=null;
            if(kickoutAfter)
            {
                kickoutSessionId=deque.removeFirst();
            }else{
                kickoutSessionId=deque.removeLast();
            }
            try{
                Session kickoutSession=sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession!=null)
                {
                    kickoutSession.setAttribute("kickout",true);
                }
            }catch (UnknownSessionException e) {
                System.out.println("session"+kickoutSessionId+"has been removed");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);
            WebUtils.redirectToSavedRequest(request, response, kickoutUrl);
            return false;
        }

        return true;

    }


    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
