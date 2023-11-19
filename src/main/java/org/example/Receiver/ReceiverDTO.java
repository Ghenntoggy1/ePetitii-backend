package org.example.Receiver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverDTO {
    private Integer id;
    private String name;
    private Map<String, String> i18n;

    public static ReceiverDTO mapFromReceiver(Receiver receiver) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ro", receiver.getReceiver_name_ro());
        map.put("ru", receiver.getReceiver_name_ru());
        map.put("en", receiver.getReceiver_name_en());
        return new ReceiverDTO(receiver.getReceiver_id(), receiver.getReceiver_name_ro(), map);
    }
}
