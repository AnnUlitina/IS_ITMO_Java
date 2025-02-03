package itmo.messaging;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AddFriendMessage {
    Long catId;
    Long friendId;
    Long ownerId;
}
