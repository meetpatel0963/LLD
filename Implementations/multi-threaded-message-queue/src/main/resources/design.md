```text
MessageQueue
- Map<TopicId, TopicHandler>
+ createTopic(String topicName)
+ publish(Topic topic, Message message)
+ subscribe(Topic topic, ISubscriber subscriber)
+ resetOffset(Topic topic, ISubscriber subscriber, int newOffset)

Topic
- id
- name
- List<Message>
- List<TopicSubscriber>

Message
- id
- text

TopicSubscriber
- AtomicInteger offset
- ISubscriber

<<ISubscriber>>
+ getId()
+ consume(Message message)

TopicHandler
+ Map<TopicSubscriberId, SubscriberWorker>
+ publish(Message message)
+ startSubscriberWorker(TopicSubscriber subscriber)

SubscriberWorker implements Runnable
- Topic
- ISubscriber
+ @Override run()
+ wakeUpIfNeeded()
```
