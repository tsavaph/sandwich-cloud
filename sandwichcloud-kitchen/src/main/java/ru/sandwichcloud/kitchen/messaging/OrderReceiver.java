package ru.sandwichcloud.kitchen.messaging;

import ru.sandwichcloud.domain.SandwichOrder;

public interface OrderReceiver {
    SandwichOrder receiveOrder();
}
