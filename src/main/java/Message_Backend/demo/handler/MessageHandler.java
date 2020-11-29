
package Message_Backend.demo.handler;

import Message_Backend.demo.bo.Message;
import Message_Backend.demo.bo.UsersEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        UsersEntity currUsers = new UsersEntity();

        /*if(Message.sendMessage(title, content, toUsers, currUsers)){
            return true;
        }*/
        return false;
    }

    public static List<Message> getMessages(String currUser) {
        UsersEntity curr = new UsersEntity();
        ArrayList<Message> msgs = Message.getMessages(curr);
        return msgs;
    }

    private static UsersEntity getUser(String name) throws ProtocolException {
        try {
            //Koppla till users container
            HttpURLConnection connection = (HttpURLConnection) new URL("http://user_backend:8002/getUser").openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("name", name);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String response = "";
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine()) {
                    response += scanner.nextLine();
                    response += "\n";
                }
                scanner.close();
                System.out.println(response);
                return null;
            }

            // an error happened
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
