package ru.sandwichcloud.kitchen.messaging;

import ru.sandwichcloud.SandwichOrder;


public interface OrderReceiver {
    SandwichOrder receiveOrder();
}
