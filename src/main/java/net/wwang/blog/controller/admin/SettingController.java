package net.wwang.blog.controller.admin;

import net.wwang.blog.constant.WebConst;
import net.wwang.blog.enums.LogEnum;
import net.wwang.blog.exception.InternalException;
import net.wwang.blog.model.Log;
import net.wwang.blog.model.Option;
import net.wwang.blog.model.User;
import net.wwang.blog.service.LogService;
import net.wwang.blog.service.OptionService;
import net.wwang.blog.utils.APIResponse;
import net.wwang.blog.utils.Commons;
import net.wwang.blog.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/setting")
public class SettingController {

    @Autowired
    private OptionService optionService;
    @Autowired
    private LogService logService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<Option> optionsList = optionService.getOptions();
        Map<String, String> options = new HashMap<>();
        optionsList.forEach((option) ->{
            options.put(option.getName(),option.getValue());
        });
        request.setAttribute("options", options);
        //thymeleaf中options.key/options.value取键与值
        return "admin/setting";
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public APIResponse saveSetting(HttpServletRequest request){
        try{
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String,String> optionsMap = new HashMap<>();
            //转换map
            parameterMap.forEach((k,v)->{
                optionsMap.put(k, Commons.arrayToString(v));
            });
            optionService.saveOptions(optionsMap);

            // 写入日志
            User user = (User) request.getSession().getAttribute(WebConst.LOGIN_SESSION_KEY);
            Log log = Commons.newLog(LogEnum.SYS_SETTING.getAction(), JsonUtil.toJsonString(optionsMap), user.getUid(),request);
            logService.insertLog(log);

            return APIResponse.success();
        }catch (InternalException e){
            e.printStackTrace();
            return APIResponse.failure(e.getMessage());
        }
    }
}
