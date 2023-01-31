package io.github.mercycrazy.chatbot.api.domain.zsxq.model.req;

/**
 * 请求问答接口信息
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
public class AnswerReq {
    private ReqData req_data;

    public AnswerReq(ReqData req_data) {
        this.req_data = req_data;
    }

    public ReqData getReq_data() {
        return req_data;
    }

    public void setReq_data(ReqData req_data) {
        this.req_data = req_data;
    }
}
