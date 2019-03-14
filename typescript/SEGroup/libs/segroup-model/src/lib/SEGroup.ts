import SEStudent from "./SEStudent";

import SEClass from "./SEClass";

  export default class SEGroup  
{
    public head: string ;

    constructor() {
      this.head = '';
      this._classes = [];
      this._students = [];

    }

    private _classes: SEClass[];

    get classes(): SEClass[] {
      return this._classes;
    }

    public withClasses(...value: any[]): SEGroup {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withClasses(item);
        } else if (item instanceof SEClass) {
          if (!this._classes.includes(item)) {
            this._classes.push(item);
            (item as SEClass).group = this;
          }
        }
      }

      return this;
    }



    public withoutClasses(...value: any[]): SEGroup {
      if (this._classes === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutClasses(...item);
        } else if (item instanceof SEClass) {
          if (this._classes.includes(item)) {
            this._classes.splice(this._classes.indexOf(item, 0), 1);
            (item as SEClass).group = null;
          }
        }
      }
      return this;
    }


    private _students: SEStudent[];

    get students(): SEStudent[] {
      return this._students;
    }

    public withStudents(...value: any[]): SEGroup {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withStudents(item);
        } else if (item instanceof SEStudent) {
          if (!this._students.includes(item)) {
            this._students.push(item);
            (item as SEStudent).group = this;
          }
        }
      }

      return this;
    }



    public withoutStudents(...value: any[]): SEGroup {
      if (this._students === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutStudents(...item);
        } else if (item instanceof SEStudent) {
          if (this._students.includes(item)) {
            this._students.splice(this._students.indexOf(item, 0), 1);
            (item as SEStudent).group = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.withoutClasses(this._classes);
      this.withoutStudents(this._students);

    }


}