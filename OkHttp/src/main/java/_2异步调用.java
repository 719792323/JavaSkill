import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

//okhttp如果通过直接调用方法.execute()是同步执行
//okhttp可以支持异步执行
public class _2异步调用 {

    static OkHttpClient client = new OkHttpClient.Builder().build();

    public static void get(){
        Request request = new Request.Builder().url("http://localhost/json").get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println(response);
                System.out.println(response.body().string());
            }
        });
    }

    public static void main(String[] args) {
        get();
    }
}
