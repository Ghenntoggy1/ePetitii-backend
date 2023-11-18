package org.example.Receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiverService {
    private final ReceiverRepository receiverRepository;

    @Autowired
    public ReceiverService(ReceiverRepository receiverController) {
        this.receiverRepository = receiverController;
    }

    public List<ReceiverDTO> getAllReceivers() {
        return receiverRepository.findAll().stream()
                .map(ReceiverDTO::mapFromReceiver)
                .collect(Collectors.toList());
    }
}
