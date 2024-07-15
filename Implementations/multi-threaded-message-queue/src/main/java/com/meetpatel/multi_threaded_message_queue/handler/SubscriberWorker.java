package com.meetpatel.multi_threaded_message_queue.handler;

import com.meetpatel.multi_threaded_message_queue.model.Message;
import com.meetpatel.multi_threaded_message_queue.model.Topic;
import com.meetpatel.multi_threaded_message_queue.model.TopicSubscriber;
import lombok.NonNull;
import lombok.SneakyThrows;

public class SubscriberWorker implements Runnable {
    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SubscriberWorker(@NonNull final Topic topic, @NonNull final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int curOffset = topicSubscriber.getOffset().get();
                while (curOffset >= topic.getMessages().size()) {
                    topicSubscriber.wait();
                }
                Message message = topic.getMessages().get(curOffset);
                topicSubscriber.getSubscriber().consume(message);

                topicSubscriber.getOffset().compareAndSet(curOffset, curOffset + 1);
            } while(true);
        }
    }

    public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
