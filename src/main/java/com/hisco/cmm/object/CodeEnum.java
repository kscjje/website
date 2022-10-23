package com.hisco.cmm.object;

import java.io.Serializable;

public interface CodeEnum extends Serializable {
    public String getCode();

    public static <E extends Enum<E> & CodeEnum> E from(E[] es, String str) {
        E rs = null;

        for (E b : es) {
            if (b.getCode().equalsIgnoreCase(str)) {
                return b;
            }
        }

        return rs;
    }
}
