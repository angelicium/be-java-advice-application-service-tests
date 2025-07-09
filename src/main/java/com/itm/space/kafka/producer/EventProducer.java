package com.itm.space.kafka.producer;

public interface EventProducer<T> {
    void produce(T event);
}
