import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Item } from 'src/app/dtos/Item/item';
import { ItemService } from 'src/app/services/item/item.service';
import { __values } from 'tslib';

@Component({
  selector: 'app-all-items',
  templateUrl: './all-items.component.html',
  styleUrls: ['./all-items.component.css']
})
export class AllItemsComponent implements OnInit {

  allItems: any;

  constructor(private route: ActivatedRoute, private router: Router, private itemService: ItemService) {
    this.getAllItems();
  }

  ngOnInit() { }

  getAllItems() {
    this.itemService.getAllItems().subscribe(
      data => {
        this.allItems = data;
      },
      error => {
        console.log("ERROR: " + error);
      }
    );
  }

}
