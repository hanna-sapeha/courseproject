package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.controller.model.ErrorDTO;
import com.hanna.sapeha.app.service.ItemService;
import com.hanna.sapeha.app.service.model.ItemDTO;
import com.hanna.sapeha.app.service.model.ItemFormDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ITEMS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_URL + ITEMS_URL)
@Log4j2
public class ItemAPIController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDTO> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/{id}")
    public ItemDTO getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @PostMapping
    public ResponseEntity<ErrorDTO> addItem(@RequestBody @Valid ItemFormDTO item,
                                            BindingResult errors) {
        if (!errors.hasErrors()) {
            itemService.addItem(item);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            log.error("Item filling error: {}", errors);
            ErrorDTO errorDTO = new ErrorDTO();
            List<String> errorsMessage = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            errorDTO.getErrors().addAll(errorsMessage);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        itemService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
