package com.wisdom.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;



public class BeanCopyUtils {
    public BeanCopyUtils() {
    }

    /**
     * 转换为单个自定义vo数据
     * @param source
     * @param clazz
     * @return
     * @param <V>
     */
    public static <V> V copyBean(Object source,Class<V> clazz){
        V result=null;
        try {
            result = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将数据转换为自定义的vo在转换为数组
     * @param list
     * @param clazz
     * @return
     * @param <O>
     * @param <V>
     */
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        return list.stream().map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }

//    public static void main(String[] args) {
//        Article article=new Article();
//        article.setId("1");
//        article.setTitle("Ss");
//        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
//        System.out.println(hotArticleVo);
//    }
}
