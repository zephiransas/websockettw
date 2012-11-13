/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zephiransas;

import javax.net.websocket.Session;
import javax.net.websocket.annotations.WebSocketClose;
import javax.net.websocket.annotations.WebSocketEndpoint;
import javax.net.websocket.annotations.WebSocketOpen;


@WebSocketEndpoint(path="/twitter")
public class TwitterWebSocket {

    @WebSocketOpen
    public void initOpen(Session session) {
        TwitterClientSingleton.peers.add(session);
    }
    
    @WebSocketClose
    public void close(Session session) {
        TwitterClientSingleton.peers.remove(session);
    }
    
}
