package io.github.mercycrazy.chatbot.api.domain.zsxq.model.res;

import io.github.mercycrazy.chatbot.api.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * 结果数据
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
public class RespData {

    private List<Topics> topics;

    public List<Topics> getTopics() {
        return topics;
    }

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }

}