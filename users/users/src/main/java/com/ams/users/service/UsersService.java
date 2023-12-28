package com.ams.users.service;

import java.util.*;

import com.ams.users.dto.LoginBody;
import com.ams.users.dto.UsersDTO;
import com.ams.users.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ams.users.entity.Users;
import com.ams.users.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    private EncryptionService encryptionService;
    /** The JWT service. */
    private JWTService jwtService;
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Users getByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public Users getById(Long id) throws Exception {
        return usersRepository.findById(id).orElseThrow(Exception::new);
    }

    public Users saveUser(UsersDTO users) {
        return usersRepository.save(new UsersDTO().toUsersEntity(users));
    }

    public Users updateUser(UsersDTO users, Long id) throws Exception {
        Users currentUser = usersRepository.findById(id).orElseThrow(Exception::new);
        currentUser.setUsername(users.getUsername());
        currentUser.setEmail(users.getEmail());
        currentUser.setFullName(users.getFullName());
        currentUser.setDateOfBirth(users.getDateOfBirth());
        currentUser.setUpdatedAt(users.getUpdatedAt());
        return usersRepository.save(currentUser);
    }

    public ResponseEntity<String> deleteUser(Long idUser) {
        if (this.usersRepository.findById(idUser).isPresent()) {
            usersRepository.deleteById(idUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("users are deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("resource not found");
    }

    public Users patchUser(Long id, Map<String, Object> users) throws Exception {
        Users currentUser = usersRepository.findById(id).orElseThrow(RuntimeException::new);
        if (users.containsKey("username"))
            currentUser.setUsername(users.get("username").toString());
        if (users.containsKey("email"))
            currentUser.setEmail(users.get("email").toString());
        if (users.containsKey("fullName"))
            currentUser.setEmail(users.get("fullName").toString());
        if (users.containsKey("dateOfBirth"))
            currentUser.setEmail(users.get("dateOfBirth").toString());
        return usersRepository.save(currentUser);
    }

    /**
     * Logins in a user and provides an authentication token back.
     * @param loginBody The login request.
     * @return The authentication token. Null if the request was invalid.
     */
    public String loginUser(LoginBody loginBody) throws Exception {
        Users userExist = usersRepository.findByUsername(loginBody.getUsername());
        if (userExist.isPersisted()) {
            UsersDTO userDto=new UsersDTO(userExist);
            if (encryptionService.verifyPassword(loginBody.getPassword(), userExist.getPassword())) {
                    return jwtService.generateJWT(userDto);
            }
        }
        return null;
    }


    /**
     * Attempts to register a user given the information provided.
     * @param registrationBody The registration information.
     * @return The local user that has been written to the database.
     * @throws UserAlreadyExistsException Thrown if there is already a user with the given information.
     */
    public Users registerUser(UsersDTO registrationBody) throws UserAlreadyExistsException, Exception {
        if (usersRepository.findByEmail(registrationBody.getEmail()).isPersisted()
                || usersRepository.findByUsername(registrationBody.getUsername()).isPersisted()) {
            throw new UserAlreadyExistsException();
        }
        Users user = new Users();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFullName(registrationBody.getFullName());
        user.setDateOfBirth(registrationBody.getDateOfBirth());
        user.setRole(registrationBody.getRole());
        user.setCreatedAt(registrationBody.getCreatedAt());
        user.setUpdatedAt(registrationBody.getUpdatedAt());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return usersRepository.save(user);
    }
}
