package com.philips.basicdetails.auth;


import com.philips.basicdetails.application.model.OtpViewModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;

@Controller
public class UserController {
    final String MENU_CODE = "archaivalprocessrequest";


    private final WhatsAppOTPService whatsAppOTPService;

    public UserController(WhatsAppOTPService whatsAppOTPService) {
        this.whatsAppOTPService = whatsAppOTPService;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout, HttpSession session, HttpServletResponse response) {
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login/login";
    }

    @GetMapping("/logout")
    public String logout(Model model, String error, String logout, HttpSession session, HttpServletResponse response) {
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logoutPost(Model model, String error, String logout, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        return "redirect:/login?logout=logout";
    }

    /*@GetMapping("/validateUser")
    public String validateUser(Model model, String error, String logout, HttpSession session, HttpServletResponse response) {
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login/login-v2";
    }
    @PostMapping("/validateUser")
    public String postValidateUser(Model model, String error, String logout, HttpSession session, HttpServletResponse response) {
        SystemUserLoginInfo systemUserLoginInfo = baseService.getCurrentUserDetails();
        List<AdminMenuEntity> adminMenuEntityList = adminMenuRepository.findByRoleId(systemUserLoginInfo.getUserRole());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> AUTHORITIES = new ArrayList<>(auth.getAuthorities());
        this.setAuthority(adminMenuEntityList, AUTHORITIES);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), AUTHORITIES);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return "login/login-v2";
    }*/

    @GetMapping({"/"})
    @PreAuthorize("!hasAuthority('" + MENU_CODE + "::browse')")
    public String adLoginPage() {
        return "home/home";
    }

    @GetMapping({"/home"})
//    @PreAuthorize("hasAuthority('" + MENU_CODE + "::browse')")
    public String welcome() {
        return "home/home";
    }
    @GetMapping({"/otp-page"})
//    @PreAuthorize("hasAuthority('" + MENU_CODE + "::browse')")
    public String getOTPPage(Model model) {
        model.addAttribute("viewModel", new OtpViewModel());
        return "home/otp-page";
    }
    @PostMapping({"/otp-page"})
//    @PreAuthorize("hasAuthority('" + MENU_CODE + "::browse')")
    public String postOTPPage(@ModelAttribute OtpViewModel viewModel, Model model) throws UnsupportedEncodingException {
        whatsAppOTPService.sendOtp(viewModel);
        model.addAttribute("viewModel", viewModel);
        return "home/otp-page";
    }

    /*private void setAuthority(List<AdminMenuEntity> adminMenuEntityList, List<GrantedAuthority> AUTHORITIES) {
        for (AdminMenuEntity menu : adminMenuEntityList) {
            String menuCode = menu.getSiteMenuLinkEntity().getMenuCode();

            if (menu.getCreatePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "create")));

            if (menu.getModifyPermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "modify")));

            if (menu.getDeletePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "delete")));

            if (menu.getAuthorizePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "authorize")));

            if (menu.getBrowsePermission() == YesNo.YES.getCode())
                AUTHORITIES.add(new SimpleGrantedAuthority(getAuthority(menuCode, "browse")));
        }
    }*/

    private String getAuthority(String menuCode, String permission) {
        return String.format("%s::%s", menuCode, permission);
    }
}