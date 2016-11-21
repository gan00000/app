package com.eee.request;

import android.app.Activity;
import android.text.TextUtils;

import com.efun.core.http.HttpRequest;
import com.efun.core.task.EfunRequestAsyncTask;

import java.util.Map;


/**
 * Created by Efun on 2016/10/9.
 */

public class AppRequest extends EfunRequestAsyncTask{

    private String requestUrl;
    private Map<String,String> requestData;
    private RequestCallBack callBack;

    private Activity activity;

    public AppRequest(Activity activity) {
        this.activity = activity;
    }

    public AppRequest() {
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String doInBackground(String... params) {
        if (TextUtils.isEmpty(requestUrl)){
            return "";
        }
        String result = HttpRequest.post(requestUrl,requestData);

        return result;
    }


    @Override
    protected void onPostExecute(String resultm) {
        super.onPostExecute(resultm);
        if (callBack != null){
            callBack.callback(resultm);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void request(String requestUrl,Map<String,String> requestData){
        this.requestData = requestData;
        this.requestUrl = requestUrl;
        asyncExcute();
    }

    public void request(String requestUrl,Map<String,String> requestData, RequestCallBack callBack){
        this.requestData = requestData;
        this.requestUrl = requestUrl;
        this.callBack = callBack;
        asyncExcute();
    }

    public void request(Activity activity, String requestUrl,Map<String,String> requestData, RequestCallBack callBack){
        this.requestData = requestData;
        this.requestUrl = requestUrl;
        this.callBack = callBack;
        this.activity = activity;
        asyncExcute();
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setRequestData(Map<String, String> requestData) {
        this.requestData = requestData;
    }

    public void setCallBack(RequestCallBack callBack) {
        this.callBack = callBack;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public interface RequestCallBack{
        void callback(String r);
    }


}
