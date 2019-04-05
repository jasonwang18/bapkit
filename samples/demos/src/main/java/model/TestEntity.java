package model;

import com.supcon.common.com_http.BaseEntity;

import java.util.Random;

/**
 * Created by wangshizhan on 2018/8/4
 * Email:wangshizhan@supcom.com
 */
public class TestEntity extends BaseEntity {


    public int viewType = new Random().nextInt(2);
    public String name ;
    public String title = "标题"+viewType;
    public int imageResoureId;
    public String iamgePath;
}
