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
    console.log('constructor');
    this.allItems = new Array<Item>();
    this.initialize();
   }

  ngOnInit() {
    console.log('oninit');
    this.getAllItems();
  }

  getAllItems() {
    this.itemService.getAllItems().subscribe(
      data => {
        console.log(data);
        this.allItems = data;
      },
      error => {
        console.log('pas mater');
        console.log(error);
        console.log('pas mater');
      }
    );
  }

  initialize() {
    this.allItems.push(new Item(12, 'magazin1'));
    this.allItems.push(new Item(24, 'Kobe The Black Mamba'));
  }

}
