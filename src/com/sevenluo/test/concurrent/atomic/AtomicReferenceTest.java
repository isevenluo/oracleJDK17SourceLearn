package com.sevenluo.test.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 测试原子更新引用类型
 * @outhor: qige
 * @create: 2020-08-29 14:31
 */
public class AtomicReferenceTest {

    private static AtomicReference<User> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("张三", 20);
        atomicReference.set(user);
        User updateUser = new User("李四",25);
        atomicReference.compareAndSet(user,updateUser);
        System.out.println(atomicReference.get());

    }


    static class User{
        private String name;
        private int age;

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
