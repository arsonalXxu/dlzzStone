package com.ipaas.monitoringplstformsys.common.response;

import java.util.List;

public class PageRespHelper<T> {
    private Integer total;
    private List<T> table;

    public PageRespHelper() {
    }

    public Integer getTotal() {
        return this.total;
    }

    public List<T> getTable() {
        return this.table;
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
        } else if (!(o instanceof PageRespHelper)) {
            return false;
        } else {
            PageRespHelper<?> other = (PageRespHelper)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
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
                    if (other$table != null) {
                        return false;
                    }
                } else if (!this$table.equals(other$table)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageRespHelper;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $total = this.getTotal();
        result = result * 59 + ($total == null ? 43 : $total.hashCode());
        Object $table = this.getTable();
        result = result * 59 + ($table == null ? 43 : $table.hashCode());
        return result;
    }

    public String toString() {
        return "PageRespHelper(total=" + this.getTotal() + ", table=" + this.getTable() + ")";
    }
}
