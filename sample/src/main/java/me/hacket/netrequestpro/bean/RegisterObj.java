package me.hacket.netrequestpro.bean;

public class RegisterObj {

    public Result result;
    public Response response;

    public static class Result {
        public int code;
        public String desc;

        @Override
        public String toString() {
            return "Result{" +
                    "code=" + code +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    public static class Response {
        public Person person;
        public Basic basic;

        @Override
        public String toString() {
            return "Response{" +
                    "person=" + person +
                    ", basic=" + basic +
                    '}';
        }
    }

    public static class Person {
        public String is_bind_card;
        public String name;
        public String nick_name_pys;
        public String id_type;
        public String name_pys;
        public String id;
        public String recommend_code;
        public String is_verify;
        public String nick_name;

        @Override
        public String toString() {
            return "Person{" +
                    "is_bind_card='" + is_bind_card + '\'' +
                    ", name='" + name + '\'' +
                    ", nick_name_pys='" + nick_name_pys + '\'' +
                    ", id_type='" + id_type + '\'' +
                    ", name_pys='" + name_pys + '\'' +
                    ", id='" + id + '\'' +
                    ", recommend_code='" + recommend_code + '\'' +
                    ", is_verify='" + is_verify + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    '}';
        }
    }

    public static class Basic {
        public String uid;
        public String email;
        public String phone;
        public String status;
        public String user_type;
        public String register_time;
        public String pic_key;

        @Override
        public String toString() {
            return "Basic{" +
                    "uid='" + uid + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", status='" + status + '\'' +
                    ", user_type='" + user_type + '\'' +
                    ", register_time='" + register_time + '\'' +
                    ", pic_key='" + pic_key + '\'' +
                    '}';
        }
    }

}
