import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from 'src/app/services/repository/repository.service';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {

  private user;
  private role;

  private taskId: string;
  private task: any;
  private enumValues = []; //sadrzaj jedne enumeracije
  private formFields = []; //sva polja fomre
  private dropdownList = []; //niz koji sadrzi mape i sluzi za prikaz u multiselectu
  private dropdownSettings: any; //podesavanja za select
  private dropdownMultiselectSettings : any; //podesavanja za multiselect
  private multiselect = []; //niz koji sadrzi informaciju je enumeracija multiselect
  private val: String; //string koji se dobija nakon submita, vrednosti enumeracije razdvojene sa zarezom
  private selectedItems = []; //niz koji sadrzi za svaki comboBox sta je selektovani
  private selectedOneEnumItems = []; //niz mala selektovanih polja u enumeraciji
  private formFieldsDto = null; //podaci o jednom polju forme
  private labels = []; //labele kod polja koja su tipa enumeracija
  private names = []; //niz koji sadrzi is polja, sluzi da se postavi name atributi u formi
  private enumerations = []; //niz svih enumeracija
  private enumList = []; //jedna vrednost u enumeraciji
  private readonlyList = []; //lista booleana koji govore da li je readonly

  constructor(private router: Router, private route: ActivatedRoute, private repositoryService: RepositoryService) { 
    this.taskId = this.route.snapshot.params.taskId;
    this.user = JSON.parse(localStorage.getItem('sessionUserName'));
    this.role = localStorage.getItem('sessionUserRole');

    alert('taskId: ' + this.taskId);
    alert(this.user);
    alert(this.role);

    const x = this.repositoryService.getTaskInfo(this.taskId);
    x.subscribe(
      res => {
        console.log(res);
        this.readonlyList = [];
        this.task = res;
        this.formFields = res.formFields;
        this.selectedItems = [];
        this.formFields.forEach((field) => {
          console.log('kurec: ' + field.properties.readonly);
          if (field.properties.readonly == 'false') {
            console.log('ovde sam - false');
            this.readonlyList.push(false);
          } else {
            console.log('ovde sam - true');
            this.readonlyList.push(true);
          }
          if (field.type.name === 'enum') {
            this.enumList = [];
            this.selectedOneEnumItems = [];
            this.enumValues = Object.keys(field.type.values);
            this.labels.push(field.label);
            for (const value of this.enumValues) {
              this.enumList.push({ item_id: value, item_text: value });
            }
            this.multiselect.push(field.properties[Object.keys(field.properties)[0]]);
            this.dropdownList.push(this.enumList);
            this.enumerations.push(this.enumValues);
            this.names.push(field.id);
            if (field.defaultValue != null) {
              this.selectedOneEnumItems.push({ item_id: field.defaultValue, item_text: field.defaultValue });
            }
            this.selectedItems.push(this.selectedOneEnumItems);
            console.log(this.selectedOneEnumItems);
          }
          console.log(this.readonlyList);
        });
      },
      err => {
        alert(err.error);
      }
    );

  }

  ngOnInit() {
    this.val = '';

    this.dropdownMultiselectSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
    this.dropdownSettings = {
      singleSelection: true,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };

  }

  onSubmit(value, form) {
    console.log('onSubmit');
    let o = new Array();
    console.log(value);
    console.log(form.values);
    // tslint:disable-next-line: forin
    for (const property in value) {
      console.log('for');
      if (typeof (value[property]) === 'object') {
        console.log('object');
        for (let index = 0; index < value[property].length; index++) {
          console.log('index = ' + index);
          if (index !== 0) {
            this.val = this.val + ',';
          }
          const something = value[property][index][Object.keys(value[property][index])[1]];
          this.val = this.val + something;
        }
        o.push({ fieldId: property, fieldValue: this.val });
      } else {
        o.push({ fieldId: property, fieldValue: value[property] });
      }
      this.val = '';
    }

    console.log(o);

    const x = this.repositoryService.postData(o, this.taskId);
    x.subscribe(
      res => {
        this.router.navigate(['']);
      },
      err => {
        alert(err.error);
      }
    );
  }

}
