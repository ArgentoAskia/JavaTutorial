package cn.argento.askia.effective.java.forty;

import java.time.LocalDateTime;

/**
 * 提供一个对外的视图对象, 此接口用于聚合内部的比较对象时使用
 * @param <E>
 */
public interface View<E extends Comparable<E>>{

    E as();
}