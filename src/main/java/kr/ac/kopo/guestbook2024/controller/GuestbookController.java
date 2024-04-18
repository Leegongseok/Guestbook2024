package kr.ac.kopo.guestbook2024.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
    @GetMapping({"/","/list"})
    public String list(){
        return "/guestbook/list";
    }
}

