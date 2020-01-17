import { Component, OnInit } from '@angular/core';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-reviewers-and-editors',
  templateUrl: './add-reviewers-and-editors.component.html',
  styleUrls: ['./add-reviewers-and-editors.component.css']
})
export class AddReviewersAndEditorsComponent implements OnInit {

  private uspesno = false;
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];

  constructor(private magazineService: MagazineService, private router: Router) {
    console.log('sta se desava');
    const x = magazineService.getReviewersAndEditorsForm();

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

  onSubmit(value, form) {
    let o = new Array();
    for (let property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    const x = this.magazineService.postReviewersAndEditorsForm(this.formFieldsDto.taskId, o);
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
