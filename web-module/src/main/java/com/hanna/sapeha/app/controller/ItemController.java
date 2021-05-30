package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.ItemService;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.UserLogin;
import com.hanna.sapeha.app.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.hanna.sapeha.app.constant.ControllerConstants.DEFAULT_PAGE_SIZE;
import static com.hanna.sapeha.app.constant.HandlerConstants.ITEMS_URL;

@Controller
@RequiredArgsConstructor
@RequestMapping(ITEMS_URL)
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public String getItems(Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber) {
        PageDTO<ItemDTO> page = itemService.getAllByPagination(pageNumber, DEFAULT_PAGE_SIZE);
        model.addAttribute("page", page);
        UserLogin user = ControllerUtil.getAuthorizedUser();
        model.addAttribute("role", user.getRoleName());
        return "items";
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam(value = "itemIdForDelete") Long id) {
        itemService.removeById(id);
        return "redirect:" + ITEMS_URL;
    }

    @PostMapping("/copy")
    public String copyItem(@RequestParam(value = "itemIdForCopy") Long id) {
        itemService.copyItem(id);
        return "redirect:" + ITEMS_URL;
    }

    @GetMapping("/{uuid}")
    public String getItem(Model model, @PathVariable UUID uuid) {
        ItemDTO item = itemService.getItemByUniqueNumber(uuid);
        model.addAttribute("item", item);
        String role = ControllerUtil.getAuthorizedUser().getRoleName();
        model.addAttribute("role", role);
        return "item";
    }
}
