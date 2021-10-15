package com.sevenluo.test.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @description: 原子更新字段类测试
 * @outhor: qige
 * @create: 2020-09-20 12:58
 */
public class AtomicIntegerFileUpdaterTest {

    private static AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        User user = new User("张三",10);
        System.out.println(updater.addAndGet(user,2));
        System.out.println(updater.get(user));

    }

    static class User{
        private String name;
        public volatile int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
