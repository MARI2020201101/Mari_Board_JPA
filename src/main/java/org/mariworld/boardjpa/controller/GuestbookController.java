package org.mariworld.boardjpa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mariworld.boardjpa.dto.GuestbookDTO;
import org.mariworld.boardjpa.dto.PageRequestDTO;
import org.mariworld.boardjpa.dto.PageResultDTO;
import org.mariworld.boardjpa.entity.Guestbook;
import org.mariworld.boardjpa.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.....");
        PageResultDTO<GuestbookDTO, Guestbook> result
                = guestbookService.getList(pageRequestDTO);

        model.addAttribute("result", result);
    }

    @GetMapping("/register")
    public void registerForm(){
        log.info("registerForm.....");
    }

    @PostMapping("/register")
    public String register(GuestbookDTO dto, RedirectAttributes rttr){
        log.info("register.....");
        Long gno = guestbookService.register(dto);
        rttr.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long gno
                    , @ModelAttribute("requestDto") PageRequestDTO pageRequestDTO
                    , Model model){
       model.addAttribute("dto", guestbookService.read(gno));
    }
    @PostMapping("/remove")
    public String remove(Long gno, RedirectAttributes rttr){
        guestbookService.remove(gno);
        rttr.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }
    @PostMapping("/modify")
    public String modify(GuestbookDTO dto
            ,@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO
            ,RedirectAttributes rttr){
        guestbookService.modify(dto);
        rttr.addAttribute("gno",dto.getGno());
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/guestbook/read";
    }
}
