package io.github.mercycrazy.chatbot.api.test;

import com.alibaba.fastjson.JSON;
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

/**
 * SpringBoot 单元测试
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
@SpringBootTest
public class SpringBootRunTest {

    private final Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Test
    public void test_zsxqApi() throws IOException {

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
}
