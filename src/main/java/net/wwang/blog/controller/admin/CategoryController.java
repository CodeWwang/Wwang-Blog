package net.wwang.blog.controller.admin;

import net.wwang.blog.constant.Types;
import net.wwang.blog.constant.WebConst;
import net.wwang.blog.dto.MetaDTO;
import net.wwang.blog.exception.InternalException;
import net.wwang.blog.model.Meta;
import net.wwang.blog.service.MetaService;
import net.wwang.blog.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private MetaService metaService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        // 获取所有目录分类
        List<MetaDTO> categories = metaService.getMetaList(Types.CATEGORY.getType(),null, WebConst.MAX_POSTS);
        // 获取所有标签分类
        List<MetaDTO> tags = metaService.getMetaList(Types.TAG.getType(), null, WebConst.MAX_POSTS);
        request.setAttribute("categories",categories);
        request.setAttribute("tags",tags);
        return "admin/category";
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public APIResponse addCategory(@RequestParam(name = "cname", required = true) String cname,
                                   @RequestParam(name = "mid", required = false) Integer mid){
        Meta meta = new Meta();
        meta.setName(cname);
        meta.setMid(mid);
        meta.setSlug(cname);
        meta.setType(Types.CATEGORY.getType());
        try{
            metaService.saveMeta(meta);
        }catch (InternalException e){
            e.printStackTrace();
            String msg = "分类保存失败";
            return APIResponse.failure(msg);
        }
        return APIResponse.success();
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    //(name = "mid", required = true)从表单传参时设置。
    public APIResponse deleteCategory( @RequestParam Integer mid){
        try {
            metaService.deleteById(mid);
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.failure(e.getMessage());
        }
        return APIResponse.success();
    }
}
