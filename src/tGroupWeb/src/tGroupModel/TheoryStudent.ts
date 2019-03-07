import Presentation from "./Presentation";

import TheoryGroup from "./TheoryGroup";

  export default class TheoryStudent  
{
    public studentId: string ;

    public name: string ;

    public ta_4: string ;

    constructor() {
      this.studentId = '';
      this.name = '';
      this.ta_4 = '';
      this._group = null;
      this._presentations = [];

    }

    private _group: TheoryGroup;

    get group(): TheoryGroup {
      return this._group;
    }

    set group(value: TheoryGroup) {
      if (this._group !== value) {
        const oldValue: TheoryGroup = this._group;
        if (this._group) {
          this._group = null;
          oldValue.withoutStudents(this);
        }
        this._group = value;
        if (value) {
          value.withStudents(this);
        }
      }
    }



    private _presentations: Presentation[];

    get presentations(): Presentation[] {
      return this._presentations;
    }

    public withPresentations(...value: any[]): TheoryStudent {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withPresentations(item);
        } else if (item instanceof Presentation) {
          if (!this._presentations.includes(item)) {
            this._presentations.push(item);
            (item as Presentation).student = this;
          }
        }
      }

      return this;
    }



    public withoutPresentations(...value: any[]): TheoryStudent {
      if (this._presentations === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutPresentations(...item);
        } else if (item instanceof Presentation) {
          if (this._presentations.includes(item)) {
            this._presentations.splice(this._presentations.indexOf(item, 0), 1);
            (item as Presentation).student = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.group = null;
      this.withoutPresentations(this._presentations);

    }


}