package com.kafka.dto;

import java.io.Serializable;

public class KafkaDto implements Serializable
{
    private String topic;
    private String value;
    private String key;
    private String partition;
    private String offset;
    private String timeStamp;

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getPartition()
    {
        return partition;
    }

    public void setPartition(String partition)
    {
        this.partition = partition;
    }

    public String getOffset()
    {
        return offset;
    }

    public void setOffset(String offset)
    {
        this.offset = offset;
    }

    public String getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp)
    {
        this.timeStamp = timeStamp;
    }
}
