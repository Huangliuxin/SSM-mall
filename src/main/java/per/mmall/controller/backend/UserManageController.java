package per.mmall.controller.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import per.mmall.common.Const;
import per.mmall.common.ServerResponse;
import per.mmall.pojo.User;
import per.mmall.service.IUserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response =iUserService.login(username,password);
        if (response.isSuccess()){
            User user = response.getData();
            if (user.getRole()== Const.Role.ROLE_ADMIN){
                //说明登录的管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员无法登录");
            }

        }
        return response;
    }

}
