package net.wwang.blog.controller.admin;

import net.wwang.blog.constant.Types;
import net.wwang.blog.enums.ErrorEnum;
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
@RequestMapping("/admin/links")
public class LinkController {
    @Autowired
    private MetaService metaService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<Meta> metaList = metaService.getMetasByType(Types.LINK.getType());
        request.setAttribute("links", metaList);
        return "admin/links";
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public APIResponse addLink(@RequestParam(name = "title", required = true) String title,
                               @RequestParam(name = "url", required = true) String url,
                               @RequestParam(name = "logo", required = false) String logo,
                               @RequestParam(name = "mid", required = false) Integer mid,
                               @RequestParam(name = "sort", required = false, defaultValue = "0") int sort) {
        try {
            Meta meta = new Meta();
            meta.setMid(mid);
            meta.setName(title);
            meta.setSlug(url);
            meta.setDescription(logo);
            meta.setSort(sort);
            meta.setType(Types.LINK.getType());
            metaService.saveLink(meta);
        } catch (InternalException e) {
            e.printStackTrace();
            throw new InternalException(ErrorEnum.ADD_META_FAIL);
        }

        return APIResponse.success();

    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public APIResponse deleteLink(@RequestParam(name = "mid", required = true) int mid) {
        try {
            metaService.deleteById(mid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(ErrorEnum.DELETE_META_FAIL);
        }
        return APIResponse.success();
    }
}
