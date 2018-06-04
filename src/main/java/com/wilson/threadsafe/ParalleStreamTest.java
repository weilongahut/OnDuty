package com.wilson.threadsafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 测试parallelStream操作时的线程安全问题.
 *
 * @author zhangweilong
 * @create 12/27/17 14:59
 **/
public class ParalleStreamTest {

    public static void main(String[] args) {

        int flag = 0;
        do {
            flag = testParallelStream();
        } while (flag == 0);
    }

    public static int testParallelStream() {
        List<Test> list = new ArrayList<>();
        List<Test> streamArray = new ArrayList<Test>();
        streamArray.add(new Test(Arrays.asList("a")));
        streamArray.add(new Test(Arrays.asList("b")));

        streamArray.parallelStream().forEach(string ->{
            Test temp = new Test(string.getListValue());
            temp.setIndex(1);
            list.add(temp);
        });

        for (Test test : list) {
            System.out.println(list);
            if (Objects.isNull(test)) {
                System.err.println("thread unsafe!");
                System.err.println(test);
                return -1;
            }
        }

        return 0;
    }

    public static class Test{
        private Integer index;
        private List<String> listValue;

        public Test(List<String> listValue) {
            this.listValue = listValue;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public List<String> getListValue() {
            return listValue;
        }

        public void setListValue(List<String> listValue) {
            this.listValue = listValue;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Test{");
            sb.append("index=").append(index);
            sb.append(", listValue=").append(listValue);
            sb.append('}');
            return sb.toString();
        }
    }
}
