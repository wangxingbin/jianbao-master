package utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 诺古 on 2016/11/30.
 */

public class OkHttpUtilzzq {
    private static final String TAG = "OkhttpUtils";
    private static Gson gson = new Gson();//创建gson对象
    private static String string;
    private static OkHttpClient client = new OkHttpClient();//okhttp对象

    public interface EntiyData {//创建接口

        void getEntiy(Object o);
    }
    private static EntiyData data;
    public static void setGetEntiydata(EntiyData data1) {
        data = data1;
    }
    //网络post请求
//Type是所有类的类型包括Javabeen
    public static void post(Map<String, String> map, String path, final Context context, final Type cla) {





        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = map.entrySet();//用entryset的方法遍历map集合
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody body = (FormBody)builder.build();
        Request request = new Request.Builder()
                .url(path)
                .post(body)
                .build();
        Call call = client.newCall(request);
        //用这种方法不用创建子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("下载失败，检查网络");
                //Toast.makeText(context, "下载数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    string = response.body().string();
                    Log.i(TAG, "获取的数据：" + string);
                    Object o = gson.fromJson(string, cla);
                    //回调结果
                    data.getEntiy(o);
                } else {
                    Log.e(TAG, "失败");
                }
            }

//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    string = response.body().string();
//                    Log.i(TAG, "获取的数据：" + string);
//                    Object o = gson.fromJson(string, cla);
//                    //回调结果
//                    data.getEntiy(o);
//                } else {
//                    Log.e(TAG, "失败");
//                }
//            }
        });
    }
    public static void postMuti(Map<String, String> map, String path, final Context context, final Type cla, ArrayList<String> imgs) {
       // RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
 //       .setType(MultipartBody.FORM).build();
//                .addFormDataPart("file", "head_image", fileBody)
//                .addFormDataPart("imagetype", imageType)
//                .addFormDataPart("userphone", userPhone)
//                .build();
        Set<Map.Entry<String, String>> entries = map.entrySet();//用entryset的方法遍历map集合
        for (Map.Entry<String, String> entry : entries) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        if (imgs!=null&&!imgs.isEmpty()){
            for (String str:imgs) {
                String newStr = str.replace("file://", "");
                File file = new File(newStr);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"),file);
                builder.addFormDataPart("photo",file.getName(), fileBody);
            }
        }
        MultipartBody requestBody = builder.setType(MultipartBody.FORM).build();
        Request request = new Request.Builder()
                .url(path)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        //用这种方法不用创建子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("联网失败，检查网络");
                //Toast.makeText(context, "下载数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    string = response.body().string();
                    Log.i(TAG, "获取的数据：" + string);
                    Object o = gson.fromJson(string, cla);
                    //回调结果
                    data.getEntiy(o);
                } else {
                    Log.e(TAG, "失败");
                }
            }

//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    string = response.body().string();
//                    Log.i(TAG, "获取的数据：" + string);
//                    Object o = gson.fromJson(string, cla);
//                    //回调结果
//                    data.getEntiy(o);
//                } else {
//                    Log.e(TAG, "失败");
//                }
//            }
        });
    }
    public static void get(final Context context, String url, final Type clas) {
        final Request request1 = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call1 = client.newCall(request1);
        call1.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("下载失败，检查网络");
                //Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string1 = response.body().string();
                    Object o1 = gson.fromJson(string1, clas);
                    data.getEntiy(o1);
                }
            }
        });
    }
}
