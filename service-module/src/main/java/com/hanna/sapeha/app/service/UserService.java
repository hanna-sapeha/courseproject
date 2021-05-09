package com.hanna.sapeha.app.service;

import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.UserDTO;
import com.hanna.sapeha.app.service.model.UserProfileDTO;

public interface UserService {

    PageDTO<UserDTO> getAllByPagination(int pageNumber, int pageSize);

    void add(UserDTO userDTO, Long idRole);

    void removeById(Long id);

    void changePasswordById(Long id);

    void changeRoleById(Long idUser, Long idRole);

    void addAndSendEmail(UserDTO user, Long idRole);

    void add(UserDTO userDTO);
}
