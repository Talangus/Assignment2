package bgu.spl.mics;

import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.LandoMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {
    private MessageBus bus;

    @BeforeEach
    void setUp() {bus = new MessageBusImpl();    }

    @Test
    void subscribeEvent() {  }

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
            assertNotNull(bus.awaitMessage(R2D2));
        } catch (InterruptedException ignored) {}
    }
    @Test
    void sendEvent() {
        MicroService Han = new HanSoloMicroservice();
        bus.register(Han);
        MicroService R2D2 = new HanSoloMicroservice();
        bus.register(R2D2);
        Event<Boolean> ev = new Event() {};
        bus.subscribeEvent(ev.getClass(),Han);
    }

    @Test
    void register() {
    }

    @Test
    void unregister() {
    }

    @Test
    void awaitMessage() {
    }
}