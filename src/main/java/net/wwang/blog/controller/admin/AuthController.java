package net.wwang.blog.controller.admin;

import net.wwang.blog.constant.WebConst;
import net.wwang.blog.enums.LogEnum;
import net.wwang.blog.exception.InternalException;
import net.wwang.blog.model.Log;
import net.wwang.blog.model.User;
import net.wwang.blog.service.LogService;
import net.wwang.blog.service.UserService;
import net.wwang.blog.utils.APIResponse;
import net.wwang.blog.utils.Commons;
import net.wwang.blog.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    private static Integer error_count = 0;

    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public APIResponse toLogin(@RequestParam(name = "username", required = true) String username,
                               @RequestParam(name = "password", required = true) String password,
                               @RequestParam(name = "remember_me", required = false) String remember_me,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            // 调用Service登录方法
            User user = userService.login(username, password);
            // 设置登录用户session
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
            // 判断是否勾选记住我
            if (StringUtils.isNotBlank(remember_me)) {
                CookieUtil.setCookie(response, WebConst.USER_IN_COOKIE, String.valueOf(user.getUid()));
            }
            Log log = Commons.newLog(LogEnum.LOGIN.getAction(),user.getUsername()+" 用户",user.getUid(),request);
            logService.insertLog(log);

        } catch (InternalException e) {
            if(error_count>2){
                error_count = 0;
            }
            error_count++;
            System.out.println(error_count);
            String msg = e.getMessage();
            return APIResponse.failure(msg);
        }
        return APIResponse.success();
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 移除session
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        // 移除cookie
        CookieUtil.removeCookie(request,response,WebConst.USER_IN_COOKIE);
        // 跳转到登录页面
        try {
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
