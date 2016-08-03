package me.hacket.netrequestpro.bean;

/**
 * Created by hacket on 2016/8/3.
 */
public class Register {
    //    {
    //        "result": {
    //        "code": 0,
    //                "desc": "请求成功"
    //    },
    //        "response": {
    //        "is_registered": "false"
    //    }
    //    }

    public Result result;
    public Response response;

    public static class Result {
        public int code;
        public String desc;
    }

    public static class Response {
        public boolean is_registered;
    }

}
