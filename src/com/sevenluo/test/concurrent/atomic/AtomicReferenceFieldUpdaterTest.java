package com.sevenluo.test.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @description:
 * @outhor: qige
 * @create: 2020-09-20 14:14
 */
public class AtomicReferenceFieldUpdaterTest {

    private static AtomicReferenceFieldUpdater<User, String> atomicReferenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(User.class, String.class,"age");

    public static void main(String[] args) {
        User user = new User("seven", "25");
        String andSet = atomicReferenceFieldUpdater.getAndSet(user, "26");
        // 返回的是
        // 设置新值之前的值
        System.out.println(andSet);
        System.out.println(atomicReferenceFieldUpdater.get(user));
    }

    static class User {
        private String name;

        public volatile String age;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public User(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
