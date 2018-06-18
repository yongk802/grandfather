package com.yong.grandfather;

import java.lang.reflect.Field;

public class C extends B {
    /**
     * We are going to call {@link A#doThat()} method here to show we can technically call it
     * though you should never actually do this.
     */
    @Override
    public void doThat() {
        exec(A.class, this, "doThat");
    }

    public <T, S extends T> void exec(Class<T> oneSuperType, S instance,
                                      String methodOfParentToExec) {
        try {
            T t = oneSuperType.newInstance();
            shareVars(oneSuperType, instance, t);
            oneSuperType.getMethod(methodOfParentToExec).invoke(t);
            shareVars(oneSuperType, t, instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T, S extends T, Z extends T> void shareVars(
            Class<T> clazz,
            S source,
            Z target) throws IllegalArgumentException, IllegalAccessException {
        Class<?> loop = clazz;
        do {
            for (Field f : loop.getDeclaredFields()) {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                f.set(target, f.get(source));
            }
            // get the super class all the way up as long as it isn't the Object class.
            loop = loop.getSuperclass();
        } while (loop != Object.class);
    }

}
