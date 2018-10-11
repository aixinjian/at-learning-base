package com.learning.base.util;

import com.learning.base.form.SortInfo;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class OrderByUtil {
    /**
     * 获得排序字段
     * @param sortInfoList
     * @param orderByField
     * @return
     */
    public static String getOrderBy(List<SortInfo> sortInfoList , Map<String,String> orderByField) {
        StringBuffer orderBy = new StringBuffer();
        if (sortInfoList != null && sortInfoList.size() != 0){
            for (int i = 0 ; i < sortInfoList.size() ; i++){
                SortInfo sortInfo = sortInfoList.get(i);
                String feild = orderByField.get(sortInfo.getField());
                if (StringUtils.isBlank(feild)){
                    continue;
                }
                orderBy.append(feild);
                orderBy.append(" ");
                String sort;
                if (StringUtils.isBlank(sortInfo.getSort())) {
                    sort = "ASC";
                } else if (sortInfo.getSort().equalsIgnoreCase("DESC")) {
                    sort = "DESC";
                } else if (sortInfo.getSort().equalsIgnoreCase("ASC")) {
                    sort = "ASC";
                } else {
                    sort = "ASC";
                }

                orderBy.append(sort).append(",");
            }
            return StringUtils.isBlank(orderBy.toString()) ? null : orderBy.substring(0,orderBy.length()-1);
        }
        return null;
    }
}
