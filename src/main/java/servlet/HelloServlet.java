/**
 * .
 *
 */
package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ContextHolder;

/**
 * a little description
 *
 * @author dyj
 */
public class HelloServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(HelloServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("get 请求");

        // 去调用另一个系统，将LogId带过去
        // 首先需要发送一个请求，这里使用HttpClient
        // 在spring中使用RestClient
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://localhost:8080/test");
        Map<String, String> contextHolder = ContextHolder.get();
        for (Map.Entry entry : contextHolder.entrySet()) {
            httpGet.addHeader((String) entry.getKey(), (String) entry.getValue());
        }
        HttpResponse response = httpclient.execute(httpGet);
        logger.info(response.getEntity().getContent().toString());

        resp.getWriter().write("Hello Servlet!");
        resp.getWriter().flush();

    }
}
