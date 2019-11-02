package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.message.ChatMessage;
import kr.sm.itaewon.travelmaker.model.ChatRoom;
import kr.sm.itaewon.travelmaker.model.Customer;
import kr.sm.itaewon.travelmaker.repo.ChatRoomRepository;
import kr.sm.itaewon.travelmaker.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/chat")
public class ChatController {

//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @MessageMapping("/message")
    public void message(ChatMessage message) {

        Customer customer = customerRepository.findByCustomerId(message.getSenderId());

        if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(customer.getName() + "님이 입장하셨습니다.");

        //messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/roomList/customerId={customerId}")
    public ResponseEntity<List<ChatRoom>> getRoomList(@PathVariable long customerId) {
        List<ChatRoom> roomList = chatRoomRepository.findAllByCustomerId(customerId);
        // 채팅방 생성순서 최근 순으로 반환
        //List chatRooms = new ArrayList<>(chatRoomMap.values());
        //Collections.reverse(chatRooms);

        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @PostMapping("/createRoom/")
    public ResponseEntity<ChatRoom> createRoom(@RequestBody ChatRoom chatRoomParam) {
        // 채팅방 생성
        ChatRoom chatRoomModel = new ChatRoom();
        while (true) {
            chatRoomModel.setRoomId(UUID.randomUUID().toString());
            chatRoomModel.setTitle(chatRoomParam.getTitle());

            // 중복 검사
            ChatRoom duplicate = chatRoomRepository.findByRoomId(chatRoomModel.getRoomId());

            if (duplicate == null) {
                chatRoomRepository.save(chatRoomModel);
                break;
            }

        }
        return new ResponseEntity<>(chatRoomModel, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoom")
    public ResponseEntity<Void> deleteRoom(@RequestBody ChatRoom chatRoom){

        return new ResponseEntity<>(HttpStatus.OK);

    }
//    // 채팅방 입장
//    @GetMapping("/room/enter/{roomId}")
//    public ResponseEntity<Void> roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId", roomId);
//        return new ResonsEntity<>(HttpStatus.OK);
//    }
//
//    // 특정 채팅방 조회
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        return chatRoomRepository.findRoomById(roomId);
//    }
}
