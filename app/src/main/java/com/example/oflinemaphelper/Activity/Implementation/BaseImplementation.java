package com.example.oflinemaphelper.Activity.Implementation;


import java.util.List;



public interface BaseImplementation {

    interface OnGettingResponseListener {
        /**
         * response list.
         * @param responses The view that was clicked.
         */
        void onResponse(List<String> responses);
    }

    interface OnSendRequestResponseListener{
        /**
         * check if send request is success full.
         * @param success boolean.
         */
        void onResponse(boolean success);
    };

    void setLocation(String from,String to);

    /**
     * send request for getting response
     */
    void sendRequest(OnSendRequestResponseListener responseListener);

    /**
     * wait for response
     * @param responseListener call back function for getting response
     */
    void startGettingResponse(OnGettingResponseListener responseListener);


}
