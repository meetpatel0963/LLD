package com.meetpatel.multi_threaded_message_queue.model;

public interface ISubscriber {
    String getId();
    void consume(Message message) throws InterruptedException;
}
