package zephiransas;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.net.websocket.Session;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;

@Startup
@Singleton
public class TwitterClientSingleton extends StatusAdapter {

    private static TwitterStream stream = null;
    
    public static Set<Session> peers = null;
    
    static {
        peers = Collections.synchronizedSet(new HashSet());
    }
    
    @PostConstruct
    public void initTwitterStream() {
        stream = TwitterStreamFactory.getSingleton();
        FilterQuery filter = new FilterQuery();
        filter.track(new String[]{"java"});
        stream.addListener(this);
        stream.filter(filter);
    }

    @Override
    public void onStatus(Status status) {
        User user = status.getUser();
        //if(user.getLang().equals("ja")) {
            String tweet = "@" + user.getScreenName() + ":" + status.getText();
            for(Session peer : peers) {
                try {
                    peer.getRemote().sendString(tweet);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        //}
    }
    
}
