import { Component, OnInit } from '@angular/core';
import { SegroupService } from './segroup.service';

@Component({
  selector: 'SEGroup-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'segroup-app';

  serverResult = {'yaml': '- text: loading ...'};

  constructor(
    // private http: HttpClient,
    private seGroupservice: SegroupService) { }

  public ngOnInit() { }

  uploadAction() {
    this.seGroupservice.uploadAction();
  }

  downloadAction() {
    this.seGroupservice.downloadAction();
  }
  syncAction() {
    this.seGroupservice.syncAction();
  }
}
