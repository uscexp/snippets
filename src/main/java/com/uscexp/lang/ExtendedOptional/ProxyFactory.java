package com.uscexp.lang.ExtendedOptional;

import javassist.*;
import javassist.util.proxy.MethodHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public final class ProxyFactory {

    private static final String PACKAGE = "com.uscexp.generated.";
    private static final String POSTFIX = "Interface";

    private ProxyFactory() {
    }

    public static Object createProxy(Class<?> type, MethodHandler methodHandler) throws CannotCompileException, NotFoundException, IllegalAccessException, InstantiationException {
        javassist.util.proxy.ProxyFactory factory = new javassist.util.proxy.ProxyFactory();

        Class<?> classInterface = type;
        if (!classInterface.isInterface()) {
            classInterface = extractInterface(type);
            factory.setInterfaces(new Class[] {classInterface});
        }
        Class<?> proxyClass = factory.createClass();
        Object result = proxyClass.newInstance();
        return result;
    }
//    public static <V> V createProxy(Class<V> type, MethodHandler methodHandler) throws CannotCompileException, NotFoundException, IllegalAccessException, InstantiationException {
//        javassist.util.proxy.ProxyFactory factory = new javassist.util.proxy.ProxyFactory();
//
//        if(type.isInterface()) {
//            factory.setInterfaces(new Class[] {type});
//        } else {
//            factory.setSuperclass(type);
//        }
//        Class<?> proxyClass = factory.createClass();
//        V result = (V) proxyClass.newInstance();
//        return result;
//    }
//
//    public static <T> Class<?> createNonFinalClass(Class<T> type) throws CannotCompileException, NotFoundException {
//        Class<?> nonFinalClass = type;
//        int modifiers = nonFinalClass.getModifiers();
//        if (!Modifier.isFinal(modifiers)) {
//            return nonFinalClass;
//        }
//        Class<?> result = null;
//        String className = PACKAGE + type.getSimpleName() + POSTFIX;
//
//        ClassPool pool = ClassPool.getDefault();
//        CtClass oldCc = pool.getOrNull(className);
//
//        if (oldCc == null) {
//            CtClass cc = pool.get(type.getName());
//            modifiers = cc.getModifiers();
//            if(javassist.Modifier.isFinal(modifiers)) {
//                int nonFinalModifiers = javassist.Modifier.clear(modifiers, javassist.Modifier.FINAL);
//                cc.setModifiers(nonFinalModifiers);
//            }
//            cc.setName(className);
//            result = pool.toClass(cc);
//        } else {
//            try {
//                result = Class.forName(className);
//            } catch (ClassNotFoundException e) {
//                result = pool.toClass(oldCc);
//            }
//        }
//        return result;
//    }

//    public static <V> V createProxy(Class<V> type, InvocationHandler invocationHandler) throws CannotCompileException, NotFoundException {
//        Class<?> classInterface = type;
//        if (!classInterface.isInterface()) {
//            classInterface = extractInterface(type);
//        }
//        V result = (V) Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class[] {classInterface}, invocationHandler);
//        return result;
//    }

    public static <T> Class<?> extractInterface(Class<T> type) throws CannotCompileException, NotFoundException {
        Class<?> result = null;
        String className = PACKAGE + type.getSimpleName() + POSTFIX;

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.getOrNull(className);

        if (cc == null) {
            cc = pool.makeInterface(className);

            Method[] methods = type.getMethods();
            for (Method method : methods) {
                if(!Modifier.isFinal(method.getModifiers())) {
                    Class<?> returnType = method.getReturnType();
                    CtClass ccReturnType = pool.get(returnType.getName());
                    CtClass[] ccParameters = getCcParameters(pool, method);
                    CtClass[] ccExceptions = getCcExceptions(pool, method);
                    CtMethod m = CtNewMethod.abstractMethod(ccReturnType, method.getName(), ccParameters, ccExceptions, cc);
                    cc.addMethod(m);
                }
            }
            result = pool.toClass(cc);
        } else {
            try {
                result = Class.forName(className);
            } catch (ClassNotFoundException e) {
                result = pool.toClass(cc);
            }
        }
        return result;
    }

    private static CtClass[] getCcExceptions(ClassPool pool, Method method) throws NotFoundException {
        CtClass[] ccExceptions = null;
        if (method.getExceptionTypes() == null && method.getExceptionTypes().length > 0) {
            ccExceptions = new CtClass[method.getExceptionTypes().length];
            for (int i = 0; i < method.getExceptionTypes().length; ++i) {
                Class ex = method.getParameterTypes()[i];
                CtClass ccEx = pool.get(ex.getName());
                ccExceptions[i] = ccEx;
            }
        }
        return ccExceptions;
    }

    private static CtClass[] getCcParameters(ClassPool pool, Method method) throws NotFoundException {
        CtClass[] ccParameters = null;
        if (method.getParameterCount() > 0) {
            ccParameters = new CtClass[method.getParameterCount()];
            for (int i = 0; i < method.getParameterCount(); ++i) {
                Class param = method.getParameterTypes()[i];
                CtClass ccParam = pool.get(param.getName());
                ccParameters[i] = ccParam;
            }
        }
        return ccParameters;
    }
}
