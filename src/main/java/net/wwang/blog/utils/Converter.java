package net.wwang.blog.utils;

import net.wwang.blog.model.Relation;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static List<Integer> RelationListToCidList(List<Relation> relationList){
        List<Integer> list = new ArrayList<>();
        for (Relation r : relationList) {
            list.add(r.getCid());
        }
        return list;
    }
}
