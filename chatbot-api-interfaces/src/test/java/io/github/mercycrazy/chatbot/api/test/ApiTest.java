package io.github.mercycrazy.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 单元测试
 *
 * @author <a href="mailto:1443424326@qq.com">mercycrazy</a>
 * @since 2023-02-01
 */
public class ApiTest {

    @Test
    public void query_unanswered_question() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88885511282812/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "zsxq_access_token=BB6C0D65-3E17-B911-9032-9E7BD541B162_A73CC9D29AFCC9AD; zsxqsessionid=ab32d447dee4be38d85b20d52f79cc21; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22182425881212152%22%2C%22first_id%22%3A%2218608d8519f314-06059e60382fc24-26021151-1382400-18608d851a01a2%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2MDhkODUxOWYzMTQtMDYwNTllNjAzODJmYzI0LTI2MDIxMTUxLTEzODI0MDAtMTg2MDhkODUxYTAxYTIiLCIkaWRlbnRpdHlfbG9naW5faWQiOiIxODI0MjU4ODEyMTIxNTIifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22182425881212152%22%7D%2C%22%24device_id%22%3A%2218608d8519f314-06059e60382fc24-26021151-1382400-18608d851a01a2%22%7D");
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/181554515824122/answer");

        post.addHeader("cookie", "zsxq_access_token=BB6C0D65-3E17-B911-9032-9E7BD541B162_A73CC9D29AFCC9AD; zsxqsessionid=ab32d447dee4be38d85b20d52f79cc21; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22182425881212152%22%2C%22first_id%22%3A%2218608d8519f314-06059e60382fc24-26021151-1382400-18608d851a01a2%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2MDhkODUxOWYzMTQtMDYwNTllNjAzODJmYzI0LTI2MDIxMTUxLTEzODI0MDAtMTg2MDhkODUxYTAxYTIiLCIkaWRlbnRpdHlfbG9naW5faWQiOiIxODI0MjU4ODEyMTIxNTIifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22182425881212152%22%7D%2C%22%24device_id%22%3A%2218608d8519f314-06059e60382fc24-26021151-1382400-18608d851a01a2%22%7D");
        post.addHeader("Content-Type", "application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我真不会！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个Java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
