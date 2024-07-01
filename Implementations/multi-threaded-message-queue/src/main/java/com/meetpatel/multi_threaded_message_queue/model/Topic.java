package com.meetpatel.multi_threaded_message_queue.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Topic {
    private final String id;
    private final String topicName;
    private final List<Message> messages;
    private final List<TopicSubscriber> subscribers;

    public Topic(@NonNull final String id, @NonNull final String topicName) {
        this.id = id;
        this.topicName = topicName;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public synchronized void addMessage(@NonNull final Message message) {
        this.messages.add(message);
    }

    public void addSubscriber(@NonNull final TopicSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }
}
