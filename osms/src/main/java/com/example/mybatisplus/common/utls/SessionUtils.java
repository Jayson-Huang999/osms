package com.example.mybatisplus.common.utls;

import com.example.mybatisplus.model.domain.Admin;
import com.example.mybatisplus.model.domain.Seller;
import com.example.mybatisplus.model.domain.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    private static final String USERKEY = "sessionUser";

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    public static Admin getCurrentAdminInfo() {
        return (Admin) session().getAttribute(USERKEY);
    }

    public static void saveCurrentAdminInfo(Admin admin) {
        session().setAttribute(USERKEY, admin);
    }

    public static User getCurrentUserInfo() {
        return (User) session().getAttribute(USERKEY);
    }

    public static void saveCurrentUserInfo(User user) {
        session().setAttribute(USERKEY, user);
    }

    public static Seller getCurrentSellerInfo() {
        return (Seller) session().getAttribute(USERKEY);
    }

    public static void saveCurrentSellerInfo(Seller seller) {
        session().setAttribute(USERKEY, seller);
    }
}
