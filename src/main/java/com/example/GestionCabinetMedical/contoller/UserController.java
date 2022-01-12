package com.example.GestionCabinetMedical.contoller;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.GestionCabinetMedical.entity.Role;
import com.example.GestionCabinetMedical.entity.RoleToUserForm;
import com.example.GestionCabinetMedical.entity.User;
import com.example.GestionCabinetMedical.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getUser() {

        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(),form.getRoleName());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(authorization);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            try {
                var refresh_token = authorization.substring("Bearer ".length());
                System.out.println(refresh_token);
                var algorithm = Algorithm.HMAC256("secret".getBytes());
                var verifier = JWT.require(algorithm).build();
                var decodedJWT = verifier.verify(refresh_token);
                var username = decodedJWT.getSubject();
                log.info("Username refresh {}",username);
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            } catch (Exception exception){
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        } else {
            throw new RuntimeException("Refresh Token is missing");
        }

    }


    /* Gestion des patient   */
    @PostMapping("/patient/save")
    public ResponseEntity<User> savePatient(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/patient/save").toUriString());
        return ResponseEntity.created(uri).body(userService.savePatient(user));
    }



    @GetMapping("/patient/getall")
    public ResponseEntity<List<User>> getAllUser() {
        try {
            if (userService.getPatients().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userService.getPatients(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/patient/{username}")
    public ResponseEntity<User> getPatientByNmae(@PathVariable("username") String username) {
        Optional<User> patient = Optional.ofNullable(userService.getPatientByNmae(username));
        if (patient.isPresent()) {
            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/patient/update")
    public ResponseEntity<User> updatePatient( @RequestBody User user) {
        Optional<User> userData = Optional.ofNullable(userService.getID(user.getId()));

        log.warn("update user id : "+user.getId());
        log.warn("Votre  user Envoyer : "+user);
        if (userData.isPresent()&&userService.isRoleUSer(userData.get(),"ROLE_PATIENT")) {
            return new ResponseEntity<>(userService.updateUser(user), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity<HttpStatus> deleteRdv(@PathVariable("id") long id) {
        try {
            if(userService.isRoleUSer(userService.getID(id),"ROLE_PATIENT"))
            {

                log.info("Patient {} Deleted from database",userService.getID(id).getUsername());
                userService.deleteUserBYid(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @PutMapping("validerRdv/{id}")
    public ResponseEntity<Rdv> validerRdv(@PathVariable("id") long id) {
        Optional<Rdv> rdvData = Optional.ofNullable(service.searchedById(id));
        if (rdvData.isPresent()) {
            service.validerRdv(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
*/

}