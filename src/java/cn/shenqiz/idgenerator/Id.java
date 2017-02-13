package cn.shenqiz.idgenerator;

/**
 * Created by 神奇的Z on 2017-2-13.
 * 名称：ID接口器
 * 功能：被所有算法的ID生成器所实现
 */
public interface Id<T> {

    /**
     * 生成一个新的ID
     *
     * @return
     */
    T generate();
}
