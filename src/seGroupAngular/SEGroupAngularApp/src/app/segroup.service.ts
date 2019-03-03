
import SEClass from "./SEGroupModel/SEClass";
import { MessageService } from './message.service';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import { Injectable } from '@angular/core';
import { Observable, of } from "rxjs";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};



@Injectable({
  providedIn: 'root'
})
export class SegroupService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) 
  { }

  private classListUrl = 'api/classList';  

  getClassList(): Observable<SEClass[]> {
    this.messageService.add("SEGroupService: trying to fetch class list");
    return this.http.get<SEClass[]>(this.classListUrl)
    .pipe(
      tap(_ => this.log('got class list')),
      catchError(this.handleError('getClassList', []))
    );; 
  }

  getSEClass(topic: string): Observable<SEClass> {
    this.messageService.add(`SEGroupService: trying to fetch seclass topic=${topic}`);
    const url = `${this.classListUrl}/${topic}`;
    return this.http.get<SEClass>(url)
      .pipe(
        tap(_ => this.log(`got seclass topic=${topic}`)),
        catchError(this.handleError<SEClass>(`getSEClass topic=${topic}`))
      );
  }


  updateSEClass(theClass: SEClass): Observable<any> {
    theClass['id'] = theClass.topic;
    return this.http.put(this.classListUrl, theClass, httpOptions)
      .pipe(
        tap(_ => this.log(`update SEClass topic=${theClass.topic}`)),
        catchError(this.handleError<SEClass>(`updateSEClass topic=${theClass.topic}`))
      );
  }


  /** POST: add a new hero to the server */
  addSEClass (newClass: SEClass): Observable<SEClass> {
    newClass['id'] = newClass.topic;
    return this.http.post<SEClass>(this.classListUrl, newClass, httpOptions).pipe(
      tap((newClass: SEClass) => this.log(`added class  with topic=${newClass.topic}`)),
      catchError(this.handleError<SEClass>('addSEClass'))
    );
  }


  /** DELETE: delete the hero from the server */
  deleteSEClass (oldClass: SEClass): Observable<SEClass> {
    const id = oldClass.topic;
    const url = `${this.classListUrl}/${id}`;
  
    return this.http.delete<SEClass>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted seclass topic=${id}`)),
      catchError(this.handleError<SEClass>('deleteSEClass'))
    );
  }



  /* GET heroes whose name contains search term */
  searchClassList(term: string): Observable<SEClass[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<SEClass[]>(`${this.classListUrl}/?topic=${term}`).pipe(
      tap(_ => this.log(`found seclasses matching "${term}"`)),
      catchError(this.handleError<SEClass[]>('searchClassList', []))
    );
  }



  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
   
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
   
      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);
   
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }



  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`SEGroupService: ${message}`);
  }
}
