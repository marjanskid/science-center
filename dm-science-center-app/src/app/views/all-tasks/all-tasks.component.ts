import { Component, OnInit } from '@angular/core';
import { RepositoryService } from 'src/app/services/repository/repository.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-tasks',
  templateUrl: './all-tasks.component.html',
  styleUrls: ['./all-tasks.component.css']
})
export class AllTasksComponent implements OnInit {

  private username;
  private tasks = [];

  constructor(private repositoryService: RepositoryService, private router: Router) {

    this.username = JSON.parse(localStorage.getItem('sessionUserName'));
    const x = this.repositoryService.getAllMyTasks(this.username);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log('Error occured');
      }
    );
  }

  ngOnInit() {
  }

  complete(taskId) {
    console.log(taskId);
    this.router.navigate(['/task-details/' + taskId]);
  }

}
