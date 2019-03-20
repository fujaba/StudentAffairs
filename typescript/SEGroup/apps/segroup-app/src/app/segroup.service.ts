import { Injectable } from '@angular/core';
import { SEGroupBuilder } from '../../../../libs/segroup-model/src';
import SEClass from 'libs/segroup-model/src/lib/SEClass';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SegroupService {
  private _gb: SEGroupBuilder = new SEGroupBuilder();
  private _currentSEClass: SEClass = undefined;
  private static httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    console.log("segroup service init " + new Date().getTime());
  }

  async uploadAction () {
    console.log('uploading...\n' + yaml);
    const yaml = this.gb.getEventSource().encodeYaml();
    const body: any = { yaml: `${yaml}` };
    try {
      await this.http.post('http://localhost:3333/api/put?caller=Albert', body, {headers: {'Content-Type': 'application/json'}}).toPromise();
    } catch (error) {
      console.log('Error? ' + JSON.stringify(error));      
    }
  }

  async downloadAction() {
    console.log('downloading...');
  
    const data: any = await this.http.get('http://localhost:3333/api/get?since=0&caller=Albert').toPromise();

    const yaml2 = data.yaml;
    console.log(yaml2);
    this.gb.applyEvents(yaml2);
    this.router.navigate(['/se-classes']);
  }

  async syncAction() {
    await this.uploadAction();
    await this.downloadAction();
  }



  get gb() {
    return this._gb;
  }

  get currentSEClass() {
    return this._currentSEClass;
  }

  set currentSEClass(value: SEClass) {
    this._currentSEClass = value;
  }
}
