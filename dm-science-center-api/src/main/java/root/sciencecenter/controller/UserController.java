package root.sciencecenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import root.sciencecenter.dtos.UserSignInDto;
import root.sciencecenter.entities.User;
import root.sciencecenter.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody UserSignInDto userSignInDto) {
        User foundUser = userService.userSignIn(userSignInDto.getUsername(), userSignInDto.getPassword());
        if (foundUser != null) {
            if (foundUser.isActivatedUser()) {
                System.out.println("Successful sign in!");
                return new ResponseEntity<String>("Successful sign in!", HttpStatus.OK);
            } else {
                System.out.println("User account is not activated!");
                return new ResponseEntity<String>("User account is not activated!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
