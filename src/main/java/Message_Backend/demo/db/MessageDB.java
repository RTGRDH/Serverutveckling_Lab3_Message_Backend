package Message_Backend.demo.db;

import Message_Backend.demo.bo.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageDB {

    public static boolean sendMessage(String title, String content, UsersEntity toUser, UsersEntity currUser) {
        MessageEntity newMessage = new MessageEntity();
        newMessage.setContent(content);
        newMessage.setFromUser(currUser);
        newMessage.setToUser(toUser);
        newMessage.setTitle(title);
        newMessage.setSent(new Timestamp(System.currentTimeMillis()));
        Session session = HibernateUtil.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(newMessage);
        session.save(newMessage);
        transaction.commit();
        session.close();
        return true;
    }

    public static ArrayList<Message> getMessages(UsersEntity currUser) {
        Session session = null;
        try{
            session = HibernateUtil.getFactory().openSession();
            session.beginTransaction();
            ArrayList<MessageEntity> result = (ArrayList<MessageEntity>) session.createQuery(
                    "From MessageEntity where toUser = '" + currUser.getId() +"'")
                    .list();
            session.getTransaction().commit();
            return entityToClass(result);
        }finally{
            session.close();
        }
    }

    private static ArrayList<Message> entityToClass(ArrayList<MessageEntity> result) {
        ArrayList<Message> r = new ArrayList<>();
        for(int i = 0; i < result.size(); i++){
            User toUser = new User();
            toUser.setUsername(result.get(i).getToUser().getUsername());
            toUser.setPassword(result.get(i).getFromUser().getPassword());
            User fromUser = new User();
            fromUser.setUsername(result.get(i).getFromUser().getUsername());
            fromUser.setPassword(result.get(i).getFromUser().getPassword());
            Message temp = new Message();
            temp.setId(result.get(i).getId());
            temp.setTitle(result.get(i).getTitle());
            temp.setContent(result.get(i).getContent());
            temp.setSent(result.get(i).getSent());
            temp.setToUser(toUser);
            temp.setFromUser(fromUser);
            r.add(temp);
        }
        return r;
    }
}
