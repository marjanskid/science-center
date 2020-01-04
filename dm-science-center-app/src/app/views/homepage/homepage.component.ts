import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HomepageService } from 'src/app/services/homepage/homepage.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  title = 'dm-science-center-app';
  backendString: string;

  constructor(private route: ActivatedRoute, private router: Router, private homepageService: HomepageService) {}

  ngOnInit() {
    this.backendString = 'pera';
  }

  testMethod() {
    console.log('kurcina iz testMetode homepage-a');
    this.homepageService.testMethod('ANGULAR FRONT').subscribe(
      data => {
        console.log(data);
        alert(data);
        const list = data;
        this.backendString = list[0];
      },
      error => {
        alert('error kurcina');
        console.log('kurcina');
        console.log(error);
        console.log('kurcina');
      }
    );
  }

}
