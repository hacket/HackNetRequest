package me.hacket.netrequestpro.bean;

/**
 * Created by zengfansheng on 2016/8/3 0003.
 */
public class TempObj {

    public Data data;

    public static class Data {
        public String name;
        public int age;

        @Override
        public String toString() {
            return "Data{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TempObj{" +
                "data=" + data +
                '}';
    }
}
