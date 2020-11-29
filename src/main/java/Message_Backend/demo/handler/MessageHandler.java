
package Message_Backend.demo.handler;

import Message_Backend.demo.bo.Message;
import Message_Backend.demo.bo.UsersEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageHandler {
    public static Boolean sendMessage(String title, String content, String toUser, String currUser) throws ProtocolException {
        //get user
        UsersEntity toUsers = getUser(toUser);
        UsersEntity currUsers = getUser(currUser);
        if(toUsers != null && currUsers != null){
            if(Message.sendMessage(title, content, toUsers, currUsers)){
                return true;
            }
        }
        return false;
    }

    public static List<Message> getMessages(String currUser) throws ProtocolException {
        UsersEntity curr = getUser(currUser);
        ArrayList<Message> msgs = Message.getMessages(curr);
        return msgs;
    }

    private static UsersEntity getUser(String name) throws ProtocolException {
        HttpURLConnection connection = null;
        try {
            //Koppla till users container
            connection = (HttpURLConnection) new URL("http://user-backend:8002/getUser?name=" + name).openConnection();

            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();


            if (responseCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                UsersEntity user = mapper.readValue(connection.getInputStream(), UsersEntity.class);
                System.out.println("Got user: " + user.getUsername() + " from API call");
                return user;
            }
            // an error happened
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
