package com.itm.space.kafka.event;

import com.fasterxml.jackson.databind.JsonNode;

public record ApplicationEvent(EventType type, JsonNode data) {
}