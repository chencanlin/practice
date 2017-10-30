package com.org.ccl.practicetwo.thread;

import java.lang.reflect.Constructor;

/**
 * Created by ccl on 2017/8/21.
 */

public class ThreadTest {














    public static void main(String[] args) {
        final InnerClass innerClassOne = InnerClass.getInnerClass();
//        InnerClass innerClassTwo = InnerClass.getInnerClass();
//        InnerClass innerClassThree = InnerClass.getInnerClass();

       /* Timer timer1 = new Timer();
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                InnerClass.println();
            }
        };
        timer1.schedule(timerTask1, 5000);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                innerClassOne.start();
            }
        };
        timer.schedule(timerTask,3000);

        Factory factory = new Factory();
        MobileFactory factory1 = factory.getFactory(MobileFactory.class);
        HuaWeiMobile huaWeiMobile = factory1.productionMobile(HuaWeiMobile.class);
        XiaoMiMobile xiaoMiMobile = factory1.productionMobile(XiaoMiMobile.class);
        System.out.println(huaWeiMobile.getName()+ " --------------------------" + xiaoMiMobile.getName());

        PersonFactory factory2 = factory.getFactory(PersonFactory.class);
        Man person = factory2.getPerson(Man.class);
        WoMan person1 = factory2.getPerson(WoMan.class);
        person.wc();
        person1.wc();

        Person.Builder builder = new Person.Builder();
        Person mPerson = builder.setName("李四")
                .setSex(1)
                .setAge(23)
                .build();
        System.out.println(mPerson.name + "----" + mPerson.age + "----" + mPerson.sex);*/
        String idNumber = "12345678";
        String regex = "^\\d";
        String s = idNumber.replaceAll(regex, "*");
        String s1 = replaceStr(idNumber,3,idNumber.length()-3);
        System.out.println(s+"-------------------" + s1);
    }

    public static String replaceStr(String param, int startIndex, int endIndex) {
        int length = param.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < endIndex -startIndex; i++) {
            stringBuilder.append("*");
        }
        int i = length - endIndex;
        return param.replaceAll("(.{"+startIndex+"})(.*)(.{"+i+"})", "$1" + stringBuilder.toString() + "$3");
    }


    private static class Person {

        private String name = "zhansan";
        private int age = 0;
        private int sex = 0;

        private Person() {

        }

        private static class Builder {
            private Person mPerson;
            private String mName;
            private int mSxe;
            private int mAge;

            private Builder() {
                mPerson = new Person();
            }

            public Builder setName(String name) {
                mName = name;
                return this;
            }

            public Builder setSex(int sex) {
                mSxe = sex;
                return this;
            }

            public Builder setAge(int age) {
                mAge = age;
                return this;
            }

            public Person build() {
                if (mName != null && mName.trim().length() != 0) {
                    mPerson.name = mName;
                }
                mPerson.age = mAge;
                mPerson.sex = mSxe;
                return mPerson;
            }
        }
    }

    private static interface IPhone {
        String getName();
    }

    private static class XiaoMiMobile implements IPhone {
        private String mName = "xiaomi";

        @Override
        public String getName() {
            return mName;
        }
    }

    private static class HuaWeiMobile implements IPhone {

        private String mName = "huawei";

        @Override
        public String getName() {
            return mName;
        }
    }

    private static class Factory implements IFactory {
        @Override
        public <T extends Factory> T getFactory(Class<T> iFactory) {
            Factory factory = null;
            try {
                Constructor<? extends Factory> declaredConstructor = iFactory.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                factory = declaredConstructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T) factory;
        }
    }

    private static interface IFactory {
        <T extends Factory> T getFactory(Class<T> iFactory);
    }

    private static class MobileFactory extends Factory {
        private <T extends IPhone> T productionMobile(Class<T> iPhone) {
            IPhone iPhone1 = null;
            try {
                Constructor<? extends IPhone> declaredConstructor = iPhone.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                iPhone1 = declaredConstructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T) iPhone1;
        }
    }

    private static class Man implements IPerson {
        @Override
        public void wc() {
            System.out.println("go to man wc");
        }
    }

    private static class WoMan implements IPerson {
        @Override
        public void wc() {
            System.out.println("go to women wc");
        }
    }

    private static interface IPerson {
        void wc();
    }

    private static class PersonFactory extends Factory {
        private <T extends IPerson> T getPerson(Class<T> tClass) {
            IPerson iPerson = null;
            try {
                Constructor<T> declaredConstructor = tClass.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                iPerson = declaredConstructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T) iPerson;
        }
    }


    private static class InnerClass extends Thread {
        private static int mCount = 5;
        private static InnerClass mInnerClass;

        private InnerClass() {
        }

        private synchronized static InnerClass getInnerClass() {
            if (mInnerClass == null) {
                mInnerClass = new InnerClass();
            }
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mInnerClass;
        }

        @Override
        public void run() {
            while (mCount > 0) {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                emptyMethod(mCount);
                mCount--;
            }
        }

        private synchronized static void emptyMethod(int count) {
            System.out.println("I am empty method" + count + "-------------------------------------");
        }

        private synchronized static void println() {
            System.out.println("I got this lock");
            try {
                Thread.sleep(20000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
