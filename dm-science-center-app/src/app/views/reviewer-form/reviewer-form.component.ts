import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reviewer-form',
  templateUrl: './reviewer-form.component.html',
  styleUrls: ['./reviewer-form.component.css']
})
export class ReviewerFormComponent implements OnInit {

  private uspesno = false;
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];

  constructor(private userService: UserService, private router: Router) {
    console.log('sta se desava');
    let x = userService.getReviewerForm();

    x.subscribe(
      res => {
        console.log(res);
        // this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        console.log(this.formFieldsDto.taskId + ' task id');
        this.formFields.forEach( (field) => {

          if (field.type.name === 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log('Error occured');
      }
    );
  }

  ngOnInit() { }

  onSubmit(value, form){
    let o = new Array();
    for (let property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    const x = this.userService.putConfirmReviewer(this.formFieldsDto.taskId, o);
    x.subscribe(
      res => {
        console.log(res);
        alert(res);
        this.uspesno = true;
        this.router.navigate(['home']);
      },
      err => {
        console.log('Error occured');
      }
    );
  }

}
