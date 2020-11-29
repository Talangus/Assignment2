package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//empty tests according to office hours and forum requirements
class MessageBusImplTest<T> {
    private MessageBus bus;

    @BeforeEach
    void setUp() {bus = new MessageBusImpl();    }

    @Test
    void subscribeEvent() {}

    @Test
    void subscribeBroadcast() {
    }

    @Test
    void complete() {
    }

    @Test
    void sendBroadcast() {
        MicroService Han = new HanSoloMicroservice();
        bus.register(Han);
        MicroService R2D2 = new HanSoloMicroservice();
        bus.register(R2D2);
        MicroService C3po = new C3POMicroservice();
        bus.register(C3po);
        Broadcast br = new Broadcast() {
        };
        bus.subscribeBroadcast(br.getClass(), R2D2);
        bus.subscribeBroadcast(br.getClass(), C3po);
        bus.sendBroadcast(br);
        try {
            assertNotNull(bus.awaitMessage(R2D2));
            assertNotNull(bus.awaitMessage(C3po));
        } catch (InterruptedException ignored) {}
    }
    @Test
    void sendEvent() {
        MicroService Han = new HanSoloMicroservice();
        bus.register(Han);
        AttackEvent ev = new AttackEvent();
        bus.subscribeEvent(ev.getClass(),Han);
        bus.sendEvent(ev);
        try {
            assertNotNull(bus.awaitMessage(Han));
        } catch (InterruptedException ignored) {}

    }

    @Test
    void register() {
    }

    @Test
    void unregister() {
    }

    @Test
    void awaitMessage() {
        MicroService Han = new HanSoloMicroservice();
        bus.register(Han);
        AttackEvent ev = new AttackEvent();
        bus.subscribeEvent(ev.getClass(),Han);
        bus.sendEvent(ev);
        try {
            assertNotNull(bus.awaitMessage(Han));
        } catch (InterruptedException ignored) {}
    }
}