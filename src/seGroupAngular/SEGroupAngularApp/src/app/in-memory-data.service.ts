
import { InMemoryDbService } from 'angular-in-memory-web-api';
import SEClass  from './SEGroupModel/SEClass';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const classList = [
      {id: 'PM', topic: 'PM', term: '2018-10' },
      {id: 'Compilers', topic: 'Compilers', term: '2018-10' },
      {id: 'IoT', topic: 'IoT', term: '2018-10' },
      {id: 'SE1', topic: 'SE1', term: '2019-04' },
      {id: 'Web Engineering', topic: 'Web Engineering', term: '2019-04' },
      {id: 'Design Pattern', topic: 'Design Pattern', term: '2019-04' },
      {id: 'SE1', topic: 'SE1', term: '2019-04' },
    ];
    return {classList};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genTopic(classList: SEClass[]): string {
    return '_' + classList.length;
  }
}