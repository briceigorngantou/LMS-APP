package com.ams.users.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ams.users.dto.LoginBody;
import com.ams.users.dto.UsersDTO;
import com.ams.users.entity.Users;
import com.ams.users.exception.UserAlreadyExistsException;
import com.ams.users.exception.UserNotFoundException;
import com.ams.users.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    // private EncryptionService encryptionService;
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
        currentUser.setAge(users.getAge());
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
        if (users.containsKey("age"))
            currentUser.setAge((Integer) users.get("age"));
        return usersRepository.save(currentUser);
    }

    /**
     * Logins in a user and provides an authentication token back.
     * 
     * @param loginBody The login request.
     * @return The authentication token. Null if the request was invalid.
     * @author Brice Ngantou
     */
    public String loginUser(LoginBody loginBody) throws Exception {
        Users userExist = usersRepository.findByUsername(loginBody.getUsername());
        if (userExist != null) {
            UsersDTO userDto = new UsersDTO(userExist);
            // if (encryptionService.verifyPassword(loginBody.getPassword(),
            // userExist.getPassword())) {
            // return jwtService.generateJWT(userDto);
            // }
            if (loginBody.getPassword().equals(userExist.getPassword())) {
                return jwtService.generateJWT(loginBody);
            }
        }
        return null;
    }

    /**
     * Attempts to register a user given the information provided.
     * 
     * @param registrationBody The registration information.
     * @return The local user that has been written to the database.
     * @throws UserAlreadyExistsException Thrown if there is already a user with the
     *                                    given information.
     * @author Brice Ngantou
     */
    public Users registerUser(UsersDTO registrationBody) throws UserAlreadyExistsException, Exception {
        if (usersRepository.findByEmail(registrationBody.getEmail()) != null
                || usersRepository.findByUsername(registrationBody.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exist");
        }
        Users user = new Users();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFullName(registrationBody.getFullName());
        user.setAge(registrationBody.getAge());
        user.setRole(registrationBody.getRole());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setPassword(registrationBody.getPassword());
        // user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return usersRepository.save(user);
    }

    public Users getUserByNameAndPassword(String name, String password) throws UserNotFoundException {
        Users user = usersRepository.findByUsernameAndPassword(name, password);
        if (user == null) {
            throw new UserNotFoundException("Invalid username or password");
        }
        return user;
    }
}
