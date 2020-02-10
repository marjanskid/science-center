import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  private user;
  private role;

  errorMessage: string;

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(5)])
  });

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {}

  ngOnInit() {
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    this.clearCredentialsErrors();
    this.userService.signin(this.username.value, this.password.value).subscribe(
      data => {
        location.reload();
        this.user = JSON.parse(localStorage.getItem('sessionUserName'));
        this.role = localStorage.getItem('sessionUserRole');
        this.router.navigate(['/home']);
      },
      error => {
        this.errorMessage = error;
      }
    );
  }

  private clearCredentialsErrors() {
    this.username.setErrors({ usernameError: null });
    this.username.updateValueAndValidity();
    this.password.setErrors({ passwordError: null });
    this.password.updateValueAndValidity();
  }

  // Geteri za polja username i password
  get username() {
    return this.loginForm.get('username');
  }
  get password() {
    return this.loginForm.get('password');
  }

}
