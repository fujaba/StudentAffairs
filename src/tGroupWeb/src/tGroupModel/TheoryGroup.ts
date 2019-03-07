import TheoryStudent from "./TheoryStudent";

import Seminar from "./Seminar";

  export default class TheoryGroup  
{
    public head: string ;

    constructor() {
      this.head = '';
      this._seminars = [];
      this._students = [];

    }

    private _seminars: Seminar[];

    get seminars(): Seminar[] {
      return this._seminars;
    }

    public withSeminars(...value: any[]): TheoryGroup {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withSeminars(item);
        } else if (item instanceof Seminar) {
          if (!this._seminars.includes(item)) {
            this._seminars.push(item);
            (item as Seminar).group = this;
          }
        }
      }

      return this;
    }



    public withoutSeminars(...value: any[]): TheoryGroup {
      if (this._seminars === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutSeminars(...item);
        } else if (item instanceof Seminar) {
          if (this._seminars.includes(item)) {
            this._seminars.splice(this._seminars.indexOf(item, 0), 1);
            (item as Seminar).group = null;
          }
        }
      }
      return this;
    }


    private _students: TheoryStudent[];

    get students(): TheoryStudent[] {
      return this._students;
    }

    public withStudents(...value: any[]): TheoryGroup {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withStudents(item);
        } else if (item instanceof TheoryStudent) {
          if (!this._students.includes(item)) {
            this._students.push(item);
            (item as TheoryStudent).group = this;
          }
        }
      }

      return this;
    }



    public withoutStudents(...value: any[]): TheoryGroup {
      if (this._students === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutStudents(...item);
        } else if (item instanceof TheoryStudent) {
          if (this._students.includes(item)) {
            this._students.splice(this._students.indexOf(item, 0), 1);
            (item as TheoryStudent).group = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.withoutSeminars(this._seminars);
      this.withoutStudents(this._students);

    }


}