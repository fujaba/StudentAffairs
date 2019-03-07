import Presentation from "./Presentation";

import TheoryGroup from "./TheoryGroup";

  export default class Seminar  
{
    public topic: string ;

    public term: string ;

    constructor() {
      this.topic = '';
      this.term = '';
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
          oldValue.withoutSeminars(this);
        }
        this._group = value;
        if (value) {
          value.withSeminars(this);
        }
      }
    }



    private _presentations: Presentation[];

    get presentations(): Presentation[] {
      return this._presentations;
    }

    public withPresentations(...value: any[]): Seminar {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withPresentations(item);
        } else if (item instanceof Presentation) {
          if (!this._presentations.includes(item)) {
            this._presentations.push(item);
            (item as Presentation).seminar = this;
          }
        }
      }

      return this;
    }



    public withoutPresentations(...value: any[]): Seminar {
      if (this._presentations === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutPresentations(...item);
        } else if (item instanceof Presentation) {
          if (this._presentations.includes(item)) {
            this._presentations.splice(this._presentations.indexOf(item, 0), 1);
            (item as Presentation).seminar = null;
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