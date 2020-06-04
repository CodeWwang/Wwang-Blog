package net.wwang.blog.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: wwang-blog
 * @Author: wangw
 * @CreateTime: 2020-05-16 10:18
 * @Description:
 */
@Component
public class PropertyGen {
    private String simpleProp;
    private String[] arrayProps;
    private List<Map<String , String>> listMapProps = new ArrayList<>();
    private  List<String> listProps = new ArrayList<>();
    private  Map<String, String> mapProps = new HashMap<>();

    public String getSimpleProp() {
        return simpleProp;
    }

    public void setSimpleProp(String simpleProp) {
        this.simpleProp = simpleProp;
    }

    public String[] getArrayProps() {
        return arrayProps;
    }

    public void setArrayProps(String[] arrayProps) {
        this.arrayProps = arrayProps;
    }

    public List<Map<String, String>> getListMapProps() {
        return listMapProps;
    }

    public void setListMapProps(List<Map<String, String>> listMapProps) {
        this.listMapProps = listMapProps;
    }

    public List<String> getListProps() {
        return listProps;
    }

    public void setListProps(List<String> listProps) {
        this.listProps = listProps;
    }

    public Map<String, String> getMapProps() {
        return mapProps;
    }

    public void setMapProps(Map<String, String> mapProps) {
        this.mapProps = mapProps;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
