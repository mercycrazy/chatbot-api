package io.github.mercycrazy.chatbot.api.application.ext;

import io.github.mercycrazy.chatbot.api.application.job.ChatbotTask;
import io.github.mercycrazy.chatbot.api.domain.ai.IOpenAI;
import io.github.mercycrazy.chatbot.api.domain.zsxq.IZsxqApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 任务注册服务, 支持多组任务配置
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-02
 */
@ConfigurationProperties(prefix = "chatbot-api")
@Configuration
@EnableScheduling
public class TaskRegistrarAutoConfig implements SchedulingConfigurer {

    private final Logger logger = LoggerFactory.getLogger(TaskRegistrarAutoConfig.class);

    /**
     * 任务配置组
     */
    private List<Map<String, String>> taskGroup;

    public List<Map<String, String>> getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(List<Map<String, String>> taskGroup) {
        this.taskGroup = taskGroup;
    }

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (Map<String, String> task : taskGroup) {
            String groupName = task.get("groupName");
            String groupId = task.get("groupId");
            String cookie = task.get("cookie");
            String openAiKey = task.get("openAiKey");
            String cronExpression = task.get("cronExpression");
            boolean silenced = Boolean.parseBoolean(task.get("silenced"));
            logger.info("创建任务 groupName：{} groupId：{} cronExpression：{}", groupName, groupId, cronExpression);
            // 添加任务
            taskRegistrar.addCronTask(new ChatbotTask(groupName, groupId, cookie, openAiKey, zsxqApi, openAI, silenced), cronExpression);
        }
    }
}
