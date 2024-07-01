package com.meetpatel.multi_threaded_message_queue.handler;

import com.meetpatel.multi_threaded_message_queue.model.Topic;
import com.meetpatel.multi_threaded_message_queue.model.TopicSubscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkerMap;

    public TopicHandler(@NonNull final Topic topic) {
        this.topic = topic;
        subscriberWorkerMap = new HashMap<>();
    }

    public void publish() {
        for(TopicSubscriber subscriber : topic.getSubscribers()) {
            startSubscriberWorker(subscriber);
        }
    }

    public void startSubscriberWorker(@NonNull final TopicSubscriber topicSubscriber) {
        final String subscriberId = topicSubscriber.getSubscriber().getId();
        if (!subscriberWorkerMap.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
            subscriberWorkerMap.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        final SubscriberWorker subscriberWorker = subscriberWorkerMap.get(subscriberId);
        subscriberWorker.wakeUpIfNeeded();
    }
}
