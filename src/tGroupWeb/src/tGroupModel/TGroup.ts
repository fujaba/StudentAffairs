import TStudent from "./TStudent";

  export default class TGroup  
{
    public name: string ;

    constructor() {
      this.name = '';
      this._students = [];

    }

    private _students: TStudent[];

    get students(): TStudent[] {
      return this._students;
    }

    public withStudents(...value: any[]): TGroup {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withStudents(item);
        } else if (item instanceof TStudent) {
          if (!this._students.includes(item)) {
            this._students.push(item);
            (item as TStudent).tGroup = this;
          }
        }
      }

      return this;
    }



    public withoutStudents(...value: any[]): TGroup {
      if (this._students === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutStudents(...item);
        } else if (item instanceof TStudent) {
          if (this._students.includes(item)) {
            this._students.splice(this._students.indexOf(item, 0), 1);
            (item as TStudent).tGroup = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.withoutStudents(this._students);

    }


}