package ru.sandwichcloud.messaging;

import ru.sandwichcloud.SandwichOrder;

public interface OrderMessagingService {
    void sendOrder(SandwichOrder order);
}
