import Seminar from "./Seminar";

import TheoryStudent from "./TheoryStudent";

  export default class Presentation  
{
    public slides: number ;

    public scholarship: number ;

    public content: string ;

    public total: number ;

    public grade: string ;

    public officeStatus: string ;

    constructor() {
      this.slides = 0;
      this.scholarship = 0;
      this.content = '';
      this.total = 0;
      this.grade = '';
      this.officeStatus = '';
      this._student = null;
      this._seminar = null;

    }

    private _student: TheoryStudent;

    get student(): TheoryStudent {
      return this._student;
    }

    set student(value: TheoryStudent) {
      if (this._student !== value) {
        const oldValue: TheoryStudent = this._student;
        if (this._student) {
          this._student = null;
          oldValue.withoutPresentations(this);
        }
        this._student = value;
        if (value) {
          value.withPresentations(this);
        }
      }
    }



    private _seminar: Seminar;

    get seminar(): Seminar {
      return this._seminar;
    }

    set seminar(value: Seminar) {
      if (this._seminar !== value) {
        const oldValue: Seminar = this._seminar;
        if (this._seminar) {
          this._seminar = null;
          oldValue.withoutPresentations(this);
        }
        this._seminar = value;
        if (value) {
          value.withPresentations(this);
        }
      }
    }



    public removeYou() {
      this.student = null;
      this.seminar = null;

    }


}