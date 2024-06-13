package kr.ac.kopo.guestbook2024.controller;

import kr.ac.kopo.guestbook2024.dto.GuestbookDTO;
import kr.ac.kopo.guestbook2024.dto.PageRequestDTO;
import kr.ac.kopo.guestbook2024.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService service;

   @GetMapping("/")
   public String index(){
       return "redirect:/guestbook/list";
   }
    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model){
    // list.html(view계층)에 방명록 목록과 화면에 보여질때 필요한 페이지번호등을 정보를 전달
       model.addAttribute("result",service.getList(pageRequestDTO));
    }
    @GetMapping("/register")
    public void register(){

    }
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
       Long gno=service.register(dto);
       redirectAttributes.addFlashAttribute("msg",gno);
       return "redirect:/guestbook/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,Model model){
        GuestbookDTO dto =service.read(gno);
        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long gno,RedirectAttributes redirectAttributes){
       service.remove(gno);
       redirectAttributes.addFlashAttribute("msg",gno);

       return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,@ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        service.modify(dto);
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";
    }


}

