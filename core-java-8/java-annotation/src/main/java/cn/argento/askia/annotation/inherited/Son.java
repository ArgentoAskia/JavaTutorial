package cn.argento.askia.annotation.inherited;


import cn.argento.askia.annotation.define.Common;

import java.io.Serializable;
import java.time.LocalDateTime;

@Common
public class Son extends Father implements Serializable, Comparable<Son> {

    @Override
    public String getName() {
        return super.getName() + " = " + LocalDateTime.now();
    }

    public int compareTo(Son o) {
        return 0;
    }
}
