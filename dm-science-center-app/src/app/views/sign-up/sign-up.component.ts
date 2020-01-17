import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { matchOtherValidator } from './match-passwords';
import { UserService } from 'src/app/services/user/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from 'src/app/services/repository/repository.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  private repeatedRassword = '';
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosenCategory = -1;
  private processInstance = '';
  private enumValues = [];
  private tasks = [];

  constructor(private userService: UserService) {

    const x = userService.startRegistrationProcess();

    x.subscribe(
      res => {
        console.log(res);
        // this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) => {

          if ( field.type.name === 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log('Error occured');
      }
    );
   }

  ngOnInit() {
  }

  onSubmit(value, form) {
    let o = new Array();
    console.log(value);
    console.log(form.values);
    for (let property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    const x = this.userService.registerUser(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        alert('You registered successfully!');
      },
      err => {
        console.log('Error occured');
      }
    );
  }

}
