import { Injectable } from '@angular/core';
import { SEGroupBuilder } from '../../../../libs/segroup-model/src';
import SEClass from 'libs/segroup-model/src/lib/SEClass';

@Injectable({
  providedIn: 'root'
})
export class SegroupService {
  private _gb: SEGroupBuilder = new SEGroupBuilder();
  private _currentSEClass: SEClass  = undefined;
  
  constructor() {
    // console.log("segroup service init " + new Date().getTime());
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
