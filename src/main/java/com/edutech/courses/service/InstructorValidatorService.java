package com.edutech.courses.service;

import com.edutech.courses.client.UserClient;
import com.edutech.courses.dto.UserResponseDto;
import com.edutech.courses.exception.ResourceNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorValidatorService {

    private final UserClient userClient;

    public UserResponseDto validateInstructor(Long instructorId) {
        UserResponseDto instructor;

        try {
            instructor = userClient.getUserById(instructorId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("El usuario no existe: " + instructorId);
        } catch (FeignException e) {
            throw new IllegalArgumentException("Error al validar el instructor: " + e.getMessage());
        }

        validateRoleOfUser(instructor.getRole().getName(), "INSTRUCTOR");

        return instructor;
    }

    public boolean validateRoleOfUser(String roleName, String nameSearched) {
        if (roleName == null || !roleName.equalsIgnoreCase(nameSearched)) {
            throw new IllegalArgumentException("El usuario ingresado no tiene el role de " + nameSearched + ".");
        }
        return true;
    }
}
