package kz.yeltayev.utility.services;

import kz.yeltayev.utility.dto.UserDto;
import kz.yeltayev.utility.entity.AuthRequest;
import kz.yeltayev.utility.entity.User;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto addUser(User user) {
        return convertToDto(userRepository.save(user));
    }

    @Transactional
    public List<UserDto> fetchUsers() {
        List<User> users = userRepository.findAll();
        return convertToListUserDto(users);
    }

    @Transactional
    public List<UserDto> fetchUsersByOwner(String owner) {
        List<User> users = userRepository.findUsersByOwner(owner);
        return convertToListUserDto(users);
    }

    @Transactional
    public UserDto fetchUserById(Long UserId) throws ResourceNotFoundException {
        return convertToDto(userRepository.findById(UserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + UserId)));
    }

    @Transactional
    public UserDto updateUser(Long userId, User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setType(userDetails.getType());

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    @Transactional
    public Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Transactional
    public UserDto auth(AuthRequest auth) {
        User serverUser = userRepository.findUserByLogin(auth.getLogin());
        if (serverUser != null && serverUser.getPassword().equals(auth.getPassword())) {
            return convertToDto(serverUser);
        } else {
            return null;
        }
    }

    private List<UserDto> convertToListUserDto(List<User> users) {
        if (users.isEmpty())
            return new ArrayList<>();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
