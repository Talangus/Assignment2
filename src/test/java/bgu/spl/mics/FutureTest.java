package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;
    private Future<String> f;

    @BeforeEach
    public void setUp(){
        future = new Future<>();
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        assertFalse(future.isDone());
        assertTrue(future.get(0, TimeUnit.MILLISECONDS)==null);
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }

    @Test
    public void get(){
        String ans = "new result";
        future.resolve(ans);
        assertTrue(ans.equals(future.get()));
    }

    @Test
    public void isDone(){
        assertFalse(future.isDone());
        future.isDone();
        assertFalse(future.isDone());   //makes sure isdone doesn't change the filed
        future.resolve(" ");
        assertTrue(future.isDone());
    }

    @Test
    public void get(long timeout, TimeUnit unit) {
    assertFalse(future.isDone());
    assertTrue(future.get(0, TimeUnit.MILLISECONDS)==null);
    String ans = "resolved";
    future.resolve("resolved");
    assertTrue(ans.equals(future.get(2,TimeUnit.SECONDS)));

    }



}
