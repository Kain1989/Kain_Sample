package com.telenav.cloud.search.test;

import java.util.Map;

/**
 * Created by zfshi on 6/5/2015.
 */
public class TestClass {

    private Map<String, HttpRequest> myList;

//    public static void main(String[] args) throws NoSuchFieldException {
//        System.out.println("中文");
//        Field field = TestClass.class.getDeclaredField("myList"); //myList的类型是List
//        Type type = field.getGenericType();
//        if (type instanceof ParameterizedType) {
//            ParameterizedType paramType = (ParameterizedType) type;
//            Type[] actualTypes = paramType.getActualTypeArguments();
//            for (Type aType : actualTypes) {
//                if (aType instanceof Class) {
//                    Class clz = (Class) aType;
//                    System.out.println(clz.getName()); //输出java.lang.String
//                }
//            }
//        }
//
//    }

    public static void main(String[] args) {
        TestA a = new TestA();
        a.setName("Kain");
//        TestB b = new TestB(a);
    }


//
//    public static void main(String[] args) {
////        String str = null;
////        System.out.println(str.toString());
//
//        String str2 = "一";
//        char[] set = str2.toCharArray();
//        char han = '一';
//        char eng = 'a';
//        System.out.println(set.length + ", " + str2.getBytes().length);
//
//        System.out.println(eng);
//        System.out.println(han);
//        System.out.println("一".compareTo("二"));
//    }
}
