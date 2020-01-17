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
    this.backendString = 'Homepage';
  }

}
