package com.supcon.mes.mbap.utils;

import com.supcon.mes.mbap.beans.BaseContactEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ContactUtil {
    private List<BaseContactEntity> contactEntities = new ArrayList<>();

    public ContactUtil(BaseContactEntity contactEntity) {
        this.contactEntities.clear();
        this.contactEntities.add(contactEntity);
    }

    public void clicked(BaseContactEntity baseContactEntity) {
        int clickedIndex = contactEntities.indexOf(baseContactEntity);
        if (clickedIndex != -1)
            contactEntities = contactEntities.subList(0, clickedIndex + 1);
        else
            contactEntities.add(baseContactEntity);
    }

    public void push(BaseContactEntity baseContactEntity) {
        contactEntities.add(baseContactEntity);
    }

    public void pop() {
        contactEntities.remove(contactEntities.size() - 1);
    }

    public BaseContactEntity getLast() {
        return contactEntities.get(contactEntities.size() - 1);
    }

    public List<BaseContactEntity> getTitleList(){
        return contactEntities;
    }
}
