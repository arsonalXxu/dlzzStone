package com.ipaas.monitoringplstformsys.common.response;

import java.util.List;

public class PageRespVo<T> {
    private String bizCode;
    private String code;
    private String message;
    private Integer total;
    private List<T> table;

    PageRespVo(final String bizCode, final String code, final String message, final Integer total, final List<T> table) {
        this.bizCode = bizCode;
        this.code = code;
        this.message = message;
        this.total = total;
        this.table = table;
    }

    public static <T> PageRespVoBuilder<T> builder() {
        return new PageRespVoBuilder();
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getTotal() {
        return this.total;
    }

    public List<T> getTable() {
        return this.table;
    }

    public void setBizCode(final String bizCode) {
        this.bizCode = bizCode;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

    public void setTable(final List<T> table) {
        this.table = table;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageRespVo)) {
            return false;
        } else {
            PageRespVo<?> other = (PageRespVo)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$bizCode = this.getBizCode();
                    Object other$bizCode = other.getBizCode();
                    if (this$bizCode == null) {
                        if (other$bizCode == null) {
                            break label71;
                        }
                    } else if (this$bizCode.equals(other$bizCode)) {
                        break label71;
                    }

                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                label57: {
                    Object this$message = this.getMessage();
                    Object other$message = other.getMessage();
                    if (this$message == null) {
                        if (other$message == null) {
                            break label57;
                        }
                    } else if (this$message.equals(other$message)) {
                        break label57;
                    }

                    return false;
                }

                Object this$total = this.getTotal();
                Object other$total = other.getTotal();
                if (this$total == null) {
                    if (other$total != null) {
                        return false;
                    }
                } else if (!this$total.equals(other$total)) {
                    return false;
                }

                Object this$table = this.getTable();
                Object other$table = other.getTable();
                if (this$table == null) {
                    if (other$table == null) {
                        return true;
                    }
                } else if (this$table.equals(other$table)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageRespVo;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $bizCode = this.getBizCode();
        result = result * 59 + ($bizCode == null ? 43 : $bizCode.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $total = this.getTotal();
        result = result * 59 + ($total == null ? 43 : $total.hashCode());
        Object $table = this.getTable();
        result = result * 59 + ($table == null ? 43 : $table.hashCode());
        return result;
    }

    public String toString() {
        return "PageRespVo(bizCode=" + this.getBizCode() + ", code=" + this.getCode() + ", message=" + this.getMessage() + ", total=" + this.getTotal() + ", table=" + this.getTable() + ")";
    }

    public static class PageRespVoBuilder<T> {
        private String bizCode;
        private String code;
        private String message;
        private Integer total;
        private List<T> table;

        PageRespVoBuilder() {
        }

        public PageRespVoBuilder<T> bizCode(final String bizCode) {
            this.bizCode = bizCode;
            return this;
        }

        public PageRespVoBuilder<T> code(final String code) {
            this.code = code;
            return this;
        }

        public PageRespVoBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public PageRespVoBuilder<T> total(final Integer total) {
            this.total = total;
            return this;
        }

        public PageRespVoBuilder<T> table(final List<T> table) {
            this.table = table;
            return this;
        }

        public PageRespVo<T> build() {
            return new PageRespVo(this.bizCode, this.code, this.message, this.total, this.table);
        }

        public String toString() {
            return "PageRespVo.PageRespVoBuilder(bizCode=" + this.bizCode + ", code=" + this.code + ", message=" + this.message + ", total=" + this.total + ", table=" + this.table + ")";
        }
    }
}
