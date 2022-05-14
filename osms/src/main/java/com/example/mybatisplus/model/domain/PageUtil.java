package com.example.mybatisplus.model.domain;

import java.util.List;

    /**
     * @author zp
     *
     */
    public class PageUtil {

        /**
         * 按照传入的参数，进行分页
         * @param pageNum  页数
         * @param pageSize 每页条数
         * @param sourceList 源列表
         * @param isPage 是否分页
         * @param <T> 泛型对象
         * @return 返回结果集
         */
        public static <T> ResultList<T> page(Integer pageNum, Integer pageSize, List<T> sourceList, Boolean isPage){

            ResultList<T> resultList = new ResultList<>();
            // 初始化总量为0，防止当列表为空的时候，total返回null
            resultList.setTotal(0);
            List<T> pageList = sourceList;

            // 当列表不为空的时候，才进行数据逻辑处理
            if (sourceList != null && !sourceList.isEmpty()) {
                int sourceSize = sourceList.size();
                if (isPage) {
                    pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
                    pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;

                    // 步骤分解，便于理解
//                int startPosition = Math.min((pageNum - 1) * pageSize, sourceSize);
//                int endPosition = Math.min(pageNum * pageSize, sourceSize);
//                pageList = sourceList.subList(startPosition, endPosition);
                    // 分页的起始和截止位置，和源列表的size大小进行对比，分别取最小值
                    pageList = sourceList.subList(Math.min((pageNum - 1) * pageSize, sourceSize), Math.min(pageNum * pageSize, sourceSize));
                }
                resultList.setList(pageList);
                resultList.setTotal(sourceSize);
            }

            return resultList;
        }
}
