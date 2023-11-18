package org.example.Receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/receiver")
public class ReceiverController {
    private final ReceiverService receiverService;

    @Autowired
    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    @GetMapping()
    public ResponseEntity<List<ReceiverDTO>> getAllReceivers() {
        List<ReceiverDTO> allReceivers = receiverService.getAllReceivers();
        return ResponseEntity.ok(allReceivers);
    }
}
