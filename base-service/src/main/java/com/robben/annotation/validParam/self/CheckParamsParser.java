package com.robben.annotation.validParam.self;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 参数格式解析器
 */
public class CheckParamsParser {

    public static void check(Object object) throws Exception {
        Annotation[] annotations;
        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);

            annotations = f.getAnnotations();
            if(annotations.length != 0){

                for (Annotation annotation : annotations) {
                    if (annotation instanceof NotNull) {
                        NotNull notNull = f.getAnnotation(NotNull.class);
                        Object o = f.get(object);
                        if(o == null){
                            throw new ParamException(notNull.codeEnum().getValue(),notNull.codeEnum().getName());
                        }
                        System.out.println(1);
                    }
                }
            }
        }
    }


//
//    /**
//     * 开始校验object对象中的字段是否合法
//     *
//     * @param object
//     * @return 返回校验的结果
//     */
//    public List<CheckError> checkErrors(Object object) {
//        List<CheckError> errorList = new ArrayList<>();
//        check(object, null, errorList);
//        return errorList;
//    }
//
//    /**
//     * 递归校验数据合法性
//     *
//     * @param object
//     * @param parent
//     * @param errorList
//     */
//    protected void check(Object object, String parent, List<CheckError> errorList) {
//        if (object == null) return;
//        Class<?> cls = object.getClass();
//        Field[] fields = cls.getDeclaredFields();
//        for (Field f : fields) {
//            f.setAccessible(true);
//            Object val = null;
//            try {
//                val = f.get(object);
//                if (parser(parent, errorList, f, val)) continue;
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                errorList.add(new CheckError(getFieldPath(parent, f), val, e.getMessage()));
//            }
//        }
//    }
//
//    /**
//     * 解析器
//     *
//     * @param parent
//     * @param errorList
//     * @param f
//     * @param val
//     * @return true 跳过该字段
//     */
//    protected boolean parser(String parent, List<CheckError> errorList, Field f, Object val) {
//        if (isIntercept(val, getFieldPath(parent, f), errorList)) {
//            return true;
//        }
//        if (isPrimitive(f)) {
//            check(val);
//            return true;
//        }
//        checkNull(f, val);
////        checkParams(f, val);
////        checkOtherAn(f, val);
//        return false;
//    }
//
//    /**
//     * 获取当前字段路径
//     *
//     * @param parent
//     * @param f
//     * @return
//     */
//    protected String getFieldPath(String parent, Field f) {
//        if (f == null) return parent;
//        return parent == null || parent.length() == 0 ? f.getName() : parent + "." + f.getName();
//    }
//
//    /**
//     * 如果是基础变量，直接返回
//     *
//     * @param f
//     * @return
//     */
//    protected boolean isPrimitive(Field f) {
//        Class<?> typeCls = f.getType();
//        if (typeCls.isPrimitive()) return false;
//        if (typeCls == Date.class) return false;
//        if (typeCls == BigDecimal.class) return false;
//        if (typeCls == Integer.class) return false;
//        if (typeCls == Double.class) return false;
//        if (typeCls == Long.class) return false;
//        if (typeCls == Float.class) return false;
//        if (typeCls == Character.class) return false;
//        if (typeCls == Byte.class) return false;
//        if (typeCls == String.class) return false;
//        return true;
//    }



//    /**
//     * 解析 CheckParams 注解
//     *
//     * @param f
//     * @param val
//     */
//    protected void checkParams(Field f, Object val) {
//        CheckParams checkParams = f.getAnnotation(CheckParams.class);
//        if (checkParams == null) return;
//        if (val == null) return;
//        Method method = MethodUtils.getMethod(checkParams);
//        MethodUtils.invokeMethod(method, val);
//    }
//
//    /**
//     * 解析包含 CheckParams 注解
//     *
//     * @param f
//     * @param val
//     */
//    private void checkOtherAn(Field f, Object val) {
//        Annotation[] annotations = f.getAnnotations();
//        for (Annotation an : annotations) {
//            CheckParams checkParams = an.annotationType().getAnnotation(CheckParams.class);
//            if (checkParams != null) {
//                Method method = MethodUtils.getMethod(checkParams);
//                MethodUtils.invokeMethod(method, an, val);
//            }
//        }
//    }
//    /**
//     * 拦截list，set，map进行校验
//     *
//     * @param val
//     * @return
//     */
//    protected boolean isIntercept(Object val, String parent, List<CheckError> errorList) {
//        if (val instanceof List) {
//            ((List) val).forEach(o -> check(o, parent, errorList));
//            return true;
//        }
//        if (val instanceof Map) {
//            ((Map) val).forEach((o, o2) -> {
//                check(o, parent + ".k", errorList);
//                check(o2, parent + ".v", errorList);
//            });
//            return true;
//        }
//
//        if (val instanceof Set) {
//            ((Set) val).forEach(o -> check(o, parent, errorList));
//            return true;
//        }
//        return false;
//    }
}
