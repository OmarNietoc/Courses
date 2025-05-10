package com.edutech.usuarios.controller;

import com.edutech.usuarios.controller.response.MessageResponse;
import com.edutech.usuarios.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/edutech/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return ResponseEntity.ok(role.get());
        } else {
            MessageResponse response = MessageResponse.builder()
                    .message("Role con ID " + id + " no encontrado.")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PostMapping
    public ResponseEntity<MessageResponse> addRole(@RequestBody Role role) {
        roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Rol creado exitosamente."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Rol no encontrado."));
        }

        Role role = optionalRole.get();
        role.setName(roleDetails.getName());
        roleRepository.save(role);
        return ResponseEntity.ok(new MessageResponse("Rol actualizado exitosamente."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRole(@PathVariable Long id) {
        if (!roleRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Rol no encontrado."));
        }
        roleRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Rol eliminado exitosamente."));
    }
}

