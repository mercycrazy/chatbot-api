package io.github.mercycrazy.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * ChatGPT open ai 接口定义: https://beta.openai.com/account/api-keys
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
public interface IOpenAI {

    String doChatGPT(String openAiKey, String question) throws IOException;
}
