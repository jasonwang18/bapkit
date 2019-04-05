package com.supcon.mes.mbap.utils;

import com.supcon.mes.mbap.beans.SheetEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2019/2/25
 * Email:wangshizhan@supcom.com
 */
public class SheetUtil {


    public static List<SheetEntity> getSheetEntities(String[] sheets) {
        List<SheetEntity> result = new ArrayList<>();
        for (String name : sheets) {
            SheetEntity offLineEntity = new SheetEntity();
            offLineEntity.name = name;
            result.add(offLineEntity);
        }
        return result;
    }


}
