package cn.shenqiz.idgenerator;

/**
 * Created by 神奇的Z on 2017-2-13.
 * 名称：ID接口器
 * 功能：被所有算法的ID生成器所实现
 */
public interface ShenqiId<T> {

    /**
     * 生成一个新的ID
     *
     * @return
     */
    T generate();

    /**
     * 判断该ID是否存在
     *
     * @param t
     * @return
     */
    boolean exists(T t);
}
