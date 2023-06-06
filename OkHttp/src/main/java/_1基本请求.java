import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class _1基本请求 {
    //创建httpclient对象
    static OkHttpClient client = new OkHttpClient.Builder().build();

    //get请求
    public static void get() {
        Request request = new Request.Builder().url("http://localhost/json").get().build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            //打印response内容
            System.out.println(response);
            System.out.println(response.code());
            //获取content-type
            System.out.println(response.body().contentType());
            //获取body中的内容
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void post() throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "alice");
        map.put("age", 10);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("http://127.0.0.1/user")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }


    public static void main(String[] args) throws Exception{
        post();
    }
}
