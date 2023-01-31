package io.github.mercycrazy.chatbot.api.domain.zsxq;

import io.github.mercycrazy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * 知识星球 API 接口定义
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
public interface IZsxqApi {

    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
}
