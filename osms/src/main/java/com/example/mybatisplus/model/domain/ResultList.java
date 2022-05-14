package com.example.mybatisplus.model.domain;

import java.util.ArrayList;
import java.util.List;

    /**
     * @author zp
     *
     */
    public class ResultList<T>{
        private static final long serialVersionUID = 1L;
        private final List<T> list = new ArrayList();
        private Integer total;

        public ResultList() {
        }

        public ResultList(int total, List<T> list) {
            this.list.addAll(list);
            this.total = total;
        }

        public List<T> getList() {
            return this.list;
        }

        public void add(T entity) {
            this.list.add(entity);
        }

        public void setList(List<T> list) {
            this.list.addAll(list);
        }

        public Integer getTotal() {
            return this.total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        @Override
        public String toString() {
            return "ResultList(list=" + this.getList() + ", total=" + this.getTotal() + ")";
        }
    }
