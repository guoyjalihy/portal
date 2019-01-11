package com.common.portal.filter;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.entity.User;
import com.common.portal.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@WebFilter
@Slf4j
public class SessionFilter implements Filter {
    private List<String> unFilterURIList = new ArrayList<>();
    @Autowired
    private MenuService menuService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
            unFilterURIList.add("/login");
            unFilterURIList.add("/css");
            unFilterURIList.add("/js");
            unFilterURIList.add("/register");
            unFilterURIList.add("/h2-console");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //判断session
        String currURI = request.getRequestURI();
        for (String uri : unFilterURIList){
            if (currURI.contains(uri)){
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }

        Object obj = request.getSession().getAttribute("user");
        if (obj == null || !(obj instanceof UserVO)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        //维护当前访问菜单信息到session
        String menuId = request.getParameter("menuId");
        if (menuId == null){
            menuId = 1+"";
        }
        MenuVO menuVO = menuService.findById(Long.valueOf(menuId));
        if (menuVO == null){
            log.error("menu is null. menuId:{}",menuId);
            return;
        }
        request.getSession().setAttribute("currMenu", menuVO);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
