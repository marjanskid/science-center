import { Component, OnInit } from '@angular/core';
import { RepositoryService } from 'src/app/services/repository/repository.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ArticleService } from 'src/app/services/article/article.service';

@Component({
  selector: 'app-article-coauthor-info',
  templateUrl: './article-coauthor-info.component.html',
  styleUrls: ['./article-coauthor-info.component.css']
})
export class ArticleCoauthorInfoComponent implements OnInit {

  private user;
  private role;

  private formFieldsDto = null; // podaci o jednom polju forme
  private formFields = []; // sva polja fomre
  private labels = []; // labele kod polja koja su tipa enumeracija
  private names = []; // niz koji sadrzi is polja, sluzi da se postavi name atributi u formi
  private enumValues = []; // sadrzaj jedne enumeracije
  private enumerations = []; // niz svih enumeracija
  private multiselect = []; // niz boolean polja da li je enumeracija multiselect
  private processInstance = ''; // id procesa
  private dropdownSettings: any; // podesavanja za select
  private dropdownMultiselectSettings: any; // podesavanja za multiselect
  private dropdownList = []; // niz koji sadrzi mape i sluzi za prikaz u multiselectu
  private enumList = []; // jedna vrednost u enumeraciji
  private val: string; // string koji se dobija nakon submita, vrednosti enumeracije razdvojene sa zarezom

  constructor(private articleService: ArticleService, private repositoryService: RepositoryService,
              private router: Router, private route: ActivatedRoute) {

    this.user = JSON.parse(localStorage.getItem('sessionUserName'));
    this.role = localStorage.getItem('sessionUserRole');

    const processInstanceId = this.route.snapshot.params.processInstanceId ;
    this.processInstance = processInstanceId;

    alert(this.processInstance);
    alert(this.user);
    alert(this.role);

    const x = this.repositoryService.getMyNextTask(this.processInstance, this.user);
    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach((field) => {
          if (field.type.name === 'enum') {
            this.enumList = [];
            this.enumValues = Object.keys(field.type.values);
            this.labels.push(field.label);
            for (const value of this.enumValues) {
              this.enumList.push({ item_id: value, item_text: value });
            }
            this.multiselect.push(field.properties[Object.keys(field.properties)[0]]);
            this.dropdownList.push(this.enumList);
            this.enumerations.push(this.enumValues);
            this.names.push(field.id);
          }
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

    const x = this.articleService.postArticleCoauthorInfo(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        alert('nesto');
      },
      err => {
        alert(err.error);
      }
    );

  }

}
