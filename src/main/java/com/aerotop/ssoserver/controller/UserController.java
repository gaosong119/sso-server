package com.aerotop.ssoserver.controller;

import com.aerotop.constant.AuthConstant;
import com.aerotop.ssoserver.pojo.User;
import com.aerotop.ssoserver.service.UserService;
import com.aerotop.ssoserver.storage.ClientStorage;
import com.aerotop.storage.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @ClassName: UserController
 * @Description: User 控制器
 * @Author: gaosong
 * @Date 2021/2/3 14:26
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, User input, Model model) {
        //查询用户
        User selectUser = userService.selectUser(input);
        if (selectUser == null) {
            model.addAttribute("error", "username or password is wrong.");
            return "redirect:/";
        }
        // 授权
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute(AuthConstant.IS_LOGIN, true);
        request.getSession().setAttribute(AuthConstant.TOKEN, token);
        // 存储，用于注销
        SessionStorage.INSTANCE.set(token, request.getSession());
        // 子系统跳转过来的登录请求，授权、存储后，跳转回去
        String clientUrl = request.getParameter(AuthConstant.CLIENT_URL);
        if (clientUrl != null && !"".equals(clientUrl)) {
            // 存储，用于注销
            ClientStorage.INSTANCE.set(token, clientUrl);
            return "redirect:" + clientUrl + "?" + AuthConstant.TOKEN + "=" + token;
        }

        return "redirect:/";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute(AuthConstant.CLIENT_URL, request.getParameter(AuthConstant.CLIENT_URL));
        return "index";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }
}
