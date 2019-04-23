import SEStudent from "./SEStudent";

import Solution from "./Solution";

import SEClass from "./SEClass";

  export default class Achievement  
{
    public grade: string ;

    public officeStatus: string ;

    constructor() {
      this.grade = '';
      this.gitUrl = '';
      this.officeStatus = '';
      this._seClass = null;
      this._student = null;
      this._solutions = [];

    }

    private _seClass: SEClass;

    get seClass(): SEClass {
      return this._seClass;
    }

    set seClass(value: SEClass) {
      if (this._seClass !== value) {
        const oldValue: SEClass = this._seClass;
        if (this._seClass) {
          this._seClass = null;
          oldValue.withoutParticipations(this);
        }
        this._seClass = value;
        if (value) {
          value.withParticipations(this);
        }
      }
    }



    private _student: SEStudent;

    get student(): SEStudent {
      return this._student;
    }

    set student(value: SEStudent) {
      if (this._student !== value) {
        const oldValue: SEStudent = this._student;
        if (this._student) {
          this._student = null;
          oldValue.withoutAchievements(this);
        }
        this._student = value;
        if (value) {
          value.withAchievements(this);
        }
      }
    }



    private _solutions: Solution[];

    get solutions(): Solution[] {
      return this._solutions;
    }

    public withSolutions(...value: any[]): Achievement {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withSolutions(item);
        } else if (item instanceof Solution) {
          if (!this._solutions.includes(item)) {
            this._solutions.push(item);
            (item as Solution).achievement = this;
          }
        }
      }

      return this;
    }



    public withoutSolutions(...value: any[]): Achievement {
      if (this._solutions === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutSolutions(...item);
        } else if (item instanceof Solution) {
          if (this._solutions.includes(item)) {
            this._solutions.splice(this._solutions.indexOf(item, 0), 1);
            (item as Solution).achievement = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.seClass = null;
      this.student = null;
      this.withoutSolutions(this._solutions);

    }


    public gitUrl: string ;

}