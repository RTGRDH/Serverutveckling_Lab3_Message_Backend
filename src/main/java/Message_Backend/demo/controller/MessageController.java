
package Message_Backend.demo.controller;

import Message_Backend.demo.bo.Message;
import Message_Backend.demo.handler.MessageHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.ProtocolException;
import java.util.List;

@RestController
public class MessageController {
    @CrossOrigin
    @GetMapping("/getMessages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam String currentUser) throws ProtocolException {
        System.out.println("Fetching current user '" + currentUser + "' messages");
        List<Message> msgs = MessageHandler.getMessages(currentUser);
        return ResponseEntity.ok(msgs);
    }
    @CrossOrigin
    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage (@RequestBody Message msg) throws ProtocolException {
        System.out.println("Sending message to user '" + msg.getToUser().getUsername() + "'");
        System.out.println("Message sent from '" + msg.getFromUser().getUsername() + "'");
        if(MessageHandler.sendMessage(msg.getTitle(), msg.getContent(), msg.getToUser().getUsername(), msg.getFromUser().getUsername())){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }
}
