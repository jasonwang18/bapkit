package com.supcon.mes.mbap.beans;

import com.supcon.common.com_http.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author xushiyun
 * @Create-time 5/23/19
 * @Pageage com.supcon.mes.mbap.beans
 * @Project mbap
 * @Email ciruy.victory@gmail.com
 * @Related-classes
 * @Desc
 */
public class ExampleMultistageEntity extends BaseMultiStageEntity {
    public String content;
    
    public ExampleMultistageEntity(String content) {
        this.content = content;
    }
    
    public static final ExampleMultistageEntity createExampleEntity(Integer type) {
        ExampleMultistageEntity exampleMultistageEntity = new ExampleMultistageEntity("创建时间："+System.currentTimeMillis());
        exampleMultistageEntity.setType(type);
        return exampleMultistageEntity;
    }
    
    public static final List<ExampleMultistageEntity> createExampleEntity(int num) {
        List<ExampleMultistageEntity> result = new ArrayList<>();
        for(int i =0 ; i <num;i++) {
            ExampleMultistageEntity exampleMultistageEntity = createExampleEntity((Integer) (i%5==0?0:1));
            exampleMultistageEntity.content = (i%5==0?"标题":"内容")+"  列表编号"+i;
            result.add(exampleMultistageEntity);
        }
        return result;
    }
    
}
