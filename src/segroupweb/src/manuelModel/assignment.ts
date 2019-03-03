import SEClass from "./SEClass";
import Solution from "./Solution";

export default class Assignment  
{
  public task: string ;
  public points: double ;
  private seClass: SEClass;
  private solutions: Solution[];
  
  constructor() {
      this.task = '';
      this.points = 0;
      this._seClass = null;
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
          oldValue.withoutAssignments(this);
        }
        this._seClass = value;
        if (value) {
          value.withAssignments(this);
        }
      }
    }



    private _solutions: Solution[];

    get solutions(): Solution[] {
      return this._solutions;
    }

    public withSolutions(...value: any[]): Assignment {
      if (!value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withSolutions(item);
        } else if (item instanceof Solution) {
          if (!this._solutions.includes(item)) {
            this._solutions.push(item);
            (item as Solution).assignment = this;
          }
        }
      }

      return this;
    }



    public withoutSolutions(...value: any[]): Assignment {
      if (this._solutions === [] || !value) return this;

      for (const item of value) {
        if (!item) continue;
        if (item instanceof Array) {
          this.withoutSolutions(...item);
        } else if (item instanceof Solution) {
          if (this._solutions.includes(item)) {
            this._solutions.splice(this._solutions.indexOf(item, 0), 1);
            (item as Solution).assignment = null;
          }
        }
      }
      return this;
    }


    public removeYou() {
      this.seClass = null;
      this.withoutSolutions(this._solutions);

    }

}