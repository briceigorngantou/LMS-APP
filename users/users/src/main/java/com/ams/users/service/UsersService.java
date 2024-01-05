package com.ams.users.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ams.users.dto.UsersDTO;
import com.ams.users.dto.UsersResponse;
import com.ams.users.entity.ROLE;
import com.ams.users.entity.Users;
import com.ams.users.exception.UserAlreadyExistsException;
import com.ams.users.exception.UserNotFoundException;
import com.ams.users.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * @return List<UserResponse>
     * @author Brice Ngantou
     */
    public List<UsersResponse> getUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    /**
     * @param user
     * @return UserResponse
     * @author Brice Ngantou
     */
    public UsersResponse convertToUserResponse(Users user) {
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setId(user.getId());
        usersResponse.setUsername(user.getUsername());
        usersResponse.setEmail(user.getEmail());
        usersResponse.setRole(user.getRole());
        usersResponse.setAge(user.getAge());
        usersResponse.setFullName(user.getFullName());
        usersResponse.setCreatedAt(user.getCreatedAt());
        usersResponse.setUpdatedAt(user.getUpdatedAt());
        return usersResponse;
    }

    /**
     * @param username
     * @return Users
     * @author Brice Ngantou
     */
    public Users getByUsername(String username) throws Exception {
        return usersRepository.findByUsername(username);
    }

    /**
     * @param id
     * @return Users
     * @throws Exception
     * @author Brice Ngantou
     */
    public Users getById(Long id) throws Exception {
        return usersRepository.findById(id).orElseThrow(Exception::new);
    }

    /**
     * @param users
     * @param id
     * @return Users
     * @throws Exception
     * @author Brice Ngantou
     */
    public Users updateUser(UsersDTO users, Long id) throws Exception {
        Users currentUser = usersRepository.findById(id).orElseThrow(Exception::new);
        currentUser.setUsername(users.getUsername());
        currentUser.setEmail(users.getEmail());
        currentUser.setFullName(users.getFullName());
        currentUser.setRole(users.getRole());
        currentUser.setAge(users.getAge());
        return usersRepository.save(currentUser);
    }

    /**
     * @param idUser
     * @return Success message if operation was make successfully. Error message
     *         else
     * @author Brice Ngantou
     */
    public ResponseEntity<String> deleteUser(Long idUser) {
        if (this.usersRepository.findById(idUser).isPresent()) {
            usersRepository.deleteById(idUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("users are deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("resource not found");
    }

    /**
     * @param id
     * @param users
     * @return Users
     * @throws Exception
     * @author Brice Ngantou
     */
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
        if (users.containsKey("role"))
            currentUser.setRole((ROLE) users.get("role"));
        return usersRepository.save(currentUser);
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
        return usersRepository.save(user);
    }

    /**
     * Login in a user.
     * 
     * @param username The login request.
     * @return User informations if exist and null if the request was invalid.
     * @throws UserNotFoundException Thrown if there a user not found.
     * @author Brice Ngantou
     */
    public Users loginUsers(String username, String password) throws AuthenticationException {
        Users user = usersRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new AuthenticationException("Invalid username or password");
        }
        return user;
    }
}
