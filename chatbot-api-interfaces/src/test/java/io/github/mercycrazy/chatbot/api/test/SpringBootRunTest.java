package io.github.mercycrazy.chatbot.api.test;

import com.alibaba.fastjson.JSON;
import io.github.mercycrazy.chatbot.api.application.ext.TaskRegistrarAutoConfig;
import io.github.mercycrazy.chatbot.api.domain.ai.IOpenAI;
import io.github.mercycrazy.chatbot.api.domain.zsxq.IZsxqApi;
import io.github.mercycrazy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import io.github.mercycrazy.chatbot.api.domain.zsxq.model.vo.Topics;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * SpringBoot 单元测试
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
@SpringBootTest
public class SpringBootRunTest {

    private final Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    @Resource
    private TaskRegistrarAutoConfig taskRegistrarAutoConfig;

    @Test
    public void test_zsxqApi() throws IOException {

        Map<String, String> task = taskRegistrarAutoConfig.getTaskGroup().get(0);

        String groupId = task.get("groupId");
        String cookie = task.get("cookie");

        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            logger.info("topicId：{} text：{}", topicId, text);

            // 回答问题
            zsxqApi.answer(groupId, cookie, topicId, text, false);
        }
    }

    @Test
    public void test_openAi() throws IOException {

        Map<String, String> task = taskRegistrarAutoConfig.getTaskGroup().get(0);

        String openAiKey = task.get("openAiKey");

        String response = openAI.doChatGPT(openAiKey, "帮我写一个Java冒泡排序");
        logger.info("测试结果: {}", response);
    }
}
