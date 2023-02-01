package io.github.mercycrazy.chatbot.api.application.job;

import com.alibaba.fastjson.JSON;
import io.github.mercycrazy.chatbot.api.domain.ai.IOpenAI;
import io.github.mercycrazy.chatbot.api.domain.zsxq.IZsxqApi;
import io.github.mercycrazy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import io.github.mercycrazy.chatbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * 问答任务
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
@Configuration
@EnableScheduling
public class ChatbotSchedule {

    private final Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        try {
            // 处理调用曲线, 使其不规律, 避免触发风控
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中...");
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 0 && hour < 7) {
                logger.info("打烊时间不工作, AI 下班了!");
                return;
            }

            // 1. 检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (CollectionUtils.isEmpty(topics)) {
                logger.info("本次检索未查询到待回答问题");
                return;
            }

            // 2. AI 回答, 每次回答一个, 避免触发风控
            Topics topic = topics.get(0);
            String answer = openAI.doChatGPT(topic.getQuestion().getText().trim());

            // 3. 问题回复
            boolean status = zsxqApi.answer(groupId, cookie, topic.getTopic_id(), answer, false);
            logger.info("编号: {}, 问题: {}, 回答: {}, 状态: {}",
                    topic.getTopic_id(), topic.getQuestion().getText(), answer, status);
        } catch (Exception e) {
            logger.error("自动回答问题异常", e);
        }
    }
}
