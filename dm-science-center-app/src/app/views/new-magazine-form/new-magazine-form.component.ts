import { Component, OnInit } from '@angular/core';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-magazine-form',
  templateUrl: './new-magazine-form.component.html',
  styleUrls: ['./new-magazine-form.component.css']
})
export class NewMagazineFormComponent implements OnInit {

  private repeatedRassword = '';
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosenCategory = -1;
  private processInstance = '';
  private enumValues = [];
  private tasks = [];

  constructor(private magazineService: MagazineService, private router: Router) {

    const x = magazineService.startNewMagazineProcess();

    x.subscribe(
      res => {
        console.log(res);
        // this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
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

  ngOnInit() {
  }

  onSubmit(value, form) {
    let o = new Array();
    for (let property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);
    const x = this.magazineService.postNewMagazine(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        alert('You created new magazine successfully!');
        this.router.navigate(['add-reviewers-and-editors']);
      },
      err => {
        console.log('Error occured');
        console.log(err);
        alert('You have tried to create magazine with existing issnNumber!');
      }
    );
  }

}
